/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Album;
import Modelo.Persona;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane main;
    @FXML
    private LeftPanelController leftPanelController;
    @FXML
    private CentralPanelController centralPanelController;
    @FXML
    private RightPanelController rightPanelController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        leftPanelController.injectMainController(this);
        centralPanelController.injectMainController(this);
        rightPanelController.injectMainController(this);
    }

    public void mostrarAlbum(Album album) {
        centralPanelController.cargarAlbumEscogido(album);
        rightPanelController.setAlbum(album);
    }

    public void mostrarPersona(Persona persona) {
        centralPanelController.cargarPersonaEscogida(persona);
    }

}
