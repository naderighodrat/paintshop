package com.gilt.paintshop.exceptions;

/**
 * Use {@link InvalidPaletteColorSizeException}If the size of the palette at first line of input file des not exist or invalid.
 */
public class InvalidPaletteColorSizeException extends PaintShopException {

    public InvalidPaletteColorSizeException(String message) {

        super(message);
    }
}
