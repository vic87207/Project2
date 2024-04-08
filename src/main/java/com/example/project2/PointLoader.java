package com.example.project2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PointLoader {
    public static List<Point> loadPointsFromFile(String filePath) {
        List<Point> points = new ArrayList<>();
        try {
            Files.lines(Paths.get(filePath)).forEach(line -> {
                String[] parts = line.split("\\s+");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                points.add(new Point(x, y));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }
}
