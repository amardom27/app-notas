/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidades.Categorias;
import entidades.Notas;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author alvaro
 */
public class NotasController {

    private final EntityManagerFactory emf;

    public NotasController() {
        this.emf = Persistence.createEntityManagerFactory("notas");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea una nueva nota con sus categorías asociadas.
     *
     * @param nota
     */
    public void create(Notas nota) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Si la nota no tiene fecha de creación, se la damos
            if (nota.getFechaCreacion() == null) {
                nota.setFechaCreacion(new Date());
            }

            // Asociar categorías a la nota (ya están dentro del objeto nota)
            List<Categorias> categorias = nota.getCategoriasList();

            // También actualizar el otro lado de la relación (opcional pero recomendable)
            if (categorias != null) {
                for (Categorias cat : categorias) {
                    if (cat.getNotasList() != null) {
                        cat.getNotasList().add(nota);
                    }
                }
            }

            em.persist(nota); // JPA se encargará de insertar en detNotasCategorias
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al crear la nota", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una nota por ID.
     *
     * @param id
     * @return
     */
    public Notas findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notas.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Devuelve todas las notas.
     *
     * @return
     */
    public List<Notas> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Notas.findAll", Notas.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Notas> findAllByUser(Integer idUsuario) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT n FROM Notas n WHERE n.usuario.idUsuario = :idUsuario", Notas.class)
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Error al obtener las notas del usuario con ID: " + idUsuario, ex);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una nota (incluye su lista de categorías).
     *
     * @param nota
     * @param nuevasCategorias
     */
    public void update(Notas nota, List<Categorias> nuevasCategorias) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar la nota existente
            Notas notaExistente = em.find(Notas.class, nota.getIdNota());
            if (notaExistente == null) {
                throw new IllegalArgumentException("Nota no encontrada con ID: " + nota.getIdNota());
            }

            // Cargar las categorías actuales asociadas a la nota
            notaExistente.getCategoriasList().size(); // Forzar inicialización si es LAZY
            List<Categorias> categoriasAnteriores = notaExistente.getCategoriasList();

            // Eliminar la nota de las categorías que ya no están
            for (Categorias cat : categoriasAnteriores) {
                if (!nuevasCategorias.contains(cat)) {
                    cat = em.merge(cat); // asegurar que esté gestionada
                    cat.getNotasList().remove(notaExistente);
                }
            }

            // Agregar la nota a las nuevas categorías si no estaban ya
            for (Categorias nuevaCat : nuevasCategorias) {
                nuevaCat = em.merge(nuevaCat); // asegurar que esté gestionada
                if (!nuevaCat.getNotasList().contains(notaExistente)) {
                    nuevaCat.getNotasList().add(notaExistente);
                }
            }

            notaExistente.setTitulo(nota.getTitulo());
            notaExistente.setDescripcion(nota.getDescripcion());
            notaExistente.setCategoriasList(nuevasCategorias);

            em.merge(notaExistente); // JPA actualiza la tabla intermedia si las colecciones están correctamente sincronizadas

            tx.commit();
        } catch (IllegalArgumentException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar la nota", ex);
        } finally {
            em.close();
        }
    }

    public void updateContenido(Notas nota) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Buscar la nota existente por su ID
            Notas notaExistente = em.find(Notas.class, nota.getIdNota());
            if (notaExistente == null) {
                throw new IllegalArgumentException("Nota no encontrada con ID: " + nota.getIdNota());
            }

            // Actualizar solo el título y la descripción
            notaExistente.setTitulo(nota.getTitulo());
            notaExistente.setDescripcion(nota.getDescripcion());

            em.merge(notaExistente);

            tx.commit();
        } catch (IllegalArgumentException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar la nota", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una nota (y su relación con categorías).
     *
     * @param idNota
     */
    public void delete(Integer idNota) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Notas nota = em.find(Notas.class, idNota);
            if (nota != null) {
                // Desvincular categorías manualmente para que se borre de detNotasCategorias
                nota.getCategoriasList().clear();
                em.remove(nota);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar la nota", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina todas las notas y reinicia el autoincremento.
     */
    public void deleteAll() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DELETE FROM detNotasCategorias").executeUpdate();
            em.createNativeQuery("DELETE FROM notas").executeUpdate();
            em.createNativeQuery("ALTER TABLE notas AUTO_INCREMENT = 1").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar todas las notas", ex);
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
