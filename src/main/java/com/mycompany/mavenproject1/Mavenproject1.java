/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author CETIC 16
 */

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Mavenproject1 {

    public static void main(String[] args) {
       try {
        BufferedImage imagen = ImageIO.read(new File("facturas/tycg_qr0003.png"));
        int[] pixels = imagen.getRGB(0, 0, imagen.getWidth(), imagen.getHeight(), null, 0, imagen.getWidth());

        RGBLuminanceSource fuente = new RGBLuminanceSource(imagen.getWidth(), imagen.getHeight(), pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(fuente));

        Reader lector = new MultiFormatReader();
        Result resultado = lector.decode(bitmap);

        System.out.println( resultado.getText());
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
