package com.epicorp.epicash.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Account {

    private int id;
    private String card_number;
    private String validation_date;
    private String name;
    private int fk_user;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;



    public Account() {
    }

    public Account(int id, String card_number, String validation_date, String name, int fk_user, Date createdAt, Date updatedAt) {
        this.card_number = card_number;
        this.validation_date = validation_date;
        this.name = name;
        this.fk_user = fk_user;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getValidation_date() {
        return validation_date;
    }

    public void setValidation_date(String validation_date) {
        this.validation_date = validation_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFk_user() {
        return fk_user;
    }

    public void setFk_user(int fk_user) {
        this.fk_user = fk_user;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
