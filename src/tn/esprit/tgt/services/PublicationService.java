
package tn.esprit.tgt.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.tgt.ConnectionBD.MyDbConnection;
import tn.esprit.tgt.entities.*;
/**
 *
 * @author Mega-PC
 */
public class PublicationService {
    
    Connection connexion;
       
    public PublicationService() {
        
       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
    
    public void ajoutPublication(Publication p) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO `publication`(`utilisateur`,`contenu`,"
                + " `date`, `photo`) VALUES (?,?,?,?);");

        ps.setInt(1, p.getU().getId());
        ps.setString(2,p.getContenu());
        ps.setDate(3, (Date) p.getDate());
        ps.setString(4, p.getPhoto());
        ps.executeUpdate();
        System.out.println("publication ajouté");
    }

    public boolean modifierPublication (Publication p) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("UPDATE publication SET contenu=?,photo=?,"
                + "  date=?"
                + " WHERE id = ?;");
        
        ps.setInt(1, p.getId());
        ps.setString(2, p.getContenu());
        ps.setDate(3, (Date) p.getDate());
        ps.setString(4, p.getPhoto());
        ps.execute();
        return true;

    }

    public void supprimerPublication(Publication p) throws SQLException {
            int res=0;
            PreparedStatement ps = connexion.prepareStatement("DELETE FROM `publication` WHERE `publication`.`id` = ?;");
            ps.setInt(1, p.getId());
            res=ps.executeUpdate();
            if(res!=0)
                System.out.println("publication supprimée avec succès ! ");
            else
                System.out.println("Suppression impossible");
    }

    
     public List<Publication> getAll(User u) throws SQLException {
        List<Publication> publications = new ArrayList<>();

        String req = "select * from publication where id="+u.getId()+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);

        while(result.next()){
           Publication p = new Publication(result.getInt(1), result.getString(2), u,result.getDate(5), result.getString(4));
            publications.add(p);
        }

        return publications;
    }
}
