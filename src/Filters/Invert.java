package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class Invert implements PixelFilter {

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                for (int i = 0; i < grid.length; i++) {

                    grid[r][c] = (short)(255-grid[r][c]);
                }
            }
        }

        img.setPixels(grid);
        return img;

    }
}
