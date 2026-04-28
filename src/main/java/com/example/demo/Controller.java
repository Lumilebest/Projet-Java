package com.example.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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
    protected void chooseImageButton() throws IOException {
        travail.setVisible(true);
        picture.setImageFC(this.stage);
        this.picture.setImagePD(this.json.searchPath(this.picture.getPath()));
        if ( picture.getImage() != null ){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void openFromFind() throws IOException {
        travail.setVisible(true);
        String selected = this.paths.getSelectionModel().getSelectedItem();
        this.picture.setImagePD(this.json.searchPath(selected));
        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected  void rotateRightImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.ROTATER);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected  void rotateLeftImageButton(){
        travail.setVisible(true);
        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void axeXImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.AXEX);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void axeYImageButton(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.AXEY);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void echangeFiltreImage(){;
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.ECHANGE);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void sepiaFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.SEPIA);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void BlackWhiteFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.BLACKWHITE);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void PrewittFiltreImage(){
        travail.setVisible(true);
        this.picture.transform(Picture.Transfo.PREWITT);

        if ( picture.getImage() != null){
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void addTagButton(){
        travail.setVisible(true);
        this.picture.addTag(this.tags.getText());
        this.tags.clear();
        travail.setVisible(false);
    }

    @FXML
    protected void searchTagsButton(){
        travail.setVisible(true);
        this.paths.setItems(FXCollections.observableArrayList(this.json.searchTags(new ArrayList<>(Arrays.asList(tags.getText().split(" "))))));
        travail.setVisible(false);
    }

    @FXML
    protected void save() throws IOException {
        travail.setVisible(true);
        this.json.save(this.picture.getPath(), this.picture.getTags(), this.picture.getTransformations(), this.picture.getCrypted());
        travail.setVisible(false);
    }

    @FXML
    protected void retour() {
        travail.setVisible(true);
        this.picture.back();
        this.imageView.setImage(this.picture.getImage());
        travail.setVisible(false);
    }

    @FXML
    protected void encryptButton() throws Exception {
        travail.setVisible(true);
        if (this.picture.getImage() != null && !this.picture.getCrypted() && !this.password.getText().isEmpty()) {
            this.picture.encrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
            this.save();
        }

        travail.setVisible(false);
    }

    @FXML
    protected void decryptButton() throws Exception {
        travail.setVisible(true);
        if (this.picture.getImage() != null && this.picture.getCrypted() && !this.password.getText().isEmpty()) {
            this.picture.decrypt(this.password.getText());
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }

    @FXML
    protected void applyTransfo(){
        travail.setVisible(true);
        if(this.picture.getImage()!=null && !this.picture.getTransformations().isEmpty()){
            this.picture.applyTransfo();
            this.imageView.setImage(this.picture.getImage());
        }
        travail.setVisible(false);
    }
}