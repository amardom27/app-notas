package servicios;

import controladores.CategoriasController;
import entidades.Categorias;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class CategoriasService {

    private static final CategoriasController cc = new CategoriasController();
    
     public static void insertarEjemplo() {
        var lista = new ArrayList<Categorias>();
        lista.add(new Categorias("peliculas"));
        lista.add(new Categorias("programacion"));
         for (Categorias categorias : lista) {
             cc.create(categorias);
         }
    }
}
