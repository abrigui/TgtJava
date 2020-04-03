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
import tn.esprit.tgt.ConnectionBD.StaticVariables;
import tn.esprit.tgt.entities.User;

/**
 *
 * @author ADMIN
 */
public class UserService {
    
 Connection connexion;
  
    public UserService() {

       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    //        *************Ajout utilisateur*************
    
    public void ajouterUser(User u) throws SQLException {
         String crypted = service_bcrypt.hashpw(u.getPassword(), StaticVariables.SALT);
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO fos_user (username,username_canonical,email,email_canonical,enabled,password,roles,nom,prenom,adresse,telephone,genre) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");

        ps.setString(1,u.getUsername());
        ps.setString(2,u.getUsername());
        ps.setString(3,u.getEmail());
        ps.setString(4,u.getEmail());   
        ps.setInt(5,1);
        ps.setString(6, crypted);
        ps.setString(7,u.getRoles());
        ps.setString(8,u.getNom());
        ps.setString(9,u.getPrenom());
        ps.setString(10,u.getAdresse());
        ps.setInt(11,u.getTelephone());
        ps.setString(12,u.getGenre());
       
        ps.executeUpdate();
        System.out.println("User ajouté avec succès ! ");
    }



    //        *************Modifier utilisateur*************
    public void modifierUser (User u) throws SQLException {
        int res=0;
        String crypted = service_bcrypt.hashpw(u.getPassword(), StaticVariables.SALT);
        PreparedStatement ps = connexion.prepareStatement("UPDATE fos_user SET username=?,username_canonical=?,email=?,email_canonical=?,enabled=?,password=?,roles=?,nom=?,prenom=?,adresse=?,telephone=?,genre=? WHERE id=?;");
        
        ps.setString(1,u.getUsername());
        ps.setString(2,u.getUsername());
        ps.setString(3,u.getEmail());
        ps.setString(4,u.getEmail());   
        ps.setInt(5,1);
        ps.setString(6, crypted);
        ps.setString(7,u.getRoles());
        ps.setString(8,u.getNom());
        ps.setString(9,u.getPrenom());
        ps.setString(10,u.getAdresse());
        ps.setInt(11,u.getTelephone());
        ps.setString(12,u.getGenre());
        ps.setInt(13,u.getId());
        
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("User modifié avec succès ! ");
        else
            System.out.println("Modification impossible");

    }
    
    
    
    //        *************Supprimer utilisateur*************
    public void supprimerUser(User u) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM `fos_user` WHERE id=?;");
        ps.setInt(1, u.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("User supprimé avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
   
    
    
    //        *************Afficher la liste des utilisateurs*************
    public ArrayList<User> getAllUsers() throws SQLException {
       ArrayList<User> users = new ArrayList<>();
        
        String req = "select * from fos_user";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            //User s= new User(result.getInt("id"),result.getString("username"),result.getString("email"),result.getString("password"),result.getString("roles"),result.getString("nom"),result.getString("prenom"),result.getString("adresse"),result.getInt("telephone"),result.getString("genre"));
            User s= new User(result.getInt("id"),result.getString("username"),result.getString("email"),result.getString("password"),result.getString("roles"),result.getString("nom"),result.getString("prenom"),result.getString("adresse"),result.getInt("telephone"),result.getString("genre"));

            users.add(s);
        }
        
        return users;
    }
    
    
    
    

  
    
    
    
    
  //        *************Virifier un utilisateur avec username et password*************
         public boolean checkUser(String username, String password) {

        try {
            PreparedStatement ps = connexion.prepareStatement("SELECT * FROM fos_user where username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String crypted = rs.getString("password");
                if (service_bcrypt.checkpw(password, crypted)) {
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
         
         //        *************Role de l'utilisateur authentifié*************
    public int getRoleByUsernameMdp ( String username , String password) throws SQLException {
         
        int x = 0 ;
        
        String req = "select * from fos_user";
        Statement ste = connexion.createStatement();
        ResultSet rs = ste.executeQuery (req);
        PreparedStatement ps = connexion.prepareCall(req);
        
        while (rs.next()) { 
            String crypted = rs.getString("password");
            
            if (rs.getString("username").equals(username)&& (service_bcrypt.checkpw(password, crypted)))
            {
                    if ( rs.getString("roles").equals("a:1:{i:0;s:10:\"ROLE_ADMIN\";}"))
                        x = 1;
                            else if ( rs.getString("roles").equals("a:1:{i:0;s:10:\"ROLE_USERT\";}"))
                            x=2;
                                else if ( rs.getString("roles").equals("a:1:{i:0;s:10:\"ROLE_USERP\";}"))
                                x=3;

            }
           
            
        }
         
          return  x;
           
       }
         
         

    
    
    
    
    
    
    
    //        *************Afficher un utilisateur avec son Id*************
    public User getUserById(int id) throws SQLException {
       User user = new User();
        
        String req = "select * from fos_user where id="+id+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
         user = new User(result.getInt("id"),result.getString("username"),result.getString("email"),result.getString("password"),result.getString("roles"),result.getString("nom"),result.getString("prenom"),result.getString("adresse"),result.getInt("telephone"),result.getString("genre"));
            
        }
        
        return user;
    }
    
    

    
    
    
}
