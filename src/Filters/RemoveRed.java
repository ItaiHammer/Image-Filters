package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class RemoveRed implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        for (int r = 0; r < green.length; r++) {
            for (int c = 0; c < green.length; c++) {
                red[r][c] = 0;
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }
}
