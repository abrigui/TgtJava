/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.Sponsor;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class DetailAgenceController implements Initializable {

    @FXML
    private Label lblNomAgence;
    @FXML
    private Separator separator;
    @FXML
    private Label lblMatriculeFiscale;
    @FXML
    private Label lblAdresseAgence;
    @FXML
    private Label lblFaxAgence;
    @FXML
    private Label lblSiteAgence;
    @FXML
    private ImageView imageAgence;
    @FXML
    private Label lblTelAgence;
    @FXML
    private Label lblEmailAgence;
    @FXML
    private ScrollPane scrollPaneEvent;
    @FXML
    private HBox HboxEvent;
    @FXML
    private ImageView imageSponsor;
    @FXML
    private Label lblNomSponsor;
    @FXML
    private Label lblAdresseSponsor;
    @FXML
    private Label lblTelSponsor;
    @FXML
    private Label lblEmailSponsor;
    @FXML
    private Label lblAvertissementSponsor;
    @FXML
    private Label lblAvertissementEvenement;
    @FXML
    private AnchorPane anchorGlobal;
    @FXML
    private Label lblLesEvenements;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void afficheDetailSponsor(Boolean bool){   
        imageSponsor.setVisible(bool);
        lblNomSponsor.setVisible(bool);
        lblAdresseSponsor.setVisible(bool);
        lblTelSponsor.setVisible(bool);
        lblEmailSponsor.setVisible(bool);
    }
    
    public void remplirInofrmationDetail(int idAgence){        
        viderChampDetail();
        EvenementService es= new EvenementService();
        AgenceService as =new AgenceService();
        SponsorService sps= new SponsorService();
        try {            
            Agence a= as.getAgenceById(idAgence);          
            lblNomAgence.setText(lblNomAgence.getText()+" "+a.getNom());
            imageAgence.setImage(new Image("http://localhost/TGT//web//uploads//logo//"+ a.getLogo()));
            lblMatriculeFiscale.setText(lblMatriculeFiscale.getText()+" "+a.getMatriculeFiscale());
            lblAdresseAgence.setText(lblAdresseAgence.getText()+" "+a.getAdresse());
            lblEmailAgence.setText(lblEmailAgence.getText()+" "+a.getEmail());
            lblTelAgence.setText(lblTelAgence.getText()+" "+a.getTelephone());
            lblFaxAgence.setText(lblFaxAgence.getText()+" "+a.getFax());
            lblSiteAgence.setText(lblSiteAgence.getText()+" "+a.getSite());   
            ///////////Sponsor/////////////
            Sponsor s= sps.getSponsorById(a.getSponsor().getId());
            if(s==null){
                afficheDetailSponsor(Boolean.FALSE);
                lblAvertissementSponsor.setVisible(true);
            }
            else{
            imageSponsor.setImage(new Image("http://localhost/TGT//web//uploads//logo//"+ s.getLogo()));
            lblNomSponsor.setText(lblNomSponsor.getText()+" "+s.getNom());
            lblAdresseSponsor.setText(lblAdresseSponsor.getText()+" "+s.getAdresse());
            lblEmailSponsor.setText(lblEmailSponsor.getText()+" "+s.getEmail());
            lblTelSponsor.setText(lblTelSponsor.getText()+" "+s.getTelephone());
            }
            ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
            lesEvents=es.getEvenementByAgence(a);
            if(lesEvents.size()==0){
                lblAvertissementEvenement.setVisible(true);
            }
            else{
                generationEvenement(lesEvents);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void generationEvenement(ArrayList<Evenement> listeEvenement){
        HboxEvent.toFront();
        HboxEvent.getChildren().clear();
        for (Evenement e : listeEvenement) {
            ImageView photo = new ImageView(new Image("http://localhost/TGT//web//uploads//evenement//"+ e.getImage()));
            
            Label lblNom = new Label("Nom: "+e.getNom());
            lblNom.setFont(Font.font(20));
                   
            VBox vb= new VBox();

            vb.setSpacing(10);
            photo.setFitHeight(180);
            photo.setFitWidth(280);
            vb.getChildren().add(photo);
            vb.getChildren().add(lblNom);
            vb.spacingProperty();
            vb.setAlignment(Pos.CENTER);

            HboxEvent.setStyle("-fx-background-color:white;");
            vb.setStyle("-fx-background-color:white;");   
            JFXButton btnDetail = new JFXButton("Détails");
            btnDetail.setMinHeight(30);
            btnDetail.setMinWidth(100);
            btnDetail.setTextFill(Paint.valueOf("white"));
            btnDetail.setStyle("-fx-background-color: #4e73df;");
            btnDetail.setFont(Font.font(20));
            vb.getChildren().add(btnDetail);        
            HboxEvent.setSpacing(20);
            HboxEvent.getChildren().add(vb);
            Separator sp= new Separator(Orientation.VERTICAL);
            //sp.setMaxWidth(400);
            sp.setMaxHeight(400);
            sp.setPadding(new Insets(25, 15,25, 15));
            HboxEvent.getChildren().add(sp);
            
             btnDetail.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {                   
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/tgt/GUI/DetailEvenement.fxml"));
                        AnchorPane pp = loader.load();
                        DetailEvenementController detailCont = loader.getController();
                        detailCont.remplirInofrmationDetail(e.getId());
                        anchorGlobal.getChildren().clear();
                        anchorGlobal.getChildren().add(pp);
                    } catch (IOException ex) {
                        Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                            
                }
            });
        }
    }
    
    public void viderChampDetail(){
        lblNomAgence.setText("Agence:");      
        lblMatriculeFiscale.setText("Matricule fiscale:");
        lblAdresseAgence.setText("Adresse:");
        lblFaxAgence.setText("Fax:");
        lblSiteAgence.setText("Site:");
        lblTelAgence.setText("Téléphone:");
        lblEmailAgence.setText("Email:");
        lblNomSponsor.setText("Sponsor:");
        lblAdresseSponsor.setText("Adresse:");
        lblTelSponsor.setText("Téléphone:");
        lblEmailSponsor.setText("Email:");
       
    }
    
}
