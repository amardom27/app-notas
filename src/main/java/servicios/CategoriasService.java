package servicios;

import controladores.CategoriasController;
import entidades.Categorias;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author alvaro
 */
public class CategoriasService {

    private static final CategoriasController cc = new CategoriasController();

    public static void agregarCategoria(Categorias cat) {
        try {
            cc.create(cat);
            JOptionPane.showMessageDialog(null, "Categoria creada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error introduciendo la categoria.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Categorias> obtenerCategorias() {
        List<Categorias> categorias = cc.findAll();
        return categorias;
    }

    public static void modificarCategoriaPorId(Categorias cat) {
        try {
            cc.update(cat);
            JOptionPane.showMessageDialog(null, "Categoria modificada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error modificando la categoria.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void borrarCategoriaPorId(Integer id) {
        try {
            cc.delete(id);
            JOptionPane.showMessageDialog(null, "Categoria borrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error borrando la categoria.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void ejemploBuscarId() {
        Categorias cat = cc.findById(2);
        System.out.println(cat);
    }
}
