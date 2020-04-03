/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.entities;

/**
 *
 * @author Mega-PC
 */
public class Like {
    
        private int id;
    private Publication p;
    private User u;

    public Like() {
    }

    public Like(Publication p, User u) {
        this.p = p;
        this.u = u;
    }

    public Like(int id, Publication p, User u) {
        this.id = id;
        this.p = p;
        this.u = u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Like{" + "id=" + id + ", p=" + p + ", u=" + u + '}';
    }
    
    

    
}
