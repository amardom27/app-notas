/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import controladores.UsuarioController;
import entidades.Usuario;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class UsuarioService {
    
    private static final UsuarioController uc = new UsuarioController();
    
    public static void insertarEjemplo() {
        var lista = new ArrayList<Usuario>();
        lista.add(new Usuario("pepe", "12345"));
        lista.add(new Usuario("antonio", "98765"));
        for (Usuario usuario : lista) {
            uc.create(usuario);
        }
    }
}
