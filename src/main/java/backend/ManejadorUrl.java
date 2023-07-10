/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;


import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
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
     public  void DescargarPdfs(ArrayList<String> qrUrls) {
         ManejadorDirectorios directorios = new ManejadorDirectorios();
        // Establecer la ubicación del controlador de Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe"); // Reemplaza con la ubicación del driver de Chrome

        // Obtener la carpeta "pdfs" en la carpeta de descargas predeterminadas
        String defaultDownloadPath = directorios.getDefaultDownloadPath() + File.separator + "pdfs";

        // Crear la carpeta "pdfs" si no existe
         directorios.createDirectory(defaultDownloadPath);

        // Configurar las opciones de Chrome para establecer la ubicación de descarga predeterminada
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", defaultDownloadPath);
        options.setExperimentalOption("prefs", prefs);

        // Inicializar el navegador con las opciones configuradas
        WebDriver driver = new ChromeDriver(options);
    
        for (int i =0; i<qrUrls.size() ; i++ ) {
            // Acceder a la página del código QR
            driver.get(qrUrls.get(i));
            
            // Hacer clic en el botón "ver factura" y descargar el archivo
            WebElement verFacturaButton = driver.findElement(By.cssSelector("button[id^='formQr:j_idt']"));
            verFacturaButton.click();
             // Esperar a que se complete la descarga del archivo
          
            if( i == qrUrls.size() -1){
              WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(60));
              wait.until((WebDriver d) -> {
               return  directorios.archivosDescargados(defaultDownloadPath);
            });
            }
          
        }

        // Cerrar el navegador
        driver.quit();
        //Elimnar archivos en caso de error
        if(! directorios.archivosDescargados(defaultDownloadPath)){
             directorios.eliminarArchivos(defaultDownloadPath);
        }
    }

  
}
