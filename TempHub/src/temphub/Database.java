
package temphub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/TempHub";
    private static final String USER = "root"; // Cambia por tu usuario de MySQL
    private static final String PASSWORD = ""; // Cambia por tu contraseña

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void guardarZona(Zona zona) throws SQLException {
        String sql = "INSERT INTO Zonas (nombre, contrasena) VALUES (?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, zona.getNombre());
            stmt.setString(2, zona.getContrasena());
            stmt.executeUpdate();
        }
    }

    public List<Zona> cargarZonas() throws SQLException {
        List<Zona> zonas = new ArrayList<>();
        String sqlZonas = "SELECT * FROM Zonas";
        String sqlMediciones = "SELECT * FROM Mediciones WHERE zona_id = ?";

        try (Connection conn = conectar();
             PreparedStatement stmtZonas = conn.prepareStatement(sqlZonas);
             ResultSet rsZonas = stmtZonas.executeQuery()) {

            while (rsZonas.next()) {
                int id = rsZonas.getInt("id");
                String nombre = rsZonas.getString("nombre");
                String contrasena = rsZonas.getString("contrasena");
                Zona zona = new Zona(nombre, contrasena);

                try (PreparedStatement stmtMediciones = conn.prepareStatement(sqlMediciones)) {
                    stmtMediciones.setInt(1, id);
                    try (ResultSet rsMediciones = stmtMediciones.executeQuery()) {
                        while (rsMediciones.next()) {
                            double temperatura = rsMediciones.getDouble("temperatura");
                            double humedad = rsMediciones.getDouble("humedad");
                            double presion = rsMediciones.getDouble("presion");
                            double velocidad = rsMediciones.getDouble("velocidad");
                            String direccion = rsMediciones.getString("direccion");
                            double precipitacion = rsMediciones.getDouble("precipitacion");
                            String idSensor = rsMediciones.getString("idSensor");
                            String fecha = rsMediciones.getString("fecha");
                            
                            Medicion medicion = new Medicion(temperatura, humedad, presion, velocidad, direccion, precipitacion, idSensor, fecha);
                            zona.agregarMedicion(medicion);
                        }
                    }
                }
                zonas.add(zona);
            }
        }
        return zonas;
    }

    public void guardarMedicion(Zona zona, Medicion medicion) throws SQLException {
        String sql = "INSERT INTO Mediciones (zona_id, temperatura, humedad, presion) VALUES ((SELECT id FROM Zonas WHERE nombre = ?), ?, ?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, zona.getNombre());
            stmt.setDouble(2, medicion.getTemperatura());
            stmt.setDouble(3, medicion.getHumedad());
            stmt.setDouble(4, medicion.getPresionAtmosferica());
            stmt.setDouble(4, medicion.getVelocidadViento());
            stmt.setString(4, medicion.getDireccionViento());
            stmt.setDouble(4, medicion.getPrecipitacion());
            stmt.setString(4, medicion.getIdSensor());
            stmt.setString(4, medicion.getFecha());
            
            stmt.executeUpdate();
        }
    }
}

