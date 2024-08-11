package com.yyh.restaurant.bean;


/**
 * 订单实体
 */
public class Order {
    private int id;
    private String ordername;
    private int num;
    private String user;
    private String time;
    private String note;
    private int state;
    private int price;



    public Order() {

    }

    public Order(int id, String ordername, int num, String user, String time, String note, int state,int price) {
        this.id = id;
        this.ordername = ordername;
        this.num = num;
        this.user = user;
        this.time = time;
        this.note = note;
        this.state = state;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public String getOrdername() {
        return ordername;
    }

    public int getNum() {
        return num;
    }

    public String getUser() {
        return user;
    }

    public String getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public int getPrice(){return price; }


    public void setId(int id) {
        this.id = id;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPrice(int price) { this.price=price;}


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", ordername='" + ordername + '\'' +
                ", num=" + num +
                ", user='" + user + '\'' +
                ", time='" + time + '\'' +
                ", note='" + note + '\'' +
                ", note='" + price + '\'' +
                ", state=" + state +
                '}';
    }

    public void setState(int i) {
    }
}
