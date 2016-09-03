package com.gilt.paintshop;

import com.gilt.paintshop.exceptions.InvalidPaletteColorSizeException;
import com.gilt.paintshop.exceptions.NoSolutionFoundException;
import com.gilt.paintshop.exceptions.PaintShopException;
import com.gilt.paintshop.exceptions.SelectionPolicyViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Paint Shop that going to read a list of customers from a file and build their favorite colors in palette.
 * Paint Shop intend to find the the best Palette that satisfy all customers.
 * Loading customers favorite colors from InputFile and shape them.
 */
public class PaintShopUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaintShopUtil.class.getCanonicalName());

    //Regex for sapling color selections. [ 1 M 3 G 4 G]
    public static final String DELIMITING = "\\s+";

    // Number of colors there are in first line of file!
    public static int PALETTE_SIZE = -1;

    /**
     * The main entry point of the application.
     *
     * @param args args[0] should contain path of input file.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            LOGGER.info("Error--> Wrong number of arguments. Please pass the input file path as the first argument!");
            System.exit(0);
        }

        LOGGER.info("Reading content of  InputFile: {}", args[0]);

        PaintShopUtil paintShopUtil = new PaintShopUtil();

        try {
            List<String> linesOfColorPalette = paintShopUtil.readInputFile(args[0]);

            List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);

            PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);

            paletteAdvisor.findMostSatisfyingPalette();

        } catch (NumberFormatException e) {
            LOGGER.info("The program could not parse the input file.", e);
            System.exit(1);
        } catch (NoSolutionFoundException e) {
            LOGGER.info("No solution exists!");
            System.exit(2);
        } catch (FileNotFoundException e) {
            LOGGER.info("The specified input file does not exist, or can not  read: " + args[0], e);
            System.exit(3);
        } catch (IOException e) {
            LOGGER.info("An error occurred while reading the input file.", e);
            System.exit(4);
        } catch (Exception e) {
            LOGGER.info("An error occurred during runtime.", e);
            System.exit(5);
        }

    }

    /**
     * Create a list of customer favourite palettes.
     *
     * @param linesOfColorPalette received a list of line from input file in form of raw string.
     * @return List<CustomerFavouritePalette>
     * @throws NumberFormatException
     */
    public List<CustomerFavouritePalette> processCustomerRawData(List<String> linesOfColorPalette) throws NumberFormatException, PaintShopException {

        List<CustomerFavouritePalette> customerFavouritePalettes = new ArrayList<CustomerFavouritePalette>(linesOfColorPalette.size());
        try {
            PALETTE_SIZE = Integer.valueOf(linesOfColorPalette.get(0).trim());
            LOGGER.info("Number of colors there are in first line of file: {}", PALETTE_SIZE);
            if (PALETTE_SIZE <= 0) {
                throw new InvalidPaletteColorSizeException("Invalid number of colors.");
            }
        } catch (Exception ex) {
            throw new InvalidPaletteColorSizeException("Invalid number of colors.\n" + ex.getMessage());
        }
        for (int i = 1; i < linesOfColorPalette.size(); i++) {
            List<FavoriteColor> favouriteColors = new ArrayList<FavoriteColor>(PALETTE_SIZE);
            // Selected color from palette will be similar to: [1 M 3 G 5 G]
            String[] colors = linesOfColorPalette.get(i).split(DELIMITING);
            for (int c = 0; c < colors.length; c += 2) {
                Integer index = Integer.parseInt(colors[c]);
                PaletteColorType colorType = PaletteColorType.getType(colors[c + 1]);
                FavoriteColor favoriteColor = new FavoriteColor(index, colorType);
                favouriteColors.add(favoriteColor);
            }
            validateSelectedColors(favouriteColors);
            customerFavouritePalettes.add(new CustomerFavouritePalette(favouriteColors));

        }
        LOGGER.info("Number of Customer Favourite Palettes Loaded From Input File: {}", customerFavouritePalettes.size());
        return customerFavouritePalettes;
    }

    /**
     * Check that customers only selected one Matte color.
     *
     * @param favouriteColors list of selected colors.
     * @throws SelectionPolicyViolationException throw exception if number of matte color selected > 1 or any policy violated!
     */
    private void validateSelectedColors(List<FavoriteColor> favouriteColors) throws SelectionPolicyViolationException {

        int numberOfMatteColorSelected = 0;
        for (FavoriteColor fc : favouriteColors) {
            if (fc.getColorType().equals(PaletteColorType.MATTE)) {
                numberOfMatteColorSelected++;
            }
            //check if index is not out of rang.
            if (fc.getIndex() > PALETTE_SIZE) {
                throw new SelectionPolicyViolationException("Color index is out of range for current palette size[" + PALETTE_SIZE + "].");
            }

        }
        if (numberOfMatteColorSelected > 1) {
            throw new SelectionPolicyViolationException("Number of Matte color in selection should not be more than one.");
        }
    }

    /**
     * Read content of input file.
     *
     * @param fileName path of input file.
     * @return an array of string line that contain customer selected colors.
     * @throws IOException
     */
    public List<String> readInputFile(String fileName) throws IOException {

        InputStream in = null;
        BufferedReader reader = null;
        List lines = new ArrayList<String>();
        try {
            in = new FileInputStream(new File(fileName));
            reader = new BufferedReader(new InputStreamReader(in));
            LOGGER.info("Reading Content:");
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals("")) {
                    lines.add(line);
                    LOGGER.info(line);
                } else {
                    //Take it easy and just skip that empty line.
                    LOGGER.warn("Empty line in the file!:" + line);
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return lines;
    }
}
