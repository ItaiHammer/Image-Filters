package Filters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.Color;
import Interfaces.Drawable;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

public class BallDetection implements PixelFilter, Interactive, Drawable {
    Color targetColor = new Color(0, 0, 0);
    double distance = 150;
    boolean autoThreashold = false;
    boolean RGB = true;

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();
        short[][] grid = img.getBWPixelGrid();
        
        double totalDist = 0;

        if (RGB) {
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    totalDist = getDistanceRGB(red[r][c], green[r][c], blue[r][c], (short)targetColor.R(), (short)targetColor.G(), (short)targetColor.B());
                    if (getDistanceRGB(red[r][c], green[r][c], blue[r][c], (short)targetColor.R(), (short)targetColor.G(), (short)targetColor.B()) <= distance) {
                        grid[r][c] = 255;
                    }else {
                        grid[r][c] = 0;
                    }
                }
            }
    
            if (autoThreashold) {
                setAutoThreasholdRGB(red, green, blue, totalDist);
            }
        }else {


            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    totalDist = getDistanceHSL(new Color(red[r][c], green[r][c], blue[r][c]).getHSL()[0], targetColor.getHSL()[0]);
                    if (getDistanceHSL(new Color(red[r][c], green[r][c], blue[r][c]).getHSL()[0], targetColor.getHSL()[0]) <= distance*255) {
                        grid[r][c] = 255;
                    }else {
                        grid[r][c] = 0;
                    }
                }
            }

            System.out.println(totalDist/(red[0].length * red.length));
            if (autoThreashold) {
                setAutoThreasholdHSL(red, green, blue, totalDist);
            }
        }
        

        img.setPixels(grid);
        return img;
    }

    private void setAutoThreasholdRGB(short[][] red, short[][] green, short[][] blue, double totalDist) {
        int totalPixelCount = (red[0].length) * (red.length);
        double avgDist = totalDist/(double)(totalPixelCount);

        distance = 60000 * avgDist;

    }

    private void setAutoThreasholdHSL(short[][] red, short[][] green, short[][] blue, double totalDist) {
        int totalPixelCount = (red[0].length) * (red.length);
        double avgDist = totalDist/(double)(totalPixelCount);

        distance = (double)(100)/avgDist * (double)(1);

    }

    private double getDistanceRGB (short red1, short green1, short blue1, short red2, short green2,short blue2) {
        return Math.sqrt(Math.pow(Math.abs(red1 - red2), 2) + Math.pow(Math.abs(green1 - green2), 2)+ Math.pow(Math.abs(blue1 - blue2), 2));
    }

    private double getDistanceHSL (double p1, double p2) {
        return Math.abs(p1-p2);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        targetColor.setRGB(red[mouseY][mouseX], green[mouseY][mouseX], blue[mouseY][mouseX]);

        System.out.println("Target Color Selected: "+targetColor);
    }

    @Override
    public void keyPressed(char key) {
        if (key == '=') {
            if (RGB) {
                distance += 5;
            }else {
                distance += 0.2;
            }
            System.out.println("Increasing Threashold: "+distance);
        }else if (key == '-') {
            if (RGB) {
                distance -= 5;
            }else {
                distance -= 0.2;
            }
            System.out.println("Decreasing Threashold: "+distance);
        }else if (key == '0') {
            autoThreashold = !autoThreashold;
            System.out.println("Auto Threashold is: "+ (autoThreashold == true ? "On" : "Off"));
        }else if (key == '9') {
            RGB = !RGB;
            System.out.println("Mode is: "+ (RGB == true ? "RGB" : "HSL"));
        }
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        // TODO Auto-generated method stub
        
    }
    
}
