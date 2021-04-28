package org.mb.introspection;

public class Fruit {

    private String color;

    public Fruit() {
    }
    public Fruit(String color) {
        this.color = color;
    }

    public Fruit cut(Fruit fruit){
        return fruit;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "color='" + color + '\'' +
                '}';
    }
}
