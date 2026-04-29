package com.example.demo;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class RotateR extends Rotate{
    @Override
    //déplace l'image vers la droite
    protected Color transform(PixelReader reader, int x, int y, int width, int height){
        return reader.getColor(x, height-y-1);
    }
}
