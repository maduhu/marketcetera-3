import java.math.BigDecimal;

import org.marketcetera.core.event.AskEvent;
import org.marketcetera.core.event.TradeEvent;
import org.marketcetera.strategy.java.Strategy;

/* $License$ */

/**
 * Sample strategy to test the ability to send arbitrary data.
 *
 * @version $Id: SendOther.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.0.0
 */
public class SendOther
        extends Strategy
{
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.java.Strategy#onAsk(org.marketcetera.core.event.AskEvent)
     */
    @Override
    public void onAsk(AskEvent inAsk)
    {
        if(getProperty("sendNull") != null) {
            send(null);
        } else if(getProperty("sendString") != null) {
            send("test string");
        } else if(getProperty("sendTwo") != null) {
            send(BigDecimal.ONE);
            send(BigDecimal.TEN);
        }
    }
}