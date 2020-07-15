/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Persona;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class PersonaEscogidaController implements Initializable {

    @FXML
    private AnchorPane personaEscogida;
    @FXML
    private TextField aliasTextField;
    @FXML
    private TextField nombresTextField;
    @FXML
    private TextField apellidosTextField;

    private MainController mainController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void actualizar(ActionEvent event) {
        String message = "¿Está seguro que desea actualizar la persona?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
        alert.setTitle("Confirmar actualizacion");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Persona nueva = new Persona();

            nueva.setAlias(aliasTextField.getText());
            nueva.setNombres(nombresTextField.getText());
            nueva.setApellidos(apellidosTextField.getText());
            nueva.actualizar();
        }

    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPersonaEscogida(Persona p) {
        aliasTextField.setText(p.getAlias());
        nombresTextField.setText(p.getNombres());
        apellidosTextField.setText(p.getApellidos());
    }

    @FXML
    private void crearPersona(ActionEvent event) {

        String message = "¿Está seguro que desea crear una nueva persona?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
        alert.setTitle("Confirmar registro");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Persona nueva = new Persona();
            nueva.setAlias(aliasTextField.getText());
            nueva.setNombres(nombresTextField.getText());
            nueva.setApellidos(apellidosTextField.getText());
            nueva.crear();

            LeftPanelController.obsPersona.add(nueva);

        }
    }

    @FXML
    private void eliminarPersona(ActionEvent event) {
        String message = "¿Está seguro que desea eliminar una nueva persona?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
        alert.setTitle("Confirmar eliminacion");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            Persona nueva = new Persona();
            nueva.setAlias(aliasTextField.getText());
            nueva.setNombres(nombresTextField.getText());
            nueva.setApellidos(apellidosTextField.getText());
            nueva.eliminar();

            LeftPanelController.obsPersona.remove(nueva);

            aliasTextField.clear();
            nombresTextField.clear();
            apellidosTextField.clear();

        }
    }

}
