package org.marketcetera.marketdata.marketcetera;

import static org.marketcetera.marketdata.marketcetera.Messages.PROVIDER_DESCRIPTION;

import org.marketcetera.core.CoreException;
import org.marketcetera.core.module.ModuleCreationException;
import org.marketcetera.core.module.ModuleFactory;
import org.marketcetera.core.module.ModuleURN;

/* $License$ */

/**
 * <code>ModuleFactory</code> implementation for the <code>MarketceteraFeed</code> market data provider.
 * <p>
 * The factory has the following characteristics.
 * <table>
 * <tr><th>Provider URN:</th><td><code>metc:mdata:marketcetera</code></td></tr>
 * <tr><th>Cardinality:</th><td>Singleton</td></tr>
 * <tr><th>Instance URN:</th><td><code>metc:mdata:marketcetera:single</code></td></tr>
 * <tr><th>Auto-Instantiated:</th><td>No</td></tr>
 * <tr><th>Auto-Started:</th><td>No</td></tr>
 * <tr><th>Instantiation Arguments:</th><td>None</td></tr>
 * <tr><th>Module Type:</th><td>{@link MarketceteraFeedModule}</td></tr>
 * </table>
 *
 * @version $Id: MarketceteraFeedModuleFactory.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
public class MarketceteraFeedModuleFactory
        extends ModuleFactory
{
    /**
     * Create a new MarketceteraFeedModuleFactory instance.
     */
    public MarketceteraFeedModuleFactory()
    {
        super(PROVIDER_URN,
              PROVIDER_DESCRIPTION,
              false,
              false);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.core.module.ModuleFactory#create(java.lang.Object[])
     */
    @Override
    public MarketceteraFeedModule create(Object... inParameters)
            throws ModuleCreationException
    {
        try {
            return new MarketceteraFeedModule();
        } catch (CoreException e) {
            throw new ModuleCreationException(e.getI18NBoundMessage());
        }
    }
    public static final String IDENTIFIER = "marketcetera";  //$NON-NLS-1$
    public static final ModuleURN PROVIDER_URN = new ModuleURN("metc:mdata:" + IDENTIFIER);  //$NON-NLS-1$
    public static final ModuleURN INSTANCE_URN = new ModuleURN(PROVIDER_URN,
                                                               "single");  //$NON-NLS-1$
}