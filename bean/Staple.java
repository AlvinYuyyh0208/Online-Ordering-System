package com.yyh.restaurant.bean;

public class Staple {

    private int id;
    private String staplename;
    private int price;
    private int popularity;

    public Staple() {
    }

    public Staple(int id, String staplename, int price, int popularity) {
        this.id = id;
        this.staplename = staplename;
        this.price = price;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public String getStaplename() {
        return staplename;
    }

    public int getPrice() {
        return price;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStaplename(String staplename) {
        this.staplename = staplename;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Staple{" +
                "id=" + id +
                ", staplename='" + staplename + '\'' +
                ", price=" + price +
                ", popularity=" + popularity +
                '}';
    }
}
