/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import controladores.UsuarioController;
import entidades.Usuarios;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class UsuarioService {
    
    private static final UsuarioController uc = new UsuarioController();
    
    public static void insertarEjemplo() {
        var lista = new ArrayList<Usuarios>();
        lista.add(new Usuarios("pepe", "12345"));
        lista.add(new Usuarios("antonio", "98765"));
        for (Usuarios usuario : lista) {
            uc.create(usuario);
        }
    }
}
