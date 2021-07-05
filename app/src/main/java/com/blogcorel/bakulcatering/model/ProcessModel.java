package com.blogcorel.bakulcatering.model;

public class ProcessModel {
    static String email, menu, alamat, hp, tanggal_m, tanggal_k, owner, nama;
    static int harga, pajak, total;


    public ProcessModel() {

    }

    public static String getMenu() {
        return menu;
    }

    public static void setMenu(String menu) {
        ProcessModel.menu = menu;
    }

    public static String getAlamat() {
        return alamat;
    }

    public static void setAlamat(String alamat) {
        ProcessModel.alamat = alamat;
    }

    public static String getHp() {
        return hp;
    }

    public static void setHp(String hp) {
        ProcessModel.hp = hp;
    }

    public static int getHarga() {
        return harga;
    }

    public static void setHarga(int harga) {
        ProcessModel.harga = harga;
    }

    public static int getPajak() {
        return pajak;
    }

    public static void setPajak(int pajak) {
        ProcessModel.pajak = pajak;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        ProcessModel.total = total;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        ProcessModel.email = email;
    }

    public static String getTanggal_m() {
        return tanggal_m;
    }

    public static void setTanggal_m(String tanggal_m) {
        ProcessModel.tanggal_m = tanggal_m;
    }

    public static String getTanggal_k() {
        return tanggal_k;
    }

    public static void setTanggal_k(String tanggal_k) {
        ProcessModel.tanggal_k = tanggal_k;
    }

    public static String getOwner() {
        return owner;
    }

    public static void setOwner(String owner) {
        ProcessModel.owner = owner;
    }

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        ProcessModel.nama = nama;
    }


}
