package com.blogcorel.bakulcatering.model;

public class FavoriteModel {

    private String email;
    private int id, id_menu;
    private String owner, name, link1, link2, link3;
    private int price, portion, fee, order;

    public FavoriteModel() {
    }

    public FavoriteModel(String email, int id, int id_menu, String owner, String name, String link1,
                         String link2, String link3, int price, int portion, int fee, int order) {
        this.email = email;
        this.id = id;
        this.id_menu = id_menu;
        this.owner = owner;
        this.name = name;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
        this.price = price;
        this.portion = portion;
        this.fee = fee;
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_menu() {
        return id_menu;
    }

    public void setId_menu(int id_menu) {
        this.id_menu = id_menu;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
