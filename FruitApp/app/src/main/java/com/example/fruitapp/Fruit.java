package com.example.fruitapp;

public class Fruit {
    public String fruitName;
    public String fruitFamily;
    public double fruitSugar;
    public double fruitCalories;
    public double fruitCarbohydrate;

    public double fruitProtein;

    public Fruit(String fruitName, String fruitFamily, double fruitSugar, double fruitCalories, double fruitCarbohydrate, double fruitProtein) {
        this.fruitName = fruitName;
        this.fruitFamily = fruitFamily;
        this.fruitSugar = fruitSugar;
        this.fruitCalories = fruitCalories;
        this.fruitCarbohydrate = fruitCarbohydrate;
        this.fruitProtein = fruitProtein;
    }

    //getter and setter methods
    public String getFruitName() {
        return "name: " + fruitName;
    }


    public String getFruitFamily() {
        return "family: " + fruitFamily;
    }

    public String getFruitSugar() {
        return "sugar: " + fruitSugar;
    }

    public String getFruitCalories() {
        return "calories: " + fruitCalories;
    }

    public String getFruitCarbohydrate() {
        return "carbohydrate: " + fruitCarbohydrate;
    }

    public String getFruitProtein() {
        return "protein: " + fruitProtein;
    }
}


