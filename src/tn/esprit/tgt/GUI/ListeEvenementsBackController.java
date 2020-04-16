/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tgt.GUI;

import com.itextpdf.text.BaseColor;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
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
   // @FXML
   // private TableColumn<Evenement, Integer> tcId;
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
    @FXML
    private JFXButton btnImprimer;
    
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
        //tcId.setCellValueFactory(new PropertyValueFactory<Evenement,Integer>("id"));
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

    @FXML
    private void imprimer(ActionEvent event) {
       try {
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\Administrateur\\Desktop\\evenements.pdf"));    
        document.open();
        
        PdfPTable table=new PdfPTable(5);
        table.setWidthPercentage(100);
	table.getDefaultCell().setBorderWidth(1);       
        
        Stream.of("Nom", "Date debut", "Lieu", "Date fin", "Nombre de place").forEach(columnTitle -> {
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
        
        Image titre = Image.getInstance("C:/Users/Administrateur/Documents/NetBeansProjects/PiJava/TgtJava/src/tn/esprit/tgt/images/listeEvenement.png");
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
        ResultSet rs=st.executeQuery("SELECT nom,dateDebut,lieu,dateFin,nbParticipantMax FROM `evenement`");
        while(rs.next())
        {
        
        table.addCell(rs.getString("nom"));
        table.addCell(rs.getString("dateDebut"));
        table.addCell(rs.getString("lieu"));
        table.addCell(rs.getString("dateFin"));
        table.addCell(rs.getString("nbParticipantMax"));
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
