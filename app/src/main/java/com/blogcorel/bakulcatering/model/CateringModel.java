package com.blogcorel.bakulcatering.model;

public class CateringModel {
    String owner, catname,link, address, city, desc;
    int order;
    static String nama;

    public CateringModel(String owner, String catname, String link, String address, String city, String desc, int order) {
        this.owner = owner;
        this.catname = catname;
        this.link = link;
        this.address = address;
        this.city = city;
        this.desc = desc;
        this.order = order;
    }

    public CateringModel() {

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        CateringModel.nama = nama;
    }
}
