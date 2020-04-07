/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tn.esprit.tgt.GUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class DashboardController implements Initializable {
    
    @FXML
    private TextField tfTGT;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnUtilisateurs;

    @FXML
    private JFXButton btnAgences;

    @FXML
    private JFXButton btnCategories;

    @FXML
    private JFXButton btnSousCategorie;

    @FXML
    private JFXButton btnEvenements;

    @FXML
    private JFXButton btnBlog;

    @FXML
    private JFXButton btnDeconnexion;

    @FXML
    private JFXTextField tfNomAdmin;

    @FXML
    private Pane mainPane;
 
    
    public Pane getMainPane() {
        return mainPane;
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
     
     
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPane.toFront();
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/AccueilAdmin.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
        
    }  
            
    @FXML
    private void btnUtilisateurs (ActionEvent event) throws IOException {
        
    }
 
    @FXML
    void btnCategories(ActionEvent event) {
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/ListeCategories.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
    @FXML
    void btnSousCategorie(ActionEvent event) {
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/SousCategorie.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
     @FXML
    void btnDashboard(ActionEvent event) {
        mainPane.toFront();
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/AccueilAdmin.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
     @FXML
    void btnAgences(ActionEvent event) {
       mainPane.toFront();
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/ListeAgenceBack.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
    @FXML
    void btnEvenements(ActionEvent event) {
        mainPane.toFront();
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/ListeEvenementsBack.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
}
