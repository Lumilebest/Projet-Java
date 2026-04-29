package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class FiltreC implements Transformation {
    //ouvre l'image et parcour les indices
    public Image apply (Image image){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setColor(x, y, this.transform(reader, x, y, width, height));
            }
        }
        return result;
    }

    //utilise l'image en soit car besoins de plusieur pixel
    protected abstract Color transform(PixelReader reader, int x, int y, int width, int height);

}
