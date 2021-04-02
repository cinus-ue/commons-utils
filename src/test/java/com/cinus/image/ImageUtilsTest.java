package com.cinus.image;


import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertTrue;


public class ImageUtilsTest {

    @Test
    public void test_grayscale() throws Exception {
        BufferedImage img = ImageUtils.loadImage("src/test/java/com/cinus/image/test-data/test.jpg");
        BufferedImage bfi = ImageUtils.grayscale(img);
        boolean ret = ImageUtils.writeImage("src/test/java/com/cinus/image/test-data/test2.jpg", ImageUtils.IMAGE_TYPE_JPG, bfi);
        assertTrue(ret);
    }

    @Test
    public void test_dilation() throws Exception {
        BufferedImage img = ImageUtils.loadImage("src/test/java/com/cinus/image/test-data/test.jpg");
        BufferedImage bfi = ImageUtils.dilation(img, 10);
        boolean ret = ImageUtils.writeImage("src/test/java/com/cinus/image/test-data/test3.jpg", ImageUtils.IMAGE_TYPE_JPG, bfi);
        assertTrue(ret);
    }


}
