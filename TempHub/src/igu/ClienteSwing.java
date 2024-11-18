package igu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClienteSwing {
    private JFrame frame;
    private JTextArea mensajesArea;
    private JTextField inputField;
    private JButton sendButton;

    private PrintWriter salida;
    private BufferedReader entrada;

    public ClienteSwing() {
        inicializarInterfaz();
        conectarServidor();
    }

    private void inicializarInterfaz() {
        // Crear la ventana principal
        frame = new JFrame("TempHub");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 720); // Tamaño adecuado para una ventana cómoda
        frame.setLayout(new BorderLayout());

        // Título de la ventana
        frame.setTitle("TempHub");

        // Crear panel para mostrar mensajes
        JPanel mensajesPanel = new JPanel(new BorderLayout());
        mensajesArea = new JTextArea();
        mensajesArea.setEditable(false);
        mensajesArea.setLineWrap(true);
        mensajesArea.setWrapStyleWord(true);
        mensajesArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane mensajesScrollPane = new JScrollPane(mensajesArea);
        mensajesPanel.add(mensajesScrollPane, BorderLayout.CENTER);

        // Crear panel de entrada para enviar mensajes
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        sendButton = new JButton("Enviar");
        
        // Panel para los controles de envío
        JPanel sendPanel = new JPanel(new BorderLayout());
        sendPanel.add(inputField, BorderLayout.CENTER);
        sendPanel.add(sendButton, BorderLayout.EAST);

        // Agregar los componentes a la ventana
        frame.add(mensajesPanel, BorderLayout.CENTER);
        frame.add(sendPanel, BorderLayout.SOUTH);

        // Acción al presionar Enter
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enviarMensaje();
                }
            }
        });

        // Acción al presionar el botón "Enviar"
        sendButton.addActionListener(e -> enviarMensaje());

        // Hacer visible la ventana
        frame.setVisible(true);
    }

    private void conectarServidor() {
        try {
            Socket socket = new Socket("localhost", 12345); // Conectar al servidor en localhost, puerto 12345
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Hilo para escuchar respuestas del servidor
            new Thread(this::escucharServidor).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "No se pudo conectar al servidor: " + e.getMessage());
        }
    }

    private void enviarMensaje() {
        String mensaje = inputField.getText().trim();
        if (!mensaje.isEmpty()) {
            salida.println(mensaje); // Enviar al servidor
            inputField.setText("");  // Limpiar campo
        }
    }

    private void escucharServidor() {
        try {
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                mostrarMensaje(mensaje);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Conexión con el servidor perdida.");
        }
    }

    private void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            mensajesArea.append(mensaje + "\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteSwing());
    }
}





