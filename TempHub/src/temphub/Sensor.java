
package temphub;
import java.util.Scanner;

public class Sensor {
    private String id;
    
    public Sensor(String id) {
        this.id = id;
    }

    public Medicion realizarMedicion() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese temperatura (°C): ");
        double temperatura = scanner.nextDouble();
        
        System.out.print("Ingrese humedad (%): ");
        double humedad = scanner.nextDouble();
        
        System.out.print("Ingrese presión atmosférica (hPa): ");
        double presionAtmosferica = scanner.nextDouble();
        
        System.out.print("Ingrese velocidad del viento (km/h): ");
        double velocidadViento = scanner.nextDouble();
        
        System.out.print("Ingrese dirección del viento (N/S/E/O): ");
        String direccionViento = scanner.next();
        
        System.out.print("Ingrese precipitación (mm): ");
        double precipitacion = scanner.nextDouble();
        
        return new Medicion(temperatura, humedad, presionAtmosferica, velocidadViento, direccionViento, precipitacion, id);
    }
}

