
package temphub;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor {
    private List<Zona> zonas;

    public Servidor() {
        zonas = new ArrayList<>();
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Sistema de Monitoreo de Zonas ===");
            System.out.println("1. Ver zonas y sus mediciones");
            System.out.println("2. Crear una nueva zona");
            System.out.println("3. Agregar una medición a una zona existente");
            System.out.println("4. Salir del sistema");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea

            switch (opcion) {
                case 1:
                    mostrarZonas();
                    break;
                case 2:
                    crearZona(scanner);
                    break;
                case 3:
                    agregarMedicion(scanner);
                    break;
                case 4:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        }

        scanner.close();
    }

    private void mostrarZonas() {
        if (zonas.isEmpty()) {
            System.out.println("No hay zonas registradas.");
        } else {
            System.out.println("\nZonas y mediciones:");
            for (Zona zona : zonas) {
                System.out.println("\n" + zona);
                for (Medicion medicion : zona.getMediciones()) {
                    System.out.println("  - " + medicion);
                }
            }
        }
    }

    private void crearZona(Scanner scanner) {
        System.out.print("Ingrese el nombre de la nueva zona: ");
        String nombreZona = scanner.nextLine();
        System.out.print("Ingrese la contraseña de la zona: ");
        String contrasena = scanner.nextLine();
        Zona nuevaZona = new Zona(nombreZona, contrasena);
        zonas.add(nuevaZona);
        System.out.println("Zona creada: " + nombreZona);
    }

    private void agregarMedicion(Scanner scanner) {
        System.out.print("Ingrese el nombre de la zona: ");
        String nombreZona = scanner.nextLine();

        Zona zona = buscarZonaPorNombre(nombreZona);
        if (zona == null) {
            System.out.println("Zona no encontrada.");
            return;
        }

        System.out.print("Ingrese la contraseña de la zona: ");
        String contrasena = scanner.nextLine();
        if (!zona.validarContrasena(contrasena)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }

        Sensor sensor = new Sensor("Sensor" + (int) (Math.random() * 1000));
        Medicion nuevaMedicion = sensor.realizarMedicion();
        zona.agregarMedicion(nuevaMedicion);
        System.out.println("Medición agregada: " + nuevaMedicion);
    }

    private Zona buscarZonaPorNombre(String nombreZona) {
        for (Zona zona : zonas) {
            if (zona.getNombre().equalsIgnoreCase(nombreZona)) {
                return zona;
            }
        }
        return null;
    }
}
