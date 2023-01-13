package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Polychrome implements PixelFilter {
    private double divider;
    private int colorCount;

    public Polychrome() {
        colorCount = 4;
        divider = (double)(255)/(double)(colorCount);
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                for (int i = 0; i < grid.length; i++) {

                    grid[r][c] = checkShade(grid[r][c]);
                }
            }
        }

        img.setPixels(grid);
        return img;

    }

    private short checkShade(short color) {
        short newColor = 0;
        
        newColor = (short)(color/(int)(divider) * divider);

        return newColor;
    }
}
