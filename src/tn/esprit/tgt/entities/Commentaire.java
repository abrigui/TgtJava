/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

import java.util.Date;


public class Commentaire {
    
    private int id;
    private String contenu;
    private Date d = new Date();
    private java.sql.Date date = new java.sql.Date(d.getTime());
    private Publication p;
    private User u;

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, java.sql.Date date) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
    }
    
    

    public Commentaire(String contenu, java.sql.Date date, Publication p, User u) {
        this.contenu = contenu;
        this.date = date;
        this.p = p;
        this.u = u;
    }

    public Commentaire(int id, String contenu, java.sql.Date date, Publication p, User u) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.p = p;
        this.u = u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public Publication getP() {
        return p;
    }

    public void setP(Publication p) {
        this.p = p;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", contenu=" + contenu + ", date=" + date + ", p=" + p + ", u=" + u + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
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
        final Commentaire other = (Commentaire) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    } 
    
}
