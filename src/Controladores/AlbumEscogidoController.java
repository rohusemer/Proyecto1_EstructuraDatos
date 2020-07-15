/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controles.FotoGrid;
import Extra.Extra;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luis A. Sarango-Parrales
 */
public class AlbumEscogidoController implements Initializable {

    @FXML
    private AnchorPane albumEscogido;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelFecha;
    @FXML
    private Button btnCarrousel;
    @FXML
    private Button btnAdd;
    @FXML
    private GridPane gridPaneFotos;
    @FXML
    private ToggleButton tbtSelectAll;
    @FXML
    private SplitMenuButton moverASptBtn;

    private String idAlbumCambio;

    public static Album albumMostrado;

    private MainController mainController;

    private CentralPanelController centralPanelController;

    {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Vistas/CentralPanel.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        centralPanelController = Loader.getController();
    }

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
                idAlbumCambio = a.getIdAlbum().toString();

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
    private void agregarFoto(ActionEvent event) {
        try {
            Pane nuevaFoto = (Pane) new FXMLLoader(getClass().getResource("/Vistas/NuevaFotoPantalla.fxml")).load();
            Stage secondStage = new Stage();
            secondStage.setTitle("Nueva Foto");
            secondStage.setScene(new Scene(nuevaFoto, 465, 596));
            secondStage.setResizable(false);
            secondStage.show();
            // Para evitar que se abra otra ventana igual
            btnAdd.disableProperty().bind(secondStage.showingProperty());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void cargarAlbumes(CircularQueue<Fotografia> fotos) {

        ListIterator<Fotografia> lit = fotos.listIterator();
        boolean bandera = true;
        int i = 0;
        while (lit.hasNext() && bandera) {
            Fotografia foto = lit.next();

            gridPaneFotos.add(new FotoGrid(foto), i % 2, i / 2);
            if (foto.equals((fotos.getRear()).getContent())) {
                bandera = false;
            }
            i++;
        }

    }

    public void setAlbumEscogido(Album albumEscogido) {
        albumMostrado = albumEscogido;
        labelNombre.setText(albumEscogido.getTitulo());
        labelFecha.setText(albumEscogido.getFecha().toString());
        cargarAlbumes(albumEscogido.mostrarFotos());
    }

    public void addFoto(Fotografia nuevaFoto) {
        albumMostrado.añadirFoto(nuevaFoto);

        centralPanelController.cargarAlbumEscogido(albumMostrado);

        /*Thread thread = new Thread(new Runnable() {
        
        @Override
        public void run() {
        Runnable updater = new Runnable() {
        
        @Override
        public void run() {
        gridPaneFotos.getChildren().add(new FotoGrid(nuevaFoto.getImagen()));
        centralPanelController.cargarAlbumEscogido(albumMostrado);
        }
        };
        
        while (true) {
        try {
        Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        
        // UI update is run on the Application thread
        Platform.runLater(updater);
        }
        }
        
        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();*/
    }

    @FXML
    private void realizarBusqueda(ActionEvent event) {
        try {
            Pane nuevaFoto = (Pane) new FXMLLoader(getClass().getResource("/Vistas/Busqueda.fxml")).load();
            Stage secondStage = new Stage();
            secondStage.setTitle("Nueva Busqueda");
            secondStage.setScene(new Scene(nuevaFoto, 373, 430));
            secondStage.setResizable(false);
            secondStage.show();
            // Para evitar que se abra otra ventana igual
            btnAdd.disableProperty().bind(secondStage.showingProperty());
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
    private void cambiarAlbum(ActionEvent event) {
        String message = "¿Está seguro que desea cambiar de album a las fotos escogidas?";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
        alert.setTitle("Confirmar cambio de album");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {

            int index = Extra.getIndexFromObs(LeftPanelController.obsAlbum, idAlbumCambio);
            Album nuevo = LeftPanelController.obsAlbum.get(index);

            for (FotoGrid f : selectedPhotos()) {
                gridPaneFotos.getChildren().remove(f);
                nuevo.añadirFoto(f.getFoto());  // creando un albumfotografia
                (f.getFoto()).cambiarAlbum(albumMostrado, nuevo); //  quita el albumfotografia y agregar un albumfotografia
                LeftPanelController.obsAlbum.set(index, nuevo); // agrega al listView

            }
        }
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
        } else {
            String message = "¿Está seguro que desea eliminar el album?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.NO, ButtonType.OK);
            alert.setTitle("Confirmar eliminacion");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                albumMostrado.eliminar();
                LeftPanelController.obsAlbum.remove(albumMostrado);
            }
        }

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

    @FXML
    private void crearNuevoAlbum(MouseEvent event) {
        try {
            Pane nuevaFoto = (Pane) new FXMLLoader(getClass().getResource("/Vistas/NuevoAlbum.fxml")).load();
            Stage secondStage = new Stage();
            secondStage.setTitle("Nuevo Album");
            secondStage.setScene(new Scene(nuevaFoto, 351, 400));
            secondStage.setResizable(false);
            secondStage.showAndWait();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
