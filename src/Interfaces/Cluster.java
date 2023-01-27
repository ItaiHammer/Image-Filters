package Interfaces;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cluster {
    ArrayList<Color> points = new ArrayList<Color>();
    Color center;

    public Cluster (Color c) {
        center = c;
    }

    public void clear () {
        points = new ArrayList<Color>();
    }

    public void addPoint (Color point) {
        points.add(point);
    }

    public void reCalculateCenter () {
        if (points.size() != 0) {
            int r = 0;
            int g = 0;
            int b = 0;

            for (int i = 0; i < points.size(); i++) {
                r += points.get(i).R();
                g += points.get(i).G();
                b += points.get(i).B();
            }

            r = (int)(r/points.size());
            g = (int)(g/points.size());
            b = (int)(b/points.size());

            center = new Color(r, g, b);
        }
    }

    public Color getCenter() {
        return center;
    }

    int getSize () {
        return points.size();
    }
}
