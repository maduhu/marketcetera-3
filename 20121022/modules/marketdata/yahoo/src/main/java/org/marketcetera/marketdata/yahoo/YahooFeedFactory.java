package org.marketcetera.marketdata.yahoo;

import org.marketcetera.core.CoreException;
import org.marketcetera.core.marketdata.AbstractMarketDataFeedFactory;

/* $License$ */

/**
 * Constructs {@link YahooFeed} objects.
 *
 * @version $Id: YahooFeedFactory.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.1.4
 */
public class YahooFeedFactory
        extends AbstractMarketDataFeedFactory<YahooFeed,YahooFeedCredentials>
{
    /* (non-Javadoc)
     * @see org.marketcetera.marketdata.IMarketDataFeedFactory#getMarketDataFeed()
     */
    @Override
    public synchronized YahooFeed getMarketDataFeed()
            throws CoreException
    {
        if(feed == null) {
            feed = new YahooFeed(getProviderName(),
                                 new YahooClientFactory() {
                                    @Override
                                    public YahooClient getClient(YahooFeedServices inFeedServices)
                                    {
                                        return new YahooClientImpl(inFeedServices);
                                    }
            });
        }
        return feed;
    }
    /* (non-Javadoc)
     * @see org.marketcetera.marketdata.IMarketDataFeedFactory#getProviderName()
     */
    @Override
    public String getProviderName()
    {
        return PROVIDER_NAME;
    }
    /**
     * feed instance
     */
    private static YahooFeed feed;
    /**
     * name of the yahoo provider
     */
    static final String PROVIDER_NAME = "yahoo"; //$NON-NLS-1$
}