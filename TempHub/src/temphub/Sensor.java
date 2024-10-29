
package temphub;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;


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
        
        System.out.print("Ingrese fecha (formato year-month-day-hour): ");
        String fecha = scanner.next();
        
        
        
        return new Medicion(temperatura, humedad, presionAtmosferica, velocidadViento, direccionViento, precipitacion, id, fecha);
    }
}

