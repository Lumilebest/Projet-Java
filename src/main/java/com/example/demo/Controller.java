package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    private Image img;
    @FXML
    private ImageView imgv;
    private Stage stage;

    protected void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    protected void choosePhotoButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisis une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );
        File dossier = new File("src/main/resources/images");
        if ( dossier.exists()){
            fileChooser.setInitialDirectory(dossier);
        }

        File choix = fileChooser.showOpenDialog(this.stage);


        this.img = new Image(choix.toURI().toString());
        this.imgv.setImage(img);
    }

    protected  void rotatePhoto(){
        PixelReader pr = this.img.getPixelReader();
        int l = (int)this.img.getWidth();
        int h = (int)this.img.getHeight();

    }

}
