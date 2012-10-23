package org.marketcetera.core.module;

import org.marketcetera.core.util.log.I18NBoundMessage;

/* $License$ */
/**
 * Indicates errors when executing a module related JMX Operations.
 *
 * @version $Id: MXBeanOperationException.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
public class MXBeanOperationException extends ModuleException {
    /**
     * Creates an instance.
     *
     * @param inCause the underlying cause.
     * @param inMessage the error message
     */
    public MXBeanOperationException(Throwable inCause,
                                    I18NBoundMessage inMessage) {
        super(inCause, inMessage);
    }
    private static final long serialVersionUID = -2634287361311711770L;
}