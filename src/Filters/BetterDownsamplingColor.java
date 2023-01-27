package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;
import core.DImage;

public class BetterDownsamplingColor implements PixelFilter {
    double size;

    public BetterDownsamplingColor () {
        size = Double.parseDouble(JOptionPane.showInputDialog(null, "What size? (%)"));
    }

    public DImage processImage (DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        short[][] finalRed = new short[(int)(((double)(size)/(double)(100)) * red.length)][(int)(((double)(size)/(double)(100)) * red[0].length)];
        short[][] finalGreen = new short[(int)(((double)(size)/(double)(100)) * green.length)][(int)(((double)(size)/(double)(100)) * green[0].length)];
        short[][] finalBlue = new short[(int)(((double)(size)/(double)(100)) * blue.length)][(int)(((double)(size)/(double)(100)) * blue[0].length)];
        
        int samplingPixelSizeY = red[0].length/finalRed[0].length;
        int samplingPixelSizeX = red.length/finalRed.length;

        // for each pixel of the new grid
        for (int r = 0; r < finalRed.length; r++) {
            for (int c = 0; c < finalRed[r].length; c++) {

                // averages grid
                for (int r2 = 0; r2 <= samplingPixelSizeX; r2++) {
                    for (int c2 = 0; c2 <= samplingPixelSizeY; c2++) {
                        if (r2 + (r * samplingPixelSizeX) < red.length && c2 + (c * samplingPixelSizeY) < red[r].length) {
                            finalRed[r][c] += red[r2 + (r * samplingPixelSizeX)][c2 + (c * samplingPixelSizeY)];
                            finalGreen[r][c] += green[r2 + (r * samplingPixelSizeX)][c2 + (c * samplingPixelSizeY)];
                            finalBlue[r][c] += blue[r2 + (r * samplingPixelSizeX)][c2 + (c * samplingPixelSizeY)];
                        }
                    }
                }
                finalRed[r][c] = (short)((finalRed[r][c] / ((samplingPixelSizeX+1) * (samplingPixelSizeY+1))));
                finalGreen[r][c] = (short)((finalGreen[r][c] / ((samplingPixelSizeX+1) * (samplingPixelSizeY+1))));
                finalBlue[r][c] = (short)((finalBlue[r][c] / ((samplingPixelSizeX+1) * (samplingPixelSizeY+1))));
            }
        }

        DImage output = new DImage( finalBlue[0].length, finalBlue.length);
        output.setColorChannels(finalRed, finalGreen, finalBlue);
        return output;
    }
}
