package com.cinus.image;


import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;


public class ImageUtilsTest {

    @Test
    public void test_grayscale() throws Exception {
        BufferedImage img = ImageUtils.loadImage("src/test/java/com/cinus/image/test-data/test.jpg");
        BufferedImage bfi = ImageUtils.grayscale(img);
        Assert.assertTrue(ImageUtils.writeImage("src/test/java/com/cinus/image/test-data/grayscale.jpg", ImageUtils.IMAGE_TYPE_JPG, bfi));
    }

    @Test
    public void test_dilation() throws Exception {
        BufferedImage img = ImageUtils.loadImage("src/test/java/com/cinus/image/test-data/test.jpg");
        BufferedImage bfi = ImageUtils.dilation(img, 10);
        Assert.assertTrue(ImageUtils.writeImage("src/test/java/com/cinus/image/test-data/dilation.jpg", ImageUtils.IMAGE_TYPE_JPG, bfi));
    }


}
