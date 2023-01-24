package com.epicorp.epicash;


public class MyData {
    private static MyData instance = new MyData();

    // Getter-Setters
    public static MyData getInstance() {
        return instance;
    }

    public static void setInstance(MyData instance) {
        MyData.instance = instance;
    }

    private String idUser;


    private MyData() {

    }

    public String getIdUser() {
        return idUser;
    }


    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }


}