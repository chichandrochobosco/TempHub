
package temphub;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServidorSocket {
    public static void main(String[] args) {
        
        ServidorSocket servidor = new ServidorSocket();
        servidor.recibirMediciones();
    }
    
    agregarZona(new Zona("Zona Sur", "5678"));
}
