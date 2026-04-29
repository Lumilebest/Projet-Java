package com.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    private Picture picture = new Picture();
    private Stage stage;
    private Json json = new Json();
    @FXML
    private TextField tags;

    @FXML
    private ListView<String> paths;

    @FXML
    private TextField password;

    @FXML
    private ImageView imageView;


    @FXML
    private ProgressIndicator travail;

    public Controller() throws IOException {
    }

    protected void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    //cherche un fichier avec filechooser, cherche aussi si il existe dans le fichier json
    protected void chooseImageButton() throws IOException {
        travail.setVisible(true);
        picture.setImageFC(this.stage);
        this.picture.setImagePD(this.json.searchPath(this.picture.getPath()));
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //Ouvre une image trouvé via un tag
    protected void openFromFind() throws IOException {
        travail.setVisible(true);
        String selected = this.paths.getSelectionModel().getSelectedItem();
        this.picture.setImagePD(this.json.searchPath(selected));

        this.imageView.setImage(this.picture.getImage());
        this.paths.setVisible(false);
        this.paths.setManaged(false);
        travail.setVisible(false);
    }

    @FXML
    //applique une rotation droite
    protected  void rotateRightImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.ROTATER);

        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //applique une rotation gauche
    protected  void rotateLeftImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.ROTATEL);

        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //applique une image d'axe X
    protected void axeXImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.AXEX);

        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //applique une image d'axe Y
    protected void axeYImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.AXEY);

        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //permute les élements rgb
    protected void echangeFiltreImage(){;
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.ECHANGE);

        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //applique un effet sepia
    protected void sepiaFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.SEPIA);
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //applique un filtre qui met l'image en noir et blanc
    protected void BlackWhiteFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.BLACKWHITE);
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //met en  évidence les bordure de l'image
    protected void PrewittFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.PREWITT);
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //ajoute un tag
    protected void addTagButton(){
        travail.setVisible(true);
        this.picture.addTag(this.tags.getText());
        travail.setVisible(false);
    }

    @FXML
    //cherche dans le json les images avec le tag choisi
    protected void searchTagsButton(){
        travail.setVisible(true);
        String[] temp = tags.getText().split(" ");
        if (temp.length > 0) {
            this.paths.setItems(FXCollections.observableArrayList(this.json.searchTags(new ArrayList<>(Arrays.asList(temp)))));
            this.paths.setVisible(true);
            this.paths.setManaged(true);
        }
        travail.setVisible(false);
    }

    @FXML
    //sauvegarde les modification et ajout
    protected void save() throws IOException {
        travail.setVisible(true);
        this.json.save(this.picture.getPath(), this.picture.getTags(), this.picture.getTransformations(), this.picture.getCrypted());
        travail.setVisible(false);
    }

    @FXML
    //fais un retour en arrière
    protected void retour() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        travail.setVisible(true);
        this.picture.back(this.password.getText());
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    //encrypte l'image source
    protected void encryptButton() throws Exception {
        if ( !this.password.getText().isEmpty() ) {
            travail.setVisible(true);
            this.picture.encrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
            this.save();
            travail.setVisible(false);
        }
    }

    @FXML
    //décrypte l'image en mémoire
    protected void decryptButton() throws Exception {
        if ( !this.password.getText().isEmpty() ) {
            travail.setVisible(true);
            this.picture.decrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
            travail.setVisible(false);
        }
    }

    @FXML
    //applique les tranformation a l'image en mémoire ( a n'utilisé que quand on charge l'image )
    protected void applyTransfo(){
        travail.setVisible(true);
        this.picture.applyTransfo();
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);

    }
}