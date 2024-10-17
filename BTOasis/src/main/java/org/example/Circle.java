package org.example;

public class Circle {
    private double radius;
    private String color;
    protected final double pi = 3.14;

    /**
     * Constructor mac dinh.
     */
    public Circle() {
    }

    /**
     * Constructor voi tham so radius.
     * @param radius ban kinh hinh tron.
     */
    public Circle(double radius) {
        this.radius = radius;
    }

    /**
     * Constructor voi tham so radius va color.
     * @param radius ban kinh hinh tron.
     * @param color mau sac hinh tron.
     */
    public Circle(double radius, String color) {
        this.radius = radius;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Tinh dien tich.
     * @return  dien tich hinh tron.
     */
    public double getArea() {
        return pi * radius * radius;
    }

    /**
     * Chuyen doi doi tuong Circle thanh chuoi.
     * @return chuoi chua thong tin cua Circle.
     */
    public String toString() {
        return "Circle[radius=" + radius
                + ",color=" + color + "]";
    }
}
