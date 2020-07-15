/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import static Controladores.AlbumEscogidoController.albumMostrado;
import Controles.FotoGrid;
import Modelo.Album;
import Modelo.Fotografia;
import Modelo.Persona;
import Modelo.TDAs.CircularQueue;
import Modelo.TDAs.DoubleLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class FotosEncontradasController implements Initializable {

    @FXML
    private AnchorPane albumEscogido;
    @FXML
    private Button btnCarrousel;
    @FXML
    private ToggleButton tbtSelectAll;
    @FXML
    private SplitMenuButton moverASptBtn;
    @FXML
    private GridPane gridPaneFotos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Persona user = new Persona();
        user = user.leer("1");

        DoubleLinkedList<Album> albumes = user.mostrarAlbumes();

        Iterator<Album> it = albumes.iterador();

        while (it.hasNext()) {
            Album a = it.next();

            EventHandler<ActionEvent> evento = (ActionEvent e) -> {
                moverASptBtn.setText(a.getIdAlbum() + ": " + a.getTitulo());
            };

            MenuItem mI = new MenuItem(a.getIdAlbum() + ": " + a.getTitulo());
            mI.setOnAction(evento);

            moverASptBtn.getItems().add(mI);

        }

    }

    @FXML
    private void verEnCarrusel(ActionEvent event) {
        try {
            Pane nuevaFoto = (Pane) new FXMLLoader(getClass().getResource("/Vistas/FotoView.fxml")).load();

            Stage secondStage = new Stage();
            secondStage.setTitle("Carrousel View");
            secondStage.setScene(new Scene(nuevaFoto, 714, 500));
            secondStage.setResizable(false);
            secondStage.show();
            // Para evitar que se abra otra ventana igual
            btnCarrousel.disableProperty().bind(secondStage.showingProperty());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void multipleSelection(ActionEvent event) {

        Iterator<Node> it = gridPaneFotos.getChildren().iterator();

        while (it.hasNext()) {
            FotoGrid fg = (FotoGrid) it.next();
            if (tbtSelectAll.isSelected()) {
                fg.check();

            } else {
                fg.disableCheck();

            }

        }

    }

    private boolean isSomeOneSelected() {
        Iterator<Node> it = gridPaneFotos.getChildren().iterator();

        while (it.hasNext()) {
            FotoGrid fg = (FotoGrid) it.next();
            if (fg.isSelected()) {
                return true;
            }

        }

        return false;
    }

    @FXML
    private void eliminarFotos(ActionEvent event) {
        if (isSomeOneSelected()) {
            String message = "¿Está seguro que desea eliminar las fotos escogidas?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
            alert.setTitle("Confirmar eliminacion");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                List<FotoGrid> seleccionadas = selectedPhotos();
                for (FotoGrid fg : seleccionadas) {
                    (fg.getFoto()).eliminar();
                    gridPaneFotos.getChildren().remove(fg);
                    albumMostrado.eliminarFoto(fg.getFoto());
                }

            }
        }
    }

    @FXML
    private void cambiarAlbum(ActionEvent event) {

    }

    private List<FotoGrid> selectedPhotos() {
        List<FotoGrid> seleccionadas = new ArrayList();
        Iterator<Node> it = gridPaneFotos.getChildren().iterator();

        while (it.hasNext()) {
            FotoGrid fg = (FotoGrid) it.next();

            if (fg.isSelected()) {
                seleccionadas.add(fg);

            }

        }

        return seleccionadas;
    }

    public void cargarFotos(CircularQueue<Fotografia> fotos) {

        int i = 0;
        ListIterator<Fotografia> it = fotos.listIterator();
        boolean bandera = true;
        while (it.hasNext() && bandera) {
            Fotografia foto = it.next();

            gridPaneFotos.add(new FotoGrid(foto), i % 2, i / 2);
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
            i++;
        }

    }

}
