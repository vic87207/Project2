package com.example.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PointDisplayApp extends Application {
    private static List<Point> initialPoints;
    private static List<Point> points = new ArrayList<>();
    private static Pane pane = new Pane();
    private List<Line> lines = new ArrayList<>();
    public static void setInitialPoints(List<Point> points) {
        initialPoints = points;
    }

    @Override
    public void start(Stage primaryStage) {
        if (initialPoints != null && !initialPoints.isEmpty()) {

            points.addAll(initialPoints);

            for (Point point : initialPoints) {
                Circle circle = new Circle(point.getX(), point.getY(), 3);
                pane.getChildren().add(circle);
            }
        }
        pane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {

                Point newPoint = new Point(e.getX(),e.getY());
                points.add(newPoint);
                Circle point = new Circle(e.getX(), e.getY(), 3);
                pane.getChildren().add(point);
                List<Point> maximalPoints = calculateMaximalPoints();
                visualizeMaximalPoints(maximalPoints);


            } else if (e.getButton() == MouseButton.SECONDARY) {

                Point clickPoint = new Point(e.getX(), e.getY());

                Point closestPoint = null;
                double minDistance = Double.MAX_VALUE;
                for (Point point : points) {
                    double distance = Math.sqrt(Math.pow(point.getX() - clickPoint.getX()
                            , 2) + Math.pow(point.getY() - clickPoint.getY(), 2));
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestPoint = point;
                    }
                }

                if (closestPoint != null) {
                    points.remove(closestPoint);

                    Circle toRemove = null;
                    for (var node : pane.getChildren()) {
                        if (node instanceof Circle) {
                            Circle circle = (Circle) node;
                            if (Math.abs(circle.getCenterX() - closestPoint.getX()) <
                                    0.1 && Math.abs(circle.getCenterY() - closestPoint.getY()) < 0.1) {
                                toRemove = circle;
                                break;
                            }
                        }
                    }

                    if (toRemove != null) {
                        pane.getChildren().remove(toRemove);
                    }
                }
                List<Point> maximalPoints = calculateMaximalPoints();
                visualizeMaximalPoints(maximalPoints);

            }
        });


        Scene scene = new Scene(pane, 500, 500);

        primaryStage.setTitle("Point Display");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private static void loadPointsFromFile(String filePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String[] parts = line.split(" ");
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                Point point = new Point(x, y);
                points.add(point);


                Circle circle = new Circle(x, y, 3);
                pane.getChildren().add(circle);
            });
        }
    }

    private void visualizeMaximalPoints(List<Point> maximalPoints) {
        // Clear existing lines to redraw based on updated maximal points
        lines.forEach(line -> pane.getChildren().remove(line));
        lines.clear();

        // Sort maximal points by x-coordinate for proper line connection
        maximalPoints.sort(Comparator.comparing(Point::getX));

        // Connect maximal points with lines
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point start = maximalPoints.get(i);
            Point end = maximalPoints.get(i + 1);
            Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            pane.getChildren().add(line);
            lines.add(line); // Keep track of added lines for future clearing
        }
    }



    public static void main(String[] args) throws IOException {
        loadPointsFromFile("points.txt");
        launch(args);
    }
    private List<Point> calculateMaximalPoints() {
        // Sort points in ascending order by x.
        points.sort(Comparator.comparing(Point::getX));

        List<Point> maximalPoints = new ArrayList<>();
        // Start with the rightmost point as it is guaranteed to be maximal.
        Point lastMaximalPoint = points.get(points.size() - 1);
        maximalPoints.add(lastMaximalPoint);

        // Iterate backwards from the second to last point.
        for (int i = points.size() - 2; i >= 0; i--) {
            Point currentPoint = points.get(i);
            // If the current point is above the last maximal point, it is also maximal.
            if (currentPoint.getY() > lastMaximalPoint.getY()) {
                maximalPoints.add(0, currentPoint); // Insert at the beginning to keep list sorted by x.
                lastMaximalPoint = currentPoint; // Update the last maximal point.
            }
        }

        return maximalPoints;
    }



}
