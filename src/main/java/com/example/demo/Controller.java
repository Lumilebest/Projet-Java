package com.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    private Picture picture = new Picture();
    private Stage stage;
    private Json json = new Json();
    @FXML
    private FlowPane tags;
    @FXML
    private TextField searchTags;
    @FXML
    private TextField newTag;
    @FXML
    private ListView<String> paths;

    @FXML
    private TextField password;

    @FXML
    private ImageView imageView;

    public Controller() throws IOException {
    }

    protected void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    protected void chooseImageButton() throws IOException {
        if (this.picture.getImage() != null){
            this.json.save(this.picture.getPath(), this.picture.getTags(), this.picture.getTransformations(), this.picture.getCrypted());
        }

        picture.setImageFC(this.stage);

        if ( picture.getImage() != null ){
            this.imageView.setImage(this.picture.getImage());
        }

    }
    @FXML
    protected  void rotateRightImageButton(){
        this.picture.addTransformation(Picture.Transfo.ROTATER);
        this.picture.transform(Picture.Transfo.ROTATER);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected  void rotateLeftImageButton(){
        this.picture.transform(Picture.Transfo.ROTATER);
        this.picture.addTransformation(Picture.Transfo.ROTATER);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void axeXImageButton(){
        this.picture.addTransformation(Picture.Transfo.AXEX);
        this.picture.transform(Picture.Transfo.AXEX);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void axeYImageButton(){
        this.picture.addTransformation(Picture.Transfo.AXEY);
        this.picture.transform(Picture.Transfo.AXEY);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void echangeFiltreImage(){
        this.picture.addTransformation(Picture.Transfo.ECHANGE);
        this.picture.transform(Picture.Transfo.ECHANGE);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void sepiaFiltreImage(){
        this.picture.transform(Picture.Transfo.SEPIA);
        this.picture.addTransformation(Picture.Transfo.SEPIA);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void BlackWhiteFiltreImage(){
        this.picture.transform(Picture.Transfo.BLACKWHITE);
        this.picture.addTransformation(Picture.Transfo.BLACKWHITE);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void PrewittFiltreImage(){
        this.picture.addTransformation(Picture.Transfo.PREWITT);
        this.picture.transform(Picture.Transfo.PREWITT);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void addTagButton(){
        this.picture.addTag(this.newTag.getText());
        this.newTag.clear();
    }

    @FXML
    protected void searchTagsButton(){
        this.paths.setItems(FXCollections.observableArrayList(this.json.searchTags(new ArrayList<>(Arrays.asList(searchTags.getText().split(" "))))));
    }

    @FXML
    protected void openFromFind() throws IOException {
        if (this.picture.getImage() != null){
            this.json.save(this.picture.getPath(), this.picture.getTags(), this.picture.getTransformations(), this.picture.getCrypted());
        }
        String selected = this.paths.getSelectionModel().getSelectedItem();
        this.picture.setImagePD(this.json.searchPath(selected));
        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void save() throws IOException {
        this.json.save(this.picture.getPath(), this.picture.getTags(), this.picture.getTransformations(), this.picture.getCrypted());
    }

    @FXML
    protected void retour() {
        this.picture.back();
        this.imageView.setImage(this.picture.getImage());
    }

    @FXML
    protected void encryptButton() throws Exception {
        if (this.picture.getImage() != null && !this.picture.getCrypted()) {
            this.picture.encrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void decryptButton() throws Exception {
        if (this.picture.getImage() != null) {
            this.picture.decrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
        }
    }

    @FXML
    protected void applyTransfo(){
        if(this.picture.getImage()!=null && !this.picture.getTransformations().isEmpty()){
            this.picture.applyTransfo();
            this.imageView.setImage(this.picture.getImage());
        }
    }
}