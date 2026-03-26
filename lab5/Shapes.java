// Import necessary packages
import java.lang.Math.*;

// Abstract class Shape
abstract class Shape {
    protected String color;

    // constructor, intiate color
    Shape(String color) {
        this.color = color;
    }

    abstract double getArea();  // abstract method

    public void displayColor() {
        System.out.println("Shape color: " + color);
    }

}

// Subclass Circle
class Circle extends Shape {
    private double radius;  // initialise new variable
    
    // Constructor
    public Circle(String color, double radius) {
        super(color);  // Grabbing (inheriting?) colour from shape
        
        // Positive Check for radius
        if (radius > 0) {
            this.radius = radius;
        }
        else {
            this.radius = 0;
        }
    }

    // Override happens here, we take over abstract method inherited from the abstract shape class
    @Override
    double getArea() {
        return  Math.PI * (radius * radius);
    }

}

// Subclass Rectangle
class Rectangle extends Shape {
    private double width;
    private double height;
    
    // Constructor
    public Rectangle(String color, double width, double height) {
        super(color);

        // Similar to circle, positive check for width and height
        if (width > 0) {
            this.width = width;
        }
        else {
            this.width = 0;
        }
        if (height > 0) {
            this.height = height;
        }
        else {
            this.height = 0;
        }
    }

    @Override
    double getArea() {
        return width * height;
    }
}

/*

abstract class Shape {
    private String color;

    // constructor, intiate color
    public Shape(String color) {
        this.color = color;
    }

    abstract double getArea() {
        
    }
}
*/