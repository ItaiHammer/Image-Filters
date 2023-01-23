package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class Contrast implements PixelFilter {
    private double value;

    public Contrast() {
        value = Double.parseDouble(JOptionPane.showInputDialog(null, "Give a value between 0.0 and 2.0?"));
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                double newValue = grid[r][c];
                newValue /= 255;
                newValue -= 0.5;
                newValue *= value;
                newValue += 0.5;
                newValue *= 255;

                if (newValue > 255) {
                    newValue = 255;
                }else if (newValue < 0) {
                    newValue = 0;
                }

                grid[r][c] = (short)(newValue);
                
            }
        }

        img.setPixels(grid);
        return img;

    }
}
