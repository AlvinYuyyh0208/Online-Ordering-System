package com.yyh.restaurant.bean;

/**
 * 小吃实体
 */
public class Snack {
    private int id;
    private String snackname;
    private int price;
    private int popularity;

    public Snack() {
    }

    public Snack(int id, String snackname, int price, int popularity) {
        this.id = id;
        this.snackname = snackname;
        this.price = price;
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public String getSnackname() {
        return snackname;
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

    public void setSnackname(String snackname) {
        this.snackname = snackname;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return "Snack{" +
                "id=" + id +
                ", snackname='" + snackname + '\'' +
                ", price=" + price +
                ", popularity=" + popularity +
                '}';
    }
}
