package com.example.demo;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Picture {
    private Image image;

    private String path;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Transfo> transformations = new ArrayList<>();
    private String password = "";

    public Picture() throws IOException {
    }

    public enum Transfo {
        ROTATER, ROTATEL, AXEX, AXEY, ECHANGE, SEPIA, BLACKWHITE, PREWITT, CHIFFREMENT
    };

    public void setImageFC(Stage stage) {
        //initialise le FileChooser
        FileChooser fileC = new FileChooser();
        fileC.setTitle("Choisis une image");
        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );

        //donne un repertoire courant au filechooser
        File dossier = new File("src/main/resources/images");

        if (dossier.exists()) {
            fileC.setInitialDirectory(dossier);
        } else {
            fileC.setInitialDirectory(new File("C:/"));
        }

        //prend le lien recolter par le file chooser
        File imageLink = fileC.showOpenDialog(stage);
        if (imageLink != null) {
            this.path = imageLink.toURI().toString();
            this.image = new Image(this.path);
        }
    }

    public void setImagePD(PictureData pd){
        if (pd != null){
            this.path = pd.getPath();
            this.image = new Image(this.path);
            this.tags = pd.getTags();
            this.transformations = new ArrayList<>();

            for ( Transfo t : pd.getTransformations()){
                this.transform(t);
                this.addTransformation(t);
            }
        }
    }


    public void addTag(String tag){
        if (!tag.isEmpty()) {
            this.tags.add(tag);
        }
    }

    public void transform(Transfo t){
        if (this.image != null) {
            Transformation transformation;
            switch (t) {
                case ROTATER:
                    transformation = new RotateR();
                    break;
                case ROTATEL:
                    transformation = new RotateL();
                    break;
                case AXEX:
                    transformation = new AxeX();
                    break;
                case AXEY:
                    transformation = new AxeY();
                    break;
                case ECHANGE:
                    transformation = new Echange();
                    break;
                case BLACKWHITE:
                    transformation = new BlackWhite();
                    break;
                case SEPIA:
                    transformation = new Sepia();
                    break;
                case PREWITT:
                    transformation = new Prewitt();
                    break;
                default:
                    transformation = null;
                    break;
            }
            if (transformation != null) {
                this.image = transformation.apply(this.image);
            }
        }
    }

    public void back(){
        if (!this.transformations.isEmpty()){
            this.transformations.removeLast();
            this.image = new Image(this.path);
            for (Transfo t : this.transformations) {
                this.transform(t);
            }
        }
    }


    public Image getImage(){
        return this.image;
    }
    public void setImage(Image image) { this.image = image; }
    public ArrayList<String> getTags(){return this.tags;}
    public void setTags(ArrayList<String> tags){ this.tags = tags; }
    public String getPath(){return this.path;}
    public void setPath(String path){this.path = path;}
    public ArrayList<Transfo> getTransformations(){return this.transformations;}
    public void setTransformations(ArrayList<Transfo> t){this.transformations = t;}
    public void addTransformation(Transfo t){this.transformations.add(t);}
}
