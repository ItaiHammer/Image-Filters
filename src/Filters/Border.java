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
        short[][] grid = new short[BWGrid[0].length][BWGrid.length];

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if () {}
            }
        }

        img.setPixels(grid);
        return img;

    }
}
