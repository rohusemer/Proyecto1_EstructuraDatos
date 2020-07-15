/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Album;
import Modelo.Persona;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class LeftPanelController implements Initializable {

    @FXML
    private AnchorPane leftPanel;
    @FXML
    private ListView<Persona> listViewPersonas;
    @FXML
    private ListView<Album> listViewAlbumes;
    @FXML
    private ListView<Album> listViewResultados;

    private MainController mainController;

    public static Persona user;

    public static ObservableList<Persona> obsPersona = FXCollections.observableArrayList();

    public static ObservableList<Album> obsAlbum = FXCollections.observableArrayList();

    public static ObservableList<Album> obsBusqueda = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        user = new Persona();
        user = user.leer("1");
        for (Persona persona : user.mostrarPersonas()) {
            obsPersona.add(persona);
        }

        listViewPersonas.setItems(obsPersona);

        addEventPersonaEscogida();
        addEventAlbumEscogido();
        addEventAlbumEncontrado();
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public ListView<Album> getListViewResultados() {
        return listViewResultados;
    }

    public void setListViewResultados(ListView<Album> listViewResultados) {
        this.listViewResultados = listViewResultados;

    }

    public ListView<Persona> getListViewPersonas() {
        return listViewPersonas;
    }

    public void setListViewPersonas(ListView<Persona> listViewPersonas) {
        this.listViewPersonas = listViewPersonas;
    }

    public ListView<Album> getListViewAlbumes() {
        return listViewAlbumes;
    }

    public void setListViewAlbumes(ListView<Album> listViewAlbumes) {
        this.listViewAlbumes = listViewAlbumes;
    }

    public AnchorPane getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(AnchorPane leftPanel) {
        this.leftPanel = leftPanel;
    }

    private void addEventPersonaEscogida() {
        listViewPersonas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mainController.mostrarPersona(newSelection);

            }
        });
    }

    private void addEventAlbumEscogido() {
        Iterator<Album> it = user.mostrarAlbumes().iterador();
        while (it.hasNext()) {
            Album a = it.next();
            obsAlbum.add(a);
        }
        listViewAlbumes.setItems(obsAlbum);

        listViewAlbumes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                mainController.mostrarAlbum(newSelection);

            }
        });
    }

    private void addEventAlbumEncontrado() {

        listViewResultados.setItems(obsBusqueda);
        listViewResultados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                mainController.mostrarAlbum(newSelection);

            }
        });
    }

}
