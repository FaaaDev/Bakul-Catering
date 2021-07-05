package com.blogcorel.bakulcatering.model;

public class DetailOrder {

    private static String id, catname, menu, username, alamat, hp, torder, tproses, status, email;
    private static int total;

    public DetailOrder() {

    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        DetailOrder.id = id;
    }

    public static String getCatname() {
        return catname;
    }

    public static void setCatname(String catname) {
        DetailOrder.catname = catname;
    }

    public static String getMenu() {
        return menu;
    }

    public static void setMenu(String menu) {
        DetailOrder.menu = menu;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DetailOrder.username = username;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        DetailOrder.alamat = alamat;
    }

    public static String getHp() {
        return hp;
    }

    public static void setHp(String hp) {
        DetailOrder.hp = hp;
    }

    public static String getTorder() {
        return torder;
    }

    public static void setTorder(String torder) {
        DetailOrder.torder = torder;
    }

    public static String getTproses() {
        return tproses;
    }

    public static void setTproses(String tproses) {
        DetailOrder.tproses = tproses;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        DetailOrder.status = status;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        DetailOrder.total = total;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DetailOrder.email = email;
    }
}
