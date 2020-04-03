/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.test;

import java.sql.Date;
import java.sql.SQLException;
import tn.esprit.tgt.entities.*;
import tn.esprit.tgt.services.*;



/**
 *
 * @author ADMIN
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        User u = new User(6, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
        UserService su = new UserService();
        su.ajouterUser(u);
        
        Categorie ct= new Categorie(4,"test");
        CategorieService kk = new CategorieService();
        kk.ajouterCategorie(ct);
        SousCategorie sct= new SousCategorie(5,"SousTest", ct);
        SousCategorieService kkk = new SousCategorieService();
        kkk.ajouterSousCategorie(sct);
        Sponsor s = new Sponsor(4,"Razer", "10 RUE PARIS", 74888555, "RAZER-CORP@GMAIL.COM", "Razer-corp.jpeg");
        SponsorService sk = new SponsorService();
        sk.ajouterSponsor(s);
        Agence ag = new Agence(8,"Slim-corp","0000AB", "Slim-corp@gmail.com", "Ben Arous", 74888555, 74222333, "SlimCorp.com", "SlimCorp.jpeg", s, u);
        AgenceService nj = new AgenceService();
        nj.ajouterAgence(ag);
        Date d = new Date(0);
        Evenement e = new Evenement("CoronaFest", "Drink Corona", d, "Munich" , d , 1000, sct, "Corona.jpeg", ag);
        EvenementService es =new EvenementService();
        es.ajouterEvenement(e);
        
        
        java.sql.Date date = new java.sql.Date(d.getTime());
        Publication p = new Publication(11,"ddddeeee", u, date, "ddd.png");
        PublicationService ps = new PublicationService();
        ps.ajoutPublication(p);
        Commentaire c = new Commentaire("dsdsd", date, p, u);
        CommentaireService cs = new CommentaireService();
        cs.ajoutCommentaire(c);
        
        
    }
    
}
