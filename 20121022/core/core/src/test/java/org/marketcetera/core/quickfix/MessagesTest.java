package org.marketcetera.core.quickfix;

import org.junit.Test;
import org.marketcetera.core.util.l10n.MessageComparator;
import org.marketcetera.util.test.TestCaseBase;

import static org.junit.Assert.assertTrue;

/* $License$ */

/**
 * @since 0.6.0
 * @version $Id: MessagesTest.java 16063 2012-01-31 18:21:55Z colin $
 */
public class MessagesTest
    extends TestCaseBase
{
    @Test
    public void messagesMatch()
        throws Exception
    {
        MessageComparator comparator=new MessageComparator(Messages.class);
        assertTrue(comparator.getDifferences(),comparator.isMatch());
    }
}