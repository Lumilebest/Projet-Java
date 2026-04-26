package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Json {
    private ArrayList<PictureData> pictureDatas = new ArrayList<>();


    public Json() throws IOException {
        File file = new File("src/main/resources/saves/data.json");
        if (file.exists() && file.length()>0){
            ObjectMapper mapper = new ObjectMapper();
            this.pictureDatas = new ArrayList<>(Arrays.asList(mapper.readValue(file, PictureData[].class)));
        }else{
            System.out.println("erreur fichier");
        }
    }

    public PictureData searchPath(String path){
        if (this.pictureDatas != null) {
            for (PictureData pd : this.pictureDatas) {
                if (pd.getPath().equals(path)) {
                    return pd;
                }
            }
        }
        return null;
    }

    public ArrayList<String> searchTags(ArrayList<String> tags){
        ArrayList<String> paths = new ArrayList<>();
        for ( PictureData pd : pictureDatas){
            if (pd.getTags().containsAll(tags)){
                paths.add(pd.getPath());
            }
        }
        return paths;
    }

    public void save(String path, ArrayList<String> tags, ArrayList<Picture.Transfo> transformations, boolean crypted) throws IOException {
        PictureData pd = this.searchPath(path);
        if(pd == null){
            pd = new PictureData();
            pd.setPath(path);
            this.pictureDatas.add(pd);
        }

        pd.setTags(tags);
        pd.setTransformations(transformations);
        pd.setCrypted(crypted);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/main/resources/saves/data.json"), this.pictureDatas.toArray());
    }
}