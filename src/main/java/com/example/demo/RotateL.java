package com.example.demo;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class RotateL extends Rotate{
    @Override
    //déplace l'image vers la gauche
    protected Color transform(PixelReader reader, int x, int y, int width, int height) {
        return reader.getColor(width-x-1, y);
    }
}

