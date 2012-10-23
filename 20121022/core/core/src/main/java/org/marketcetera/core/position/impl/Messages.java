package org.marketcetera.core.position.impl;

import org.marketcetera.core.util.log.I18NLoggerProxy;
import org.marketcetera.core.util.log.I18NMessage1P;
import org.marketcetera.core.util.log.I18NMessageNP;
import org.marketcetera.core.util.log.I18NMessageProvider;

/* $License$ */

/**
 * Messages for position classes.
 * 
 * @version $Id: Messages.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.5.0
 */
public interface Messages {

    /**
     * The message provider
     */
    static final I18NMessageProvider PROVIDER = new I18NMessageProvider(
            "core_position"); //$NON-NLS-1$
    /**
     * The message logger.
     */
    static final I18NLoggerProxy LOGGER = new I18NLoggerProxy(PROVIDER);

    /**
     * The messages.
     */
    static final I18NMessageNP EXECUTION_REPORT_ADAPTER_TO_STRING = new I18NMessageNP(
            LOGGER, "execution_report_adapter.to_string"); //$NON-NLS-1$
    static final I18NMessage1P VALIDATION_MATCHER_INVALID_EXECUTION_REPORT = new I18NMessage1P(
            LOGGER, "validation_matcher.invalid_execution_report"); //$NON-NLS-1$
}