
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

    @Override
    public String toString() {
        return "Zona: " + nombre;
    }
    
    public Zona autenticarZona(String nombreZona, String contrasena) {
        Iterable<Zona> zonas = null;
    for (Zona zona : zonas) {
        if (zona.getNombre().equals(nombreZona) && zona.validarContrasena(contrasena)) {
            System.out.println("Acceso concedido a la zona: " + nombreZona);
            return zona;
        }
    }
    System.out.println("Acceso denegado a la zona: " + nombreZona);
    return null;
}
}




