package com.example.lioratton.swaiter;


public class Dish {
    public String id;
    public String name;
    public double price;

    public Dish(String id, String name, double price)
    {
        this.id=id;
        this.name=name;
        this.price=price;
    }

    public Dish() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name+" "+price+".";
    }
}