/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.services;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.ConnectionBD.MyDbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.tgt.entities.Categorie;

/**
 *
 * @author Administrateur
 */
public class SousCategorieService {
    
    Connection connexion;
  
    public SousCategorieService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    public void ajouterSousCategorie(SousCategorie sc) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO sous_categorie(categorie_id,libelle) VALUES (?,?);");
        
        ps.setInt(1,sc.getCategorie().getId());
        ps.setString(2,sc.getLibelle());
        ps.executeUpdate();
        System.out.println("Sous Catégorie ajoutée avec succès ! ");
    }
    
    public void modifierSousCatgeorie (SousCategorie sc) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE sous_categorie SET categorie_id=?,libelle=? WHERE id=? ;");
        
        ps.setInt(1, sc.getCategorie().getId());  
        ps.setString(2, sc.getLibelle());      
        ps.setInt(3, sc.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Catégorie modifiée avec succès ! ");
        else
            System.out.println("Modification impossible");


    }
    
    public void supprimerSousCategorie(SousCategorie sc) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM sous_categorie WHERE id =?");
        
        ps.setInt(1, sc.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Catégorie supprimée avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
    public ArrayList<SousCategorie> getAllSousCategorieByCategorie(Categorie c) throws SQLException {
       ArrayList<SousCategorie> sousCategories = new ArrayList<>();
        
        String req = "select * from sous_categorie where categorie_id="+c.getId()+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            SousCategorie sc = new SousCategorie(result.getInt(1), result.getString("libelle"),c);
            sousCategories.add(sc);
        }
        
        return sousCategories;
    }
    
    
    
}
