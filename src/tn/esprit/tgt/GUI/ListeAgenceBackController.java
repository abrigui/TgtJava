/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.services.AgenceService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListeAgenceBackController implements Initializable {
    @FXML
    private TableView<Agence> tvAgence;

    @FXML
    private TableColumn<Agence, Integer> tcId;
    @FXML
    private TableColumn<Agence, String> tcNomAgence;
    @FXML
    private TableColumn<Agence, String> tcMatriculeFoscale;
    @FXML
    private TableColumn<Agence, String> tcAdresse;
    @FXML
    private TableColumn<Agence, Integer> tcTelephone;
    @FXML
    private TableColumn<Agence, Integer> tcFax;
    @FXML
    private TableColumn<Agence, String> tcSite;
    @FXML
    private TableColumn<Agence, String> tcEmail;
    @FXML
    private TextField tfRecherche;
    

    
     public void populate() {
        AgenceService agService= new AgenceService();
        ArrayList<Agence> agences = new ArrayList<>();
        try {
            agences=agService.getAgenceApprouver();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList obs = FXCollections.observableArrayList(agences);
        tvAgence.setItems(obs);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
        tcId.setCellValueFactory(new PropertyValueFactory<Agence,Integer>("id"));
        tcNomAgence.setCellValueFactory(new PropertyValueFactory<Agence,String>("nom"));
        tcMatriculeFoscale.setCellValueFactory(new PropertyValueFactory<Agence,String>("matriculeFiscale"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<Agence,String>("email"));
        tcAdresse.setCellValueFactory(new PropertyValueFactory<Agence,String>("adresse"));
        tcTelephone.setCellValueFactory(new PropertyValueFactory<Agence,Integer>("telephone"));
        tcFax.setCellValueFactory(new PropertyValueFactory<Agence,Integer>("fax"));
        tcSite.setCellValueFactory(new PropertyValueFactory<Agence,String>("site"));
    }

  
     @FXML
    void rechercheAgence(KeyEvent event) throws SQLException, InterruptedException, AWTException {
       String motCle=tfRecherche.getText();            
           Robot robot= new Robot();
           tfRecherche.requestFocus();
           robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);

                 
       if(motCle.isEmpty())
       {
        populate();
       }
       else
       {          
            AgenceService agService= new AgenceService();
            ArrayList<Agence> agences = new ArrayList<>();
            agences=agService.rechercheAgence(motCle);
            System.out.println("Recherche Terminer");
            ObservableList obs = FXCollections.observableArrayList(agences);
            tvAgence.setItems(obs); 

       }
       
    }
    
}
