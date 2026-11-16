package co.edu.uniquindio.poo.monedero.model;

/**
 * Se lanza cuando el cliente no dispone de los puntos requeridos.
 */
public class PuntosInsuficientesException extends IllegalArgumentException {
    public PuntosInsuficientesException(String mensaje) {
        super(mensaje);
    }
}
