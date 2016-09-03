package com.gilt.paintshop;

/**
 * Each customer select bunch of @{@link PaletteColorType}
 */
public class FavoriteColor {

    /**
     * Index of color in color Palette
     */
    private int index;

    /**
     * Selected color
     */
    private PaletteColorType colorType;

    /**
     * Value of overall consideration.
     */
    private float consideration;

    public FavoriteColor(int index, PaletteColorType colorType) {

        this.index = index;
        this.colorType = colorType;
    }

    public PaletteColorType getColorType() {

        return colorType;
    }

    public void setColorType(PaletteColorType colorType) {

        this.colorType = colorType;
    }

    public float getConsideration() {

        return consideration;
    }

    public void setConsideration(float consideration) {

        this.consideration = consideration;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {

        this.index = index;
    }
}