package com.example.demo;

import javafx.scene.paint.Color;

public class Sepia extends FiltreS{
    @Override
    //met un filtre jaunatre sur l'image
    protected Color transform(Color color) {
        double r = color.getRed()*0.393+color.getGreen()*0.769+color.getBlue()*0.189;
        double g = color.getRed()*0.349+ color.getGreen()*0.686+ color.getBlue()*0.168;
        double b = color.getRed()*0.272+ color.getGreen()*0.534+ color.getBlue()*0.131;
        return new Color(Math.min(r,1),Math.min(g,1),Math.min(b,1),color.getOpacity());
    }
}
