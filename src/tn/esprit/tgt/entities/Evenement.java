/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public class Evenement {
    
 private int id;
    private String nom;
    private String description ;
    private Date dateDebut;
    private String lieu;
    private Date dateFin;
    private int nbParticipantMax;
    private SousCategorie sousCategorie;
    private String image;
    private Agence agence;

    public Evenement() {
    }

    public Evenement(int id, String nom, String description, Date dateDebut, String lieu, Date dateFin, int nbParticipantMax, SousCategorie sousCategorie, String image, Agence agence) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.lieu = lieu;
        this.dateFin = dateFin;
        this.nbParticipantMax = nbParticipantMax;
        this.sousCategorie = sousCategorie;
        this.image = image;
        this.agence = agence;
    }

    public Evenement(String nom, String description, Date dateDebut, String lieu, Date dateFin, int nbParticipantMax, SousCategorie sousCategorie, String image, Agence agence) {
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.lieu = lieu;
        this.dateFin = dateFin;
        this.nbParticipantMax = nbParticipantMax;
        this.sousCategorie = sousCategorie;
        this.image = image;
        this.agence = agence;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbParticipantMax() {
        return nbParticipantMax;
    }

    public void setNbParticipantMax(int nbParticipantMax) {
        this.nbParticipantMax = nbParticipantMax;
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
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
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", dateDebut=" + dateDebut + ", lieu=" + lieu + ", dateFin=" + dateFin + ", nbParticipantMax=" + nbParticipantMax + ", sousCategorie=" + sousCategorie + ", image=" + image + ", agence=" + agence + '}';
    }
    
    
    
    
    
    
}

