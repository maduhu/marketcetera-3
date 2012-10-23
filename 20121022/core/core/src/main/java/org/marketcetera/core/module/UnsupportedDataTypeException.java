package org.marketcetera.core.module;

import org.marketcetera.core.util.log.I18NBoundMessage;

/* $License$ */
/**
 * Thrown when a module receives data that it
 * doesn't know how to handle.
 *
 * @version $Id: UnsupportedDataTypeException.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
public class UnsupportedDataTypeException extends ReceiveDataException {
    private static final long serialVersionUID = -1731701511521781335L;

    /**
     * Creates an instance.
     *
     * @param inMessage the error message.
     */
    public UnsupportedDataTypeException(I18NBoundMessage inMessage) {
        super(inMessage);
    }
}