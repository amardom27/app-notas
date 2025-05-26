/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import controladores.NotasController;
import entidades.Categorias;
import entidades.Notas;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author alvaro
 */
public class NotasService {

    private static final NotasController nc = new NotasController();

    public static List<Notas> obtenerNotas() {
        List<Notas> lista = nc.findAll();
        return lista;
    }

    public static List<Notas> obtenerNotasPorUsuario(Integer id) {
        List<Notas> lista = nc.findAllByUser(id);
        return lista;
    }

    public static Notas obtenerNotaPorId(Integer id) {
        Notas n = nc.findById(id);
        return n;
    }

    public static void agregarNota(Notas nota) {
        try {
            nc.create(nota);
            JOptionPane.showMessageDialog(null, "Nota creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error introduciendo la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void borrarNotaPorId(Integer id) {
        try {
            nc.delete(id);
            JOptionPane.showMessageDialog(null, "Nota borrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error borrando la nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void modificarNota(Notas nota) {
        nc.updateContenido(nota);
    }

    public static void modificarCategorias(Notas nota, List<Categorias> categorias) {
        try {
            nc.update(nota, categorias);
            JOptionPane.showMessageDialog(null, "Categorias de la Nota modificadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error modificando las categorias de la Nota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
