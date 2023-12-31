/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author CETIC 16
 */

import backend.ManejadorUrl;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import frontend.PanelGui;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mavenproject1 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Descargar Pdfs de facturas"); // Cambia el título a tu preferencia
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        PanelGui panel = new PanelGui();
//      panel.onIniciar(direccionIP, contrasena, rutaArchivo);
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        // Ruta de la carpeta que contiene las imágenes
       
    }

    // Método para verificar si un archivo es una imagen
    private static boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".png") || fileName.endsWith(".gif");
    }
    public static void descargarFacturas(String folderPath){
        ManejadorUrl manejadorUrl = new ManejadorUrl();
        ArrayList<String> urls = new ArrayList<>();
        // Crear objeto File para la carpeta
        File folder = new File(folderPath);

        // Obtener la lista de archivos en la carpeta
        File[] files = folder.listFiles();

        // Verificar si la carpeta existe y contiene archivos
        if (folder.exists() && folder.isDirectory() && files != null) {
            // Iterar sobre cada archivo en la carpeta
            for (File file : files) {
                // Verificar si es un archivo de imagen
                if (isImageFile(file)) {
                    // Obtener el nombre del archivo
                    String ImagenRuta = file.getAbsolutePath();
                    String url = Leerqr(ImagenRuta).replace(" ", "");
                    if(!url.equalsIgnoreCase("")){
                    System.out.println(url);
                    urls.add(url);}                   
                }
            }
        } else {
            System.out.println("La carpeta no existe o no contiene archivos.");
        }
        if(!urls.isEmpty()){
            manejadorUrl.DescargarPdfs(urls);
        }
    
    }
    private static String Leerqr (String ImageRuta){     
    try {
        BufferedImage imagen = ImageIO.read(new File(ImageRuta));
        int[] pixels = imagen.getRGB(0, 0, imagen.getWidth(), imagen.getHeight(), null, 0, imagen.getWidth());

        RGBLuminanceSource fuente = new RGBLuminanceSource(imagen.getWidth(), imagen.getHeight(), pixels);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(fuente));

        Reader lector = new MultiFormatReader();
        Result resultado = lector.decode(bitmap);

        return  resultado.getText();
    } catch (Exception e) {
        System.out.println("error en escaner imagen");
        return " ";
    }
    }
}
