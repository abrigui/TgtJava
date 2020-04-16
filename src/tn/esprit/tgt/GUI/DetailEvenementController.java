/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.entities.User;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class DetailEvenementController implements Initializable {

 
    @FXML
    private Label lblNom;
    @FXML
    private Separator separator;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblLieu;
    @FXML
    private Label lblDateDebut;
    @FXML
    private Label lblDateFin;
    @FXML
    private Label lblNbrParticipantMax;
    @FXML
    private Label lblSousCat;
    @FXML
    private Label lblPlaceRestantes;
    @FXML
    private ImageView imageEvent;
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
    private AnchorPane anchorGlobal;
    
    int idEvent;
    
    private User currentUser=new User(7, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
   

    

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(idEvent);
    }
    
    public void remplirInofrmationDetail(int id){        
        EvenementService es= new EvenementService();
        AgenceService as =new AgenceService();
        SousCategorieService scs= new SousCategorieService();
        try {
            Evenement e= es.getEvenementById(id);
            Agence a= as.getAgenceById(e.getAgence().getId());
            SousCategorie sc= scs.getSousCategorieById(e.getSousCategorie().getId());
            lblNom.setText(e.getNom());
            imageEvent.setImage(new Image("http://localhost/TGT//web//uploads//evenement//"+ e.getImage()));
            lblDescription.setText(lblDescription.getText()+" "+e.getDescription());
            lblLieu.setText(lblLieu.getText()+" "+e.getLieu());
            lblDateDebut.setText(lblDateDebut.getText()+" "+e.getDateDebut().toString());
            lblDateFin.setText(lblDateFin.getText()+" "+e.getDateFin().toString());
            lblNbrParticipantMax.setText(lblNbrParticipantMax.getText()+" "+e.getNbParticipantMax());
            lblSousCat.setText(lblSousCat.getText()+" "+sc.getLibelle());
            int nbrParticipation= es.getNbrParticipationParEvenement(idEvent);
            int placeRestantes=e.getNbParticipantMax()-nbrParticipation;
            lblPlaceRestantes.setText(lblPlaceRestantes.getText()+" "+placeRestantes);
            lblNomAgence.setText(lblNomAgence.getText()+" "+a.getNom());
            lblTelAgence.setText(lblTelAgence.getText()+" "+a.getTelephone());
            lblEmailAgence.setText(lblEmailAgence.getText()+" "+a.getEmail());
            //////////Contorle inscription///////////
            int verifParticipation=es.verifierParticipationParEvenementEtUser(idEvent, currentUser.getId());
            if(verifParticipation>0){
                btnParticiper.setVisible(false);
                btnAnnulerParticipation.setVisible(true);
                btnAnnulerParticipation.setDisable(false);
            }
            else{
                btnParticiper.setVisible(true);
                btnAnnulerParticipation.setVisible(false);
                btnAnnulerParticipation.setDisable(true);
            }
            /////Controle Date/////
            Date d= new Date();
            if( d.after(e.getDateDebut())){
                  btnParticiper.setVisible(true);
                  btnParticiper.setDisable(true);
                  btnAnnulerParticipation.setVisible(false);
                  lblAvertissement.setVisible(true);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(DetailEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void participation(ActionEvent event) throws SQLException {
         JOptionPane jop = new JOptionPane();    	
      int option = jop.showConfirmDialog(null, 
        "Voulez-vous confirmer votre participation à cet événement?",
        "Inscription", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);

      if(option != JOptionPane.NO_OPTION &&  
      option != JOptionPane.CLOSED_OPTION){
        EvenementService es= new EvenementService();
        es.ajouterParticipation(currentUser.getId(), idEvent);
         JOptionPane jopConf = new JOptionPane();    	
        jopConf.showMessageDialog(null, "Votre inscription à été éffectuée avec succès !", "Inscription", JOptionPane.INFORMATION_MESSAGE);
        viderChampDetailEvenement();
        remplirInofrmationDetail(idEvent);
      }
    }

    @FXML
    private void annulerParticipation(ActionEvent event) throws SQLException {
        JOptionPane jop = new JOptionPane();    	
      int option = jop.showConfirmDialog(null, 
        "Voulez-vous confirmer l'annulation de votre participation à cet événement?",
        "Annulation", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);

      if(option != JOptionPane.NO_OPTION &&  
      option != JOptionPane.CLOSED_OPTION){
         EvenementService es= new EvenementService();
        es.supprimerParticipation(currentUser.getId(), idEvent);
         JOptionPane jopConf = new JOptionPane();    	
         jopConf.showMessageDialog(null, "Votre Annulation à été éffectuée avec succès !", "Annulation", JOptionPane.INFORMATION_MESSAGE);		
        viderChampDetailEvenement();
        remplirInofrmationDetail(idEvent);
         
      }
    }
    
    public void viderChampDetailEvenement(){
            lblNom.setText("Nom:");            
            lblDescription.setText("Description:");
            lblLieu.setText("Lieu:");
            lblDateDebut.setText("Du:");
            lblDateFin.setText("Au:");
            lblNbrParticipantMax.setText("Nombre de place:");
            lblSousCat.setText("Classification:");           
            lblPlaceRestantes.setText("Nombre de place restantes:");
            lblNomAgence.setText("Organisé par:");
            lblTelAgence.setText("Téléphone:");
            lblEmailAgence.setText("Email:");
    }
    
}
