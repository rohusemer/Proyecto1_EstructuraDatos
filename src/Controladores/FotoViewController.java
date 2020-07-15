/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Fotografia;
import Modelo.TDAs.CircularQueue;
import java.net.URL;
import java.time.LocalDate;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class FotoViewController implements Initializable {

    @FXML
    private ImageView centralImage;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField txtLugar;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ListView<String> etiquetasListView;

    private ListIterator<Fotografia> it;

    private CircularQueue fotos;

    private Fotografia photo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setFotos(AlbumEscogidoController.albumMostrado.mostrarFotos());

        } catch (Exception e) {

        }
    }

    public ListIterator<Fotografia> getIt() {
        return it;
    }

    public void setIt(ListIterator<Fotografia> it) {
        this.it = it;

        Fotografia f = it.next();
        photo = f;
        datePicker.setPromptText(f.getFecha().toString());
        txtLugar.setText(f.getLugar());
        txtDescripcion.setText(f.getDescripcion());
        centralImage.setImage((f.getImagen()).getImage());
        etiquetasListView.getItems().setAll(f.getEtiquetas().toSet());
        centralImage.setImage((f.getImagen()).getImage());
    }

    public CircularQueue getFotos() {
        return fotos;
    }

    public void setFotos(CircularQueue fotos) {
        this.fotos = fotos;
        setIt(fotos.listIterator());

    }

    @FXML
    private void previous(ActionEvent event) {
        if (fotos.size() != 1) {

            Fotografia f = it.previous();
            photo = f;

            datePicker.setPromptText(f.getFecha().toString());
            txtLugar.setText(f.getLugar());
            txtDescripcion.setText(f.getDescripcion());
            centralImage.setImage((f.getImagen()).getImage());
            etiquetasListView.getItems().setAll(f.getEtiquetas().toSet());

        }
    }

    @FXML
    private void next(ActionEvent event) {
        if (fotos.size() != 1) {
            Fotografia f = it.next();
            photo = f;

            datePicker.setPromptText(f.getFecha().toString());
            txtLugar.setText(f.getLugar());
            txtDescripcion.setText(f.getDescripcion());
            centralImage.setImage((f.getImagen()).getImage());
            etiquetasListView.getItems().setAll(f.getEtiquetas().toSet());

        }
    }

    @FXML
    private void actualizarFoto(ActionEvent event) {
        Fotografia actual = new Fotografia();
        actual.setIdAlbum(AlbumEscogidoController.albumMostrado.getIdAlbum());
        actual.setIdFotografia(photo.getIdFotografia());
        actual.setLugar(txtLugar.getText());
        actual.setRuta(photo.getRuta());
        actual.setDescripcion(txtDescripcion.getText());
        actual.setFecha(LocalDate.parse(datePicker.getPromptText()));
        actual.actualizar();

    }

}
