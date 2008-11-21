package org.marketcetera.trade;

import org.marketcetera.util.misc.ClassVersion;

import java.math.BigDecimal;

/* $License$ */
/**
 * Implementation for Single Order Suggestions.
 *
 * @author anshul@marketcetera.com
 * @version $Id$
 * @since $Release$
 */
@ClassVersion("$Id$") //$NON-NLS-1$
class OrderSingleSuggestionImpl
        implements OrderSingleSuggestion {
    @Override
    public String getIdentifier() {
        return mIdentifier;
    }

    @Override
    public void setIdentifier(String inIdentifier) {
        mIdentifier = inIdentifier;
    }

    @Override
    public BigDecimal getScore() {
        return mScore;
    }

    @Override
    public void setScore(BigDecimal inScore) {
        mScore = inScore;
    }

    @Override
    public OrderSingle getOrder() {
        return mOrder == null
                ? null
                : mOrder.clone();
    }

    @Override
    public void setOrder(OrderSingle inOrder) {
        mOrder = inOrder == null
                ? null
                : inOrder.clone();
    }

    private String mIdentifier;
    private BigDecimal mScore;
    private OrderSingle mOrder;
    private static final long serialVersionUID = 1L;
}
