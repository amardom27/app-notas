/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import controladores.CategoriasController;
import controladores.NotasController;
import controladores.UsuariosController;
import entidades.Categorias;
import entidades.Notas;
import entidades.Usuarios;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alvaro
 */
public class NotasService {

    private static final NotasController notasController = new NotasController();
    private static final UsuariosController usuariosController = new UsuariosController();
    private static final CategoriasController categoriasController = new CategoriasController();

    public static List<Notas> obtenerNotas() {
        List<Notas> lista = notasController.findAll();
        return lista;
    }
    
    public static void modificarNota(Notas nota) {
        notasController.updateContenido(nota);
    }

    public static void insertarEjemplo() {
        // Crear o buscar usuario
        Usuarios usuario = new Usuarios();
        usuario.setNomUsuario("alvaro");
        usuario.setPassUsuario("1234");
        usuariosController.create(usuario);

        // Asegurarse de que existan categorías
        Categorias cat1 = new Categorias("peliculas");
        categoriasController.create(cat1);

        Categorias cat2 = new Categorias("programacion");
        categoriasController.create(cat2);

        // Crear nota con categorías
        Notas nota1 = new Notas();
        nota1.setTitulo("Revisar JavaFX");
        nota1.setDescripcion("Practicar interfaces gráficas en JavaFX");
        nota1.setFechaCreacion(new Date());
        nota1.setIdUsuario(usuario);
        nota1.setCategoriasList(Arrays.asList(cat2)); // asociada a programación

        Notas nota2 = new Notas();
        nota2.setTitulo("Ver película");
        nota2.setDescripcion("Ver El Señor de los Anillos");
        nota2.setFechaCreacion(new Date());
        nota2.setIdUsuario(usuario);
        nota2.setCategoriasList(Arrays.asList(cat1)); // asociada a películas

        // Guardar notas
        notasController.create(nota1);
        notasController.create(nota2);

        System.out.println("Notas de ejemplo insertadas correctamente.");
    }

    public static void insertarEjemplo2() {
        // Supongamos que ya existe una categoría "deportes"
        Categorias cat3 = new Categorias("deportes");
        categoriasController.create(cat3);

        // Buscar una nota existente por ID (por ejemplo, ID = 1)
        Notas notaExistente = notasController.findById(1);
        if (notaExistente == null) {
            System.out.println("No se encontró la nota con ID 1");
            return;
        }

        // Obtener la lista actual de categorías y añadir la nueva
        List<Categorias> categoriasActuales = new ArrayList<>(notaExistente.getCategoriasList());
        categoriasActuales.add(cat3);

        // Actualizar la nota con la lista nueva de categorías
        notasController.update(notaExistente, categoriasActuales);

        System.out.println("Categoría 'deportes' añadida a la nota con ID 1");
    }

    public static void insertarEjemplo3() {
        // Mostrar categorías asociadas a una nota (por ejemplo, nota con ID=1)
        Notas nota = notasController.findById(1);
        if (nota == null) {
            System.out.println("Nota con ID 1 no encontrada.");
            return;
        }

        System.out.println("Categorías de la nota '" + nota.getTitulo() + "':");
        List<Categorias> categorias = nota.getCategoriasList();
        if (categorias == null || categorias.isEmpty()) {
            System.out.println("  - No tiene categorías asociadas.");
        } else {
            for (Categorias cat : categorias) {
                System.out.println("  - " + cat.getNombre());
            }
        }
    }

    public static void insertarEjemplo4() {
        // Mostrar notas asociadas a una categoría (por ejemplo, categoría con ID=1)
        Categorias categoria = categoriasController.findById(1);
        if (categoria == null) {
            System.out.println("Categoría con ID 1 no encontrada.");
            return;
        }

        System.out.println("Notas asociadas a la categoría '" + categoria.getNombre() + "':");
        List<Notas> notas = categoria.getNotasList();
        if (notas == null || notas.isEmpty()) {
            System.out.println("  - No hay notas asociadas.");
        } else {
            for (Notas nota : notas) {
                System.out.println("  - " + nota.getTitulo());
            }
        }
    }
}
