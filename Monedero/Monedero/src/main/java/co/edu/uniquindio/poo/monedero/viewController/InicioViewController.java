package co.edu.uniquindio.poo.monedero.viewController;

import co.edu.uniquindio.poo.monedero.Aplicacion;
import javafx.fxml.FXML;

public class InicioViewController {
    private Aplicacion aplicacion;

    public void setApp(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    @FXML
    void abrirClientes() {
        aplicacion.openViewClientes();
    }

    @FXML
    void abrirMonedero() {
        aplicacion.openViewMonedero();
    }
}