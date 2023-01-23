package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class SwapColors  implements PixelFilter{

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        for (int r = 0; r < green.length; r++) {
            for (int c = 0; c < green.length; c++) {
                short storage = red[r][c];

                red[r][c] = blue[r][c];
                blue[r][c] = storage;
                
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }
    
}
