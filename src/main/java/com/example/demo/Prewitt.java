package com.example.demo;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Prewitt extends FiltreC{
    @Override
    //met en évidence les bordures d'une image
    protected Color transform(PixelReader reader, int x, int y, int width, int height) {
        double gx = 0;
        double gy = 0;

        //les matrices permetant les calculs
        int[][] matriceX= {{-1,0,1},{-1,0,1},{-1,0,1}};
        int[][] matriceY= {{-1,-1,-1},{0,0,0},{1,1,1}};

        //calcule pour chaque pixel autour du pixel cible et
        for (int i = -1; i <=1; i++) {
            for (int j = -1; j <= 1; j++) {
                int securex = x + i;
                int securey = y + j;
                if (i + x < 0 || i + x > width - 1) {
                    securex = x;
                }
                if (j + y < 0 || j + y > height - 1) {
                    securey = y;
                }
                Color color = reader.getColor(securex, securey);
                double gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                gx += matriceX[i + 1][j + 1] * gray;
                gy += matriceY[i + 1][j + 1] * gray;
            }
        }
        //met dans les bornes du rgb et renvoie la couleur
        double gray = Math.min(Math.sqrt(gx*gx+gy*gy),1);
        return new Color(gray,gray,gray,1);
    }
}
