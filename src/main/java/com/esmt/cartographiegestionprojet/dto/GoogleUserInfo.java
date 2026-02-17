package com.esmt.cartographiegestionprojet.dto;

public class GoogleUserInfo {
    private String sub;           // Google User ID
    private String email;
    private String name;
    private String given_name;    // Prénom
    private String family_name;   // Nom
    private String picture;       // URL photo de profil
    private boolean email_verified;

    // Constructeurs
    public GoogleUserInfo() {}

    // Getters et Setters
    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGiven_name() { return given_name; }
    public void setGiven_name(String given_name) { this.given_name = given_name; }
    public String getFamily_name() { return family_name; }
    public void setFamily_name(String family_name) { this.family_name = family_name; }
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
    public boolean isEmail_verified() { return email_verified; }
    public void setEmail_verified(boolean email_verified) { this.email_verified = email_verified; }
}