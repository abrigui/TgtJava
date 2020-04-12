/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.tgt.entities.*;
import tn.esprit.tgt.services.*;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class DetailEvenementController implements Initializable {

    @FXML
    private Label lblDescription;
    @FXML
    private Label lblLieu;
    @FXML
    private Label lblDateDebut;
    @FXML
    private Label lblNbrParticipantMax;
    @FXML
    private Label lblSousCat;
    @FXML
    private Label lblPlaceRestantes;
    @FXML
    private JFXButton btnParticiper;
    @FXML
    private Label lblAvertissement;
    @FXML
    private Label lblNomAgence;
    @FXML
    private Label lblTelAgence;
    @FXML
    private Label lblEmailAgence;
    @FXML
    private JFXButton btnAnnulerParticipation;
    @FXML
    private ImageView imageEvent;
    @FXML
    private Label lblDateFin;
    @FXML
    private Label lblNom;
    
    private int idEvent;
    //private User currentUser=new User(7, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
     User user;
   
   

//    public int getIdEvent() {
//        return idEvent;
//    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

//    public User getUser() {
//        return user;
//    }

    public void setUser(User user) {
        this.user = user;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*EvenementService es= new EvenementService();
        AgenceService as =new AgenceService();
        SousCategorieService scs= new SousCategorieService();
        try {
            Evenement e= es.getEvenementById(idEvent);
            Agence a= as.getAgenceById(e.getAgence().getId());
            SousCategorie sc= scs.getSousCategorieById(e.getSousCategorie().getId());
            lblNom.setText(e.getNom());
            imageEvent.setImage(new Image("http://localhost/TGT//web//uploads//evenement//"+ e.getImage()));
            lblDescription.setText(e.getDescription());
            lblLieu.setText(e.getLieu());
            lblDateDebut.setText(e.getDateDebut().toString());
            lblDateFin.setText(e.getDateFin().toString());
            lblNbrParticipantMax.setText(""+e.getNbParticipantMax());
            lblSousCat.setText(sc.getLibelle());
            int nbrParticipation= es.getNbrParticipationParEvenement(idEvent);
            //lblPlaceRestantes.setText(e.getNbParticipantMax()-nbrParticipation);
            lblNomAgence.setText(a.getNom());
            lblTelAgence.setText(""+a.getTelephone());
            lblEmailAgence.setText(a.getEmail());
            //////////Contorle///////////
            int verifParticipation=es.verifierParticipationParEvenementEtUser(idEvent, currentUser.getId());
            if(verifParticipation>0){
                btnParticiper.setVisible(false);
                btnAnnulerParticipation.setVisible(true);
                btnAnnulerParticipation.setDisable(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DetailEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        if(user==null)
            System.out.println("Null");
        else 
            System.out.println(user);
    }    
    
}
