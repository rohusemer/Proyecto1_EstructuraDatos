/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Modelo.Fotografia;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class FotoGrid extends VBox {

    private Fotografia foto;

    private final ImageView icono = new ImageView("Vistas/Imagenes/etiq.png");

    private ImageView image;

    private CheckBox chb;

    private Button boton;

    private BorderPane bPane;

    private TextField txt;

    EventHandler<ActionEvent> cambiarPantalla = (ActionEvent e) -> {
        if (!txt.getText().equals("")) {
            for (String alias : txt.getText().split(", ")) {
                foto.etiquetarPersona(alias);
                txt.clear();
            }
        }

    };

    public FotoGrid(Fotografia f) {
        foto = f;
        icono.setFitHeight(16);
        icono.setFitWidth(17);
        image = f.getImagen();
        image.setFitHeight(170);
        image.setFitWidth(230);
        chb = new CheckBox();
        chb.getStyleClass().add("big-check-box");

        chb.setPrefHeight(20);
        boton = new Button("", icono);
        boton.getStyleClass().add("my-button");
        bPane = new BorderPane();

        bPane.setLeft(boton);
        txt = new TextField();
        bPane.setCenter(txt);
        bPane.setRight(chb);
        getChildren().addAll(image, bPane);
        boton.setOnAction(cambiarPantalla);
    }

    public void check() {
        chb.setSelected(true);
    }

    public void disableCheck() {
        chb.setSelected(false);
    }

    public boolean isSelected() {
        return chb.isSelected();
    }

    public Fotografia getFoto() {
        return foto;
    }

    public void setFoto(Fotografia foto) {
        this.foto = foto;
    }

}
