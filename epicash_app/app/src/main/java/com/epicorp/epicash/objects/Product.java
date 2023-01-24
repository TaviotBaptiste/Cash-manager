package com.epicorp.epicash.objects;

import android.text.format.DateFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;

public class Product {

    private int id;
    private String libelle;
    private String prix;
    private String desc;
    private String image;
    private boolean activated;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;

    public Product() {
    }

    public Product(int id, String libelle, String prix, String desc, String image, boolean activated, Date createdAt, Date updatedAt) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.desc = desc;
        this.image = image;
        this.activated = activated;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Product(String libelle, String prix, String image, String desc){
        this.libelle = libelle;
        this.prix = prix;
        this.image = image;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {return image; }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
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
