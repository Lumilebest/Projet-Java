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
import java.net.URI;
import java.net.URISyntaxException;
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
    private boolean crypted ;

    public Picture() throws IOException {
    }

    public enum Transfo {
        ROTATER, ROTATEL, AXEX, AXEY, ECHANGE, SEPIA, BLACKWHITE, PREWITT, CHIFFREMENT
    };

    public void setImageFC(Stage stage) {
        //créer et le file chooser
        FileChooser fileC = new FileChooser();
        fileC.setTitle("Choisis une image");
        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp")
        );

        //va a un repertoire courent
        File dossier = new File("src/main/resources/images");

        //Si le dossier existe le filechooser l'utilise sinon il va ailleurs
        if (dossier.exists()) {
            fileC.setInitialDirectory(dossier);
        } else {
            fileC.setInitialDirectory(new File("C:/"));
        }

        //récupaire l'adresse sélectionné
        File imageLink = fileC.showOpenDialog(stage);

        //si l'image existe charge l'objet
        if (imageLink != null) {
            this.path = imageLink.toURI().toString();
            this.image = new Image(this.path);
            this.crypted = false;
            this.transformations = new ArrayList<>();
            this.tags = new ArrayList<>();
        }
    }

    //recois les informations du json et charge l'image a partir de ca
    public void setImagePD(PictureData pictureData){
        if (pictureData != null){
            this.path = pictureData.getPath();
            this.image = new Image(this.path);
            this.tags = pictureData.getTags();
            this.transformations = pictureData.getTransformations();
            this.crypted = pictureData.getCrypted();
        }
    }

    //ajotue un tag
    public void addTag(String tag){
        String [] tags = tag.split(" ");
        if (this.image != null && tags.length>=1) {
            for(String t : tags ){
                if ( !this.tags.contains(t) ) {
                    this.tags.add(t);
                }
            }
        }
    }

    public void encrypt(String password) throws NoSuchAlgorithmException, IOException, URISyntaxException {
        if (this.image != null ){
            this.crypted = true;

            //créer le générateur et lui donne une seed
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedpassword = md.digest(password.getBytes("UTF-8"));

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(hashedpassword);

            //charge l'image d'origine et prend des informations utile
            BufferedImage image = ImageIO.read(new File(new URI(this.path)));
            this.image = null;
            int width = image.getWidth();
            int height = image.getHeight();
            int totalp = width*height;

            //déplace chaque pixel
            int[] pixels = image.getRGB(0,0,width, height, null, 0, width);
            for (int i = 0; i<totalp; i++){
                int temp = pixels[i];
                int nextInt = random.nextInt(totalp);
                pixels[i] = pixels[nextInt];
                pixels[nextInt] = temp;
            }

            //traduit sous format rgb
            image.setRGB(0,0, width, height, pixels, 0, width);

            //enregistre et recharge l'image ( sans les modifications )
            ImageIO.write(image, "png", new File(new URI(this.path)));
            this.image = new Image(this.path);
        }
    }

    public void decrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if ( this.image != null ){

            //créer le générateur et lui donne une seed
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedpassword = md.digest(password.getBytes("UTF-8"));

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(hashedpassword);

            //prend la taille de l'image
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();
            int totalp = width * height;

            PixelReader reader = image.getPixelReader();
            WritableImage result = new WritableImage(width, height);
            PixelWriter writer = result.getPixelWriter();

            //créer un tableau des nombre aléatoire pour les refaire dans l'autre sens
            int[] echangeIndex = new int[totalp];
            for (int i = 0; i < totalp; i++) {
                echangeIndex[i] = random.nextInt(totalp);
            }

            //fais un tableau d'image ( pour faire des permutation et donc ne pas détruire des pixels
            Color[][] tab = new Color[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tab[y][x] = reader.getColor(x, y);
                }
            }

            //applique sur le tableau de pixel les permutations
            for (int i = totalp - 1; i >= 0; i--) {
                int x = i % width;
                int y = i / width;

                int ind = echangeIndex[i];
                int x2 = ind % width;
                int y2 = ind / width;

                Color temp = tab[y][x];
                tab[y][x] = tab[y2][x2];
                tab[y2][x2] = temp;
            }

            //copie le tableau de pixel dans l'image
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    writer.setColor(x, y, tab[y][x]);
                }
            }

            this.image = result;
        }
    }

    //applique des transformations
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
                this.transformations.add(t);
            }
        }
    }

    //applique toutes les transformations latente
    public void applyTransfo(){
        if ( this.image != null ){
            ArrayList<Transfo> temp = new ArrayList<>(this.transformations);
            this.transformations = new ArrayList<>();

            for( Transfo t : temp){
                this.transform(t);
            }
        }
    }

    //fais un retour en arrière ( supprime une modification et les rejoue tout )
    public void back(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (this.image != null && !this.transformations.isEmpty()){
            this.transformations.removeLast();
            this.image = new Image(this.path);
            if (!this.crypted){
                this.applyTransfo();
            }else{
                if (!password.isEmpty()){
                    this.decrypt(password);
                    this.applyTransfo();
                }
            }
        }
    }

    public Image getImage(){ return this.image; }
    public ArrayList<String> getTags(){return this.tags;}
    public String getPath(){return this.path;}
    public ArrayList<Transfo> getTransformations(){return this.transformations;}
    public boolean getCrypted(){ return this.crypted; }
}
