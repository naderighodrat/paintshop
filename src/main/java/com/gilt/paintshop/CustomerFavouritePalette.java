package com.gilt.paintshop;

import java.util.List;

/**
 * Customer Favourite Palette for each customer-- each have a bunch of colors they like, either gloss or matte --> {@link FavoriteColor} and {@link PaletteColorType}.
 */
public class CustomerFavouritePalette {

    /**
     * Add this score for most wanted color
     */
    public static final int POPULARITY_SCORE = 1;

    /**
     * List of customer selected favourite colors.
     */
    private final List<FavoriteColor> favoriteColors;

    public CustomerFavouritePalette(List<FavoriteColor> favoriteColors) {

        this.favoriteColors = favoriteColors;
    }

    public List<FavoriteColor> getFavoriteColors() {

        return favoriteColors;
    }

    /**
     * A basic satisfaction rating algorithm to calculate for each customer
     * base on the number of selection and group number of selection.
     */
    public void calculateSatisfaction() {

        float selectionCost = selectionWorth();
        PaletteColorType mostSelectedColor = mostSelectedColor();
        for (FavoriteColor sc : this.favoriteColors) {
            if (sc.getColorType() == mostSelectedColor) {
                sc.setConsideration(selectionCost + POPULARITY_SCORE);
            } else {
                sc.setConsideration(selectionCost - POPULARITY_SCORE);
            }
        }
    }

    /**
     * A basic evaluation system to grade each select in customer selection list.
     *
     * @return a float number regarding each selection element.
     */
    private float selectionWorth() {

        return ((float) PaintShopUtil.PALETTE_SIZE / this.favoriteColors.size());
    }

    /**
     * Based on which color requested mostly there will be another point for those types.
     *
     * @return return most wanted category of colors. if they are equal most expensive color will be returned.
     */
    private PaletteColorType mostSelectedColor() {

        int numberOfGLOSS = 0;
        for (FavoriteColor sc : this.favoriteColors) {
            if (sc.getColorType() == PaletteColorType.GLOSS) {
                numberOfGLOSS++;
            }
        }
        return numberOfGLOSS <= ((float) this.favoriteColors.size() / 2) ? PaletteColorType.MATTE : PaletteColorType.GLOSS;
    }
}
