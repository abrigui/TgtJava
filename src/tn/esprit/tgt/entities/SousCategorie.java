/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public class SousCategorie {
    
private int id;
    private String libelle;
    private Categorie categorie;

    public SousCategorie() {
    }

    public SousCategorie(int id, String libelle, Categorie categorie) {
        this.id = id;
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public SousCategorie(String libelle, Categorie categorie) {
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public SousCategorie(int id) {
        this.id = id;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
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
        final SousCategorie other = (SousCategorie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.libelle, other.libelle)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SousCategorie{" + "id=" + id + ", libelle=" + libelle + ", categorie=" + categorie + '}';
    }
    
    
    
}

