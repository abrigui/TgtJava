/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import static tn.esprit.tgt.GUI.AjoutAgenceController.copyFile;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Evenement;
import tn.esprit.tgt.entities.SousCategorie;
import tn.esprit.tgt.entities.Sponsor;
import tn.esprit.tgt.entities.User;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import tn.esprit.tgt.services.SousCategorieService;
import tn.esprit.tgt.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ModifierEvenementController implements Initializable {

    @FXML
    private AnchorPane anchorModifierEvent;
    @FXML
    private TextField tfNomEvenement;
    @FXML
    private TextField tfLieu;
    @FXML
    private JFXButton btnSelectionImageEvenement;
    @FXML
    private JFXButton btnValider;
    @FXML
    private ComboBox<Agence> cbAgence;
    @FXML
    private ImageView ivLogoEvent;
    @FXML
    private TextArea tfDescription;
    @FXML
    private DatePicker dtpDD;
    @FXML
    private DatePicker dtpDF;
    @FXML
    private ComboBox<SousCategorie> cbSousCat;
    @FXML
    private TextField tfNbrParticipant;
    
    private String urlEvent;
    private static File photoEvent;
    private int idEvent=10;
    private User currentUser=new User(7, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            comboBoxFill();
            EvenementService evs = new EvenementService();
            SousCategorieService ss= new SousCategorieService();
            AgenceService ags = new AgenceService();
            Evenement e=evs.getEvenementById(idEvent);
            e.setAgence(ags.getAgenceById(e.getAgence().getId()));
            e.setSousCategorie(ss.getSousCategorieById(e.getSousCategorie().getId()));
            tfNomEvenement.setText(e.getNom());
            tfDescription.setText(e.getDescription());
            tfLieu.setText(e.getLieu());
            tfNbrParticipant.setText(""+e.getNbParticipantMax());
            dtpDD.setValue(LocalDate.parse(e.getDateDebut().toString()));
            dtpDF.setValue(LocalDate.parse(e.getDateFin().toString()));
            cbSousCat.setValue(e.getSousCategorie());
            cbAgence.setValue(e.getAgence());
            ivLogoEvent.setImage(new Image("http://localhost/TGT//web//uploads//evenement//"+ e.getImage()));
            photoEvent= new File("C:\\xampp\\htdocs\\TGT\\web\\uploads\\evenement\\"+e.getImage());
            centrerIamgeEvent();
        } catch (SQLException ex) {
            Logger.getLogger(AjouterEvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void selectionImageEvenet(ActionEvent event) {
         FileChooser fc = new FileChooser();
        //File selectedfile = fc.showOpenDialog(null);
        photoEvent =fc.showOpenDialog(null);
        if (photoEvent != null) 
        {
            urlEvent=photoEvent.toURI().toString();
            Image img = new Image(urlEvent);
            ivLogoEvent.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoEvent.getFitWidth() / img.getWidth();
                double ratioY = ivLogoEvent.getFitHeight() / img.getHeight();

                double reducCoeff = 0;
                if(ratioX >= ratioY) 
                {
                    reducCoeff = ratioY;
                }
                else 
                {
                reducCoeff = ratioX;
                }

                w = img.getWidth() * reducCoeff;
                h = img.getHeight() * reducCoeff;

                ivLogoEvent.setX((ivLogoEvent.getFitWidth() - w) / 2);
                ivLogoEvent.setY((ivLogoEvent.getFitHeight() - h) / 2);
            }
        }
    }

    @FXML
    private void validerEvent(ActionEvent event) throws SQLException, IOException {
        if(tfNomEvenement.getText().isEmpty() || tfDescription.getText().isEmpty() || tfLieu.getText().isEmpty()  || dtpDD.getValue()==null 
                || dtpDF.getValue()==null || cbAgence.getValue()==null || cbSousCat.getValue()==null  || ivLogoEvent.getImage()==null
                || tfNbrParticipant.getText().isEmpty())
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de l'ajout", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                Date dateDebut=Date.from(dtpDD.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                Date dateFin=Date.from(dtpDF.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                /////Controle Date/////
                Date dateSys= new Date();
                if( dateFin.before(dateDebut) || dateFin.before(dateSys)){
                  JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Verifier la date début et la date fin de l'événement ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else if (Integer.parseInt(tfNbrParticipant.getText())== 0){
                     JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le nombre de participant doit être supérieur à 0 ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }     
                else
                {
                    EvenementService evs= new EvenementService();
                    Evenement e = new Evenement(idEvent,tfNomEvenement.getText(), tfDescription.getText(), dateDebut, tfLieu.getText(), dateFin, Integer.parseInt(tfNbrParticipant.getText()), cbSousCat.getValue(), photoEvent.getName(),cbAgence.getValue());
                    evs.modifierEvenement(e);
                    copyFile(photoEvent); 
                    System.out.println("Modifié");
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Modification effectuée avec succès ", "Modification", JOptionPane.INFORMATION_MESSAGE);
                    viderChamps();
                }
               
            }  
       
             
    }
    
     public static void copyFile(File photo) throws IOException{

        File source = new File(photo.getAbsolutePath());
        String name = photo.getName();
        File  dest = new File("C:\\xampp\\htdocs\\TGT\\web\\uploads\\evenement");
        String destination = dest.toPath().toString().concat("\\"+name);
        Path fd = Paths.get(destination);
        try
        {
            System.out.println(fd);
            Files.copy(source.toPath(),fd,StandardCopyOption.REPLACE_EXISTING);
            System.out.println(fd);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
     
     public void comboBoxFill() throws SQLException{
        ArrayList<Agence> lesAgences= new ArrayList<>();
        AgenceService agService= new AgenceService();
        lesAgences=agService.getAgenceByUser(currentUser);
        ObservableList obsAg = FXCollections.observableArrayList(lesAgences);
        cbAgence.setItems(obsAg);
        
        ArrayList<SousCategorie> lesSousCat= new ArrayList<>();
        SousCategorieService sousCatService= new SousCategorieService();
        lesSousCat=sousCatService.getAllSousCategorie();
        ObservableList obsSousCat = FXCollections.observableArrayList(lesSousCat);
        cbSousCat.setItems(obsSousCat);
    }
    
     public void centrerIamgeEvent(){
          if (photoEvent != null) 
        {
            urlEvent=photoEvent.toURI().toString();
            Image img = new Image(urlEvent);
            ivLogoEvent.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoEvent.getFitWidth() / img.getWidth();
                double ratioY = ivLogoEvent.getFitHeight() / img.getHeight();

                double reducCoeff = 0;
                if(ratioX >= ratioY) 
                {
                    reducCoeff = ratioY;
                }
                else 
                {
                reducCoeff = ratioX;
                }

                w = img.getWidth() * reducCoeff;
                h = img.getHeight() * reducCoeff;

                ivLogoEvent.setX((ivLogoEvent.getFitWidth() - w) / 2);
                ivLogoEvent.setY((ivLogoEvent.getFitHeight() - h) / 2);
            }
        }
    }
     
    public void viderChamps(){
          tfNomEvenement.setText("");
            tfDescription.setText("");
            tfLieu.setText("");
            tfNbrParticipant.setText("");
            dtpDD.setValue(null);
            dtpDF.setValue(null);
            cbSousCat.setValue(null);
            cbAgence.setValue(null);
            ivLogoEvent.setImage(null);
            photoEvent= null;
    }
}
