/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Luis A. Sarango-Parrales
 */
public class Picasa extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Main.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/Vistas/css/style.css");

        stage.setTitle("Picasa");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

}
