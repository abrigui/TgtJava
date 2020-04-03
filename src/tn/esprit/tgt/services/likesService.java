/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tn.esprit.tgt.ConnectionBD.MyDbConnection;
import tn.esprit.tgt.entities.*;

/**
 *
 * @author Mega-PC
 */
public class likesService {

    Connection connexion;

    public likesService() {

        connexion = MyDbConnection.getInstance().getConnexion();
    }

    public void ajouterLike(Like l) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO `publication_like`(`publication_id`,"
                + "`utilisateur_id`) VALUES (?,?,?,?);");

        ps.setInt(1, l.getP().getId());
        ps.setInt(2, l.getU().getId());

        ps.executeUpdate();
    }

    public void supprimerLike(Like l) throws SQLException {
        int res = 0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM publication_like WHERE id=?;");
        ps.setInt(1, l.getId());
        res = ps.executeUpdate();
        if (res != 0) {
            System.out.println("like supprimée avec succès ! ");
        } else {
            System.out.println("Suppression impossible");
        }
    }

}
