package com.gilt.paintshop.exceptions;

/**
 * Use {@link SelectionPolicyViolationException}If the selection policy violated.
 */
public class SelectionPolicyViolationException extends PaintShopException {

    public SelectionPolicyViolationException(String message) {

        super(message);
    }
}
