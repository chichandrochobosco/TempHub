package temphub;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private List<Zona> zonas;
    private Database database;
    private static final int PUERTO = 12345;

    public Servidor() {
        database = new Database();
        try {
            zonas = database.cargarZonas();
        } catch (Exception e) {
            System.out.println("Error al cargar zonas desde la base de datos: " + e.getMessage());
            zonas = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + clienteSocket.getInetAddress());

                // Crear un hilo para manejar al cliente
                new Thread(new ClienteHandler(clienteSocket, this)).start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    public synchronized List<Zona> getZonas() {
        return zonas;
    }

    public synchronized void agregarZona(Zona zona) {
        zonas.add(zona);
        try {
            database.guardarZona(zona);
        } catch (Exception e) {
            System.out.println("Error al guardar la zona en la base de datos: " + e.getMessage());
        }
    }

    public synchronized Zona buscarZonaPorNombre(String nombreZona) {
        for (Zona zona : zonas) {
            if (zona.getNombre().equalsIgnoreCase(nombreZona)) {
                return zona;
            }
        }
        return null;
    }

    public synchronized void guardarMedicion(Zona zona, Medicion medicion) {
        try {
            zona.agregarMedicion(medicion);
            database.guardarMedicion(zona, medicion);
        } catch (Exception e) {
            System.out.println("Error al guardar la medición: " + e.getMessage());
        }
    }
}

class ClienteHandler implements Runnable {

    private Socket socket;
    private Servidor servidor;

    public ClienteHandler(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String opcion;
            do {
                salida.println("\n=== Sistema de Monitoreo de Zonas ===");
                salida.println("1. Ver zonas y sus mediciones");
                salida.println("2. Crear una nueva zona");
                salida.println("3. Agregar una medición a una zona existente");
                salida.println("4. Salir");
                salida.println("Seleccione una opción: ");

                opcion = entrada.readLine();

                switch (opcion) {
                    case "1":
                        List<Zona> zonas = servidor.getZonas();
                        if (zonas.isEmpty()) {
                            salida.println("No hay zonas registradas.");
                        } else {
                            for (Zona zona : zonas) {
                                salida.println(zona);
                                salida.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                                for (Medicion medicion : zona.getMediciones()) {
                                    salida.println("  - " + medicion);
                                }
                                salida.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            }

                            salida.println("¿Desea ver los promedios de medición de alguna zona? (si/no): ");
                            String respuesta = entrada.readLine();

                            if (respuesta.equalsIgnoreCase("si")) {
                                salida.println("Ingrese el nombre de la zona para ver los promedios: ");
                                String nombreZona = entrada.readLine();
                                Zona zona = servidor.buscarZonaPorNombre(nombreZona);
                                if (zona != null) {
                                    salida.println("Promedio de temperatura: " + zona.calcularPromedioTemperatura() + " °C");
                                    salida.println("Promedio de humedad: " + zona.calcularPromedioHumedad() + " %");
                                    salida.println("Promedio de presión atmosférica: " + zona.calcularPromedioPresion() + " hPa");
                                    salida.println("Promedio de velocidad del viento: " + zona.calcularPromedioVelocidadViento() + " km/h");
                                    salida.println("Promedio de precipitación: " + zona.calcularPromedioPrecipitacion() + " mm");
                                } else {
                                    salida.println("Zona no encontrada.");
                                }
                            }
                        }
                        break;
                    case "2":
                        salida.println("Ingrese el nombre de la nueva zona: ");
                        String nombreZona = entrada.readLine();
                        salida.println("Ingrese la contraseña de la zona: ");
                        String contrasena = entrada.readLine();
                        servidor.agregarZona(new Zona(nombreZona, contrasena));
                        salida.println("Zona creada: " + nombreZona);
                        break;
                    case "3":
                        salida.println("Ingrese el nombre de la zona: ");
                        String nombre = entrada.readLine();
                        Zona zona = servidor.buscarZonaPorNombre(nombre);

                        if (zona == null) {
                            salida.println("Zona no encontrada.");
                            break;
                        }

                        salida.println("Ingrese la contraseña de la zona: ");
                        String pass = entrada.readLine();
                        if (!zona.validarContrasena(pass)) {
                            salida.println("Contraseña incorrecta.");
                            break;
                        }

                        salida.println("Por favor, ingrese los datos de la medición:");

                        salida.println("Ingrese temperatura (°C): ");
                        double temperatura = Double.parseDouble(entrada.readLine());

                        salida.println("Ingrese humedad (%): ");
                        double humedad = Double.parseDouble(entrada.readLine());

                        salida.println("Ingrese presión atmosférica (hPa): ");
                        double presionAtmosferica = Double.parseDouble(entrada.readLine());

                        salida.println("Ingrese velocidad del viento (km/h): ");
                        double velocidadViento = Double.parseDouble(entrada.readLine());

                        salida.println("Ingrese dirección del viento (N/S/E/O): ");
                        String direccionViento = entrada.readLine();

                        salida.println("Ingrese precipitación (mm): ");
                        double precipitacion = Double.parseDouble(entrada.readLine());

                        salida.println("Ingrese fecha (formato year-month-day-hour): ");
                        String fecha = entrada.readLine();

                        // Crear un sensor y realizar la medición
                        Sensor sensor = new Sensor("Sensor" + (int) (Math.random() * 1000));
                        Medicion medicion = sensor.realizarMedicion(temperatura, humedad, presionAtmosferica, velocidadViento, direccionViento, precipitacion, fecha);

                        servidor.guardarMedicion(zona, medicion);
                        salida.println("Medición agregada: " + medicion);
                        break;
                    case "4":
                        salida.println("Desconectando...");
                        break;
                    default:
                        salida.println("Opción inválida.");
                }
            } while (!opcion.equals("4"));
        } catch (IOException e) {
            System.out.println("Error con el cliente: " + e.getMessage());
        }
    }
}

