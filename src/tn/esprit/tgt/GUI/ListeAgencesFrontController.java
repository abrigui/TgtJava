/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.entities.Sponsor;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;
import tn.esprit.tgt.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListeAgencesFrontController implements Initializable {

    @FXML
    private AnchorPane anchorGlobal;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox mainPain;
    @FXML
    private TextField tfRecherche;
    @FXML
    private Label lblRecherche;
    @FXML
    private FontAwesomeIconView iconRecherche;
    @FXML
    private Label lblLesAgences;
    
    
    private int idAgence;
    private Label lblLesEvenements;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AgenceService ags= new AgenceService();
        ArrayList<Agence> lesAgences= new ArrayList<Agence>();
        try {            
            lesAgences=ags.getAgenceApprouver();
        } catch (SQLException ex) {
            Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        afficheListe(Boolean.TRUE);
       
        creationPage(lesAgences);
        
    }
    public void creationPage(ArrayList<Agence> listeAgences){
        mainPain.toFront();
        mainPain.getChildren().clear();
        for (Agence a : listeAgences) {
            ImageView photo = new ImageView(new Image("http://localhost/TGT//web//uploads//logo//"+ a.getLogo()));
            
            Label lblNom = new Label("Nom: "+a.getNom());
            lblNom.setFont(Font.font(20));
            Label lblEmail = new Label("Email: "+a.getEmail());
            lblEmail.setFont(Font.font(20));
            Label lblTel = new Label("Téléphone: "+a.getTelephone());   
            lblTel.setFont(Font.font(20));
          
                   
            VBox vb= new VBox();

            vb.setSpacing(10);
            photo.setFitHeight(200);
            photo.setFitWidth(300);
            vb.getChildren().add(photo);
            vb.getChildren().add(lblNom);
            vb.spacingProperty();
            vb.getChildren().add(lblEmail);
            vb.getChildren().add(lblTel);
            vb.setAlignment(Pos.CENTER);

            mainPain.setStyle("-fx-background-color:white;");
            vb.setStyle("-fx-background-color:white;");   
            
            mainPain.setSpacing(20);
            mainPain.setAlignment(Pos.CENTER);

            JFXButton btnDetail = new JFXButton("Détails");
            btnDetail.setMinHeight(40);
            btnDetail.setMinWidth(100);
            btnDetail.setTextFill(Paint.valueOf("white"));
            btnDetail.setStyle("-fx-background-color: #4e73df;");
            btnDetail.setFont(Font.font(20));
            vb.getChildren().add(btnDetail);
            Separator sp = new Separator();
            sp.setMaxWidth(400);
            sp.setMaxHeight(15);
            sp.setPadding(new Insets(15, 0,15, 0));
            vb.getChildren().add(sp);
            mainPain.getChildren().add(vb);
            
                     
            btnDetail.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    idAgence=a.getId();
                    System.out.println(idAgence);
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/tgt/GUI/DetailAgence.fxml"));
                        AnchorPane pp = loader.load();
                        DetailAgenceController detailAg = loader.getController();
                        detailAg.remplirInofrmationDetail(idAgence);
                        anchorGlobal.getChildren().clear();
                        anchorGlobal.getChildren().add(pp);
                    } catch (IOException ex) {
                        Logger.getLogger(ListeAgencesFrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
      
          
         }
    }

    @FXML
    private void rechercheEvenement(KeyEvent event) throws SQLException, AWTException {
         String motCle=tfRecherche.getText();            
           Robot robot= new Robot();
           tfRecherche.requestFocus();
           robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);

                 
       if(motCle.isEmpty())
       {
            AgenceService ags= new AgenceService();
            ArrayList<Agence> lesAgences= new ArrayList<Agence>();
            lesAgences=ags.getAgenceApprouver();
            creationPage(lesAgences);         
       }
       else
       {    
             AgenceService ags= new AgenceService();
            ArrayList<Agence> lesAgences= new ArrayList<Agence>();
            lesAgences=ags.rechercheAgence(motCle);
            creationPage(lesAgences);            
       }
    }
    
    public void afficheListe(Boolean bool){
        lblLesAgences .setVisible(bool);
        scrollPane .setVisible(bool);
        mainPain .setVisible(bool);
        tfRecherche .setVisible(bool);
        lblRecherche .setVisible(bool);
        iconRecherche .setVisible(bool);

    }
       
}
