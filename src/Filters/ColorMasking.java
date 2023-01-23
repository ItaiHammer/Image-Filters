package Filters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.Color;
import Interfaces.PixelFilter;
import core.DImage;

public class ColorMasking implements PixelFilter {
    short[] targetColor = new short[3];
    double distance;

    public ColorMasking () {
        String input = JOptionPane.showInputDialog(null, "Enter Target color seperated by \",\"?");
        distance = Double.parseDouble(JOptionPane.showInputDialog(null, "How close to the color do you want it to be?"));
        while (input.contains(" ")) {
            input = input.replace(" ", "");
        }
        String[] targetColorString = input.split(",");
        targetColor[0] = Short.parseShort(targetColorString[0]);
        targetColor[1] = Short.parseShort(targetColorString[1]);
        targetColor[2] = Short.parseShort(targetColorString[2]);
        
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (getDistance(red[r][c], green[r][c], blue[r][c], targetColor[0], targetColor[1], targetColor[2]) <= distance) {
                    grid[r][c] = 255;
                }else {
                    grid[r][c] = 0;
                }
            }
        }

        img.setPixels(grid);
        return img;
    }

    private double getDistance (short red1, short green1, short blue1, short red2, short green2,short blue2) {
        return Math.sqrt(Math.pow(Math.abs(red1 - red2), 2) + Math.pow(Math.abs(green1 - green2), 2)+ Math.pow(Math.abs(blue1 - blue2), 2));
    }
    
}
