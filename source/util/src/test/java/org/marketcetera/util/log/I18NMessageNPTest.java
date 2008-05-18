package org.marketcetera.util.log;

import java.util.Locale;
import org.apache.log4j.Level;
import org.junit.Test;

import static org.junit.Assert.*;

public class I18NMessageNPTest
    extends I18NMessageTestBase
{
    private static final String TEST_MSG_EN=
        "PN msg (expected) en "+TEST_P1+" "+TEST_P2+" "+TEST_P3+" "+TEST_P4+
        " "+TEST_P5+" "+TEST_P6+" "+TEST_P7;
    private static final String TEST_TTL_EN=
        "PN ttl (expected) en "+TEST_P1+" "+TEST_P2+" "+TEST_P3+" "+TEST_P4+
        " "+TEST_P5+" "+TEST_P6+" "+TEST_P7;
    private static final String TEST_MSG_FR=
        "PN msg (expected) fr "+TEST_P1+" "+TEST_P2+" "+TEST_P3+" "+TEST_P4+
        " "+TEST_P5+" "+TEST_P6+" "+TEST_P7;
    private static final String TEST_TTL_FR=
        "PN ttl (expected) fr "+TEST_P1+" "+TEST_P2+" "+TEST_P3+" "+TEST_P4+
        " "+TEST_P5+" "+TEST_P6+" "+TEST_P7;


    private static void castOverride
        (I18NMessageNP m) {}


    @Test
    public void basic()
    {
        unboundTests
            (new I18NMessageNP(TestMessages.LOGGER,TEST_MSG_ID,TEST_ENTRY_ID),
             new I18NMessageNP(TestMessages.LOGGER,TEST_MSG_ID));
    }

    @Test
    public void messageProvider()
    {
        assertEquals
            (TEST_MSG_EN,TestMessages.PN_MSG.getText
             (TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,TEST_P7));
        assertEquals
            (TEST_TTL_EN,TestMessages.PN_TTL.getText
             (TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,TEST_P7));
        assertEquals
            (TEST_MSG_FR,TestMessages.PN_MSG.getText
             (Locale.FRENCH,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
              TEST_P7));
        assertEquals
            (TEST_TTL_FR,TestMessages.PN_TTL.getText
             (Locale.FRENCH,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
              TEST_P7));
    }

    @Test
    public void loggerProxy()
    {
        TestMessages.PN_MSG.error
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.ERROR,TEST_CATEGORY,TEST_MSG_EN);
        TestMessages.PN_MSG.error
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.ERROR,TEST_CATEGORY,TEST_MSG_EN);

        TestMessages.PN_TTL.error
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.ERROR,TEST_CATEGORY,TEST_TTL_EN);
        TestMessages.PN_TTL.error
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.ERROR,TEST_CATEGORY,TEST_TTL_EN);

        TestMessages.PN_MSG.warn
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.WARN,TEST_CATEGORY,TEST_MSG_EN);
        TestMessages.PN_MSG.warn
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.WARN,TEST_CATEGORY,TEST_MSG_EN);

        TestMessages.PN_TTL.warn
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.WARN,TEST_CATEGORY,TEST_TTL_EN);
        TestMessages.PN_TTL.warn
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.WARN,TEST_CATEGORY,TEST_TTL_EN);

        TestMessages.PN_MSG.info
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.INFO,TEST_CATEGORY,TEST_MSG_EN);
        TestMessages.PN_MSG.info
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.INFO,TEST_CATEGORY,TEST_MSG_EN);

        TestMessages.PN_TTL.info
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.INFO,TEST_CATEGORY,TEST_TTL_EN);
        TestMessages.PN_TTL.info
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.INFO,TEST_CATEGORY,TEST_TTL_EN);
 
        TestMessages.PN_MSG.debug
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.DEBUG,TEST_CATEGORY,TEST_MSG_EN);
        TestMessages.PN_MSG.debug
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.DEBUG,TEST_CATEGORY,TEST_MSG_EN);

        TestMessages.PN_TTL.debug
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.DEBUG,TEST_CATEGORY,TEST_TTL_EN);
        TestMessages.PN_TTL.debug
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.DEBUG,TEST_CATEGORY,TEST_TTL_EN);
 
        TestMessages.PN_MSG.trace
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.TRACE,TEST_CATEGORY,TEST_MSG_EN);
        TestMessages.PN_MSG.trace
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.TRACE,TEST_CATEGORY,TEST_MSG_EN);

        TestMessages.PN_TTL.trace
            (TEST_CATEGORY,TEST_THROWABLE,TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7);
        assertSingleEvent(Level.TRACE,TEST_CATEGORY,TEST_TTL_EN);
        TestMessages.PN_TTL.trace
            (TEST_CATEGORY,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,TEST_P6,
             TEST_P7);
        assertSingleEvent(Level.TRACE,TEST_CATEGORY,TEST_TTL_EN);
    }

    @Test
    public void bound()
    {
        Object[] params=new Object[]
            {TEST_P1,TEST_P2,TEST_P3,TEST_P4,
             TEST_P5,TEST_P6,TEST_P7};
        I18NBoundMessageNP m=new I18NBoundMessageNP
            (TestMessages.PN_MSG,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,
             TEST_P6,TEST_P7);
        boundTests(m,params,TestMessages.PN_MSG,TEST_MSG_EN,TEST_MSG_FR);
        castOverride(m.getMessage());
        boundTests(new I18NBoundMessageNP
                   (TestMessages.PN_TTL,TEST_P1,TEST_P2,TEST_P3,TEST_P4,TEST_P5,
                    TEST_P6,TEST_P7),params,TestMessages.PN_TTL,
                   TEST_TTL_EN,TEST_TTL_FR);
    }
}