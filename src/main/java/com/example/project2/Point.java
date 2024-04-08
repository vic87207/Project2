package com.example.project2;

public class Point implements Comparable<Point>{
    private final double x;
    private final double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        if (Double.compare(getX(), point.getX()) != 0) return false;
        return Double.compare(getY(), point.getY()) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getX());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getY());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Point other) {
        return Double.compare(this.x, other.x);
    }

    public boolean isBelowAndToLeftOf(Point other) {
        return this.x < other.x && this.y < other.y;
    }

}
