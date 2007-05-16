package org.marketcetera.photon.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.marketcetera.core.MSymbol;
import org.marketcetera.core.MarketceteraException;
import org.marketcetera.photon.marketdata.IMarketDataListCallback;
import org.marketcetera.photon.marketdata.MarketDataFeedService;
import org.marketcetera.photon.marketdata.MarketDataUtils;
import org.marketcetera.photon.marketdata.OptionContractData;
import org.marketcetera.photon.marketdata.OptionMarketDataUtils;
import org.marketcetera.photon.parser.OpenCloseImage;
import org.marketcetera.photon.parser.OptionCFICodeImage;
import org.marketcetera.photon.parser.OrderCapacityImage;
import org.marketcetera.photon.parser.PriceImage;
import org.marketcetera.photon.ui.OptionBookComposite;
import org.marketcetera.photon.ui.validation.IToggledValidator;
import org.marketcetera.photon.ui.validation.StringRequiredValidator;
import org.marketcetera.photon.ui.validation.fix.DateToStringCustomConverter;
import org.marketcetera.photon.ui.validation.fix.EnumStringConverterBuilder;
import org.marketcetera.photon.ui.validation.fix.FIXObservables;
import org.marketcetera.photon.ui.validation.fix.PriceConverterBuilder;
import org.marketcetera.photon.ui.validation.fix.StringToDateCustomConverter;

import quickfix.DataDictionary;
import quickfix.Message;
import quickfix.field.CFICode;
import quickfix.field.MaturityDate;
import quickfix.field.OpenClose;
import quickfix.field.OrdType;
import quickfix.field.OrderCapacity;
import quickfix.field.StrikePrice;
import quickfix.field.Symbol;
import quickfix.field.UnderlyingSymbol;

