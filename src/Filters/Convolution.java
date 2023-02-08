package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class Convolution implements PixelFilter {
    String kernalType;

    public Convolution () {
        kernalType = JOptionPane.showInputDialog(null, "Choose Kernal Type:");
    }

    public Convolution (String k) {
        kernalType = k;
    }

    public DImage processImage (DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] newRed = img.getRedChannel();
        short[][] newGreen = img.getGreenChannel();
        short[][] newBlue = img.getBlueChannel();

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
                newRed[r][c] = applyKernal(red, r, c, kernal);
                
                newGreen[r][c] = applyKernal(green, r, c, kernal);
                
                newBlue[r][c] = applyKernal(blue, r, c, kernal);

                // BWGrid[r][c] = applyKernal(BWGrid, r, c, kernal);
            }
        }

        img.setColorChannels(red, green, blue);
        // img.setPixels(BWGrid);
        return img;
    }

    private short applyKernal(short[][] color, int r, int c, double[][] kernal) {
        double averageWeighted = 0;
        if (r + 1 < color.length - (int)(kernal.length) && c + 1 < color[0].length - (int)(kernal[0].length)) {
            // in the bounds

            // adding up weighted colors
            averageWeighted = getAvarageWeighted(kernal, color, r, c, getTotalKernalValue(kernal));
            
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

    private double getAvarageWeighted(double[][] kernal, short[][] color, int r, int c, double totalKernal) {
        
        // System.out.println("---------------------------------------------------------------");
        double averageWeighted = 0;

        for (int i = r; i < r+(int)(kernal.length); i++) {
            for (int j = c; j < c+(int)(kernal[0].length); j++) {
                // System.out.println("--------------------------");
                // System.out.println("Color: "+color[i][j]);
                // System.out.println("Kernal: "+kernal[i-r][j-c]);
                // System.out.println("Color: "+(color[i][j] * kernal[i-r][j-c]));
                averageWeighted += (color[i][j] * kernal[i-r][j-c]);
            }
        }

        // System.out.println("Before Division: "+averageWeighted);
        // System.out.println("Total Kernal: "+totalKernal);
        
        // dividing
        averageWeighted = (double)(averageWeighted) / (double)(totalKernal == 0 ? 1 : totalKernal);
        // System.out.println("Final: "+averageWeighted);

        averageWeighted *= 5;
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
