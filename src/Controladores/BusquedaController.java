/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Album;
import Modelo.Fotografia;
import Modelo.TDAs.CircularQueue;
import Modelo.TDAs.DoubleLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class BusquedaController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private ToggleButton btnLugar;
    @FXML
    private RadioButton albumRadioButton;
    @FXML
    private RadioButton fotoRadioButton;
    @FXML
    private ToggleButton btnFecha;
    @FXML
    private ToggleButton btnPersona;
    @FXML
    private TextField personaTextField;
    @FXML
    private TextField lugarTextField;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private DatePicker fechaInicio;

    final ToggleGroup group = new ToggleGroup();

    private Album album;

    private Fotografia foto;

    private DoubleLinkedList<Album> albumes;

    private CircularQueue<Fotografia> fotos;

    private FotosEncontradasController fotosEncontradasController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        albumRadioButton.setToggleGroup(group);
        fotoRadioButton.setToggleGroup(group);
        errorLabel.setVisible(false);
        fechaInicio.setDisable(true);
        fechaFin.setDisable(true);
        lugarTextField.setDisable(true);
        personaTextField.setDisable(true);
        setEventFecha();
        setEventLugar();
        setEventPersona();

    }

    private void setEventFecha() {
        EventHandler<ActionEvent> cambiarDatePicker = (ActionEvent e) -> {
            if (btnFecha.isSelected()) {
                fechaInicio.setDisable(false);
                fechaFin.setDisable(false);

            } else {
                fechaInicio.getEditor().clear();
                fechaFin.getEditor().clear();

                fechaInicio.setDisable(true);
                fechaFin.setDisable(true);
            }

        };
        btnFecha.setOnAction(cambiarDatePicker);

    }

    private void setEventLugar() {
        EventHandler<ActionEvent> cambiarTextField = (ActionEvent e) -> {
            if (btnLugar.isSelected()) {
                lugarTextField.setDisable(false);

            } else {
                lugarTextField.setText("");
                lugarTextField.setDisable(true);

            }

        };
        btnLugar.setOnAction(cambiarTextField);

    }

    private void setEventPersona() {
        EventHandler<ActionEvent> cambiarTextField = (ActionEvent e) -> {
            if (btnPersona.isSelected()) {
                personaTextField.setDisable(false);

            } else {
                personaTextField.setText("");
                personaTextField.setDisable(true);

            }
        };
        btnPersona.setOnAction(cambiarTextField);

    }

    private Set<String> getPersonasByText() {
        Set<String> personas = new HashSet();
        for (String p : personaTextField.getText().split(", ")) {
            personas.add(p);
        }
        return personas;
    }

    @FXML
    private void realizarBusqueda(ActionEvent event) {
        boolean isBusquedaFecha = btnFecha.isSelected();
        boolean isBusquedaPersona = btnPersona.isSelected();
        boolean isBusquedaLugar = btnLugar.isSelected();
        boolean isBusquedaFoto = fotoRadioButton.isSelected();
        boolean isBusquedaAlbum = albumRadioButton.isSelected();

        if (isBusquedaFoto) {
            foto = new Fotografia();

            if (isBusquedaPersona && isBusquedaLugar) {
                fotos = foto.buscarXPersonaLugar(LeftPanelController.user.mostrarTodaLasFotos(), getPersonasByText(), lugarTextField.getText());
            } else if (isBusquedaPersona && isBusquedaFecha) {
                fotos = foto.buscarXPersonaFecha(LeftPanelController.user.mostrarTodaLasFotos(), getPersonasByText(), fechaInicio.getValue(), fechaFin.getValue());
            } else if (isBusquedaLugar && isBusquedaFecha) {
                fotos = foto.buscarXLugarFecha(LeftPanelController.user.mostrarTodaLasFotos(), lugarTextField.getText(), fechaInicio.getValue(), fechaFin.getValue());
            } else if (isBusquedaFecha) {
                fotos = foto.buscarXFecha(LeftPanelController.user.mostrarTodaLasFotos(), fechaInicio.getValue(), fechaFin.getValue());
            } else if (isBusquedaPersona) {
                fotos = foto.buscarXPersonas(LeftPanelController.user.mostrarTodaLasFotos(), getPersonasByText());
            } else if (isBusquedaLugar) {
                fotos = foto.buscarXLugar(LeftPanelController.user.mostrarTodaLasFotos(), lugarTextField.getText());
            }

            Album a = new Album();
            a.setFotos(fotos);
            AlbumEscogidoController.albumMostrado = a;
            openFotosCentral();
        } else if (isBusquedaAlbum) {
            album = new Album();

            if (isBusquedaPersona && isBusquedaLugar) {
                albumes = album.buscarXPersonaLugar(LeftPanelController.user.mostrarAlbumes(), getPersonasByText(), lugarTextField.getText());
            } else if (isBusquedaPersona && isBusquedaFecha) {
                albumes = album.buscarXPersonaFecha(LeftPanelController.user.mostrarAlbumes(), getPersonasByText(), fechaInicio.getValue(), fechaFin.getValue());

            } else if (isBusquedaLugar && isBusquedaFecha) {
                albumes = album.buscarXLugarFecha(LeftPanelController.user.mostrarAlbumes(), lugarTextField.getText(), fechaInicio.getValue(), fechaFin.getValue());

            } else if (isBusquedaFecha) {
                albumes = album.buscarXFecha(LeftPanelController.user.mostrarAlbumes(), fechaInicio.getValue(), fechaFin.getValue());
            } else if (isBusquedaPersona) {
                albumes = album.buscarXPersonas(LeftPanelController.user.mostrarAlbumes(), getPersonasByText());
            } else if (isBusquedaLugar) {
                albumes = album.buscarXLugar(LeftPanelController.user.mostrarAlbumes(), lugarTextField.getText());
            }

            LeftPanelController.obsBusqueda.setAll(albumes.toSet());

        }
    }

    public DoubleLinkedList<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(DoubleLinkedList<Album> albumes) {
        this.albumes = albumes;
    }

    public CircularQueue<Fotografia> getFotos() {
        return fotos;
    }

    public void setFotos(CircularQueue<Fotografia> fotos) {
        this.fotos = fotos;
    }

    private void openFotosCentral() {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Vistas/FotosEncontradas.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fotosEncontradasController = Loader.getController();
        fotosEncontradasController.cargarFotos(fotos);

        Stage newStage = new Stage();
        AnchorPane root = Loader.getRoot();
        Scene scene = new Scene(root, 487, 614);
        newStage.setScene(scene);

        newStage.showAndWait();
    }

}
