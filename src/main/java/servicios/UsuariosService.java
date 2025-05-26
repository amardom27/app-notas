/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import controladores.UsuariosController;
import entidades.Usuarios;
import javax.swing.JOptionPane;

/**
 *
 * @author alvaro
 */
public class UsuariosService {

    private static final UsuariosController uc = new UsuariosController();

    public static Usuarios autenticar(String nombreUsuario, String contraseña) {
        Usuarios usuario = uc.findByNombreYContrasena(nombreUsuario, contraseña);
        return usuario;
    }

    public static void insertarUsuario(Usuarios usuario) {
        try {
            uc.create(usuario);
            JOptionPane.showMessageDialog(null, "Usuario creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error introduciendo al usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Usuarios obtenerUsuarioPorId(Integer id) {
        return uc.findById(id);
    }

    public static void borrarUsuario(Integer id) {
        try {
            uc.delete(id);
            JOptionPane.showMessageDialog(null, "Usuario borrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error borrando al usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void modificarUsuario(Usuarios usuario) {
        try {
            uc.update(usuario);
            JOptionPane.showMessageDialog(null, "Usuario modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error modificando el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
