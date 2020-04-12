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
import java.util.Properties;
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
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tn.esprit.tgt.entities.Agence;
import tn.esprit.tgt.services.AgenceService;
import tn.esprit.tgt.services.EvenementService;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        String corps="Chère agence "+agenceATraiter.getNom()+",\nNous avons le plaisir de vous informer que votre demande a été acceptée.\n"
                + "Vous pouvez dès maintenant commencer l'utilisation de notre plateforme. Nous vous souhaitons une expérience agréable."
                 + "\n\nCordialement,\nL'équipe TGT";
        sendEmail(agenceATraiter.getEmail(), corps);

        
    }

    @FXML
    void RefuserAgence(ActionEvent event) throws SQLException {
        AgenceService agService= new AgenceService();
        agService.supprimerAgence(agenceATraiter);
        btnApprouver.setDisable(true);
        btnRefuser.setDisable(true);
        populate();
        String corps="Chère agence "+agenceATraiter.getNom()+",\nEn réponse à votre demande d'inscription, Nous sommes au regret de devoir vous informer que celle-ci n'a pas été retenue.\n"
                + "Nous sommes très sensibles à l'intérêt que vous portez à notre plateforme, mais jugeons que votre domaine d'activité ne correspond pas à ceux visés par notre activité. "
                 + "\n\nCordialement,\nL'équipe TGT";
        sendEmail(agenceATraiter.getEmail(), corps); 
    }
    
    public static void sendEmail(String emailAddress,String sub){
    String to = emailAddress;//change accordingly  
     
    String host = "smtp.gmail.com";//or IP address  
    final String username = "pijavatgt@gmail.com";
    final String password = "Azertyuiop123";
    //Get the session object  
    Properties props = System.getProperties();  
    props.setProperty("mail.smtp.host", host);  
    props.put("mail.smtp.starttls.enable","true");
    /* mail.smtp.ssl.trust is needed in script to avoid error "Could not convert socket to TLS"  */
    props.setProperty("mail.smtp.ssl.trust", host);
    props.put("mail.smtp.auth", "true");      
          
    props.put("mail.smtp.port", "587");
    Session session = Session.getDefaultInstance(props, 
    new javax.mail.Authenticator() 
    {         
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
     
     
      try
      {  
        MimeMessage message = new MimeMessage(session);
   
        message.setFrom(new InternetAddress(username));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject("TGT PLATEFORME D'ÉVÉNEMENT: demande d'inscription");  
        message.setText(sub);  
 
        // Send message  
        Transport.send(message);  
        System.out.println("message sent successfully....");  
  
      }
      catch (MessagingException mex)
      {
          mex.printStackTrace();
      }  
     
}
    
}
