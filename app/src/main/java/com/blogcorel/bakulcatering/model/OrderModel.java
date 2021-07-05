package com.blogcorel.bakulcatering.model;

import java.util.List;

public class OrderModel {
    int harga, porsi, pajak, total;
    String menu, link1, link2, link3;
    List<OrderModel> om;

    public OrderModel(int harga, int porsi, int pajak, String menu, String link1, String link2, String link3) {
        this.harga = harga;
        this.porsi = porsi;
        this.pajak = pajak;
        this.menu = menu;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
    }

    public OrderModel() {

    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getPorsi() {
        return porsi;
    }

    public void setPorsi(int porsi) {
        this.porsi = porsi;
    }

    public int getPajak() {
        return pajak;
    }

    public void setPajak(int pajak) {
        this.pajak = pajak;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
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

    public List<OrderModel> getOm() {
        return om;
    }

    public void setOm(List<OrderModel> om) {
        this.om = om;
    }
}
