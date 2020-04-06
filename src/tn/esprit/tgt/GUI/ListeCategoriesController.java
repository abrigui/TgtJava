/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Categorie;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.CategorieService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ListeCategoriesController implements Initializable {

     @FXML
    private TableView<Categorie> tvCategories;

    @FXML
    private TableColumn<Categorie, Integer> tcIdCategorie;

    @FXML
    private TableColumn<Categorie, String> tcLibelleCategorie;

    @FXML
    private JFXButton btnAjouter;

    @FXML
    private JFXButton btnModifier;

    @FXML
    private JFXButton btnSupprimer;

    @FXML
    private Label lblListeCategorie;

    @FXML
    private Label lblAction;
    
    @FXML
    private Label lblLibelle;

    @FXML
    private JFXTextField tfLibelle;

    @FXML
    private JFXButton btnValider;
    
    private Categorie categorieGlobal=null;
    private String action=null;

     
     
    public void populate() {
        CategorieService catService= new CategorieService();
        ArrayList<Categorie> categories = new ArrayList<>();
        try {
            categories=catService.getAllCategorie();
        } catch (SQLException ex) {
            Logger.getLogger(ListeCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList obs = FXCollections.observableArrayList(categories);
        tvCategories.setItems(obs);
    }
   
     
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        populate();
        tcIdCategorie.setCellValueFactory(new PropertyValueFactory<Categorie,Integer>("id"));
        tcLibelleCategorie.setCellValueFactory(new PropertyValueFactory<Categorie,String>("libelle"));
        
           
         tvCategories.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btnAjouter.setDisable(false);
                tfLibelle.setText("");
                action="";
                ///////////////// Translation//////////////
               // btnAjouter.setLayoutY(261);
               // btnModifier.setLayoutY(327);
               // btnSupprimer.setLayoutY(394);
                //tvCategories.setLayoutY(233);
               // lblListeCategorie.setLayoutY(174);
                //////////////// Affichage champ d'ajout///////////
                lblAction.setText("");
                lblAction.setVisible(false);
                lblAction.setDisable(true);
                lblLibelle.setVisible(false);
                lblLibelle.setDisable(true);
                tfLibelle.setVisible(false);
                tfLibelle.setDisable(true);
                btnValider.setDisable(true);
                btnValider.setVisible(false); 
                int idCat=tvCategories.getSelectionModel().getSelectedItem().getId();
               // System.out.println("Id cat: "+idCat);
                try {
                   CategorieService catService= new CategorieService();
                     categorieGlobal = catService.getCategorieById(idCat);
                    if(categorieGlobal!=null){
                       // btnAjouter.setDisable(true);
                        btnModifier.setDisable(false);
                        ////////Controle pour les sous Categorie et les evenement avant d'autorisé la supp//////////
                        SousCategorieService sousCatService=new SousCategorieService();
                        ArrayList <SousCategorie> lesSousCat=sousCatService.getAllSousCategorieByCategorie(categorieGlobal);
                        ArrayList <Evenement> lesEvenements= new ArrayList<>();
                        if(lesSousCat.size()>0)
                        {
                            for (int i=0; i<lesSousCat.size();i++)
                            {
                                SousCategorie sc = lesSousCat.get(i);
                                EvenementService eventService = new EvenementService();
                                ArrayList <Evenement> eveIntermediare= new ArrayList<>();
                                eveIntermediare=eventService.getEvenementBySousCategorie(sc);
                                for(int j=0;j<eveIntermediare.size();j++){
                                    lesEvenements.add(eveIntermediare.get(j));
                                }
                            }
                            if(lesEvenements.size()>0)
                                btnSupprimer.setDisable(true);
                            else
                               btnSupprimer.setDisable(false);                                   
                        }
                        else
                             btnSupprimer.setDisable(false); 
                        
                                                                       
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ListeCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
                }
                

            }

        });
    }
    
    @FXML
    void AjouterCategorie(ActionEvent event) {
        tfLibelle.setText("");
        action="Ajouter";
        ///////////////// Translation//////////////
       // btnAjouter.setLayoutY(392);
        //btnModifier.setLayoutY(458);
       // btnSupprimer.setLayoutY(525);
       // tvCategories.setLayoutY(364);
       // lblListeCategorie.setLayoutY(305);
        //////////////// Affichage champ d'ajout///////////
        lblAction.setText("Ajout de catégorie :");
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
    void ModifierCategorie(ActionEvent event) {
        tfLibelle.setText("");
        action="Modifier";
        tfLibelle.setText(categorieGlobal.getLibelle());
        ///////////////// Translation//////////////
      //  btnAjouter.setLayoutY(392);
      // btnModifier.setLayoutY(458);
      //  btnSupprimer.setLayoutY(525);
      //  tvCategories.setLayoutY(364);
      //  lblListeCategorie.setLayoutY(305);
        //////////////// Affichage champ d'ajout///////////
        lblAction.setText("Modification de catégorie :");
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
    void SupprimerCategorie(ActionEvent event) throws SQLException {
        JOptionPane jop = new JOptionPane();    	
      int option = jop.showConfirmDialog(null, 
        "Voulez-vous supprimer cette catégorie ?\n NB: Suite à la suppression toutes les sous catégories appartenant à cette dérnière seront eux aussi supprimées.",
        "Suppression", 
        JOptionPane.YES_NO_OPTION, 
        JOptionPane.QUESTION_MESSAGE);

      if(option != JOptionPane.NO_OPTION &&  
      option != JOptionPane.CLOSED_OPTION){
          SousCategorieService sousCatService = new SousCategorieService();          
          sousCatService.supprimerSousCategorieParCategorie(categorieGlobal);
          System.out.println("supp sous cat");
          CategorieService catService=new CategorieService();
          System.out.println("supp cat :"+categorieGlobal.getLibelle());
          catService.supprimerCategorie(categorieGlobal);
          categorieGlobal=null;
          btnAjouter.setDisable(false);
          btnModifier.setDisable(true);
          btnSupprimer.setDisable(true);
          populate();
      }
    }
    
    
    @FXML void ValiderAction(ActionEvent event) throws SQLException { 
        if (action.equals("Ajouter"))
        {
            if(tfLibelle.getText().isEmpty())
            {
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de l'ajout", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                Categorie cat =new Categorie(tfLibelle.getText());
                CategorieService catService= new CategorieService();
                catService.ajouterCategorie(cat);
                populate();
                tfLibelle.setText("");
                action="";
                ///////////////// Translation//////////////
              //  btnAjouter.setLayoutY(261);
              //  btnModifier.setLayoutY(327);
              //  btnSupprimer.setLayoutY(394);
              //  tvCategories.setLayoutY(233);
              //  lblListeCategorie.setLayoutY(174);
                //////////////// Affichage champ d'ajout///////////
                lblAction.setText("");
                lblAction.setVisible(false);
                lblAction.setDisable(true);
                lblLibelle.setVisible(false);
                lblLibelle.setDisable(true);
                tfLibelle.setVisible(false);
                tfLibelle.setDisable(true);
                btnValider.setDisable(true);
                btnValider.setVisible(false); 
            }                           
        }
        else //Action.equals("Modifier")
        {
            if(tfLibelle.getText().isEmpty())
            {
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de la modification", "Attention",JOptionPane.WARNING_MESSAGE);
                
            }
            else
            {
                categorieGlobal.setLibelle(tfLibelle.getText());
                CategorieService catService= new CategorieService();
                catService.modifierCatgeorie(categorieGlobal);
                populate();
                action="";
                categorieGlobal=null;               
                ///////////////// Translation//////////////
              //  btnAjouter.setLayoutY(261);
              //  btnModifier.setLayoutY(327);
              //  btnSupprimer.setLayoutY(394);
              //  tvCategories.setLayoutY(233);
             //   lblListeCategorie.setLayoutY(174);
                //////////////// Affichage champ d'ajout///////////
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
            }                    
        }
    
    }
    
    
    
}
