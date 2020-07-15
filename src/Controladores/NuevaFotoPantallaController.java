/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Fotografia;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class NuevaFotoPantallaController implements Initializable {

    @FXML
    private TextField lugarTextField;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView fotoEscogidaImgView;
    @FXML
    private TextField etiquetasTxt;

    private String ruta;

    private AlbumEscogidoController albumEscogidoController;

    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Vistas/AlbumEscogido.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        albumEscogidoController = Loader.getController();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void agregar(ActionEvent event) {
        Fotografia nuevaFoto = new Fotografia();
        nuevaFoto.setDescripcion(descripcionTextField.getText());
        nuevaFoto.setFecha(datePicker.getValue());
        nuevaFoto.setLugar(lugarTextField.getText());
        nuevaFoto.setRuta(ruta);

        nuevaFoto.crear();

        if (!etiquetasTxt.getText().equals("")) {
            for (String alias : etiquetasTxt.getText().split(", ")) {
                nuevaFoto.etiquetarPersona(alias);
            }
        }

        albumEscogidoController.addFoto(nuevaFoto);

    }

    @FXML
    private void seleccionarRuta(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File imgFile = fileChooser.showOpenDialog(null);

        if (imgFile != null) {
            try {

                fotoEscogidaImgView.setImage(new Image("file:" + imgFile.getAbsolutePath()));
                ruta = imgFile.toURI().toURL().toString();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
