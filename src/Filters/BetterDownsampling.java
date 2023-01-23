package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class BetterDownsampling implements PixelFilter {
    double size;

    public BetterDownsampling() {
        size = Double.parseDouble(JOptionPane.showInputDialog(null, "What size? (%)"));
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();

        short[][] newGrid = new short[(int)(((double)(size)/(double)(100)) * grid.length)][(int)(((double)(size)/(double)(100)) * grid[0].length)];
        int samplingPixelSizeY = grid[0].length/newGrid[0].length;
        int samplingPixelSizeX = grid.length/newGrid.length;

        // for each pixel of the new grid
        for (int r = 0; r < newGrid.length; r++) {
            for (int c = 0; c < newGrid[r].length; c++) {

                // averages grid
                for (int r2 = 0; r2 <= samplingPixelSizeX; r2++) {
                    for (int c2 = 0; c2 <= samplingPixelSizeY; c2++) {
                        if (r2 + (r * samplingPixelSizeX) < grid.length && c2 + (c * samplingPixelSizeY) < grid[r].length) {
                            newGrid[r][c] += grid[r2 + (r * samplingPixelSizeX)][c2 + (c * samplingPixelSizeY)];
                        }
                    }
                }
                newGrid[r][c] = (short)((newGrid[r][c] / ((samplingPixelSizeX+1) * (samplingPixelSizeY+1))));
            }
        }

        img.setPixels(newGrid);
        return img;

    }
}
