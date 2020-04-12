/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import tn.esprit.tgt.services.SponsorService;
import tn.esprit.tgt.entities.Sponsor;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AjoutSponsorController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfAdresse;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelephone;
    @FXML
    private ImageView ivLogo;
    @FXML
    private JFXButton btnSelectionImage;
    @FXML
    private JFXButton btnValider;
    
    private String url=""; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void selectionImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedfile = fc.showOpenDialog(null);
        if (selectedfile != null) 
        {
            url=selectedfile.toURI().toString();
            Image img = new Image(url);
            ivLogo.setImage(img);
        
            if (img != null) 
            {
                double w = 0;
                double h = 0;

                double ratioX = ivLogo.getFitWidth() / img.getWidth();
                double ratioY = ivLogo.getFitHeight() / img.getHeight();

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

                ivLogo.setX((ivLogo.getFitWidth() - w) / 2);
                ivLogo.setY((ivLogo.getFitHeight() - h) / 2);
            }
        }
    }

    @FXML
    private void valider(ActionEvent event) throws SQLException {
        if(tfNom.getText().isEmpty() || tfNom.getText().isEmpty() || tfNom.getText().isEmpty()  || tfNom.getText().isEmpty())
            {             
                JOptionPane jop2 = new JOptionPane();
                jop2.showMessageDialog(null, "Aucun champ ne doit être vide lors de l'ajout", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            { 
                Pattern pattern = Pattern.compile("[0-9]{8}");
                Matcher matcher = pattern.matcher(tfTelephone.getText());  
                if(!matcher.find())
                {
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Le champ téléphone deoit correspondre à un numéro ! ", "Attention", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    SponsorService ss= new SponsorService();
                    Sponsor sponsor = new Sponsor(tfNom.getText(), tfAdresse.getText(),Integer.parseInt(tfTelephone.getText()), tfEmail.getText(), url) ;
                    ss.ajouterSponsor(sponsor);
                }
                
            }              
    }
    
}
