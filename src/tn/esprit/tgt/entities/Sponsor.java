/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Sponsor {
    
 private int id;
    private String nom;
    private String adresse ;
    private int telephone;
    private String email;
    private String logo;
    private ArrayList<Agence> agences=new ArrayList<>();

    public Sponsor() {
    }

    public Sponsor(int id, String nom, String adresse, int telephone, String email, String logo) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.logo = logo;
    }

    public Sponsor(String nom, String adresse, int telephone, String email, String logo) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.logo = logo;
    }

    public Sponsor(int id) {
        this.id = id;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setddresse(String addresse) {
        this.adresse = addresse;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ArrayList<Agence> getAgences() {
        return agences;
    }

    public void setAgences(ArrayList<Agence> agences) {
        this.agences = agences;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sponsor other = (Sponsor) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom;
    }
    
    
    
   
    
}
