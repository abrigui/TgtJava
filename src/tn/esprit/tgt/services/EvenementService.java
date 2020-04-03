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
public class EvenementService {
    
    Connection connexion;
  
    public EvenementService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    public void ajouterEvenement(Evenement e) throws SQLException {
       PreparedStatement ps = connexion.prepareStatement("INSERT INTO evenement (nom,image,description,dateDebut,lieu,dateFin,nbParticipantMax,SousCategorie_id,Agence_id) VALUES (?,?,?,?,?,?,?,?,?);");

        ps.setString(1,e.getNom());
        ps.setString(2,e.getImage());
        ps.setString(3,e.getDescription());
        ps.setDate(4,new java.sql.Date(e.getDateDebut().getTime()));
        ps.setString(5,e.getLieu());
        ps.setDate(6,new java.sql.Date(e.getDateFin().getTime()));
        ps.setInt(7,e.getNbParticipantMax());
        ps.setInt(8,e.getSousCategorie().getId());
        ps.setInt(9,e.getAgence().getId());
       
        ps.executeUpdate();
        System.out.println("Evenement ajouté avec succès ! ");
    }
    
    public void modifierEvenement (Evenement e) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE `evenement` SET `nom`=?,`image`=?,`description`=?,`dateDebut`=?,`lieu`=?,`dateFin`=?, `nbParticipantMax`=?,`SousCategorie_id`=? WHERE `id`=?;");
        
        ps.setString(1,e.getNom());
        ps.setString(2,e.getImage());
        ps.setString(3,e.getDescription());
        ps.setDate(4,new java.sql.Date(e.getDateDebut().getTime()));
        ps.setString(5,e.getLieu());
        ps.setDate(6,new java.sql.Date(e.getDateFin().getTime()));
        ps.setInt(7,e.getNbParticipantMax());
        ps.setInt(8,e.getSousCategorie().getId());
        ps.setInt(9,e.getId());
        
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Evenement modifié avec succès ! ");
        else
            System.out.println("Modificarion impossible");

    }
    
    public void supprimerEvenement(Evenement e) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM `evenement` WHERE id=?;");
        ps.setInt(1, e.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Evenement supprimée avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
   
    public ArrayList<Evenement> getAllEvenement() throws SQLException {
       ArrayList<Evenement> evenements = new ArrayList<>();
        
        String req = "select * from evenement";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            Agence a= new Agence(result.getInt("Agence_id"));
            SousCategorie sc = new SousCategorie(result.getInt("SousCategorie_id"));
            Evenement e= new Evenement(result.getInt("id"),result.getString("nom"), result.getString("description"), result.getDate("dateDebut"), result.getString("lieu"), result.getDate("dateFin"), result.getInt("nbParticipantMax"), sc, result.getString("image"), a);
            evenements.add(e);
        }
        
        return evenements;
    }
    
    public Evenement getEvenementById(int id) throws SQLException {
       Evenement e = new Evenement();
        
        String req = "select * from evenement where id="+id+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
         Agence a= new Agence(result.getInt("Agence_id"));
            SousCategorie sc = new SousCategorie(result.getInt("SousCategorie_id"));
           e= new Evenement(result.getInt("id"),result.getString("nom"), result.getString("description"), result.getDate("dateDebut"), result.getString("lieu"), result.getDate("dateFin"), result.getInt("nbParticipantMax"), sc, result.getString("image"), a);
            
        }
        
        return e;
    }
    
       public ArrayList<Evenement> getEvenementByAgence(Agence ag) throws SQLException {
        ArrayList<Evenement> evenements = new ArrayList<>();
        
        String req = "select * from evenement where Agence_id="+ag.getId()+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
           Agence a= ag;
           SousCategorie sc = new SousCategorie(result.getInt("SousCategorie_id"));
           Evenement e= new Evenement(result.getInt("id"),result.getString("nom"), result.getString("description"), result.getDate("dateDebut"), result.getString("lieu"), result.getDate("dateFin"), result.getInt("nbParticipantMax"), sc, result.getString("image"), a);
           evenements.add(e);
        }
        
        return evenements;
    }
    
}
