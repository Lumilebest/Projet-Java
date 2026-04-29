package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Json {
    private ArrayList<PictureData> pictureDatas = new ArrayList<>();

    //ouvre le fichier json s'il existe et charges les éléments
    public Json() throws IOException {
        File file = new File("src/main/resources/saves/data.json");
        if (file.exists() && file.length()>0){
            ObjectMapper mapper = new ObjectMapper();
            this.pictureDatas = new ArrayList<>(Arrays.asList(mapper.readValue(file, PictureData[].class)));
        }
    }

    //cherche l'objet avec le meme chemin
    public PictureData searchPath(String path){
        if (this.pictureDatas != null) {
            for (PictureData pictureData : this.pictureDatas) {
                if (pictureData.getPath().equals(path)) {
                    return pictureData;
                }
            }
        }
        return null;
    }

    //prend les tags envoyaient et cherche une image avec ses tags
    public ArrayList<String> searchTags(ArrayList<String> tags){
        ArrayList<String> paths = new ArrayList<>();
        for ( PictureData pictureData : pictureDatas){
            if (pictureData.getTags().containsAll(tags)){
                paths.add(pictureData.getPath());
            }
        }
        return paths;
    }

    //prend les infos et les enregistres en mémoire et sur le disque
    public void save(String path, ArrayList<String> tags, ArrayList<Picture.Transfo> transformations, boolean crypted) throws IOException {
        PictureData pictureData = this.searchPath(path);
        if(pictureData == null){
            pictureData = new PictureData();
            pictureData.setPath(path);
            this.pictureDatas.add(pictureData);
        }

        pictureData.setTags(tags);
        pictureData.setTransformations(transformations);
        pictureData.setCrypted(crypted);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/main/resources/saves/data.json"), this.pictureDatas.toArray());
    }
}