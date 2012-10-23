package org.marketcetera.modules.async;

import org.marketcetera.core.util.l10n.MessageComparator;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/* $License$ */
/**
 * Verifies that all the messages are correctly specified.
 *
 * @version $Id: MessagesTest.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.0.0
 */
public class MessagesTest {
    @Test
    public void messagesMatch() throws Exception {
        MessageComparator comparator=new MessageComparator(Messages.class);
        assertTrue(comparator.getDifferences(),comparator.isMatch());
    }
}