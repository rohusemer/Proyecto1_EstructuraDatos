/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Album;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class NuevoAlbumController implements Initializable {

    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtTitulo;
    @FXML
    private DatePicker datePicker;
    
    private Album nuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtDescripcion.setDisable(false);
        txtTitulo.setDisable(false);
    }

    @FXML
    private void guardar(ActionEvent event) {
        nuevo = new Album();
        nuevo.setAlias("1");
        nuevo.setDescripcion(txtDescripcion.getText());
        nuevo.setTitulo(txtTitulo.getText());
        if (datePicker.getValue() != null) {
            nuevo.setFecha(datePicker.getValue());
        } else {
            nuevo.setFecha(LocalDate.now());
        }

        nuevo.crear();
        LeftPanelController.obsAlbum.add(nuevo);

    }

}
