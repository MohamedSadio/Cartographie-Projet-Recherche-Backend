package com.esmt.cartographiegestionprojet.utils;

public enum Role {
    CANDIDAT("Candidat","CANDIDAT"),
    GESTIONNAIRE("gestionnaire","GESTIONNAIRE"),
    ADMIN("administrateur","ADMINISTRATEUR");

    private final String libele;
    private final String code;

    Role(String libele, String code) {
        this.libele = libele;
        this.code = code;
    }

    public String getLibele() {
        return libele;
    }

    public String getCode() {
        return code;
    }
}
