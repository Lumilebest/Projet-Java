package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Picture {
    private Image image;

    private String path;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Transfo> transformations = new ArrayList<>();
    private boolean crypt ;

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

    public void encrypt(String password) throws NoSuchAlgorithmException, IOException {
        this.crypt = true;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedpassword = md.digest(password.getBytes("UTF-8"));

        SecureRandom random = new SecureRandom(hashedpassword);

        BufferedImage image = ImageIO.read(new File(this.path));
        int width = image.getWidth();
        int height = image.getHeight();
        int totalp = width*height;

        int[] pixels = image.getRGB(0,0,width, height, null, 0, width);
        for (int i = 0; i<totalp; i++){
            int temp = pixels[i];
            int nextInt = random.nextInt(totalp);
            pixels[i] = pixels[nextInt];
            pixels[nextInt] = temp;
        }

        image.setRGB(0,0, width, height, pixels, 0, width);

        ImageIO.write(image, "png", new File(this.path));
    }

    public void decrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.crypt = true;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedpassword = md.digest(password.getBytes("UTF-8"));

        SecureRandom random = new SecureRandom(hashedpassword);

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int totalp = width * height;

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        int[] echangeIndex = new int[totalp];
        for (int i = 0; i < totalp; i++) {
            echangeIndex[i] = random.nextInt(totalp);
        }

        Color[][] tab = new Color[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tab[y][x] = reader.getColor(x, y);
            }
        }

        for (int i = totalp - 1; i >= 0; i--) {
            int x = i % width;
            int y = i / width;
            int ind = echangeIndex[i];
            int xi = ind % width;
            int yi = ind / width;

            Color temp = tab[y][x];
            tab[y][x] = tab[yi][xi];
            tab[yi][xi] = temp;
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setColor(x, y, tab[y][x]);
            }
        }

        this.image = result;
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
    public void addTransformation(Transfo transfo){this.transformations.add(transfo);}
    public boolean getCrypt(){ return this.crypt; }
    public void setCrypt(boolean crypt){this.crypt = crypt;}
}
