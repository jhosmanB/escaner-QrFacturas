/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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

        // Obtener las direcciones URL de los códigos QR (reemplaza con tus direcciones URL)
        String[] qrUrls = {
                "https://siat.impuestos.gob.bo/consulta/QR?nit=1009379021&cuf=451084124B75BB0F75F680B0646333F83CDFA7812B6E903028498FD74&numero=400133&t=2",
                "https://siat.impuestos.gob.bo/consulta/QR?nit=1009379021&cuf=451084124B75BB0F75F680B0646333F8394B3B9F716E903028498FD74&numero=246383&t=2"
                // Agrega aquí todas las URL de los códigos QR que deseas procesar
        };

        // Iterar sobre las direcciones URL de los códigos QR
        for (String qrUrl : qrUrls) {
            // Acceder a la página del código QR
            driver.get(qrUrl);

            // Hacer clic en el botón "ver factura" y descargar el archivo
            WebElement verFacturaButton = driver.findElement(By.cssSelector("button[id^='formQr:j_idt']"));
            verFacturaButton.click();
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Cerrar el navegador
        driver.quit();
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
}
