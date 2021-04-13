package com.cinus.image;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static String IMAGE_TYPE_PNG = "png";
    public static String IMAGE_TYPE_JPG = "jpg";


    public static BufferedImage loadImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        return image;
    }


    public static boolean writeImage(String fileName, String imgType, BufferedImage _image) throws IOException {
        File outputfile = new File(fileName);
        return ImageIO.write(_image, imgType, outputfile);
    }

    public static BufferedImage grayscale(BufferedImage _image) {
        BufferedImage imageOutput = copyImage(_image);
        int grayscale;
        for (int i = 0; i < _image.getWidth(); i++) {
            for (int j = 0; j < _image.getHeight(); j++) {
                int rgb;
                int p = getRGBW(_image, i, j);

                rgb = (int) ((((p >> 16) & 0xFF) * 0.2125) + (((p >> 8) & 0xFF) * 0.7154) + ((p & 0xFF) * 0.0721));
                rgb = (rgb << 16) | (rgb << 8) | (rgb);

                imageOutput.setRGB(i, j, rgb);
            }
        }
        return imageOutput;
    }

    public static BufferedImage dilation(BufferedImage _image, int _radius) {

        int width = _image.getWidth();
        int height = _image.getHeight();
        int radius = _radius;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, maxPixel(_image, i, j, radius));
            }
        }

        return image;
    }


    private static int maxPixel(BufferedImage _image, int _w, int _h, int r) {
        int maxR = 0;
        int maxG = 0;
        int maxB = 0;
        int radiusPow = r * r;
        int _r = r / 2;
        for (int i = -_r; i < _r; i++) {
            for (int j = -_r; j < _r; j++) {
                if (i * i + j * j < radiusPow) {
                    int c = getRGBW(_image, _w + i, _h + j);
                    maxR = Math.max(maxR, (c >> 16) & 0xFF);
                    maxG = Math.max(maxG, (c >> 8) & 0xFF);
                    maxB = Math.max(maxB, c & 0xFF);
                }
            }
        }
        return (maxR << 16) | (maxG << 8) | maxB;
    }


    public static int getRGBW(BufferedImage image, int i, int j) {
        int width = image.getWidth();
        int height = image.getHeight();
        i = Math.max(0, Math.min(width - 1, i));
        j = Math.max(0, Math.min(height - 1, j));
        return image.getRGB(i, j);
    }

    public static BufferedImage copyImage(BufferedImage _image) {
        ColorModel cm = _image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = _image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static int[] getImageSize(File img) throws IOException {
        BufferedImage bi = ImageIO.read(img);
        return new int[]{bi.getWidth(), bi.getHeight()};
    }

}
