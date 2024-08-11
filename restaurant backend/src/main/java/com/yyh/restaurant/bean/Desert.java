package com.yyh.restaurant.bean;


/**
 * 甜品实体
 */
public class Desert {

    private int id;
    private String desertname;
    private int popularity;
    private int price;

    public Desert() {
    }

    public Desert(int id, String desertname, int popularity, int price) {
        this.id = id;
        this.desertname = desertname;
        this.popularity = popularity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDesertname() {
        return desertname;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesertname(String desertName) {
        this.desertname = desertName;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Desert{" +
                "id=" + id +
                ", desertname='" + desertname + '\'' +
                ", popularity=" + popularity +
                ", price=" + price +
                '}';
    }
}


