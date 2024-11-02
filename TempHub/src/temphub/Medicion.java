
package temphub;

import java.util.Date;

public class Medicion {
    private double temperatura; // Grados Celsius
    private double humedad; // Porcentaje
    private double presionAtmosferica; // Milibares
    private double velocidadViento; // km/h
    private String direccionViento; // Ejemplo: Norte, Sur, Este, Oeste
    private double precipitacion; // mm
    private String idSensor;
    private String fecha;

    public Medicion(double temperatura, double humedad, double presionAtmosferica, double velocidadViento, String direccionViento, double precipitacion, String idSensor, String fecha) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.presionAtmosferica = presionAtmosferica;
        this.velocidadViento = velocidadViento;
        this.direccionViento = direccionViento;
        this.precipitacion = precipitacion;
        this.idSensor = idSensor;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    

    // Getters para obtener los valores
    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getPresionAtmosferica() {
        return presionAtmosferica;
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public String getDireccionViento() {
        return direccionViento;
    }

    public double getPrecipitacion() {
        return precipitacion;
    }

    public String getIdSensor() {
        return idSensor;
    }

    @Override
    public String toString() {
        return "Sensor " + idSensor + " - Temp: " + temperatura + "°C, Humedad: " + humedad + "%, Presión: " + presionAtmosferica + " hPa, Vel. Viento: " + velocidadViento + " km/h, Dir. Viento: " + direccionViento + ", Precipitación: " + precipitacion + " mm" + "fecha: " +fecha;
    }
}

