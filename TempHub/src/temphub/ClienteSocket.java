package temphub;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClienteSocket {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String respuesta;
            while ((respuesta = entrada.readLine()) != null) {
                System.out.println(respuesta);

                if (respuesta.endsWith("Seleccione una opción: ")) {
                    String opcion = scanner.nextLine();
                    
                    salida.println(opcion);
                }
                if (respuesta.contains("Ingrese")) {
                    String mensaje = scanner.nextLine();
                    
                    salida.println(mensaje);
                }
                
                
            }
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}




