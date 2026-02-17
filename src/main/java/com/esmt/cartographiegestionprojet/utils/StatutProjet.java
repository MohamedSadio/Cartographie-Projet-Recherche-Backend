package com.esmt.cartographiegestionprojet.utils;

public enum StatutProjet {
    EN_COURS("en_cours","EN_COURS"),
    TERMINE("termine","TERMINE"),
    SUSPENDU("suspendu","SUSPENDU");

    private final String libele;
    private final String code;

    StatutProjet(String libele, String code) {
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
