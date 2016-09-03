package com.gilt.paintshop.test;

import com.gilt.paintshop.CustomerFavouritePalette;
import com.gilt.paintshop.PaintShopUtil;
import com.gilt.paintshop.PaletteAdvisor;
import com.gilt.paintshop.PaletteColorType;
import com.gilt.paintshop.exceptions.InvalidPaletteColorSizeException;
import com.gilt.paintshop.exceptions.NoSolutionFoundException;
import com.gilt.paintshop.exceptions.PaintShopException;
import com.gilt.paintshop.exceptions.SelectionPolicyViolationException;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyTest {

    private String arrayList2String(List<PaletteColorType> paletteColorTypes) {

        StringBuilder sb = new StringBuilder();
        for (PaletteColorType c : paletteColorTypes) {
            sb.append(" " + c);
        }
        return sb.toString().trim();
    }

    private String getFilePath(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return classLoader.getResource(fileName).toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testCase() throws IOException, PaintShopException {
        /*5
        1 M 3 G 5 G
        2 G 3 M 4 G
        5 M*/
        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase1.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        List<PaletteColorType> paletteColorTypes = paletteAdvisor.findMostSatisfyingPalette();
        String result = arrayList2String(paletteColorTypes);
        assertEquals(result, "G G G G M");
    }

    @Test(expected = NoSolutionFoundException.class)
    public void testCase2() throws IOException, PaintShopException {
      /*  1
        1 G
        1 M*/
        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase2.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a NoSolutionFoundException to be thrown");


    }

    @Test
    public void testCase3() throws PaintShopException, IOException {
       /* 5
        2 M
        5 G
        1 G
        5 G 1 G 4 M
        3 G
        5 G
        3 G 5 G 1 G
        3 G
        2 M
        5 G 1 G
        2 M
        5 G
        4 M
        5 G 4 M*/

        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase3.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);

        List<PaletteColorType> paletteColorTypes = paletteAdvisor.findMostSatisfyingPalette();
        String result = arrayList2String(paletteColorTypes);
        assertEquals(result, "G M G M G");


    }

    @Test
    public void testCase4() throws PaintShopException, IOException {
   /*     2
        1 G 2 M
        1 M*/
        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase4.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        List<PaletteColorType> paletteColorTypes = paletteAdvisor.findMostSatisfyingPalette();
        String result = arrayList2String(paletteColorTypes);
        assertEquals(result, "M M");

    }

    @Test(expected = NoSolutionFoundException.class)
    public void testCase5() throws PaintShopException, IOException {
        /*2
          1 G 2 M
          1 M 2 G*/

        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase5.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a NoSolutionFoundException to be thrown");
    }

    @Test(expected = SelectionPolicyViolationException.class)
    public void testCaseInvalidContent1() throws PaintShopException, IOException {
      /*1
        1 G 2 M
        2 M*/

        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-1.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a SelectionPolicyViolationException to be thrown");

    }

    @Test(expected = NumberFormatException.class)
    public void testCaseInvalidContent2() throws PaintShopException, IOException {
      /*3
        3 G 2 M
        1M  1 g*/
        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-2.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a NumberFormatException to be thrown");

    }

    @Test(expected = InvalidPaletteColorSizeException.class)
    public void testCaseInvalidContent3() throws PaintShopException, IOException {
      /*
      * 1 G 2 M
        3 M 1 G
      * */

        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-3.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a NumberFormatException to be thrown");
    }

    @Test(expected = NumberFormatException.class)
    public void testCaseInvalidContent4() throws PaintShopException, IOException {
       /* 3
        1 G 2 M
        3 M 1 G
        3 G $ M*/
        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-4.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a NumberFormatException to be thrown");
    }

    @Test(expected = SelectionPolicyViolationException.class)
    public void testCaseInvalidContent5() throws PaintShopException, IOException {
        /*3
        1 G 2 M
        3 M 1 G 2 M
        3 G 2 G*/

        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-5.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a SelectionPolicyViolationException to be thrown");
    }

    @Test(expected = SelectionPolicyViolationException.class)
    public void testCaseInvalidContent6() throws PaintShopException, IOException {
        /*2
        1 G 2 M
        2 M
        1 G 3 M*/


        PaintShopUtil paintShopUtil = new PaintShopUtil();
        List<String> linesOfColorPalette = paintShopUtil.readInputFile(getFilePath("testcase-invalid-content-6.txt"));
        List<CustomerFavouritePalette> customerFavouritePalettes = paintShopUtil.processCustomerRawData(linesOfColorPalette);
        PaletteAdvisor paletteAdvisor = new PaletteAdvisor(customerFavouritePalettes);
        paletteAdvisor.findMostSatisfyingPalette();
        fail("Expected a SelectionPolicyViolationException to be thrown");
    }
}
