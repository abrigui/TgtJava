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
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.entities.Sponsor;
import tn.esprit.tgt.entities.User;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.SponsorService;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class ModifierAgenceController implements Initializable {

    @FXML
    private TextField tfNomAgence;
    @FXML
    private TextField tfMatriculeFiscale;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfTelephone;
    @FXML
    private ImageView ivLogoAgence;
    @FXML
    private JFXButton btnSelectionImageAgence;
    @FXML
    private JFXButton btnValider;
    @FXML
    private TextField tfFax;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfSite;
    @FXML
    private ComboBox<Sponsor> cbSponsor;
    @FXML
    private JFXButton btnAjouterSponsor;
    @FXML
    private TextField tfNomSponsor;
    @FXML
    private TextField tfAdresseSponsor;
    @FXML
    private TextField tfEmailSponsor;
    @FXML
    private TextField tfTelephoneSponsor;
    @FXML
    private ImageView ivLogoSponsor;
    @FXML
    private JFXButton btnSelectionImageSponsor;
    @FXML
    private JFXButton btnValiderSponsor;
    @FXML
    private JFXButton btnModifierSponsor;
    @FXML
    private JFXButton btnAnnuler;

    private String urlAgence;
    private String urlSponsor;
    private static File photoAgence;
    private static File photoSponsor;
    
    private ObservableList obsSponsor;    
    private User currentUser=new User(7, "kk", "kk", "12kk", "kk", "kk", "kk", "kk", 200145, "kk");
    private int idAgenceGlobal=11;
      
    private int idSponsorModif;
    private String action; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            comboBoxFill();
            AgenceService ags= new AgenceService();
            Agence ag = ags.getAgenceById(idAgenceGlobal);
            SponsorService sps =new SponsorService();          
            ag.setSponsor(sps.getSponsorById(ag.getSponsor().getId()));
          
            tfNomAgence.setText(ag.getNom()); 
            tfMatriculeFiscale.setText(ag.getMatriculeFiscale());
            tfEmail.setText(ag.getEmail()); 
            tfAdresse.setText(ag.getAdresse());
            tfTelephone.setText(""+ag.getTelephone()); 
            tfFax.setText(""+ag.getFax()); 
            tfSite.setText(ag.getSite());
            ivLogoAgence.setImage(new Image("http://localhost/TGT//web//uploads//logo//"+ ag.getLogo()));
            cbSponsor.setValue(ag.getSponsor());
            photoAgence=new File("C:\\xampp\\htdocs\\TGT\\web\\uploads\\logo\\"+ag.getLogo());
            photoSponsor= new File("C:\\xampp\\htdocs\\TGT\\web\\uploads\\logo\\"+ag.getSponsor().getLogo());
            centrerImageAgence();
        
            } catch (SQLException ex) {
            Logger.getLogger(AjoutAgenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    

    @FXML
    private void selectionImageAgence(ActionEvent event) {
         FileChooser fc = new FileChooser();
        //File selectedfile = fc.showOpenDialog(null);
        photoAgence =fc.showOpenDialog(null);
        if (photoAgence != null) 
        {
            urlAgence=photoAgence.toURI().toString();
            Image img = new Image(urlAgence);
            ivLogoAgence.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoAgence.getFitWidth() / img.getWidth();
                double ratioY = ivLogoAgence.getFitHeight() / img.getHeight();

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

                ivLogoAgence.setX((ivLogoAgence.getFitWidth() - w) / 2);
                ivLogoAgence.setY((ivLogoAgence.getFitHeight() - h) / 2);
            }
        }
    }

    @FXML
    private void validerAgence(ActionEvent event) throws SQLException, IOException {
         if(tfNomAgence.getText().isEmpty() || tfEmail.getText().isEmpty() || tfAdresse.getText().isEmpty()  || tfTelephone.getText().isEmpty() || 
                 tfFax.getText().isEmpty() || tfSite.getText().isEmpty()  || tfMatriculeFiscale.getText().isEmpty() || cbSponsor.getValue()==null  )
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de la modification", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                Pattern pattern = Pattern.compile("[0-9]{8}");
                Matcher matcherTel = pattern.matcher(tfTelephone.getText());
                Matcher matcherFax = pattern.matcher(tfFax.getText()); 
                if(!matcherTel.find())
                {
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le champ téléphone doit correspondre à un numéro ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else if(!matcherFax.find()){
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le champ Fax doit correspondre à un numéro ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else
                {                     
                    AgenceService ags= new AgenceService();
                    Agence ag = new Agence(idAgenceGlobal,tfNomAgence.getText(), tfMatriculeFiscale.getText(),tfEmail.getText(), tfAdresse.getText(), Integer.parseInt(tfTelephone.getText()), Integer.parseInt(tfFax.getText()), tfSite.getText(), photoAgence.getName(), cbSponsor.getValue(),currentUser);                                     
                    ags.modifierAgence(ag);
                    copyFile(photoAgence);   
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Modification effectuée avec succès ", "Modification", JOptionPane.INFORMATION_MESSAGE);
                    viderChampsAgence();
                    
                }
                
            }      
    }

    @FXML
    private void btnAjouterSponsor(ActionEvent event) {
        activationDetailSponsor(false);
        action="Ajouter";
    }

    @FXML
    private void selectionImageSponsor(ActionEvent event) {
         FileChooser fc = new FileChooser();
        //File selectedfile = fc.showOpenDialog(null);
        photoSponsor =fc.showOpenDialog(null);
        if (photoSponsor != null) 
        {
            urlSponsor=photoSponsor.toURI().toString();
            Image img = new Image(urlSponsor);
            ivLogoSponsor.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoSponsor.getFitWidth() / img.getWidth();
                double ratioY = ivLogoSponsor.getFitHeight() / img.getHeight();

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

                ivLogoSponsor.setX((ivLogoSponsor.getFitWidth() - w) / 2);
                ivLogoSponsor.setY((ivLogoSponsor.getFitHeight() - h) / 2);
            }
        }
    }

    @FXML
    private void validerSponsor(ActionEvent event) throws SQLException, IOException {
        if(action.equals("Ajouter")){
             if(tfNomSponsor.getText().isEmpty() || tfEmailSponsor.getText().isEmpty() || tfAdresseSponsor.getText().isEmpty()  || tfTelephoneSponsor.getText().isEmpty())
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de l'ajout", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                Pattern pattern = Pattern.compile("[0-9]{8}");
                Matcher matcher = pattern.matcher(tfTelephoneSponsor.getText());  
                if(!matcher.find())
                {
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le champ téléphone doit correspondre à un numéro ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    SponsorService ss= new SponsorService();
                    Sponsor sponsor = new Sponsor(tfNomSponsor.getText(), tfAdresseSponsor.getText(),Integer.parseInt(tfTelephoneSponsor.getText()), tfEmailSponsor.getText(), photoSponsor.getName()) ;
                    ss.ajouterSponsor(sponsor);
                    copyFile(photoSponsor);
                    comboBoxFill();
                    cbSponsor.setValue(sponsor);
                    activationDetailSponsor(true);
                    viderDetailSponsor();
                }
                
            }         
        }
        else{// Modifier
             if(tfNomSponsor.getText().isEmpty() || tfEmailSponsor.getText().isEmpty() || tfAdresseSponsor.getText().isEmpty()  || tfTelephoneSponsor.getText().isEmpty())
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de la modification", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                Pattern pattern = Pattern.compile("[0-9]{8}");
                Matcher matcher = pattern.matcher(tfTelephoneSponsor.getText());  
                if(!matcher.find())
                {
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le champ téléphone doit correspondre à un numéro ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    SponsorService ss= new SponsorService();
                    Sponsor sponsor = new Sponsor(idSponsorModif,tfNomSponsor.getText(), tfAdresseSponsor.getText(),Integer.parseInt(tfTelephoneSponsor.getText()), tfEmailSponsor.getText(), photoSponsor.getName()) ;
                    ss.modifierSponsor(sponsor);
                    copyFile(photoSponsor);
                    comboBoxFill();
                    cbSponsor.setValue(sponsor);
                    activationDetailSponsor(true);
                    viderDetailSponsor();
                }
                
            }         
        }
       
    }
    
    private void activationDetailSponsor(boolean bool){
        tfNomSponsor.setDisable(bool);
        tfAdresseSponsor.setDisable(bool);
        tfEmailSponsor.setDisable(bool);
        tfTelephoneSponsor.setDisable(bool);
        btnValiderSponsor.setDisable(bool);
        btnSelectionImageSponsor.setDisable(bool);
        btnAnnuler.setDisable(bool);
    }
    
    private void viderDetailSponsor(){
        tfNomSponsor.setText("");
        tfAdresseSponsor.setText("");
        tfEmailSponsor.setText("");
        tfTelephoneSponsor.setText("");
        ivLogoSponsor.setImage(null);
    }
    
    public static void copyFile(File photo) throws IOException{

        File source = new File(photo.getAbsolutePath());
        String name = photo.getName();
        File  dest = new File("C:\\xampp\\htdocs\\TGT\\web\\uploads\\logo");
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
        ArrayList<Sponsor> lesSponsor= new ArrayList<>();
        SponsorService sponService= new SponsorService();
        lesSponsor=sponService.getAllSponsor();
        obsSponsor = FXCollections.observableArrayList(lesSponsor);
        cbSponsor.setItems(obsSponsor);
    }

    @FXML
    private void btnModfierSponsor(ActionEvent event) {
        if(cbSponsor.getValue() == null)
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Un sponsor doit être selectionner pour la modification !", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                
                activationDetailSponsor(false);
                Sponsor s = cbSponsor.getValue();
                action="Modifier";
                idSponsorModif=s.getId();
                tfNomSponsor.setText(s.getNom());
                tfAdresseSponsor.setText(s.getAdresse());
                tfEmailSponsor.setText(s.getEmail());
                tfTelephoneSponsor.setText(""+s.getTelephone());
                ivLogoSponsor.setImage(new Image("http://localhost/TGT//web//uploads//logo//"+ s.getLogo()));
                
            }         
        
    }

    @FXML
    private void btnAnnuler(ActionEvent event) {
        viderDetailSponsor();
        activationDetailSponsor(true);
    }
    
    public void centrerImageAgence(){
        if (photoAgence != null) 
        {
            urlAgence=photoAgence.toURI().toString();
            Image img = new Image(urlAgence);
            ivLogoAgence.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoAgence.getFitWidth() / img.getWidth();
                double ratioY = ivLogoAgence.getFitHeight() / img.getHeight();

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

                ivLogoAgence.setX((ivLogoAgence.getFitWidth() - w) / 2);
                ivLogoAgence.setY((ivLogoAgence.getFitHeight() - h) / 2);
            }
        }
    }
        
        public void centrerImageSponsor(){
        if (photoSponsor != null) 
        {
            urlSponsor=photoSponsor.toURI().toString();
            Image img = new Image(urlSponsor);
            ivLogoSponsor.setImage(img);

            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogoSponsor.getFitWidth() / img.getWidth();
                double ratioY = ivLogoSponsor.getFitHeight() / img.getHeight();

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

                ivLogoSponsor.setX((ivLogoSponsor.getFitWidth() - w) / 2);
                ivLogoSponsor.setY((ivLogoSponsor.getFitHeight() - h) / 2);
            }
        }
    }
        public void viderChampsAgence(){
            tfNomAgence.setText("");
            tfMatriculeFiscale.setText("");
            tfEmail.setText("");
            tfAdresse.setText("");
            tfTelephone.setText("");
            tfFax.setText("");
            tfSite.setText("");
            photoAgence=null;
            photoSponsor=null;
            action="";
            urlAgence="";
            urlSponsor="";
            ivLogoAgence=null;
                    
        }
    
    
    
}
