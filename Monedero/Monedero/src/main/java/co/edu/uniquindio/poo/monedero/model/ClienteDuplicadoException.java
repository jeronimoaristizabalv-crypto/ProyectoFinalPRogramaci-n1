package co.edu.uniquindio.poo.monedero.model;

/**
 * Se lanza cuando se intenta registrar un cliente con un documento existente.
 */
public class ClienteDuplicadoException extends IllegalArgumentException {
    public ClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
