package co.edu.uniquindio.poo.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class Cliente {
    private String documento;
    private String nombre;
    private final List<Monedero> monederos = new ArrayList<>();
    private final List<Transaccion> historial = new ArrayList<>();
    private final List<MovimientoPuntos> movimientosPuntos = new ArrayList<>();
    private boolean descuentoTransferencia;
    private LocalDate retirosGratisHasta;
    public Cliente(String documento, String nombre) {
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.documento = documento.trim();
        this.nombre = nombre.trim();
        monederos.add(new Monedero("Principal"));
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public List<Monedero> getMonederos() {
        return Collections.unmodifiableList(monederos);
    }

    public List<Transaccion> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public List<MovimientoPuntos> getMovimientosPuntos() {
        return Collections.unmodifiableList(movimientosPuntos);
    }

    void agregarMonedero(Monedero monedero) {
        if (monedero == null) {
            throw new IllegalArgumentException("El monedero no puede ser nulo.");
        }
        monederos.add(monedero);
    }

    void agregarTransaccion(Transaccion transaccion) {
        if (transaccion == null) {
            throw new IllegalArgumentException("La transacción no puede ser nula.");
        }
        historial.add(transaccion);
    }

    public void extenderRetirosGratis(LocalDate hoy) {
        LocalDate inicio = tieneRetirosGratis(hoy) ? retirosGratisHasta : hoy;
        retirosGratisHasta = inicio.plusMonths(1);
    }

    public int getPuntos() {
        int total = 0;
        for (MovimientoPuntos movimiento : movimientosPuntos) total += movimiento.getCantidad();
        return total;
    }

    void agregarPuntos(int cantidad, String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del movimiento es obligatoria.");
        }
        movimientosPuntos.add(new MovimientoPuntos(cantidad, descripcion));
    }

    public boolean tieneDescuentoTransferencia() {
        return descuentoTransferencia;
    }

    public void setDescuentoTransferencia(boolean descuento) {
        descuentoTransferencia = descuento;
    }

    public boolean tieneRetirosGratis(LocalDate fecha) {
        return retirosGratisHasta != null && !fecha.isAfter(retirosGratisHasta);
    }

    public void activarRetirosGratis(LocalDate hasta) {
        retirosGratisHasta = hasta;
    }

    public double getDescuentoPorRango() {
        if (getRango().equals("Plata")) return 0.05;
        if (getRango().equals("Oro")) return 0.10;
        if (getRango().equals("Platino")) return 0.20;
        return 0;
    }

    public String getRango() {
        if (getPuntos() <= 500) return "Bronce";
        if (getPuntos() <= 1000) return "Plata";
        if (getPuntos() <= 5000) return "Oro";
        return "Platino";
    }

    @Override
    public String toString() { return nombre + " (" + documento + ")"; }
}
