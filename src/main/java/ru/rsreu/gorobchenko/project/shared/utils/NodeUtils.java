package ru.rsreu.gorobchenko.project.shared.utils;

import javafx.scene.Node;

public class NodeUtils {
    public static void bindCenter(Node node, double boxWidth, double boxHeight) {
        _bindXCenter(node, boxWidth);
        _bindYCenter(node, boxHeight);
    }

    public static void bindXCenter(Node node, double boxWidth) {
        _bindXCenter(node, boxWidth);
    }

    public static void bindYCenter(Node node, double boxHeight) {
        _bindYCenter(node, boxHeight);
    }

    private static void _bindXCenter(Node node, double boxWidth) {
        double nodeWidth = node.getBoundsInLocal().getWidth();
        if (nodeWidth == 0) {
            nodeWidth = node.getBoundsInParent().getWidth();
        }

        node.setTranslateX(Math.abs(boxWidth - nodeWidth) / 2.0);
    }

    private static void _bindYCenter(Node node, double boxHeight) {
        node.setTranslateY(Math.abs(boxHeight - node.getBoundsInLocal().getHeight()) / 2.0);
    }
}
