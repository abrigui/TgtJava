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
public class Agence {
    
 private int id;
    private String nom;
    private String matriculeFiscale;
    private String email;
    private String adresse;
    private int telephone;
    private int fax;
    private String site;
    private String logo ;
    private Sponsor sponsor;
    private User utilisateurProfessionnel;
    private int etat;
    private ArrayList<Evenement> evenements= new ArrayList<>();

    public Agence() {
    }

    public Agence(int id, String nom, String matriculeFiscale, String email, String adresse, int telephone, int fax, String site, String logo, Sponsor sponsor, User utilisateurProfessionnel) {
        this.id = id;
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.fax = fax;
        this.site = site;
        this.logo = logo;
        this.sponsor = sponsor;
        this.utilisateurProfessionnel = utilisateurProfessionnel;
        this.etat = 0;
    }

    public Agence(int id, String nom, String matriculeFiscale, String email, String adresse, int telephone, int fax, String site, String logo, Sponsor sponsor, User utilisateurProfessionnel, int etat) {
        this.id = id;
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.fax = fax;
        this.site = site;
        this.logo = logo;
        this.sponsor = sponsor;
        this.utilisateurProfessionnel = utilisateurProfessionnel;
        this.etat = etat;
    }

    public Agence(String nom, String matriculeFiscale, String email, String adresse, int telephone, int fax, String site, String logo, Sponsor sponsor, User utilisateurProfessionnel) {
        this.nom = nom;
        this.matriculeFiscale = matriculeFiscale;
        this.email = email;
        this.adresse = adresse;
        this.telephone = telephone;
        this.fax = fax;
        this.site = site;
        this.logo = logo;
        this.sponsor = sponsor;
        this.utilisateurProfessionnel = utilisateurProfessionnel;
    }

    public Agence(int id) {
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

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public int getFax() {
        return fax;
    }

    public void setFax(int fax) {
        this.fax = fax;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public User getUtilisateurProfessionnel() {
        return utilisateurProfessionnel;
    }

    public void setUtilisateurProfessionnel(User utilisateurProfessionnel) {
        this.utilisateurProfessionnel = utilisateurProfessionnel;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(ArrayList<Evenement> evenements) {
        this.evenements = evenements;
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
        final Agence other = (Agence) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.matriculeFiscale, other.matriculeFiscale)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom;
    }
    
    
                                
    
    
}

