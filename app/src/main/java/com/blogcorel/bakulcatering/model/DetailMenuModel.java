package com.blogcorel.bakulcatering.model;

public class DetailMenuModel {
    static String owner, m_name, m_link1, m_link2, m_link3;
    static int m_price, m_portion, m_fee, m_order, id;
    static boolean fav = true;

    public DetailMenuModel() {
    }

    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        DetailMenuModel.owner = owner;
    }

    public static String getM_name() {
        return m_name;
    }

    public static void setM_name(String m_name) {
        DetailMenuModel.m_name = m_name;
    }

    public static String getM_link1() {
        return m_link1;
    }

    public static void setM_link1(String m_link1) {
        DetailMenuModel.m_link1 = m_link1;
    }

    public static String getM_link2() {
        return m_link2;
    }

    public static void setM_link2(String m_link2) {
        DetailMenuModel.m_link2 = m_link2;
    }

    public static String getM_link3() {
        return m_link3;
    }

    public static void setM_link3(String m_link3) {
        DetailMenuModel.m_link3 = m_link3;
    }

    public static int getM_price() {
        return m_price;
    }

    public static void setM_price(int m_price) {
        DetailMenuModel.m_price = m_price;
    }

    public static int getM_portion() {
        return m_portion;
    }

    public static void setM_portion(int m_portion) {
        DetailMenuModel.m_portion = m_portion;
    }

    public static int getM_fee() {
        return m_fee;
    }

    public static void setM_fee(int m_fee) {
        DetailMenuModel.m_fee = m_fee;
    }

    public static int getM_order() {
        return m_order;
    }

    public static void setM_order(int m_order) {
        DetailMenuModel.m_order = m_order;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        DetailMenuModel.id = id;
    }

    public static boolean isFav() {
        return fav;
    }

    public static void setFav(boolean fav) {
        DetailMenuModel.fav = fav;
    }
}
