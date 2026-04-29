package com.example.demo;

import javafx.scene.paint.Color;

public class BlackWhite extends FiltreS{
    @Override
    //tranforme les composents rgb en un gris plus ou moins noire ou blanc
    protected Color transform(Color color) {
        double gray = (color.getBlue()+ color.getGreen()+ color.getRed())/3;
        return new Color(gray,gray,gray,color.getOpacity());
    }
}
