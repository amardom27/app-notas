/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package daw;

import vistas.LoginDialog;
import vistas.VentanaPrincipal;

/**
 *
 * @author alvaro
 */
public class AppNotas {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null, true);
            login.setVisible(true); // bloquea hasta que el login se cierre

            if (login.isLoginExitoso()) {
                new VentanaPrincipal().setVisible(true);
            } else {
                System.exit(0); // o simplemente no haces nada
            }
        });
    }
}
