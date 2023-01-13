package Filters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import Interfaces.PixelFilter;
import core.DImage;

public class BiggestBird implements PixelFilter {

    public BiggestBird() {
        
    }

    public DImage processImage (DImage img) {
        short[][] grid = img.getBWPixelGrid();
        ArrayList<ArrayList<Short>> birdGrid = new ArrayList<ArrayList<Short>>();
        

        String file = readFile("./birdFilter.txt");
        String[] row = file.split("\n");
        for (int i = 0; i < row.length; i++) {
            ArrayList<ArrayList<Short>> newRow = new ArrayList<ArrayList<Short>>();
            String[] col = row[i].split(", ");
            for (int j = 0; j < grid.length; j++) {
                
            }
        }
        

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                // birdGrid += grid[r][c]+", ";
            }
        }

        img.setPixels(grid);
        return img;
    }

    public static void writeDataToFile(String filePath, String data) {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {
    
    
            writer.println(data);
    
    
        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        }catch (IOException err) {
            System.err.println("There was a problem reading to the file: " + fileName);
            err.printStackTrace();
        }
        return null;
    }
}
