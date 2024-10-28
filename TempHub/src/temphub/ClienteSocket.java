
package temphub;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class ClienteSocket {
   /* public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese ID del sensor: ");
        String idSensor = scanner.nextLine();

        Sensor sensor = new Sensor(idSensor);
        Medicion medicion = sensor.realizarMedicion();

        try (Socket socket = new Socket("localhost", 12345); // Cambiar "localhost" por la IP del servidor si es necesario
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            out.writeObject(medicion);
            System.out.println("Medición enviada: " + medicion);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

