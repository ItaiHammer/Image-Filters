package Filters;

import javax.swing.JOptionPane;

import Interfaces.PixelFilter;

public class Tile implements PixelFilter {
    public Tile () {
        int x = JOptionPane.showInputDialog(null, "How big of a grid on the x?");
        int y = JOptionPane.showInputDialog(null, "How big of a grid on the y?");
    }
}
