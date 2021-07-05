package com.blogcorel.bakulcatering.model;

public class MenuModel {
    String owner, m_name, m_link1, m_link2, m_link3;
    int m_price, m_portion, m_fee, m_order, id;
    boolean check = false;
    boolean fav = false;

    public MenuModel(String owner, String m_name, String m_link1, String m_link2, String m_link3,
                     int m_price, int m_portion, int m_fee, int m_order, int id, boolean fav) {
        this.owner = owner;
        this.m_name = m_name;
        this.m_link1 = m_link1;
        this.m_link2 = m_link2;
        this.m_link3 = m_link3;
        this.m_price = m_price;
        this.m_portion = m_portion;
        this.m_fee = m_fee;
        this.m_order = m_order;
        this.id = id;
        this.fav = fav;
    }

    public MenuModel() {
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getM_link1() {
        return m_link1;
    }

    public void setM_link1(String m_link1) {
        this.m_link1 = m_link1;
    }

    public String getM_link2() {
        return m_link2;
    }

    public void setM_link2(String m_link2) {
        this.m_link2 = m_link2;
    }

    public String getM_link3() {
        return m_link3;
    }

    public void setM_link3(String m_link3) {
        this.m_link3 = m_link3;
    }

    public int getM_price() {
        return m_price;
    }

    public void setM_price(int m_price) {
        this.m_price = m_price;
    }

    public int getM_portion() {
        return m_portion;
    }

    public void setM_portion(int m_portion) {
        this.m_portion = m_portion;
    }

    public int getM_fee() {
        return m_fee;
    }

    public void setM_fee(int m_fee) {
        this.m_fee = m_fee;
    }

    public int getM_order() {
        return m_order;
    }

    public void setM_order(int m_order) {
        this.m_order = m_order;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean notFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }
}
