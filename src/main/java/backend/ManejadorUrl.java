/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;


import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author CETIC 16
 */
public class ManejadorUrl {
     public static void main(String[] args) {
        // Establecer la ubicación del controlador de Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe"); // Reemplaza con la ubicación del driver de Chrome

        // Obtener la carpeta "pdfs" en la carpeta de descargas predeterminadas
        String defaultDownloadPath = getDefaultDownloadPath() + File.separator + "pdfs";

        // Crear la carpeta "pdfs" si no existe
        createDirectory(defaultDownloadPath);

        // Configurar las opciones de Chrome para establecer la ubicación de descarga predeterminada
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", defaultDownloadPath);
        options.setExperimentalOption("prefs", prefs);

        // Inicializar el navegador con las opciones configuradas
        WebDriver driver = new ChromeDriver(options);

        // Obtener las direcciones URL de los códigos 
        String[] qrUrls = {
                "https://siat.impuestos.gob.bo/consulta/QR?nit=1009379021&cuf=451084124B75BB0F75F680B0646333F83CDFA7812B6E903028498FD74&numero=400133&t=2",
                "https://siat.impuestos.gob.bo/consulta/QR?nit=1009379021&cuf=451084124B75BB0F75F680B0646333F8394B3B9F716E903028498FD74&numero=246383&t=2"
                      };

        // Iterar sobre las direcciones URL de los códigos QR
    
        for (int i =0; i<qrUrls.length ; i++ ) {
            // Acceder a la página del código QR
            driver.get(qrUrls[i]);
            
            // Hacer clic en el botón "ver factura" y descargar el archivo
            WebElement verFacturaButton = driver.findElement(By.cssSelector("button[id^='formQr:j_idt']"));
            verFacturaButton.click();
             // Esperar a que se complete la descarga del archivo
          
            if( i == qrUrls.length -1){
              WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(60));
              wait.until((WebDriver d) -> {
               return archivosDescargados(defaultDownloadPath);
            });
            }
          
        }

        // Cerrar el navegador
        driver.quit();
        //Elimnar archivos en caso de error
        if(!archivosDescargados(defaultDownloadPath)){
            eliminarArchivos(defaultDownloadPath);
        }
    }

    // Método para obtener la carpeta de descargas predeterminadas
    private static String getDefaultDownloadPath() {
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
    private static void createDirectory(String path) {
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
    
    private static boolean archivosDescargados(String defaultDownloadPath){
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
    
    private static void eliminarArchivos(String folderPath){
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
