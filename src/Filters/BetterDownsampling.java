package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class BetterDownsampling implements PixelFilter {

    public BetterDownsampling() {
        
    }

    public DImage processImage (DImage img) {
        short[][] ogGrid = img.getBWPixelGrid();
        short[][] grid = img.getBWPixelGrid();
        short[][] finalGrid;

        System.out.println(grid[0].length);

        while (grid[0].length % 4 != 0) {
            grid = new short[grid.length][grid[0].length-1];
        }
        
        System.out.println(grid[0].length);
        System.out.println(grid[0].length % 4);

        System.out.println("------");

        System.out.println(grid.length);

        while (grid.length % 4 != 0) {
            grid = new short[grid.length - 1][grid[0].length];
        }

        System.out.println(grid.length);
        System.out.println(grid.length % 4);

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = ogGrid[r][c];
            }
        }

        finalGrid = new short[grid.length/2][grid[0].length/2];

        for (int r = 0; r < finalGrid.length; r++) {
            for (int c = 0; c < finalGrid[r].length; c++) {
                short avg = grid[r][c];
                finalGrid[r][c] = avg;
            }
        }

        img.setPixels(finalGrid);
        return img;

    }
}
