package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class MotionBlur implements PixelFilter {
    private String dir;
    private int value;

    public MotionBlur() {
        dir = JOptionPane.showInputDialog(null, "What direction (horizontal or vertical)?");
        // value = Double.parseDouble(JOptionPane.showInputDialog(null, "Give a bluring value"));
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                double newValue = grid[r][c];
                    
                for (int i = -value; i < value; i++) {
                    
                }

                grid[r][c] = (short)(newValue);
            }
        }

        img.setPixels(grid);
        return img;

    }
}
