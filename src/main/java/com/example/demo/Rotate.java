package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class Rotate implements Transformation {
    //créer une image avec des dimensions différente et parcour les indices
    public Image apply (Image image){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(height, width);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                writer.setColor(x, y, this.transform(reader, y, x, width, height));
            }
        }
        return result;
    }

    protected abstract Color transform(PixelReader reader, int x, int y, int width, int height);
}