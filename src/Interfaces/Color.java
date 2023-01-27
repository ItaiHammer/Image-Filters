package Interfaces;

public class Color {
    private int r, g, b;
    private double a;

    public Color (int r1, int g1, int b1, double a1) {
        r = checkRGBBounderies(r1);
        g = checkRGBBounderies(g1);
        b = checkRGBBounderies(b1);

        a = checkAlphaBounderies(a1);
    }

    public Color (int r1, int g1, int b1) {
        r = checkRGBBounderies(r1);
        g = checkRGBBounderies(g1);
        b = checkRGBBounderies(b1);
        a = 1;
    }

    public void setRGB (int r2, int g2, int b2) {
        r = r2;
        g = g2;
        b = b2;
    }

    public void setR (int r2) {
        r = r2;
    }

    public void setG (int g2) {
        g = g2;
    }

    public void setB (int b2) {
        b = b2;
    }
    
    public String getHex () {
        String hex = "";

        // looping for r, g, and b
        hex += convertNumbertoHexText(r);
        hex += convertNumbertoHexText(g);
        hex += convertNumbertoHexText(b);

        return hex;
    }

    private String convertNumbertoHexText(int num) {
        String hex = "";

        int temp = num;
        int multiplier = 0;
        int remainder = num % 16;
        for (int j = 0; j < num; j++) {
            if (temp > 15 && multiplier != 15) {
                temp -= 15;
                multiplier++;
            }
        }

        if (num > 159) {
            multiplier--;
        }

        hex = hex + convertSingleNumberToLetter(multiplier) + convertSingleNumberToLetter(remainder);

        return hex;
    }

    private String convertSingleNumberToLetter(int num) {
        String letter = ""+num;

        if (num >= 10) {
            if (num == 10) {
                letter = "A";
            }else if (num == 11) {
                letter = "B";
            }else if (num == 12) {
                letter = "C";
            }else if (num == 13) {
                letter = "D";
            }else if (num == 14) {
                letter = "E";
            }else if (num == 15) {
                letter = "F";
            }
        }

        return letter;
    }

    public double[] getHSL() {
        double[] hsl = new double[3];
        double rd = (double) r/255;
        double gd = (double) g/255;
        double bd = (double) b/255;
        double max = Math.max(Math.max(rd, gd), bd);
        double min = Math.min(Math.min(rd, gd), bd);
        double h, s, l = (max + min) / 2;
    
        if(max == min) {
            h = 0;
        } else if (max == rd && gd >= bd) {
            h = 60 * (gd - bd) / (max - min);
        } else if (max == rd && gd < bd) {
            h = 60 * (gd - bd) / (max - min) + 360;
        } else if (max == gd) {
            h = 60 * (bd - rd) / (max - min) + 120;
        } else {
            h = 60 * (rd - gd) / (max - min) + 240;
        }
        s = (max == 0) ? 0 : (1 - (min/max));
        hsl[0] = h;
        hsl[1] = s;
        hsl[2] = l;
        return hsl;
    }

    public int checkRGBBounderies (int value) {
        int newValue = value;

        if (value > 255) {
            newValue = 255;
        }else if (value < 0) {
            newValue = 0;
        }

        return newValue;
    }

    public double checkAlphaBounderies (double alpha) {
        double newAplha = alpha;

        if (alpha > 1) {
            newAplha = 255;
        }else if (alpha < 0) {
            newAplha = 0;
        }

        return newAplha;
    }

    public int R() {
        return r;
    }
    
    public int G() {
        return g;
    }
    
    public int B() {
        return b;
    }

    public double A () {
        return a;
    }

    public String toString() {
        return "("+r+", "+g+", "+b+", "+a+")";
    }
}
