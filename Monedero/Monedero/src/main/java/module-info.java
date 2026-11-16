module co.edu.uniquindio.poo.monedero {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.edu.uniquindio.poo.monedero.viewController to javafx.fxml;

    exports co.edu.uniquindio.poo.monedero;
    exports co.edu.uniquindio.poo.monedero.model;
    exports co.edu.uniquindio.poo.monedero.controller;
}
