package org.marketcetera.core.marketdata;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.marketcetera.core.LoggerConfiguration;

/* $License$ */

/**
 * Base class for market data provider <code>Module</code> tests that simulate data.
 *
 * @version $Id: SimulatedMarketDataModuleTestBase.java 82329 2012-04-10 16:28:13Z colin $
 * @since 1.0.0
 */
public abstract class SimulatedMarketDataModuleTestBase
        extends MarketDataModuleTestBase
{
    @BeforeClass
    public static void setupOnce()
    {
        LoggerConfiguration.logSetup();
        System.setProperty(AbstractMarketDataFeed.MARKETDATA_SIMULATION_KEY,
                           "true");
    }
    @AfterClass
    public static void teardownOnce()
    {
        System.setProperty(AbstractMarketDataFeed.MARKETDATA_SIMULATION_KEY,
                           "false");
    }
}