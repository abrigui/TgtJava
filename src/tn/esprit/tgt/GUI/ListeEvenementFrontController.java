/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.AWTException;
import java.awt.Panel;
import java.awt.Robot;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Categorie;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.entities.User;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.CategorieService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListeEvenementFrontController implements Initializable {

    @FXML
    private VBox mainPain;
    @FXML
    private TextField tfRecherche;
    @FXML
    private ComboBox<SousCategorie> cbSousCat;
    @FXML
    private AnchorPane anchorGlobal;
    @FXML
    private Label lblLesEvenement;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label lblRecherche;
    @FXML
    private FontAwesomeIconView iconRecherche;
    @FXML
    private Label lblFiltrer;
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

    private int idEvent;
    private User currentUser=new User(7, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EvenementService es= new EvenementService();
        ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
        try {            
            lesEvents=es.getAllEvenement();
        } catch (SQLException ex) {
            Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fillCombo();
        } catch (SQLException ex) {
            Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        creationPage(lesEvents);
    } 
    
    public void fillCombo() throws SQLException{
        ArrayList<SousCategorie> lesSousCategories= new ArrayList<>();
        SousCategorieService sousCatService= new SousCategorieService();
        lesSousCategories=sousCatService.getAllSousCategorie();
        lesSousCategories.add(0,new SousCategorie("All", null));
        ObservableList obs = FXCollections.observableArrayList(lesSousCategories);
        cbSousCat.setItems(obs);
    }
    
    public void creationPage(ArrayList<Evenement> listeEvenement){
        mainPain.toFront();
        mainPain.getChildren().clear();
        for (Evenement e : listeEvenement) {
            ImageView photo = new ImageView(new Image("http://localhost/TGT//web//uploads//evenement//"+ e.getImage()));
            
            Label lblNom = new Label("Nom: "+e.getNom());
            lblNom.setFont(Font.font(20));
            Label lblDuree = new Label("Du: "+e.getDateDebut()+" Au: "+e.getDateFin());
            lblDuree.setFont(Font.font(20));
            Label lblLieu = new Label("Lieu: "+e.getLieu());   
            lblLieu.setFont(Font.font(20));
          
                   
            VBox vb= new VBox();

            vb.setSpacing(10);
            photo.setFitHeight(200);
            photo.setFitWidth(300);
            vb.getChildren().add(photo);
            vb.getChildren().add(lblNom);
            vb.spacingProperty();
            vb.getChildren().add(lblDuree);
            vb.getChildren().add(lblLieu);
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
                    afficheListe(Boolean.FALSE);
                    afficheDetail(Boolean.TRUE);
                    idEvent=e.getId();
                    System.out.println(idEvent);
                    remplirInofrmationDetail(idEvent);
                }
            });
            
      
          
         }
    }

    @FXML
    private void rechercheEvenement(KeyEvent event) throws AWTException, SQLException {
        String motCle=tfRecherche.getText();            
           Robot robot= new Robot();
           tfRecherche.requestFocus();
           robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);

                 
       if(motCle.isEmpty())
       {
            EvenementService es= new EvenementService();
            ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
            lesEvents=es.getAllEvenement();
            creationPage(lesEvents);         
       }
       else
       {    
            EvenementService eveService= new EvenementService();
            ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
            lesEvents=eveService.rechercheEvenement(motCle);         
            creationPage(lesEvents);         
       }
    }

    @FXML
    private void filtrage(ActionEvent event) throws SQLException {
        SousCategorie sc= cbSousCat.getValue();
        if(sc.getLibelle().equals("All"))
        {
            EvenementService es= new EvenementService();
            ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
            lesEvents=es.getAllEvenement();
            creationPage(lesEvents);    
        }
        else
        {
            EvenementService es= new EvenementService();
            ArrayList<Evenement> lesEvents= new ArrayList<Evenement>();
            lesEvents=es.getEvenementBySousCategorie(sc);
            creationPage(lesEvents);    
        }
            
    }
    
    public void afficheListe(Boolean bool){
        lblLesEvenement.setVisible(bool);
        scrollPane.setVisible(bool);
        tfRecherche.setVisible(bool);
        lblRecherche.setVisible(bool);
        iconRecherche.setVisible(bool);
        lblFiltrer.setVisible(bool);
        cbSousCat.setVisible(bool);
    }
    
    public void afficheDetail(Boolean bool){
        lblNom.setVisible(bool);
        lblDescription.setVisible(bool);
        lblLieu.setVisible(bool);
        lblDateDebut.setVisible(bool);
        lblDateFin.setVisible(bool);
        lblNbrParticipantMax.setVisible(bool);
        lblSousCat.setVisible(bool);
        lblPlaceRestantes.setVisible(bool);
        imageEvent.setVisible(bool);
        btnParticiper.setVisible(bool);
        //lblAvertissement.setVisible(bool);
        lblNomAgence.setVisible(bool);
        lblTelAgence.setVisible(bool);
        lblEmailAgence.setVisible(bool);
        //btnAnnulerParticipation.setVisible(bool);
        separator.setVisible(bool) ;
    }
    
    public void remplirInofrmationDetail(int idEvent){        
        EvenementService es= new EvenementService();
        AgenceService as =new AgenceService();
        SousCategorieService scs= new SousCategorieService();
        try {
            Evenement e= es.getEvenementById(idEvent);
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
                  btnParticiper.setVisible(false);
                  btnAnnulerParticipation.setVisible(false);
                  lblAvertissement.setVisible(true);
            }           
        } catch (SQLException ex) {
            Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
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


    
}
