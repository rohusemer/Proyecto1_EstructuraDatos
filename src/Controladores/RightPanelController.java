/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Extra.Extra;
import Modelo.Album;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class RightPanelController implements Initializable {

    @FXML
    private AnchorPane rightPanel;
    @FXML
    private TextField txtfTitulo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label id;

    private Album album;

    private MainController mainController;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
        txtfTitulo.setText(album.getTitulo());
        txtDescripcion.setText(album.getDescripcion());
        datePicker.setPromptText(album.getFecha().toString());
        id.setText(album.getIdAlbum().toString());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setVisible(false);
    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void actualizarAlbum(ActionEvent event) {
        Album actual = new Album();
        actual.setAlias("1");
        actual.setIdAlbum(Long.parseLong(id.getText()));
        actual.setTitulo(txtfTitulo.getText());
        actual.setDescripcion(txtDescripcion.getText());
        actual.setFecha(LocalDate.parse(datePicker.getPromptText()));
        actual.actualizar();
        int index = Extra.getIndexFromObs(LeftPanelController.obsAlbum, id.getText());
        Album viejo = LeftPanelController.obsAlbum.get(index);
        actual.setFotos(viejo.mostrarFotos());
        LeftPanelController.obsAlbum.set(index, actual);

    }

}
