package com.example.demo;

import java.util.ArrayList;

public class PictureData {
    private String path;
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<Picture.Transfo> transformations = new ArrayList<>();
    private boolean crypted;

    //laisse le constructeur vide pour que jackson puisse y acceder
    public PictureData(){};

    public ArrayList<String> getTags(){return this.tags;}
    public void setTags(ArrayList<String> tags){ this.tags = tags; }
    public String getPath(){return this.path;}
    public void setPath(String path){this.path = path;}
    public ArrayList<Picture.Transfo> getTransformations(){return this.transformations;}
    public void setTransformations(ArrayList<Picture.Transfo> t){this.transformations = t;}
    public boolean getCrypted(){return this.crypted;}
    public void setCrypted(boolean crypted){this.crypted = crypted;}
}
