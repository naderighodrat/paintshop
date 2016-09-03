package com.gilt.paintshop;

import com.gilt.paintshop.exceptions.NoSolutionFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Palette Advisor will advice a palette of colors that will satisfy most customers.
 */
public class PaletteAdvisor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaletteAdvisor.class.getCanonicalName());

    private final List<CustomerFavouritePalette> customerFavouritePalettes;

    public PaletteAdvisor(List<CustomerFavouritePalette> customerFavouritePalettes) {

        this.customerFavouritePalettes = customerFavouritePalettes;
    }

    /**
     * Looking to find most satisfied palette of colors for all customers.
     *
     * @return List<PaletteColorType> a palette of {@link PaletteColorType} that will satisfy all/most customers.
     * @throws NoSolutionFoundException
     */
    public List<PaletteColorType> findMostSatisfyingPalette() throws NoSolutionFoundException {

        List<PaletteColorType> mostSatisfyingPalette = new ArrayList<PaletteColorType>(PaintShopUtil.PALETTE_SIZE);
        calculateSatisfaction();
        if (canSatisfyAllCustomers()) {
            throw new NoSolutionFoundException("No solution exists");
        }
        for (int i = 1; i <= PaintShopUtil.PALETTE_SIZE; i++) {
            PaletteColorType favouriteColor = topDemandedColorInPalette(i);
            if (favouriteColor != null) {
                mostSatisfyingPalette.add(favouriteColor);
            }
        }
        LOGGER.info("Most Satisfying Palette Found={}", mostSatisfyingPalette);

        if (mostSatisfyingPalette.size() <= 0) {
            throw new NoSolutionFoundException("It is impossible to satisfy all the customer!");
        }

        return mostSatisfyingPalette;
    }

    /**
     * Trying to check if there is any constraint violation.
     *
     * @return true if there is an problem to satisfy all customers.
     */
    private boolean canSatisfyAllCustomers() {

        for (int i = 0; i < this.customerFavouritePalettes.size(); i++) {
            for (FavoriteColor color : this.customerFavouritePalettes.get(i).getFavoriteColors()) {
                for (int j = i + 1; j < this.customerFavouritePalettes.size(); j++) {
                    List<FavoriteColor> favouriteColors = this.customerFavouritePalettes.get(j).getFavoriteColors();
                    for (FavoriteColor sc : favouriteColors) {
                        if (sc.getIndex() == color.getIndex() && sc.getConsideration() == color.getConsideration() && !sc.getColorType().equals(color.getColorType())) {
                            LOGGER.info("Conflict for color found at: [index= {} , Color={} ]", sc.getIndex(), sc.getColorType());
                            return true;//Conflict found! there is an problem to satisfy all customers!
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Trying to find top valuable for each cell of palette.
     *
     * @param index of color in the palette.
     * @return return a PaletteColorType the most favourite for that index.
     * @throws NoSolutionFoundException
     */
    private PaletteColorType topDemandedColorInPalette(int index) throws NoSolutionFoundException {

        float highestValue = 0;
        PaletteColorType favouriteColor = null;
        for (CustomerFavouritePalette cfp : this.customerFavouritePalettes) {
            for (FavoriteColor sc : cfp.getFavoriteColors()) {
                if (sc.getIndex() == index) {
                    if (sc.getConsideration() > highestValue) {
                        highestValue = sc.getConsideration();
                        favouriteColor = sc.getColorType();
                    }
                }
            }
        }

        //You make as few mattes as possible (because they are more expensive).
        if (favouriteColor == PaletteColorType.MATTE && highestValue < PaintShopUtil.PALETTE_SIZE) {
            favouriteColor = PaletteColorType.GLOSS;
        }
        return favouriteColor;
    }

    /**
     * Calculate satisfaction for all customers.
     */
    public void calculateSatisfaction() {

        for (CustomerFavouritePalette cfp : this.customerFavouritePalettes) {
            cfp.calculateSatisfaction();
        }
    }
}