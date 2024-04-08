package com.example.project2;

import javafx.application.Application;
import java.util.List;

public class Project2 {
    public static void main(String[] args) {
        List<Point> initialPoints = PointLoader.loadPointsFromFile("C:/Users/vic87/IdeaProjects/Project2/src/main/java/com/example/project2/points.txt");
        PointDisplayApp.setInitialPoints(initialPoints);
        Application.launch(PointDisplayApp.class, args);
    }
}
