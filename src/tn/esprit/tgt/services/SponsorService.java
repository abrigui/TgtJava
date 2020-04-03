/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import tn.esprit.tgt.ConnectionBD.MyDbConnection;
import tn.esprit.tgt.entities.*;

/**
 *
 * @author Administrateur
 */
public class SponsorService {
    
       Connection connexion;
  
    public SponsorService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    public void ajouterSponsor(Sponsor s) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO sponsor (nom,adresse,logo,email,telephone) VALUES (?,?,?,?,?);");

        ps.setString(1,s.getNom());
        ps.setString(2,s.getAdresse());
        ps.setString(3,s.getLogo());
        ps.setString(4,s.getEmail());
        ps.setInt(5,s.getTelephone());
       
        ps.executeUpdate();
        System.out.println("Sponsor ajouté avec succès ! ");
    }
    
    public void modifierSponsor (Sponsor s) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE sponsor SET nom=?,adresse=?,logo=?,email=?,telephone=? WHERE id=?;");
        
        ps.setString(1,s.getNom());
        ps.setString(2,s.getAdresse());
        ps.setString(3,s.getLogo());
        ps.setString(4,s.getEmail());
        ps.setInt(5,s.getTelephone());
        ps.setInt(6,s.getId());
        
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Sponsor modifié avec succès ! ");
        else
            System.out.println("Modification impossible");

    }
    
    public void supprimerSponsor(Sponsor s) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM `sponsor` WHERE id=?;");
        ps.setInt(1, s.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Sponsor supprimé avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
   
    public ArrayList<Sponsor> getAllSponsor() throws SQLException {
       ArrayList<Sponsor> sponsors = new ArrayList<>();
        
        String req = "select * from sponsor";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            Sponsor s= new Sponsor(result.getInt("id"),result.getString("nom"),result.getString("adresse"),result.getInt("telephone"),result.getString("email"),result.getString("logo"));
            sponsors.add(s);
        }
        
        return sponsors;
    }
    
    public Sponsor getSponsorById(int id) throws SQLException {
       Sponsor sponsor = new Sponsor();
        
        String req = "select * from sponsor where id="+id+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
         sponsor= new Sponsor(result.getInt("id"),result.getString("nom"),result.getString("adresse"),result.getInt("telephone"),result.getString("email"),result.getString("logo"));
            
        }
        
        return sponsor;
    }
    
    
}
