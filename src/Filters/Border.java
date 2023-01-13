package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Border implements PixelFilter {
    private int thickness;

    public Border() {
        thickness = 20;
    }

    public DImage processImage (DImage img) {
        short[][] BWGrid = img.getBWPixelGrid();
        short[][] grid = new short[BWGrid[0].length + (thickness*2)][BWGrid.length + (thickness*2)];

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (c <= thickness || c >= thickness + BWGrid[0].length || r <= thickness || r >= thickness + BWGrid.length) {
                    grid[r][c] = 0;
                }else {
                    grid[r][c] = BWGrid[r-thickness][c-thickness];
                }
            }
        }

        img.setPixels(grid);
        return img;

    }
}
