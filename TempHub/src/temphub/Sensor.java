
package temphub;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;


public class Sensor {
    private String id;
    
    public Sensor(String id) {
        this.id = id;
    }

    public Medicion realizarMedicion( double temperatura, double humedad, double presionAtmosferica,  double velocidadViento, String direccionViento, double precipitacion, String fecha) {
        System.out.println("llego a realizar medicion");
        Scanner scanner = new Scanner(System.in);
        
        /*
        temperatura = temperatura;
        
        
        double humedad = scanner.nextDouble();
        
        
        double presionAtmosferica = scanner.nextDouble();
        
        
        double velocidadViento = scanner.nextDouble();
        
        
        String direccionViento = scanner.next();
        
        
        double precipitacion = scanner.nextDouble();
        
        
        String fecha = scanner.next();
        
        */
        
        return new Medicion(temperatura, humedad, presionAtmosferica, velocidadViento, direccionViento, precipitacion, id, fecha);
    }
}

