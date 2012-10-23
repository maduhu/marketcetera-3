package org.marketcetera.marketdata.bogus;

import org.marketcetera.core.CoreException;
import org.marketcetera.core.NoMoreIDsException;
import org.marketcetera.core.marketdata.AbstractMarketDataFeedFactory;
import org.marketcetera.core.marketdata.FeedException;

/* $License$ */

/**
 * {@link BogusFeed} constructor factory.
 *
 * @version $Id: BogusFeedFactory.java 16063 2012-01-31 18:21:55Z colin $
 * @since 0.5.0
 */
public class BogusFeedFactory
    extends AbstractMarketDataFeedFactory<BogusFeed,BogusFeedCredentials> 
{
    private final static BogusFeedFactory sInstance = new BogusFeedFactory();
    public static BogusFeedFactory getInstance()
    {
        return sInstance;
    }
	public String getProviderName()
	{
		return "Marketcetera (Bogus)"; //$NON-NLS-1$
	}
    /* (non-Javadoc)
     * @see org.marketcetera.marketdata.IMarketDataFeedFactory#getMarketDataFeed()
     */
    @Override
    public BogusFeed getMarketDataFeed()
            throws CoreException
    {
        try {
            return BogusFeed.getInstance(getProviderName());
        } catch (NoMoreIDsException e) {
            throw new FeedException(e);
        }
    }
}