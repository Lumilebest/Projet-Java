package com.example.demo;
import javafx.scene.paint.Color;

public class Echange extends FiltreS{
    //décale les composantes rgb
    protected Color transform(Color color){
        return new Color(color.getGreen(),color.getBlue(),color.getRed(),color.getOpacity());
    }
}
