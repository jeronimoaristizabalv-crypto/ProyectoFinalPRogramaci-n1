
package co.edu.uniquindio.poo.monedero.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class MonederoVirtualTest {

    MonederoVirtual monederoVirtual;

    private static final Logger LOG =
            Logger.getLogger(MonederoVirtualTest.class.getName());

    @BeforeEach
    public void setMonederoVirtual() {
        monederoVirtual = new MonederoVirtual("Monedero");
    }

    // Test 1: Registrar cliente
    @Test
    public void registrarClienteTest() {

        LOG.info("Inicio de prueba registrar cliente...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        assertEquals("123", cliente.getDocumento());
        assertEquals("Juan", cliente.getNombre());
        assertEquals(1, monederoVirtual.getListaClientes().size());

        LOG.info("Fin de prueba: el cliente fue registrado correctamente...");
    }

    // Test 2: Evitar registrar clientes duplicados
    @Test
    public void registrarClienteDuplicadoTest() {

        LOG.info("Inicio de prueba registrar cliente duplicado...");

        monederoVirtual.registrarCliente("123", "Juan");

        ClienteDuplicadoException excepcion = assertThrows(
                ClienteDuplicadoException.class,
                () -> {
                    monederoVirtual.registrarCliente("123", "Pedro");
                }
        );

        System.out.println(excepcion);

        assertEquals(1, monederoVirtual.getListaClientes().size());

        LOG.info("Fin de prueba: no se permitió registrar un documento duplicado...");
    }

    // Test 3: Registrar un monedero adicional
    @Test
    public void registrarMonederoTest() {

        LOG.info("Inicio de prueba registrar monedero...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = monederoVirtual.registrarMonedero(
                cliente, "Ahorros"
        );

        assertEquals("Ahorros", monedero.getNombre());
        assertEquals(2, cliente.getMonederos().size());

        LOG.info("Fin de prueba: el monedero adicional fue registrado correctamente...");
    }

    // Test 4: Realizar un depósito
    @Test
    public void depositarTest() {

        LOG.info("Inicio de prueba depositar...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = cliente.getMonederos().get(0);

        Transaccion transaccion = monederoVirtual.depositar(
                cliente, monedero, 500
        );

        assertEquals(500.0, monedero.getSaldo(), 0.001);
        assertEquals(5, cliente.getPuntos());
        assertEquals("Depósito", transaccion.getTipo());

        LOG.info("Fin de prueba: el depósito se realizó correctamente...");
    }

    // Test 5: Realizar un retiro
    @Test
    public void retirarTest() {

        LOG.info("Inicio de prueba retirar...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = cliente.getMonederos().get(0);

        monederoVirtual.depositar(cliente, monedero, 500);

        Transaccion transaccion = monederoVirtual.retirar(
                cliente, monedero, 100
        );

        assertEquals(399.0, monedero.getSaldo(), 0.001);
        assertEquals(7, cliente.getPuntos());
        assertEquals("Retiro", transaccion.getTipo());
        assertEquals(1.0, transaccion.getComision(), 0.001);

        LOG.info("Fin de prueba: el retiro se realizó correctamente con su comisión...");
    }

    // Test 6: Realizar una transferencia
    @Test
    public void transferirTest() {

        LOG.info("Inicio de prueba transferir...");

        Cliente cliente1 = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Cliente cliente2 = monederoVirtual.registrarCliente(
                "456", "Pedro"
        );

        Monedero origen = cliente1.getMonederos().get(0);
        Monedero destino = cliente2.getMonederos().get(0);

        monederoVirtual.depositar(cliente1, origen, 1000);

        Transaccion transferencia = monederoVirtual.transferir(
                cliente1, origen, cliente2, destino, 200
        );

        assertEquals(798.0, origen.getSaldo(), 0.001);
        assertEquals(200.0, destino.getSaldo(), 0.001);
        assertEquals("Transferencia", transferencia.getTipo());
        assertEquals(2.0, transferencia.getComision(), 0.001);

        LOG.info("Fin de prueba: la transferencia se realizó correctamente...");
    }

    // Test 7: Canjear puntos
    @Test
    public void canjearPuntosTest() {

        LOG.info("Inicio de prueba canjear puntos...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = cliente.getMonederos().get(0);

        monederoVirtual.depositar(cliente, monedero, 100000);

        monederoVirtual.canjear(cliente, monedero, 1000);

        assertEquals(0, cliente.getPuntos());
        assertEquals(100050.0, monedero.getSaldo(), 0.001);

        LOG.info("Fin de prueba: los puntos fueron canjeados correctamente...");
    }

    // Test 8: Revertir una transacción
    @Test
    public void revertirDepositoTest() {

        LOG.info("Inicio de prueba revertir depósito...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = cliente.getMonederos().get(0);

        Transaccion deposito = monederoVirtual.depositar(
                cliente, monedero, 500
        );

        monederoVirtual.revertir(
                cliente, deposito.getCodigo()
        );

        assertEquals(0.0, monedero.getSaldo(), 0.001);
        assertEquals(0, cliente.getPuntos());
        assertTrue(deposito.isRevertida());

        LOG.info("Fin de prueba: el depósito fue revertido correctamente...");
    }

    // Test 9: Ejecutar transacción programada y generar reporte
    @Test
    public void ejecutarProgramadaYReporteTest() {

        LOG.info("Inicio de prueba ejecutar transacción programada...");

        Cliente cliente = monederoVirtual.registrarCliente(
                "123", "Juan"
        );

        Monedero monedero = cliente.getMonederos().get(0);

        LocalDate fecha = LocalDate.now().plusDays(1);

        TransaccionProgramada programada = monederoVirtual.programar(
                "Depósito",
                cliente,
                monedero,
                null,
                null,
                250,
                fecha,
                "Única"
        );

        assertEquals(1, monederoVirtual.getListaProgramadas().size());

        String resultado = monederoVirtual.ejecutarPendientes(fecha);

        assertEquals("Operaciones realizadas: 1 | Fallidas: 0", resultado);
        assertEquals(250.0, monedero.getSaldo(), 0.001);
        assertEquals(1, programada.getEjecuciones());
        assertNull(programada.getFecha());

        String reporte = monederoVirtual.reporte(cliente);

        assertTrue(reporte.contains("Depósitos: $250.00"));
        assertTrue(reporte.contains("Puntos actuales: 2"));

        LOG.info("Fin de prueba: la operación programada se ejecutó y el reporte fue generado...");
    }
}
