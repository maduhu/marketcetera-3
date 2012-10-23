package org.marketcetera.strategy;

import org.marketcetera.core.CoreException;
import org.marketcetera.core.util.log.I18NBoundMessage;
import org.marketcetera.core.util.misc.ClassVersion;

/* $License$ */

/**
 * An exception thrown during execution of a {@link Strategy}.
 *
 * @version $Id: StrategyException.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
public class StrategyException
        extends CoreException
{
    private static final long serialVersionUID = -2152924775246996522L;
    /**
     * Create a new StrategyException instance.
     *
     * @param inNested
     */
    public StrategyException(Throwable inNested)
    {
        super(inNested);
    }
    /**
     * Create a new StrategyException instance.
     *
     * @param inMessage
     */
    public StrategyException(I18NBoundMessage inMessage)
    {
        super(inMessage);
    }
    /**
     * Create a new StrategyException instance.
     *
     * @param inNested
     * @param inMessage
     */
    public StrategyException(Throwable inNested,
                             I18NBoundMessage inMessage)
    {
        super(inNested,
              inMessage);
    }
}