package org.marketcetera.core.module;

import org.marketcetera.core.util.log.I18NBoundMessage;

/* $License$ */
/**
 * Indicates data flow errors.
 *
 * @version $Id: DataFlowException.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
public class DataFlowException extends ModuleException {
    /**
     * Creates an instance.
     *
     * @param inCause the underlying cause
     */
    public DataFlowException(Throwable inCause) {
        super(inCause);
    }

    /**
     * Creates an instance.
     *
     * @param inMessage the error message.
     */
    protected DataFlowException(I18NBoundMessage inMessage) {
        super(inMessage);
    }

    /**
     * Creates an instance.
     *
     * @param inCause the underlying cause
     * @param inMessage the error message
     */
    protected DataFlowException(Throwable inCause, I18NBoundMessage inMessage) {
        super(inCause, inMessage);
    }

    private static final long serialVersionUID = -7224891693493920871L;
}