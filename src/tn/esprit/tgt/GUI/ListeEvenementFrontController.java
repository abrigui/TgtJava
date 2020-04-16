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
                       // afficheListe(Boolean.FALSE);
                        //afficheDetail(Boolean.TRUE);
                        idEvent=e.getId();
                        //System.out.println(idEvent);
                        //remplirInofrmationDetail(idEvent);
                    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/tgt/GUI/DetailEvenement.fxml"));
                        AnchorPane pp = loader.load();
                        DetailEvenementController detailCont = loader.getController();
                        detailCont.remplirInofrmationDetail(idEvent);
                        anchorGlobal.getChildren().clear();
                        anchorGlobal.getChildren().add(pp);
                    } catch (IOException ex) {
                        Logger.getLogger(ListeEvenementFrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                            
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
    
   
}
