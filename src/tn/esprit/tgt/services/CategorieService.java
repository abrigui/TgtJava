/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.services;

import tn.esprit.tgt.entities.Categorie;
import tn.esprit.tgt.ConnectionBD.MyDbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class CategorieService {
    
     Connection connexion;
  
    public CategorieService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    public void ajouterCategorie(Categorie c) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO categorie (libelle) VALUES (?);");

        ps.setString(1,c.getLibelle());
        ps.executeUpdate();
        System.out.println("Catégorie ajoutée avec succès ! ");
    }
    
    public void modifierCatgeorie (Categorie c) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE categorie SET libelle=? WHERE id=?;");

        ps.setString(1, c.getLibelle());
        ps.setInt(2, c.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Catégorie modifiée avec succès ! ");
        else
            System.out.println("Modification impossible");

    }
    
    public void supprimerCategorie(Categorie c) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM `categorie` WHERE id=?;");
        ps.setInt(1, c.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Catégorie supprimée avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
   
    public ArrayList<Categorie> getAllCategorie() throws SQLException {
       ArrayList<Categorie> categories = new ArrayList<>();
        
        String req = "select * from categorie";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            Categorie c = new Categorie(result.getInt("id"), result.getString("libelle"));
            categories.add(c);
        }
        
        return categories;
    }
    
    public Categorie getCategorieById(int id) throws SQLException {
       Categorie categorie = new Categorie();
        
        String req = "select * from categorie where id="+id+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            categorie = new Categorie(result.getInt("id"), result.getString("libelle"));
            
        }
        
        return categorie;
    }
    
    
}
