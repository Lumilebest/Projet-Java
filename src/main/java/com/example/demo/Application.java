package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //charge le fichier fxml
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));

        //lit et construit l'affichage a partir du fxml
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);

        //charge le controller et lui passe le stage en paramètre
        Controller c = fxmlLoader.getController();
        c.setStage(stage);

        //donne un titre puis met la scene a l'affichage puis la montre
        stage.setTitle("Application projet!");
        stage.setScene(scene);
        stage.show();
    }
}
