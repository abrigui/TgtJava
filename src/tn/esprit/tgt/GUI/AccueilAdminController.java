/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AccueilAdminController implements Initializable {
    @FXML
    private Label lblUtilisateurs;

    @FXML
    private Label lblAgences;

    @FXML
    private Label lblArticles;

    @FXML
    private Label lblEvenements;

    @FXML
    private JFXButton btnApprouver;

    @FXML
    private JFXButton btnRefuser;

    @FXML
    private TableView<Agence> tvListeDemande;

    @FXML
    private TableColumn<Agence, Integer> tcIdAgence;
    
    @FXML
    private TableColumn<Agence, String> tcNom;

    @FXML
    private TableColumn<Agence, String> tcMatFisc;

    @FXML
    private TableColumn<Agence, Integer> tcTelephone;
    
    Agence agenceATraiter = null;
    
    public void populate() {
        AgenceService agService= new AgenceService();
        ArrayList<Agence> agences = new ArrayList<>();
        try {
            agences=agService.getAgenceEnAttente();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList obs = FXCollections.observableArrayList(agences);
        tvListeDemande.setItems(obs);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        populate();
        tcIdAgence.setCellValueFactory(new PropertyValueFactory<Agence,Integer>("id"));
        tcNom.setCellValueFactory(new PropertyValueFactory<Agence,String>("nom"));
        tcMatFisc.setCellValueFactory(new PropertyValueFactory<Agence,String>("matriculeFiscale"));
        tcTelephone.setCellValueFactory(new PropertyValueFactory<Agence,Integer>("telephone"));
        try {
            //////////////////////////////
            AgenceService agService= new AgenceService();
            lblAgences.setText(""+agService.getNbrAgenceApprouver());
            EvenementService es= new EvenementService();
            lblEvenements.setText(""+es.getNbrEvenement());
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         tvListeDemande.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int idAgence=tvListeDemande.getSelectionModel().getSelectedItem().getId();
                System.out.println("Id agence: "+idAgence);
                try {
                    AgenceService agService= new AgenceService();
                    agenceATraiter = agService.getAgenceById(idAgence);
                    if(agenceATraiter!=null){
                        System.out.println(agenceATraiter.getNom()+" "+agenceATraiter.getAdresse());
                        btnApprouver.setDisable(false);
                        btnRefuser.setDisable(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                

            }

        });
    }  
    
    
    @FXML
    void ApprouverAgence(ActionEvent event) throws SQLException {
        AgenceService agService= new AgenceService();
        agService.updateEtatAgence(agenceATraiter, 1);
        btnApprouver.setDisable(true);
        btnRefuser.setDisable(true);
        populate();
        lblAgences.setText(""+agService.getNbrAgenceApprouver());
        
    }

    @FXML
    void RefuserAgence(ActionEvent event) throws SQLException {
        AgenceService agService= new AgenceService();
        agService.supprimerAgence(agenceATraiter);
        btnApprouver.setDisable(true);
        btnRefuser.setDisable(true);
        populate();
    }
    
}
