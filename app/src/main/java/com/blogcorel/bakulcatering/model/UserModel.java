package com.blogcorel.bakulcatering.model;

public class UserModel {

    static String user, username, token;
    static int level=0;

    public UserModel(String user, String username, int level) {
        this.user = user;
        this.username = username;
        this.level = level;
    }

    public UserModel(){

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserModel.username = username;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        UserModel.level = level;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserModel.token = token;
    }
}
