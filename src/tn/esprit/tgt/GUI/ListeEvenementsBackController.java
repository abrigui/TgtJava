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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import tn.esprit.tgt.entities.*;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListeEvenementsBackController implements Initializable {

    @FXML
    private TableView<Evenement> tvEvenements;
    @FXML
    private TableColumn<Evenement, Integer> tcId;
    @FXML
    private TableColumn<Evenement, String> tcNomEvenement;
    @FXML
    private TableColumn<Evenement, Date> tcDateDebut;
    @FXML
    private TableColumn<Evenement, String> tcLieu;
    @FXML
    private TableColumn<Evenement, Date> tcDateFin;
    @FXML
    private TableColumn<Evenement, Integer> tcNbPlace;
    @FXML
    private TableColumn<Evenement, SousCategorie> tcClassification;
    @FXML
    private TableColumn<Evenement, Agence> tcAgence;
    @FXML
    private TextField tfRecherche;
    
    public void populate() {
        EvenementService eveService= new EvenementService();
        ArrayList<Evenement> events = new ArrayList<>();
        try {
            events=eveService.getAllEvenement();
            AgenceService agServ = new AgenceService();
            SousCategorieService sousCatServ = new SousCategorieService();
            for(int i=0;i<events.size();i++)
            {
                events.get(i).setAgence(agServ.getAgenceById(events.get(i).getAgence().getId()));
                events.get(i).setSousCategorie(sousCatServ.getSousCategorieById(events.get(i).getSousCategorie().getId()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList obs = FXCollections.observableArrayList(events);
        tvEvenements.setItems(obs);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
        tcId.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("id"));
        tcNomEvenement.setCellValueFactory(new PropertyValueFactory<Evenement,String>("nom"));
        tcDateDebut.setCellValueFactory(new PropertyValueFactory<Evenement,Date>("dateDebut"));
        tcLieu.setCellValueFactory(new PropertyValueFactory<Evenement,String>("lieu"));
        tcDateFin.setCellValueFactory(new PropertyValueFactory<Evenement,Date>("dateFin"));
        tcNbPlace.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("nbParticipantMax"));
        tcClassification.setCellValueFactory(new PropertyValueFactory<Evenement,SousCategorie>("sousCategorie"));
        tcAgence.setCellValueFactory(new PropertyValueFactory<Evenement,Agence>("agence"));
    }    


    @FXML
    private void rechercheEvenement(KeyEvent event) throws SQLException, AWTException {
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
            ArrayList<Evenement> events = new ArrayList<>();
            EvenementService eveService= new EvenementService();
            events=eveService.rechercheEvenement(motCle);
            AgenceService agServ = new AgenceService();
            SousCategorieService sousCatServ = new SousCategorieService();
            for(int i=0;i<events.size();i++)
            {
                events.get(i).setAgence(agServ.getAgenceById(events.get(i).getAgence().getId()));
                events.get(i).setSousCategorie(sousCatServ.getSousCategorieById(events.get(i).getSousCategorie().getId()));
            }
            
            System.out.println("Recherche Terminer");
            ObservableList obs = FXCollections.observableArrayList(events);
            tvEvenements.setItems(obs); 

       }
    }
    
}
