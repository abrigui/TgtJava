/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;
import com.itextpdf.text.BaseColor;
import java.io.File;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.JOptionPane;
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
    @FXML
    private JFXButton btnImprimer;
    

    
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

    @FXML
    private void imprimer(ActionEvent event) {
        try {
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\Administrateur\\Desktop\\agences.pdf"));    
        document.open();
        
        PdfPTable table=new PdfPTable(5);
        table.setWidthPercentage(100);
	table.getDefaultCell().setBorderWidth(1);       
        
        Stream.of("ID", "Nom", "Matricule fiscale", "Adresse", "Site").forEach(columnTitle -> {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(columnTitle));
        table.addCell(header);
        });
        table.setHeaderRows(1);
        
        Image logo = Image.getInstance("C:/Users/Administrateur/Documents/NetBeansProjects/PiJava/TgtJava/src/tn/esprit/tgt/images/logoTGT.png");
        logo.scalePercent(60);
        document.add(logo);
        
        com.itextpdf.text.Paragraph p = new com.itextpdf.text.Paragraph();
        p.add("\n");
        p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        document.add(p); 
        
        Image titre = Image.getInstance("C:/Users/Administrateur/Documents/NetBeansProjects/PiJava/TgtJava/src/tn/esprit/tgt/images/listeAgence.png");
        titre.scalePercent(30);
        titre.setAlignment(5);
        document.add(titre);
  
        com.itextpdf.text.Paragraph p1 = new com.itextpdf.text.Paragraph();
        p1.add("\n");
        p1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
        document.add(p1);  
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tgt", "root", "");
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("SELECT id,nom,matriculeFiscale,adresse,site FROM `agence` where etat=1");
        while(rs.next())
        {
        table.addCell(rs.getString("id"));
        table.addCell(rs.getString("nom"));
        table.addCell(rs.getString("matriculeFiscale"));
        table.addCell(rs.getString("adresse"));
        table.addCell(rs.getString("site"));
        }
        document.add(table);
        JOptionPane.showMessageDialog(null, " données exportées en pdf avec succés ");
        document.close();
                       
        } catch (Exception e) {

            System.out.println("Error PDF");
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
        
    }

    
    

}
