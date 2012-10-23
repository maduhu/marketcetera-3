package org.marketcetera.api.symbolresolver;

import org.marketcetera.core.util.log.I18NLoggerProxy;
import org.marketcetera.core.util.log.I18NMessage1P;
import org.marketcetera.core.util.log.I18NMessageProvider;

/* $License$ */

/**
 * Provides messages for the symbol resolver package.
 *
 * @version $Id$
 * @since $Release$
 */
public interface Messages
{
    /**
     * The message provider.
     */
    static final I18NMessageProvider PROVIDER = new I18NMessageProvider("symbolresolver", //$NON-NLS-1$
                                                                        Messages.class.getClassLoader());
    /**
     * The logger.
     */
    static final I18NLoggerProxy LOGGER = new I18NLoggerProxy(PROVIDER);
    public static final I18NMessage1P UNABLE_TO_RESOLVE_SYMBOL = new I18NMessage1P(LOGGER,
                                                                                   "unable_to_resolve_symbol"); //$NON-NLS-1$
}