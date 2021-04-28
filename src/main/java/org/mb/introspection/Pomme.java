package org.mb.introspection;

public class Pomme extends Fruit {

    public Pomme() {
        super();
    }

    public Pomme(String color) {
        super(color);
    }

    public Pomme cut(Pomme fruit) {
        return fruit;
    }

    @Override
    public String toString() {
        return "Pomme{"+ super.toString() +"}";
    }
}
