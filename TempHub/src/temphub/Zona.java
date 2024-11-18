
package temphub;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Zona {
    private String nombre;
    private String contrasena;
    private List<Medicion> mediciones;

    public Zona(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.mediciones = new ArrayList<>();
    }

    public boolean validarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }

    public void agregarMedicion(Medicion medicion) {
        mediciones.add(medicion);
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public String getNombre() {
        return nombre;
    }

    // Método para calcular el promedio de la temperatura
    public double calcularPromedioTemperatura() {
        if (mediciones.isEmpty()) {
            return 0.0;
        }
        double sumaTemperaturas = 0.0;
        for (Medicion medicion : mediciones) {
            sumaTemperaturas += medicion.getTemperatura();
        }
        return sumaTemperaturas / mediciones.size();
    }

    // Método para calcular el promedio de la humedad
    public double calcularPromedioHumedad() {
        if (mediciones.isEmpty()) {
            return 0.0;
        }
        double sumaHumedad = 0.0;
        for (Medicion medicion : mediciones) {
            sumaHumedad += medicion.getHumedad();
        }
        return sumaHumedad / mediciones.size();
    }

    // Método para calcular el promedio de la presión atmosférica
    public double calcularPromedioPresion() {
        if (mediciones.isEmpty()) {
            return 0.0;
        }
        double sumaPresion = 0.0;
        for (Medicion medicion : mediciones) {
            sumaPresion += medicion.getPresionAtmosferica();
        }
        return sumaPresion / mediciones.size();
    }

    // Método para calcular el promedio de la velocidad del viento
    public double calcularPromedioVelocidadViento() {
        if (mediciones.isEmpty()) {
            return 0.0;
        }
        double sumaVelocidad = 0.0;
        for (Medicion medicion : mediciones) {
            sumaVelocidad += medicion.getVelocidadViento();
        }
        return sumaVelocidad / mediciones.size();
    }

    // Método para calcular el promedio de la precipitación
    public double calcularPromedioPrecipitacion() {
        if (mediciones.isEmpty()) {
            return 0.0;
        }
        double sumaPrecipitacion = 0.0;
        for (Medicion medicion : mediciones) {
            sumaPrecipitacion += medicion.getPrecipitacion();
        }
        return sumaPrecipitacion / mediciones.size();
    }

    @Override
    public String toString() {
        return "Zona: " + nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

