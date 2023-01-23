package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class ColorNoise implements PixelFilter {
    int prob;

    public ColorNoise () {
        prob = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter noise probability?"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        for (int r = 0; r < green.length; r++) {
            for (int c = 0; c < green.length; c++) {
                red[r][c] += generatePixelOffset();
                if (red[r][c] > 255) red[r][c] = 255;
                else if (red[r][c] < 0) red[r][c] = 0;

                blue[r][c] += generatePixelOffset();
                if (blue[r][c] > 255) blue[r][c] = 255;
                else if (blue[r][c] < 0) blue[r][c] = 0;

                green[r][c] += generatePixelOffset();
                if (green[r][c] > 255) green[r][c] = 255;
                else if (green[r][c] < 0) green[r][c] = 0;
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }

    public double generatePixelOffset () {
        return (Math.random() - 0.5)*2 * prob;
    }
}
