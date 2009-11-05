package org.marketcetera.event.beans;

import java.io.Serializable;

import javax.annotation.concurrent.NotThreadSafe;

import org.marketcetera.event.Messages;
import org.marketcetera.event.OptionEvent;
import org.marketcetera.event.util.EventServices;
import org.marketcetera.options.ExpirationType;
import org.marketcetera.trade.Instrument;
import org.marketcetera.trade.Option;
import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * Stores the attributes necessary for {@link OptionEvent}.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
@NotThreadSafe
@ClassVersion("$Id$")
public final class OptionBean
        implements Serializable, Messages
{
    /**
     * Creates a shallow copy of the given <code>OptionBean</code>.
     *
     * @param inBean an <code>OptionBean</code> value
     * @return an <code>OptionBean</code> value
     */
    public static OptionBean copy(OptionBean inBean)
    {
        OptionBean newBean = new OptionBean();
        copyAttributes(inBean,
                       newBean);
        return newBean;
    }
    /**
     * Builds an <code>OptionBean</code> based on the values of
     * the given event.
     *
     * @param inOptionEvent an <code>OptionEvent</code> value
     * @return an <code>OptionBean</code> value
     */
    public static OptionBean getOptionBeanFromEvent(OptionEvent inOptionEvent)
    {
        OptionBean option = new OptionBean();
        option.setExpirationType(inOptionEvent.getExpirationType());
        option.setHasDeliverable(inOptionEvent.hasDeliverable());
        option.setInstrument(inOptionEvent.getInstrument());
        option.setMultiplier(inOptionEvent.getMultiplier());
        option.setUnderlyingInstrument(inOptionEvent.getUnderlyingInstrument());
        return option;
    }
    /**
     * Gets the instrument.
     *
     * @return an <code>Option</code> value
     */
    public Option getInstrument()
    {
        return (Option)instrument;
    }
    /**
     * Sets the instrument.
     *
     * @param inOption an <code>Option</code> value
     */
    public void setInstrument(Option inOption)
    {
        instrument = inOption;
    }
    /**
     * Get the underlyingInstrument value.
     *
     * @return an <code>Instrument</code> value
     */
    public Instrument getUnderlyingInstrument()
    {
        return underlyingInstrument;
    }
    /**
     * Sets the underlyingInstrument value.
     *
     * @param inUnderlyingInstrument an <code>Instrument</code> value
     */
    public void setUnderlyingInstrument(Instrument inUnderlyingInstrument)
    {
        underlyingInstrument = inUnderlyingInstrument;
    }
    /**
     * Get the expirationType value.
     *
     * @return an <code>ExpirationType</code> value
     */
    public ExpirationType getExpirationType()
    {
        return expirationType;
    }
    /**
     * Sets the expirationType value.
     *
     * @param inExpirationType an <code>ExpirationType</code> value
     */
    public void setExpirationType(ExpirationType inExpirationType)
    {
        expirationType = inExpirationType;
    }
    /**
     * Get the multiplier value.
     *
     * @return an <code>int</code> value
     */
    public int getMultiplier()
    {
        return multiplier;
    }
    /**
     * Sets the multiplier value.
     *
     * @param inMultiplier an <code>int</code> value
     */
    public void setMultiplier(int inMultiplier)
    {
        multiplier = inMultiplier;
    }
    /**
     * Get the hasDeliverable value.
     *
     * @return a <code>boolean</code> value
     */
    public boolean hasDeliverable()
    {
        return hasDeliverable;
    }
    /**
     * Sets the hasDeliverable value.
     *
     * @param inHasDeliverable a <code>boolean</code> value
     */
    public void setHasDeliverable(boolean inHasDeliverable)
    {
        hasDeliverable = inHasDeliverable;
    }
    /**
     * Performs validation of the attributes.
     *
     * @throws IllegalArgumentException if <code>Instrument</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>UnderlyingInstrument</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>ExpirationType</code> is <code>null</code>
     */
    public void validate()
    {
        if(instrument == null) {
            EventServices.error(VALIDATION_NULL_INSTRUMENT);
        }
        if(underlyingInstrument == null) {
            EventServices.error(VALIDATION_NULL_UNDERLYING_INSTRUMENT);
        }
        if(expirationType == null) {
            EventServices.error(VALIDATION_NULL_EXPIRATION_TYPE);
        }
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expirationType == null) ? 0 : expirationType.hashCode());
        result = prime * result + (hasDeliverable ? 1231 : 1237);
        result = prime * result + ((instrument == null) ? 0 : instrument.hashCode());
        result = prime * result + multiplier;
        result = prime * result + ((underlyingInstrument == null) ? 0 : underlyingInstrument.hashCode());
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OptionBean)) {
            return false;
        }
        OptionBean other = (OptionBean) obj;
        if (expirationType == null) {
            if (other.expirationType != null) {
                return false;
            }
        } else if (!expirationType.equals(other.expirationType)) {
            return false;
        }
        if (hasDeliverable != other.hasDeliverable) {
            return false;
        }
        if (instrument == null) {
            if (other.instrument != null) {
                return false;
            }
        } else if (!instrument.equals(other.instrument)) {
            return false;
        }
        if (multiplier != other.multiplier) {
            return false;
        }
        if (underlyingInstrument == null) {
            if (other.underlyingInstrument != null) {
                return false;
            }
        } else if (!underlyingInstrument.equals(other.underlyingInstrument)) {
            return false;
        }
        return true;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("expirationType=%s, hasDeliverable=%s, instrument=%s, multiplier=%s, underlyingInstrument=%s", //$NON-NLS-1$
                             expirationType,
                             hasDeliverable,
                             instrument,
                             multiplier,
                             underlyingInstrument);
    }
    /**
     * Copies all member attributes from the donor to the recipient.
     *
     * @param inDonor an <code>OptionBean</code> value
     * @param inRecipient an <code>OptionBean</code> value
     */
    protected static void copyAttributes(OptionBean inDonor,
                                         OptionBean inRecipient)
    {
        inRecipient.setExpirationType(inDonor.getExpirationType());
        inRecipient.setHasDeliverable(inDonor.hasDeliverable());
        inRecipient.setInstrument(inDonor.getInstrument());
        inRecipient.setMultiplier(inDonor.getMultiplier());
        inRecipient.setUnderlyingInstrument(inDonor.getUnderlyingInstrument());
    }
    /**
     * the underlying instrument for the option
     */
    private Instrument underlyingInstrument;
    /**
     * the expiration type of the option
     */
    private ExpirationType expirationType;
    /**
     * the multiplier of the option
     */
    private int multiplier;
    /**
     * indicates if the option includes deliverables
     */
    private boolean hasDeliverable;
    /**
     * the instrument of the option
     */
    private Instrument instrument;
    private final static long serialVersionUID = 1L;
}