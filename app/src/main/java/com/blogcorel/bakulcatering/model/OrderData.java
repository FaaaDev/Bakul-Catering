package com.blogcorel.bakulcatering.model;

public class OrderData {

    String id, catname, menu, username, alamat, hp, torder, tproses, status, email;
    int total;

    public OrderData() {
    }

    public OrderData(String id, String catname, String menu, String username, String alamat, String hp, String torder, String tproses, String status, String email, int total) {
        this.id = id;
        this.catname = catname;
        this.menu = menu;
        this.username = username;
        this.alamat = alamat;
        this.hp = hp;
        this.torder = torder;
        this.tproses = tproses;
        this.status = status;
        this.total = total;
        this.email = email;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTorder() {
        return torder;
    }

    public void setTorder(String torder) {
        this.torder = torder;
    }

    public String getTproses() {
        return tproses;
    }

    public void setTproses(String tproses) {
        this.tproses = tproses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
