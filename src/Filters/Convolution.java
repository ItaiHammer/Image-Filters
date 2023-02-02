package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class Convolution implements PixelFilter {
    String kernalType;

    public Convolution () {
        kernalType = JOptionPane.showInputDialog(null, "Choose Kernal Type:");
    }

    public DImage processImage (DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] BWGrid = img.getBWPixelGrid();

        double[][] kernal = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

        if (kernalType.equals("emboss")) {
            double[][] embossKernal = {{-4, 0, 0}, {0, 0, 0}, {0, 0, 4}};
            kernal = embossKernal;
        }else if (kernalType.equals("outline")) {
            double[][] outlineKernal = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
            kernal = outlineKernal;
        }else if (kernalType.equals("blur")) {
            double[][] blurKernal = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
            kernal = blurKernal;
        }

        // for each pixel of the new grid
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                red[r][c] = applyKernal(red, r, c, kernal);
                
                green[r][c] = applyKernal(green, r, c, kernal);
                
                blue[r][c] = applyKernal(blue, r, c, kernal);

                // BWGrid[r][c] = applyKernal(BWGrid, r, c, kernal);
            }
        }

        img.setColorChannels(red, green, blue);
        // img.setPixels(BWGrid);
        return img;
    }

    private short applyKernal(short[][] color, int r, int c, double[][] kernal) {
        double averageWeighted = 0;
        if (r - 1 > 0 && r + 1 < color.length && c - 1 > 0 && c + 1 < color[0].length) {
            // in the bounds

            // adding up weighted colors
            averageWeighted = getAvarageWeighted(kernal, color, r, c);
            
            // clipping
            averageWeighted = clipColor(averageWeighted);
        }

        return (short)(averageWeighted);
    }

    private double clipColor(double averageWeighted) {
        double clipped = 0;

        if (averageWeighted < 0) {
            clipped = 0;
        }else if (averageWeighted > 255) {
            clipped = 255;
        }

        return clipped;
    }

    private double getAvarageWeighted(double[][] kernal, short[][] color, int r, int c) {
        double averageWeighted = 0;


        for (int i = r-1; i < r + kernal.length-1; i++) {
            for (int j = c-1; j < c + kernal[0].length-1; j++) {
                averageWeighted += color[i][j] * kernal[i-r+1][j-c+1];
            }
        }

        // getting total kernal value
        double totalKernal = getTotalKernalValue(kernal);
        
        // dividing
        averageWeighted = averageWeighted / (totalKernal == 0 ? 1 : totalKernal);

        return averageWeighted;
    }

    private double getTotalKernalValue(double[][] kernal) {
        double totalKernal = 0;

        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[0].length; j++) {
                totalKernal += kernal[i][j];
            }
        }

        return totalKernal;
    }
}
