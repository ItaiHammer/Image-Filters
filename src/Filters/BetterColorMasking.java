package Filters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.Color;
import Interfaces.PixelFilter;
import core.DImage;

public class BetterColorMasking implements PixelFilter {
    short[] targetColor = new short[3];
    double[] targetHSL;
    double distance;

    public BetterColorMasking () {
        String input = JOptionPane.showInputDialog(null, "Enter Target color seperated by \",\"?");
        distance = Double.parseDouble(JOptionPane.showInputDialog(null, "How close to the color do you want it to be? (0-100)"))/100;
        while (input.contains(" ")) {
            input = input.replace(" ", "");
        }
        String[] targetColorString = input.split(",");
        targetColor[0] = Short.parseShort(targetColorString[0]);
        targetColor[1] = Short.parseShort(targetColorString[1]);
        targetColor[2] = Short.parseShort(targetColorString[2]);

        targetHSL = new Color(targetColor[0], targetColor[1], targetColor[2]).getHSL();
        
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();
        short[][] grid = img.getBWPixelGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                double[] hsl = new Color(red[r][c], green[r][c], blue[r][c]).getHSL();
                if (getDistance(hsl[0], targetHSL[0]) <= distance*255 && getDistance(hsl[1], targetHSL[1]) <= distance*100) {
                    grid[r][c] = 255;
                }else {
                    grid[r][c] = 0;
                }
            }
        }

        img.setPixels(grid);
        return img;
    }

    // private double getDistance (double p1, double p2) {
    //     return Math.sqrt(Math.pow(Math.abs(p1), 2) + Math.pow(Math.abs(p2), 2));
    // }

    private double getDistance (double p1, double p2) {
        return Math.abs(p1-p2);
    }


    
}
