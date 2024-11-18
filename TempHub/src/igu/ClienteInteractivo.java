package igu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteInteractivo {
    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    private JFrame frame;
    private JComboBox<String> comboZonas;
    private JTable tablaMediciones;
    private DefaultTableModel modeloTabla;
    private JTextField campoZona, campoContrasena, campoTemperatura, campoHumedad, campoPresion;
    private JButton botonAgregarZona, botonAgregarMedicion;

    public ClienteInteractivo() {
        configurarInterfaz();
        conectarAlServidor();
    }

    private void configurarInterfaz() {
        frame = new JFrame("Cliente TempHub - Interactivo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel superior: Selección de zonas
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboZonas = new JComboBox<>();
        comboZonas.addActionListener(e -> mostrarMediciones());
        panelSuperior.add(new JLabel("Zonas:"));
        panelSuperior.add(comboZonas);

        frame.add(panelSuperior, BorderLayout.NORTH);

        // Panel central: Tabla para mediciones
        String[] columnas = {"Fecha", "Temperatura (°C)", "Humedad (%)", "Presión (hPa)"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaMediciones = new JTable(modeloTabla);
        frame.add(new JScrollPane(tablaMediciones), BorderLayout.CENTER);

        // Panel inferior: Formularios para agregar zona y medición
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));

        // Formulario para agregar zona
        JPanel formularioZona = new JPanel(new FlowLayout());
        formularioZona.add(new JLabel("Nueva Zona:"));
        campoZona = new JTextField(10);
        formularioZona.add(campoZona);
        formularioZona.add(new JLabel("Contraseña:"));
        campoContrasena = new JTextField(10);
        formularioZona.add(campoContrasena);
        botonAgregarZona = new JButton("Agregar Zona");
        botonAgregarZona.addActionListener(this::agregarZona);
        formularioZona.add(botonAgregarZona);

        panelInferior.add(formularioZona);

        // Formulario para agregar medición
        JPanel formularioMedicion = new JPanel(new FlowLayout());
        formularioMedicion.add(new JLabel("Temperatura:"));
        campoTemperatura = new JTextField(5);
        formularioMedicion.add(campoTemperatura);
        formularioMedicion.add(new JLabel("Humedad:"));
        campoHumedad = new JTextField(5);
        formularioMedicion.add(campoHumedad);
        formularioMedicion.add(new JLabel("Presión:"));
        campoPresion = new JTextField(5);
        formularioMedicion.add(campoPresion);
        botonAgregarMedicion = new JButton("Agregar Medición");
        botonAgregarMedicion.addActionListener(this::agregarMedicion);
        formularioMedicion.add(botonAgregarMedicion);

        panelInferior.add(formularioMedicion);

        frame.add(panelInferior, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void conectarAlServidor() {
        try {
            socket = new Socket(HOST, PUERTO);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // Iniciar un hilo para escuchar mensajes del servidor
            new Thread(this::escucharServidor).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error al conectar con el servidor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void escucharServidor() {
        try {
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                procesarMensajeServidor(mensaje);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Conexión cerrada con el servidor.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void procesarMensajeServidor(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            if (mensaje.startsWith("Zona:")) {
                // Actualizar zonas en el combo
                String zona = mensaje.substring(5);
                comboZonas.addItem(zona);
            } else if (mensaje.startsWith("Medicion:")) {
                // Actualizar tabla con nuevas mediciones
                String[] datos = mensaje.substring(9).split(",");
                modeloTabla.addRow(datos);
            }
        });
    }

    private void agregarZona(ActionEvent event) {
        String zona = campoZona.getText().trim();
        String contrasena = campoContrasena.getText().trim();
        if (!zona.isEmpty() && !contrasena.isEmpty()) {
            salida.println("2");
            salida.println(zona);
            salida.println(contrasena);
            campoZona.setText("");
            campoContrasena.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Por favor, completa los campos de la nueva zona.");
        }
    }

    private void agregarMedicion(ActionEvent event) {
        String zona = (String) comboZonas.getSelectedItem();
        if (zona == null) {
            JOptionPane.showMessageDialog(frame, "Selecciona una zona antes de agregar una medición.");
            return;
        }
        try {
            double temperatura = Double.parseDouble(campoTemperatura.getText());
            double humedad = Double.parseDouble(campoHumedad.getText());
            double presion = Double.parseDouble(campoPresion.getText());

            salida.println("3");
            salida.println(zona);
            salida.println(temperatura + "," + humedad + "," + presion);

            campoTemperatura.setText("");
            campoHumedad.setText("");
            campoPresion.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Ingresa valores válidos para las mediciones.");
        }
    }

    private void mostrarMediciones() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        salida.println("1"); // Pedir mediciones al servidor
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClienteInteractivo::new);
    }
}

