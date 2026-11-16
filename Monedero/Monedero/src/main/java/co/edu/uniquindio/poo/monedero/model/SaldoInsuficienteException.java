package co.edu.uniquindio.poo.monedero.model;

/**
 * Se lanza cuando el monedero no tiene saldo suficiente para una operación.
 */
public class SaldoInsuficienteException extends IllegalStateException {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
