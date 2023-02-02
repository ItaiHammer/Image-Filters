package Filters;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Interfaces.Cluster;
import Interfaces.Color;
import Interfaces.PixelFilter;
import core.DImage;

public class KMeansCluster implements PixelFilter {
    int k;

    public KMeansCluster () {
        k = Integer.parseInt(JOptionPane.showInputDialog(null, "How many colors?"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        // init
        ArrayList<Cluster> clusters = initClusters();

        ArrayList<Color> points = makePointsList(red, green, blue);

        boolean stable = false;

        while (!stable) {
            clearClusters(clusters);
            assignPointsToClusters(clusters, points);
            recalculateClustersCenters(clusters);

            stable = areAllClustersStable(clusters);
        }

        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                Color newColor = getNewColor(clusters, red[r][c], green[r][c], blue[r][c]);
                red[r][c] = (short) newColor.R();
                green[r][c] = (short) newColor.G();
                blue[r][c] = (short) newColor.B();
            }
        }

        img.setColorChannels(red, green, blue);
        return img;
    }

    private boolean areAllClustersStable(ArrayList<Cluster> clusters) {
        boolean stable = true;
        for (Cluster c : clusters) {
            if (!c.isStable()) {
                stable = false;
            }
        }

        System.out.println("All clusters are stable: "+stable);

        return stable;
    }

    private void clearClusters(ArrayList<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private Color getNewColor(ArrayList<Cluster> clusters, short r, short g, short b) {
        Color point = new Color(r, g, b);
        double closestDistance = Double.MAX_VALUE;
        int indexOfClosest = 0;
        
        //looping through all the clusters
        for (int j = 0; j < clusters.size(); j++) {
            double distance = getDistance(point.R(), point.G(), point.B(), clusters.get(j).getCenter().R(), clusters.get(j).getCenter().G(), clusters.get(j).getCenter().B());
            if (distance < closestDistance) {
                closestDistance = distance;
                indexOfClosest = j;
            }
        }
        
        return clusters.get(indexOfClosest).getCenter();
    }

    private void recalculateClustersCenters(ArrayList<Cluster> clusters) {
        for (Cluster c : clusters) {
            c.reCalculateCenter();
        }
    }

    private void assignPointsToClusters(ArrayList<Cluster> clusters, ArrayList<Color> points) {
        // looping through points
        for (int i = 0; i < points.size(); i++) {
            Color point = points.get(i);

            double closestDistance = Double.MAX_VALUE;
            int indexOfClosest = 0;
            
            //looping through all the clusters
            for (int j = 0; j < clusters.size(); j++) {
                double distance = getDistance(point.R(), point.G(), point.B(), clusters.get(j).getCenter().R(), clusters.get(j).getCenter().G(), clusters.get(j).getCenter().B());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    indexOfClosest = j;
                }
            }

            clusters.get(indexOfClosest).addPoint(point);
        }
    }

    private ArrayList<Color> makePointsList(short[][] red, short[][] green, short[][] blue) {
        ArrayList<Color> points = new ArrayList<>();

        // looping throught the image
        for (int r = 0; r < red.length; r++) {
            for (int c = 0; c < red[r].length; c++) {
                Color point = new Color(red[r][c], green[r][c], blue[r][c]);
                points.add(point);
            }
        }

        return points;
    }

    private ArrayList<Cluster> initClusters() {
        ArrayList<Cluster> clusters = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Color piz = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
            Cluster kak = new Cluster(piz);

            clusters.add(kak);
        }

        return clusters;
    }

    private double getDistance (int i, int j, int k2, int k3, int k4,int k5) {
        return Math.sqrt(Math.pow(Math.abs(i - k3), 2) + Math.pow(Math.abs(j - k4), 2)+ Math.pow(Math.abs(k2 - k5), 2));
    }
}
