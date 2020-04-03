/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.test;

import java.sql.SQLException;
import tn.esprit.tgt.entities.User;
import tn.esprit.tgt.services.UserService;


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
        User u = new User(0, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
        UserService su = new UserService();
        su.ajouterUser(u);
    }
    
}