public class OptionOrderTicketControllerHelper extends
		OrderTicketControllerHelper {
	private IOptionOrderTicket optionTicket;

	private EnumStringConverterBuilder<Character> orderCapacityConverterBuilder;

	private EnumStringConverterBuilder<Character> openCloseConverterBuilder;

	private EnumStringConverterBuilder<String> putOrCallConverterBuilder;

	private PriceConverterBuilder strikeConverterBuilder;

	private BindingHelper bindingHelper;

	/**
	 * Map from option root symbol to cache entry.
	 */
	private HashMap<MSymbol, OptionContractCacheEntry> optionContractCache = new HashMap<MSymbol, OptionContractCacheEntry>();

	private MSymbol lastOptionRoot;

	public OptionOrderTicketControllerHelper(IOptionOrderTicket ticket) {
		super(ticket);
		this.optionTicket = ticket;

		bindingHelper = new BindingHelper();
	}

	@Override
	protected void initBuilders() {
		super.initBuilders();
		initOrderCapacityConverterBuilder();
		initOpenCloseConverterBuilder();
		initPutOrCallConverterBuilder();
		initStrikeConverterBuilder();
	}

	@Override
	protected void initListeners() {
		super.initListeners();

		addUpdateOptionSymbolModifyListener(optionTicket.getExpireYearCombo());
		addUpdateOptionSymbolModifyListener(optionTicket.getExpireMonthCombo());
		addUpdateOptionSymbolModifyListener(optionTicket
				.getStrikePriceControl());
		addUpdateOptionSymbolModifyListener(optionTicket.getPutOrCallCombo());
	}

	@Override
	public void clear() {
		super.clear();
		lastOptionRoot = null;
	}

	private void addUpdateOptionSymbolModifyListener(Control targetControl) {
		targetControl.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				conditionallyUpdateOptionContractSymbol();
			}
		});
	}

	@Override
	protected void listenMarketDataAdditional(MarketDataFeedService service,
			final String optionRootStr) throws MarketceteraException {

		MSymbol optionRoot = new MSymbol(optionRootStr);
		UnderlyingSymbolInfoComposite symbolComposite = getUnderlyingSymbolInfoComposite();
		symbolComposite.addUnderlyingSymbolInfo(optionRootStr);

		if (!optionContractCache.containsKey(optionRoot)) {
			requestOptionSecurityList(service, optionRoot);
		} else {
			conditionallyUpdateInputControls(optionRoot);
		}
	}

	private void requestOptionSecurityList(MarketDataFeedService service,
			final MSymbol optionRoot) {
		IMarketDataListCallback callback = new IMarketDataListCallback() {
			public void onMarketDataFailure(MSymbol symbol) {
				// Restore the full expiration choices.
				updateComboChoicesFromDefaults();
			}

			public void onMarketDataListAvailable(
					List<Message> derivativeSecurityList) {
				List<OptionContractData> optionContracts = OptionMarketDataUtils
						.getOptionExpirationMarketData(optionRoot
								.getBaseSymbol(), derivativeSecurityList);
				if (optionContracts == null || optionContracts.isEmpty()) {
					updateComboChoicesFromDefaults();
				} else {
					OptionContractCacheEntry cacheEntry = new OptionContractCacheEntry(
							optionContracts);
					optionContractCache.put(optionRoot, cacheEntry);

					conditionallyUpdateInputControls(optionRoot);
				}
			}
		};

		Message query = OptionMarketDataUtils.newOptionRootQuery(optionRoot,
				false);
		MarketDataUtils.asyncMarketDataQuery(optionRoot, query, service
				.getMarketDataFeed(), callback);
	}

	private void conditionallyUpdateInputControls(MSymbol optionRoot) {
		if (lastOptionRoot == null || !lastOptionRoot.equals(optionRoot)) {
			lastOptionRoot = optionRoot;
			OptionContractCacheEntry cacheEntry = optionContractCache
					.get(optionRoot);
			if (cacheEntry != null) {
				updateComboChoices(cacheEntry);
				updateOptionContractSymbol(cacheEntry);
			}
		}
	}

	private void conditionallyUpdateOptionContractSymbol() {
		String symbolText = optionTicket.getSymbolText().getText();
		boolean attemptedUpdate = false;
		if (symbolText != null) {
			MSymbol optionRoot = new MSymbol(symbolText);
			OptionContractCacheEntry cacheEntry = optionContractCache
					.get(optionRoot);
			if (cacheEntry != null) {
				attemptedUpdate = true;
				updateOptionContractSymbol(cacheEntry);
			}
		}
		if (!attemptedUpdate) {
			clearOptionSymbolControl();
		}
	}

	private boolean updateOptionContractSymbol(
			OptionContractCacheEntry cacheEntry) {
		String expirationYear = optionTicket.getExpireYearCombo().getText();
		String expirationMonth = optionTicket.getExpireMonthCombo().getText();
		String strikePrice = optionTicket.getStrikePriceControl().getText();
		boolean putWhenTrue = optionTicket.isPut();

		boolean textWasSet = false;
		OptionContractData optionContract = cacheEntry.getOptionContractData(
				expirationYear, expirationMonth, strikePrice, putWhenTrue);
		if (optionContract != null) {
			MSymbol optionContractSymbol = optionContract.getOptionSymbol();
			if (optionContractSymbol != null) {
				String fullSymbol = optionContractSymbol.getFullSymbol();
				optionTicket.getOptionSymbolControl().setText(fullSymbol);
				textWasSet = true;
			}
		}
		if (!textWasSet) {
			clearOptionSymbolControl();
		}
		return textWasSet;
	}

	private void clearOptionSymbolControl() {
		optionTicket.getOptionSymbolControl().setText("");
	}

	private void updateComboChoices(OptionContractCacheEntry cacheEntry) {
		updateComboChoices(optionTicket.getExpireMonthCombo(), cacheEntry
				.getExpirationMonthsForUI());
		updateComboChoices(optionTicket.getExpireYearCombo(), cacheEntry
				.getExpirationYearsForUI());
		updateComboChoices(optionTicket.getStrikePriceControl(), cacheEntry
				.getStrikePricesForUI());
	}

	private void updateComboChoicesFromDefaults() {
		OptionDateHelper dateHelper = new OptionDateHelper();
		List<String> months = dateHelper.createDefaultMonths();
		updateComboChoices(optionTicket.getExpireMonthCombo(), months);
		List<String> years = dateHelper.createDefaultYears();
		updateComboChoices(optionTicket.getExpireYearCombo(), years);
		// todo: What should the defaults be for strike price?
		List<String> strikePrices = new ArrayList<String>();
		updateComboChoices(optionTicket.getStrikePriceControl(), strikePrices);
	}

	private void updateComboChoices(Combo combo, Collection<String> choices) {
		combo.removeAll();
		boolean first = true;
		for (String choice : choices) {
			if (choice != null) {
				combo.add(choice);
				if (first) {
					combo.setText(choice);
					first = false;
				}
			}
		}
		if( combo.isFocusControl() ) {
			combo.setSelection(new Point(0,3));
		}
	}

	@Override
	protected int getSymbolFIXField() {
		// Make the Symbol input control be the option root.
		return UnderlyingSymbol.FIELD;
	}

	@Override
	protected void bindImpl(Message message, boolean enableValidators) {
		super.bindImpl(message, enableValidators);

		Realm realm = getTargetRealm();
		DataBindingContext dataBindingContext = getDataBindingContext();
		DataDictionary dictionary = getDictionary();

		/**
		 * Note that the MaturityDate and StrikePrice in the order are not used
		 * by the OMS. They are used here to have a place for the data binding
		 * to store the data. The code part of the option contract symbol
		 * represents that data.
		 */

		final int swtEvent = SWT.Modify;
		// ExpireDate Month
		{
			Control whichControl = optionTicket.getExpireMonthCombo();
			IToggledValidator validator = new StringRequiredValidator();
			validator.setEnabled(enableValidators);
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), FIXObservables
					.observeMonthDateValue(realm, message, MaturityDate.FIELD,
							dictionary), new UpdateValueStrategy()
					.setAfterGetValidator(validator).setConverter(
							new StringToDateCustomConverter(
									DateToStringCustomConverter.MONTH_FORMAT)),
					new UpdateValueStrategy()
							.setConverter(new DateToStringCustomConverter(
									DateToStringCustomConverter.MONTH_FORMAT)));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}
		// ExpireDate Year
		{
			Control whichControl = optionTicket.getExpireYearCombo();
			IToggledValidator validator = new StringRequiredValidator();
			validator.setEnabled(enableValidators);
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), FIXObservables
					.observeMonthDateValue(realm, message, MaturityDate.FIELD,
							dictionary), new UpdateValueStrategy()
					.setAfterGetValidator(validator).setConverter(
							new StringToDateCustomConverter(
									DateToStringCustomConverter.YEAR_FORMAT)),
					new UpdateValueStrategy()
							.setConverter(new DateToStringCustomConverter(
									DateToStringCustomConverter.YEAR_FORMAT)));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}

		// StrikePrice
		{
			Control whichControl = optionTicket.getStrikePriceControl();
			IToggledValidator validator = strikeConverterBuilder
					.newTargetAfterGetValidator();
			validator.setEnabled(enableValidators);
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), FIXObservables.observeValue(
					realm, message, StrikePrice.FIELD, dictionary),
					bindingHelper.createToModelUpdateValueStrategy(
							strikeConverterBuilder, validator), bindingHelper
							.createToTargetUpdateValueStrategy(
									strikeConverterBuilder, validator));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}
		// OptionSymbol (the symbol for the actual option contract)
		{
			Control whichControl = optionTicket.getOptionSymbolControl();
			IToggledValidator validator = new StringRequiredValidator();
			// todo: This validator will never be enabled because the control
			// itself is disabled.
			validator.setEnabled(enableValidators);
			dataBindingContext.bindValue(SWTObservables.observeText(
					whichControl, swtEvent), FIXObservables.observeValue(realm,
					message, Symbol.FIELD, dictionary),
					new UpdateValueStrategy().setAfterGetValidator(validator),
					new UpdateValueStrategy());
			addControlStateListeners(whichControl, validator);
		}
		// PutOrCall (OptionCFICode)
		{
			Control whichControl = optionTicket.getPutOrCallCombo();
			IToggledValidator validator = putOrCallConverterBuilder
					.newTargetAfterGetValidator();
			validator.setEnabled(enableValidators);
			IObservableValue fixObservable = FIXObservables.observeValue(
					realm, message, CFICode.FIELD, dictionary);
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), fixObservable, bindingHelper
					.createToModelUpdateValueStrategy(
							putOrCallConverterBuilder, validator),
					bindingHelper.createToTargetUpdateValueStrategy(
							putOrCallConverterBuilder, validator));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}
		// OrderCapacity
		{
			Control whichControl = optionTicket.getOrderCapacityCombo();
			IToggledValidator validator = orderCapacityConverterBuilder
					.newTargetAfterGetValidator();
			validator.setEnabled(enableValidators);
			// The FIX field may need to be updated., See
			// http://trac.marketcetera.org/trac.fcgi/ticket/185
			final int orderCapacityFIXField = OrderCapacity.FIELD;
//			final int orderCapacityFIXField = CustomerOrFirm.FIELD;
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), FIXObservables.observeValue(
					realm, message, orderCapacityFIXField, dictionary),
					bindingHelper.createToModelUpdateValueStrategy(
							orderCapacityConverterBuilder, validator),
					bindingHelper.createToTargetUpdateValueStrategy(
							orderCapacityConverterBuilder, validator));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}
		// OpenClose
		{
			Control whichControl = optionTicket.getOpenCloseCombo();
			IToggledValidator validator = openCloseConverterBuilder
					.newTargetAfterGetValidator();
			validator.setEnabled(enableValidators);
			dataBindingContext.bindValue(SWTObservables
					.observeText(whichControl), FIXObservables.observeValue(
					realm, message, OpenClose.FIELD, dictionary), bindingHelper
					.createToModelUpdateValueStrategy(
							openCloseConverterBuilder, validator),
					bindingHelper.createToTargetUpdateValueStrategy(
							openCloseConverterBuilder, validator));
			addControlStateListeners(whichControl, validator);
			if (!enableValidators)
				addControlRequiringUserInput(whichControl);
		}
	}

	public void initOrderCapacityConverterBuilder() {
		orderCapacityConverterBuilder = new EnumStringConverterBuilder<Character>(
				Character.class);
		bindingHelper.initCharToImageConverterBuilder(
				orderCapacityConverterBuilder, OrderCapacityImage.values());
	}

	private void initOpenCloseConverterBuilder() {
		openCloseConverterBuilder = new EnumStringConverterBuilder<Character>(
				Character.class);
		bindingHelper.initCharToImageConverterBuilder(
				openCloseConverterBuilder, OpenCloseImage.values());
	}

	private void initPutOrCallConverterBuilder() {
		putOrCallConverterBuilder = new EnumStringConverterBuilder<String>(
				String.class);
		bindingHelper.initStringToImageConverterBuilder(putOrCallConverterBuilder,
				OptionCFICodeImage.values());
	}

	private void initStrikeConverterBuilder() {
		strikeConverterBuilder = new PriceConverterBuilder(getDictionary());
		// todo: Is this mapping correct for strike price?
		strikeConverterBuilder.addMapping(OrdType.MARKET, PriceImage.MKT
				.getImage());
	}

	@Override
	protected void onQuoteAdditional(final Message message) {
		super.onQuoteAdditional(message);
		
		Display theDisplay = Display.getDefault();		
		if (theDisplay.getThread() == Thread.currentThread()){
			underlyingSymbolOnQuote(message);
		} else {
			theDisplay.asyncExec(
				new Runnable(){
					public void run()
					{
						underlyingSymbolOnQuote(message);
					}
				}
			);
		}		
	}

	private void underlyingSymbolOnQuote(Message message) {
		UnderlyingSymbolInfoComposite symbolComposite = getUnderlyingSymbolInfoComposite();
		if (symbolComposite.matchUnderlyingSymbol(message)) {
			symbolComposite.onQuote(message);
		}
	}
	
	private UnderlyingSymbolInfoComposite getUnderlyingSymbolInfoComposite() {
		OptionBookComposite bookComposite = ((OptionBookComposite)optionTicket.getBookComposite());
		UnderlyingSymbolInfoComposite symbolComposite = bookComposite
				.getUnderlyingSymbolInfoComposite();
		return symbolComposite;
		
	}
}
