package Interfaces;

public class Color {
    int r, g, b;
    double a;

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

    @Override
    public String toString() {
        return "(, "+r+", "+g+", "+b+", "+a+")";
    }
}
