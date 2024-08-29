package org.tejas.aop.service;

import org.tejas.aop.aspect.Loggable;
import org.tejas.aop.model.Circle;
import org.tejas.aop.model.Triangle;

public class ShapeService {
    private Circle circle;
    private Triangle triangle;

    @Loggable
    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Triangle getTriangle() {
        return triangle;
    }

    public void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }
}
