
package temphub;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String nombre;
    private String contrasena;
    private List<Zona> zonas = new ArrayList<>();

    public Zona(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean validarContrasena(String contrasenaIngresada) {
        return this.contrasena.equals(contrasenaIngresada);
    }

    @Override
    public String toString() {
        return "Zona: " + nombre;
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
}




