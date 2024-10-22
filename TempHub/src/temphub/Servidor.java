
package temphub;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Servidor {
    private List<Zona> zonas = new ArrayList<>();
    private List<Medicion> mediciones = new ArrayList<>();

    public void agregarZona(Zona zona) {
        zonas.add(zona);
    }

    public Zona autenticarZona(String nombreZona, String contrasena) {
        for (Zona zona : zonas) {
            if (zona.getNombre().equals(nombreZona) && zona.validarContrasena(contrasena)) {
                System.out.println("Acceso concedido a la zona: " + nombreZona);
                return zona;
            }
        }
        System.out.println("Acceso denegado a la zona: " + nombreZona);
        return null;
    }

    public void recibirMedicion(Medicion medicion) {
        mediciones.add(medicion);
        System.out.println("Medición recibida: " + medicion);
    }

    public void mostrarMediciones() {
        System.out.println("Mediciones registradas:");
        for (Medicion medicion : mediciones) {
            System.out.println(medicion);
        }
    }

    public void mostrarPromedios() {
        double sumaTemp = 0, sumaHumedad = 0, sumaPresion = 0, sumaViento = 0, sumaPrecipitacion = 0;
        int cantidad = mediciones.size();

        for (Medicion medicion : mediciones) {
            sumaTemp += medicion.getTemperatura();
            sumaHumedad += medicion.getHumedad();
            sumaPresion += medicion.getPresionAtmosferica();
            sumaViento += medicion.getVelocidadViento();
            sumaPrecipitacion += medicion.getPrecipitacion();
        }

        System.out.println("Promedio de temperatura: " + (sumaTemp / cantidad) + "°C");
        System.out.println("Promedio de humedad: " + (sumaHumedad / cantidad) + "%");
        System.out.println("Promedio de presión atmosférica: " + (sumaPresion / cantidad) + " hPa");
        System.out.println("Promedio de velocidad del viento: " + (sumaViento / cantidad) + " km/h");
        System.out.println("Promedio de precipitación: " + (sumaPrecipitacion / cantidad) + " mm");
    }
}
