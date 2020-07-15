/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Album;
import Modelo.Persona;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class CentralPanelController implements Initializable {

    @FXML
    private AnchorPane centralPanel;

    private MainController mainController;

    private PersonaEscogidaController personaEscogidaController;

    private AlbumEscogidoController albumEscogidoController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public AnchorPane getCentralPanel() {
        return centralPanel;
    }

    public void setCentralPanel(AnchorPane centralPanel) {
        this.centralPanel = centralPanel;
    }

    public void cargarAlbumEscogido(Album album) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Vistas/AlbumEscogido.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        albumEscogidoController = Loader.getController();
        albumEscogidoController.setAlbumEscogido(album);

        centralPanel.getChildren().setAll((Pane) Loader.getRoot());

    }

    public void cargarPersonaEscogida(Persona persona) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Vistas/PersonaEscogida.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        personaEscogidaController = Loader.getController();
        personaEscogidaController.setPersonaEscogida(persona);

        centralPanel.getChildren().setAll((Pane) Loader.getRoot());
    }

}
