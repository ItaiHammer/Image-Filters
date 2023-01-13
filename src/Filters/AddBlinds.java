package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class AddBlinds implements PixelFilter {
    int blindSize;

    public AddBlinds() {
        blindSize = 40;
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();
        
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                for (int i = 0; i < grid.length; i++) {

                    if ((int)(r / blindSize) % 2 == 0) {
                        grid[r][c] = 0;
                    }
                }
            }
        }

        img.setPixels(grid);
        return img;

    }
}
