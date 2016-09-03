package com.gilt.paintshop.exceptions;

/**
 * No solution found exception in case application couldn't find an optimal solution.
 */
public class NoSolutionFoundException extends PaintShopException {

    public NoSolutionFoundException(String message) {

        super(message);
    }
}
