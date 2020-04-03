
package tn.esprit.tgt.services;

import java.sql.Connection;
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
public class CommentaireService {
    
        Connection connexion;
       
    public CommentaireService() {
        
       connexion=MyDbConnection.getInstance().getConnexion();
    }
    
       public void ajoutCommentaire(Commentaire c) throws SQLException {
        PreparedStatement ps = connexion.prepareStatement("INSERT INTO `commentaire_publication`(`publication_id`,`contenu`,"
                + " `date`, `utilisateurTalent_id`) VALUES (?,?,?,?);");

        ps.setInt(1, c.getP().getId());
        ps.setString(2,c.getContenu());
        ps.setDate(3, c.getDate());
        ps.setInt(4, c.getU().getId());
        ps.executeUpdate();
    }

    public boolean modifierCommentaire (Commentaire c) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("UPDATE commentaire_publication SET contenu=?,"
                + "  date=?"
                + " WHERE id = ?;");
        ps.setString(1,c.getContenu());
        ps.setDate(2, c.getDate());
        ps.setInt(3, c.getId());
       
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Commentaire modifiée avec succès ! ");
        else
            System.out.println("Modificarion impossible");
        return true;

    }

  public void supprimerCommentaire(Commentaire c) throws SQLException {
        int res=0;
        PreparedStatement ps = connexion.prepareStatement("DELETE FROM commentaire_publication WHERE id=?;");
        ps.setInt(1, c.getId());
        res=ps.executeUpdate();
        if(res!=0)
            System.out.println("Catégorie supprimée avec succès ! ");
        else
            System.out.println("Suppression impossible");
    }
    
     
     public List<Commentaire> getAll(Publication p) throws SQLException {
        List<Commentaire> Commentaires = new ArrayList<>();

        String req = "select * from commentaire_publication where id="+p.getId()+";";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);

        while(result.next()){
           Commentaire c = new Commentaire(result.getInt("id"),result.getString("contenu"),result.getDate("date"));
            Commentaires.add(c);
        }

        return Commentaires;
    }
    
    
}
