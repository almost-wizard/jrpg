package ru.rsreu.gorobchenko.project.shared.utils;

import javafx.geometry.Point2D;

public class VectorUtils {
    public static Point2D perpendicular(final Point2D vector) {
        return new Point2D(vector.getY(), -vector.getX());
    }
}
