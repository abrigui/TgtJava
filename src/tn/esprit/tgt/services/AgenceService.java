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
public class AgenceService {
    
      Connection connexion;
  
    public AgenceService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    public void ajouterAgence(Agence a) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO agence (sponsor_id,logo,nom,matriculeFiscale,adresse,telephone,fax,site,email,utilisateurProfessionnel_id,etat) VALUES (?,?,?,?,?,?,?,?,?,?,?);");

        ps.setInt(1,a.getSponsor().getId());
        ps.setString(2,a.getLogo());
        ps.setString(3,a.getNom());
        ps.setString(4,a.getMatriculeFiscale());
        ps.setString(5,a.getAdresse());
        ps.setInt(6,a.getTelephone());
        ps.setInt(7,a.getFax());
        ps.setString(8,a.getSite());
        ps.setString(9,a.getEmail());
        ps.setInt(10,a.getUtilisateurProfessionnel().getId());
        ps.setInt(11,a.getEtat());
       
        ps.executeUpdate();
        System.out.println("Agence ajoutée avec succès ! ");
    }
    
    public void modifierAgence (Agence a) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE `agence` SET `sponsor_id`=?,`logo`=?,`nom`=?,`matriculeFiscale`=?,`adresse`=?,`telephone`=?,`fax`=?,`site`=?,`email`=? WHERE `id`=?");
        
        ps.setInt(1,a.getSponsor().getId());
        ps.setString(2,a.getLogo());
        ps.setString(3,a.getNom());
        ps.setString(4,a.getMatriculeFiscale());
        ps.setString(5,a.getAdresse());
        ps.setInt(6,a.getTelephone());
        ps.setInt(7,a.getFax());
        ps.setString(8,a.getSite());
        ps.setString(9,a.getEmail());
        ps.setInt(10,a.getId());
        
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Agence modifiée avec succès ! ");
        else
            System.out.println("Modificarion impossible");

    }
    
    public void supprimerAgence(Agence a) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM `agence` WHERE id=?;");
        ps.setInt(1, a.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Agence supprimée avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
   
    public ArrayList<Agence> getAllAgence() throws SQLException {
       ArrayList<Agence> agences = new ArrayList<>();
        
        String req = "select * from agence";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            Sponsor s= new Sponsor(result.getInt("sponsor_id"));
            User u = new User(result.getInt("utilisateurProfessionnel_id"));
            Agence agence= new Agence(result.getInt("id"), result.getString("nom"), result.getString("matriculeFiscale"), result.getString("email"), result.getString("adresse"), result.getInt("telephone"), result.getInt("fax"), result.getString("site"), result.getString("logo"), s, u,result.getInt("etat"));
            agences.add(agence);
        }
        
        return agences;
    }
    
    public Agence getAgenceById(int id) throws SQLException {
       Agence agence = new Agence();
        
        String req = "select * from agence where id="+id+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
          Sponsor s= new Sponsor(result.getInt("sponsor_id"));
          User u = new User(result.getInt("utilisateurProfessionnel_id"));
          agence= new Agence(result.getInt("id"), result.getString("nom"), result.getString("matriculeFiscale"), result.getString("email"), result.getString("adresse"), result.getInt("telephone"), result.getInt("fax"), result.getString("site"), result.getString("logo"), s, u,result.getInt("etat"));
            
        }
        
        return agence;
    }
    
     public void updateEtatAgence (Agence a,int etat) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE agence SET etat=? WHERE id=?;");
        
        ps.setInt(1,etat);
        ps.setInt(2,a.getId());
       
        
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Etat Agence modifié avec succès ! ");
        else
            System.out.println("Modification Etat impossible");

    }
    
}
