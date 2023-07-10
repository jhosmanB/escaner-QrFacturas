/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.io.File;

/**
 *
 * @author CETIC 16
 */
public class ManejadorDirectorios {
        // Método para obtener la carpeta de descargas predeterminadas
    public  String getDefaultDownloadPath() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return System.getProperty("user.home") + File.separator + "Downloads";
        } else if (os.contains("mac")) {
            return System.getProperty("user.home") + File.separator + "Downloads";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            return System.getProperty("user.home") + File.separator + "Downloads";
        }

        return "";
    }

    // Método para crear una carpeta si no existe
    public void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Se ha creado la carpeta: " + path);
            } else {
                System.out.println("No se pudo crear la carpeta: " + path);
            }
        }
    }
    
    public boolean archivosDescargados(String defaultDownloadPath){
         File[] files = new File(defaultDownloadPath).listFiles();
                boolean res = true;
                if (files != null) {
                    for (File file : files) {
                        if (!file.getName().endsWith(".pdf")) {
                            res = false;
                            break;
                        }
                    }
                    return res;
                }
                return false;
    }
    
   public void eliminarArchivos(String folderPath){
        File folder = new File(folderPath);

        // Verificar si la carpeta existe
        if (folder.exists() && folder.isDirectory()) {
            // Obtener la lista de archivos en la carpeta
            File[] files = folder.listFiles();

            // Verificar si hay archivos en la carpeta
            if (files != null) {
                // Iterar sobre los archivos y eliminarlos
                for (File file : files) {
                    if (file.isFile()) {
                        // Eliminar el archivo
                        boolean deleted = file.delete();
                        if (deleted) {
                            System.out.println("Se eliminó el archivo: " + file.getName());
                        } else {
                            System.out.println("No se pudo eliminar el archivo: " + file.getName());
                        }
                    }
                }
            } else {
                System.out.println("La carpeta está vacía");
            }
        } else {
            System.out.println("La carpeta no existe");
        }
    }
}
