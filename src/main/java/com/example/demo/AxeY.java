package com.example.demo;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class AxeY extends FiltreC {
    //déplace les pixels sur l'axe Y
    protected Color transform(PixelReader reader, int x, int y, int width, int height){
        return reader.getColor(x,height-y-1);
    }
}