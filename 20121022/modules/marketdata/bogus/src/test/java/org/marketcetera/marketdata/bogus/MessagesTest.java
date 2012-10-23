package org.marketcetera.marketdata.bogus;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.marketcetera.core.util.l10n.MessageComparator;

/* $License$ */

/**
 * Tests the messages for the Bogus Market Data Feed.
 *
 * @version $Id: MessagesTest.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.1.0
 */
public class MessagesTest
{
    @Test
    public void messagesMatch()
        throws Exception
    {
        MessageComparator comparator = new MessageComparator(Messages.class);
        assertTrue(comparator.getDifferences(),
                   comparator.isMatch());
    }
}