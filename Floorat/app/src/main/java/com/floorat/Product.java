package com.floorat;

public class Product {

    public String name;
    public Integer price;
    public Product(String name, Integer price) {
        super();
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }

}