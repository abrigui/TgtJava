/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.tgt.entities.User;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class MenuController implements Initializable {

    @FXML
    private Button btn_connexion;
    @FXML
    private Button btn_inscription;
    @FXML
    private Button btn_evenements;
    @FXML
    private Button btn_agences;
    @FXML
    private Button btn_talents;
    @FXML
    private Button btn_accueil;
    @FXML
    private Button btn_blog;
    @FXML
    private AnchorPane mainPane;
    
    
    public AnchorPane getMainPane() {
        return mainPane;
    }

    public void setMainPane(AnchorPane mainPane) {
        this.mainPane = mainPane;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    

    @FXML
    private void BtnAgences(ActionEvent event) {
    }

    @FXML
    private void BtnTalents(ActionEvent event) {
        
    }

    @FXML
    private void BtntAccueil(ActionEvent event) {
    }

    @FXML
    private void afficherListeEvenement(ActionEvent event) {
        mainPane.getChildren().clear();
        Parent parent = null;
        try 
        {
            parent = FXMLLoader.load(getClass().getResource("/tn/esprit/tgt/GUI/ListeEvenementFront.fxml"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainPane.getChildren().add(parent);
    }
    
}
