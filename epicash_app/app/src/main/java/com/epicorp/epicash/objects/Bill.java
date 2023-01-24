package com.epicorp.epicash.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Bill {

    private int id;
    private String reference;

    // TO DO
    // Gérer les lignes de la facture puis générer un constructeur vide, un constructeur full, les getters et les setters

    private int fk_user;
    private String total;
    @JsonIgnore
    private LocalDateTime createdAt;
}
