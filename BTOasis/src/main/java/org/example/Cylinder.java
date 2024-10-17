package org.example;

public class Cylinder extends Circle {
    private double height;

    public Cylinder(){}

    /**
     * Constructor voi tham so radius.
     * @param radius ban kinh hinh tru.
     */
    public Cylinder(double radius) {
        super(radius);
    }

    /**
     * Constructor voi tham so radius va height.
     * @param radius ban kinh hinh tru.
     * @param height chieu cao hinh tru.
     */
    public Cylinder(double radius, double height) {
        super(radius);
        this.height = height;
    }

    /**
     * Constructor voi tham so radius, color va height.
     * @param radius ban kinh hinh tru.
     * @param color mau sac hinh tru.
     * @param height chieu cao hinh tru.
     */
    public Cylinder(double radius, double height, String color) {
        super(radius, color);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Tinh the tich hinh tru.
     * @return the tich hinh tru.
     */
    public double getVolume() {
        return getArea() * height;
    }

    /**
     * Chuyen doi doi tuong Cylinder thanh chuoi.
     * @return chuoi chua thong tin cua Cylinder.
     */
    public String toString() {
        return String.format("Cylinder[%s, height=%.2f]",
                super.toString(), height);
    }

    /**
     * Tinh dien tich xung quanh hinh tru.
     * @return dien tich xung quanh hinh tru.
     */
    public double getArea() {
        return 2 * pi * getRadius() * (getRadius() + height);
    }
}
