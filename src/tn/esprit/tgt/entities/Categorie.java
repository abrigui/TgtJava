/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public class Categorie {
    private int id;
    private String libelle;
    private ArrayList<SousCategorie> sousCategories= new ArrayList<>();

    public Categorie() {
    }

    public Categorie(int id, String libelle) {
 
        this.id=id;
        this.libelle = libelle;
    }

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    public Categorie(int id) {
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

    public ArrayList<SousCategorie> getSousCategories() {
        return sousCategories;
    }

    public void setSousCategories(ArrayList<SousCategorie> sousCategories) {
        this.sousCategories = sousCategories;
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
        final Categorie other = (Categorie) obj;
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
        return  libelle ;
    }
    
    
    
}

