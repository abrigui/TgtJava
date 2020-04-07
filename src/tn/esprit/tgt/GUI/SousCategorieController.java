/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.AWTException;
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
import javax.swing.JOptionPane;
import tn.esprit.tgt.entities.Categorie;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.services.CategorieService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class SousCategorieController implements Initializable {
    
   @FXML
    private TableView<SousCategorie> tvSousCategories;

    @FXML
    private TableColumn<SousCategorie, Integer> tcIdSousCategorie;

    @FXML
    private TableColumn<SousCategorie, String> tcLibelleSousCategorie;
    
    @FXML
    private TableColumn<SousCategorie, Categorie> tcCategorie;

    @FXML
    private JFXButton btnAjouter;

    @FXML
    private JFXButton btnModifier;

    @FXML
    private JFXButton btnSupprimer;

    @FXML
    private Label lblListeSousCategorie;

    @FXML
    private Label lblAction;

    @FXML
    private JFXTextField tfLibelle;

    @FXML
    private Label lblLibelle;

    @FXML
    private JFXButton btnValider;

    @FXML
    private JFXComboBox<Categorie> cbCategorie;

    @FXML
    private Label lblCategorie;
    
    private SousCategorie sousCategorieGlobal=null;
    private String action=null;
    
    /**
     * Initializes the controller class.
     */
    public void populate() {
        SousCategorieService sousCatService= new SousCategorieService();
        ArrayList<SousCategorie> lesSousCategories = new ArrayList<>();
        try {
            lesSousCategories=sousCatService.getAllSousCategorie();
            for(int i=0;i<lesSousCategories.size();i++){
                CategorieService catService = new CategorieService();
                lesSousCategories.get(i).setCategorie(catService.getCategorieById(lesSousCategories.get(i).getCategorie().getId()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SousCategorieController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList obs = FXCollections.observableArrayList(lesSousCategories);
        tvSousCategories.setItems(obs);
    }
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
        tcIdSousCategorie.setCellValueFactory(new PropertyValueFactory<SousCategorie,Integer>("id"));
        tcLibelleSousCategorie.setCellValueFactory(new PropertyValueFactory<SousCategorie,String>("libelle"));
        tcCategorie.setCellValueFactory(new PropertyValueFactory<SousCategorie,Categorie>("categorie"));
        tvSousCategories.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnAjouter.setDisable(false);
                lblAction.setText("");
                lblAction.setVisible(false);
                lblAction.setDisable(true);
                lblLibelle.setVisible(false);
                lblLibelle.setDisable(true);
                tfLibelle.setVisible(false);
                tfLibelle.setDisable(true);
                btnValider.setDisable(true);
                btnValider.setVisible(false);
                cbCategorie.setDisable(true);
                cbCategorie.setVisible(false);
                lblCategorie.setDisable(true);
                lblCategorie.setVisible(false);
                tfLibelle.setText("");
                sousCategorieGlobal=tvSousCategories.getSelectionModel().getSelectedItem();
                if(sousCategorieGlobal!=null)
                {
                    //btnAjouter.setDisable(true);
                    btnModifier.setDisable(false);
                    ////////Controle pour les evenement avant d'autorisé la supp//////////
                    ArrayList <Evenement> lesEvenements= new ArrayList<>();
                    EvenementService eventService = new EvenementService();                  
                    try 
                    {
                        lesEvenements=eventService.getEvenementBySousCategorie(sousCategorieGlobal);
                    } 
                    catch (SQLException ex) 
                    {
                        Logger.getLogger(SousCategorieController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                           
                    if(lesEvenements.size()>0)
                        btnSupprimer.setDisable(true);
                    else
                        btnSupprimer.setDisable(false);                                   
                }
                else
                    btnSupprimer.setDisable(false);                                  
           }
       });
    }

  @FXML
    void AjouterSousCategorie(ActionEvent event) throws SQLException {
        tfLibelle.setText("");
        cbCategorie.setValue(null);
        action="Ajouter";

        //////////////// Affichage champ d'ajout///////////
        lblAction.setText("Ajout de sous catégorie :");
        lblAction.setVisible(true);
        lblAction.setDisable(false);
        lblLibelle.setVisible(true);
        lblLibelle.setDisable(false);
        tfLibelle.setVisible(true);
        tfLibelle.setDisable(false);
        ////////comboBox//////////////
        lblCategorie.setDisable(false);
        lblCategorie.setVisible(true);
        cbCategorie.setDisable(false);
        cbCategorie.setVisible(true);
        ArrayList<Categorie> lesCategories= new ArrayList<>();
        CategorieService catService= new CategorieService();
        lesCategories=catService.getAllCategorie();
        ObservableList obs = FXCollections.observableArrayList(lesCategories);
        cbCategorie.setItems(obs);
        ///////////////////////////////
        btnValider.setDisable(false);
        btnValider.setVisible(true);  
    }

    @FXML
    void ModifierSousCategorie(ActionEvent event) throws SQLException {
        tfLibelle.setText("");
        cbCategorie.setValue(null);
        action="Modifier";
        tfLibelle.setText(sousCategorieGlobal.getLibelle());

        ////////comboBox//////////////
        lblCategorie.setDisable(false);
        lblCategorie.setVisible(true);
        cbCategorie.setDisable(false);
        cbCategorie.setVisible(true);
        ArrayList<Categorie> lesCategories= new ArrayList<>();
        CategorieService catService= new CategorieService();
        lesCategories=catService.getAllCategorie();
        ObservableList obs = FXCollections.observableArrayList(lesCategories);
        cbCategorie.setItems(obs);
        cbCategorie.setValue(sousCategorieGlobal.getCategorie());

        //////////////// Affichage champ d'ajout///////////
        lblAction.setText("Modification de sous catégorie :");
        lblAction.setVisible(true);
        lblAction.setDisable(false);
        lblLibelle.setVisible(true);
        lblLibelle.setDisable(false);
        tfLibelle.setVisible(true);
        tfLibelle.setDisable(false);
        btnValider.setDisable(false);
        btnValider.setVisible(true);    
    }

    @FXML
    void SupprimerSousCategorie(ActionEvent event) throws SQLException, AWTException {
        JOptionPane jop = new JOptionPane();    	
      int option = jop.showConfirmDialog(null, 
        "Voulez-vous supprimer cette sous catégorie ?",
        "Suppression", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);

      if(option != JOptionPane.NO_OPTION &&  
      option != JOptionPane.CLOSED_OPTION){
          SousCategorieService sousCatService = new SousCategorieService();          
          sousCatService.supprimerSousCategorie(sousCategorieGlobal);         
          sousCategorieGlobal=null;
          btnAjouter.setDisable(false);
          btnModifier.setDisable(true);
          btnSupprimer.setDisable(true);
          populate();
          Notifications n = new Notifications();
          n.displayTray("Sous catégories", "Sous catégorie supprimée avec succès !");
      }
    }

    @FXML
    void ValiderAction(ActionEvent event) throws SQLException, AWTException {
         if (action.equals("Ajouter"))
        {
            if(tfLibelle.getText().isEmpty() || cbCategorie.getValue()== null)
            {
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de l'ajout", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            {                
                Categorie cat =cbCategorie.getValue();
                SousCategorie sousCat= new SousCategorie(tfLibelle.getText(), cat);
                SousCategorieService sousCatService= new SousCategorieService();
                sousCatService.ajouterSousCategorie(sousCat);
                populate();
                tfLibelle.setText("");
                action="";

                lblAction.setText("");
                lblAction.setVisible(false);
                lblAction.setDisable(true);
                lblLibelle.setVisible(false);
                lblLibelle.setDisable(true);
                tfLibelle.setVisible(false);
                tfLibelle.setDisable(true);
                btnValider.setDisable(true);
                btnValider.setVisible(false);
                cbCategorie.setDisable(true);
                cbCategorie.setVisible(false);
                lblCategorie.setDisable(true);
                lblCategorie.setVisible(false);
                
                 Notifications n = new Notifications();
                 n.displayTray("Sous catégories", "Sous catégorie ajoutée avec succès !");
            }                           
        }
        else //Action.equals("Modifier")
        {
            if(tfLibelle.getText().isEmpty() || cbCategorie.getValue()== null)
            {
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de la modification", "Attention",JOptionPane.WARNING_MESSAGE);
                
            }
            else
            {
                sousCategorieGlobal.setLibelle(tfLibelle.getText());
                sousCategorieGlobal.setCategorie(cbCategorie.getValue());
                SousCategorieService sc= new SousCategorieService();
                sc.modifierSousCatgeorie(sousCategorieGlobal);
                populate();

                action="";
                sousCategorieGlobal=null;               
               
                lblAction.setText("");
                lblAction.setVisible(false);
                lblAction.setDisable(true);
                lblLibelle.setVisible(false);
                lblLibelle.setDisable(true);
                tfLibelle.setText("");
                tfLibelle.setVisible(false);
                tfLibelle.setDisable(true);
                btnValider.setDisable(true);
                btnValider.setVisible(false); 
                btnAjouter.setDisable(false);
                btnModifier.setDisable(true);
                btnSupprimer.setDisable(true);
                lblCategorie.setDisable(false);
                lblCategorie.setVisible(false);
                cbCategorie.setVisible(false);
                
                Notifications n = new Notifications();
                n.displayTray("Sous catégories", "Sous catégorie modifiée avec succès !");
            }                    
        }
         
               
    }
    
}
