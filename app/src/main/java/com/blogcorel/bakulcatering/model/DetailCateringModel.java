package com.blogcorel.bakulcatering.model;

public class DetailCateringModel {
    static String owner, catname,link, address, city, desc;
    static int order;

    public DetailCateringModel() {
    }

    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        DetailCateringModel.owner = owner;
    }

    public static String getCatname() {
        return catname;
    }

    public static void setCatname(String catname) {
        DetailCateringModel.catname = catname;
    }

    public static String getLink() {
        return link;
    }

    public static void setLink(String link) {
        DetailCateringModel.link = link;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DetailCateringModel.address = address;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        DetailCateringModel.city = city;
    }

    public static String getDesc() {
        return desc;
    }

    public static void setDesc(String desc) {
        DetailCateringModel.desc = desc;
    }

    public static int getOrder() {
        return order;
    }

    public static void setOrder(int order) {
        DetailCateringModel.order = order;
    }
}
