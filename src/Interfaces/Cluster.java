package Interfaces;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cluster {
    ArrayList<Color> points = new ArrayList<Color>();
    Color oldCenter;
    Color center;
    boolean stable = false;

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
        oldCenter = center;
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

        if (oldCenter.R() == center.R() && oldCenter.G() == center.G() && oldCenter.B() == center.B()) {
            stable = true;
            System.out.println("Stable");
        }else {
            System.out.println("Not stable, old: "+oldCenter+", new: "+center);
        }
    }

    public boolean isStable() {
        return stable;
    }

    public Color getCenter() {
        return center;
    }

    int getSize () {
        return points.size();
    }
}
