package controladores;

import entidades.Categorias;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author alvaro
 */
public class CategoriasController {

    private final EntityManagerFactory emf;

    public CategoriasController() {
        this.emf = Persistence.createEntityManagerFactory("notas");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoria La categoría a crear.
     */
    public void create(Categorias categoria) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(categoria);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al crear la categoría", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una categoría por su ID.
     *
     * @param id El ID de la categoría.
     * @return La categoría encontrada o null si no existe.
     */
    public Categorias findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las categorías de la base de datos.
     *
     * @return Una lista de categorías.
     */
    public List<Categorias> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Categorias.findAll", Categorias.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca una categoría por su nombre.
     *
     * @param nombre El nombre de la categoría.
     * @return La categoría encontrada o null si no existe.
     */
    public Categorias findByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Categorias> query = em.createNamedQuery("Categorias.findByNombre", Categorias.class);
            query.setParameter("nombre", nombre);
            List<Categorias> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param categoria La categoría a actualizar.
     */
    public void update(Categorias categoria) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(categoria);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar la categoría", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una categoría de la base de datos.
     *
     * @param id El ID de la categoría a eliminar.
     */
    public void delete(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categorias categoria = em.find(Categorias.class, id);
            if (categoria != null) {
                em.remove(categoria);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar la categoría", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina todas las categorías de la base de datos y reinicia el autoincremento.
     */
    public void deleteAll() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DELETE FROM categorias").executeUpdate();
            em.createNativeQuery("ALTER TABLE categorias AUTO_INCREMENT = 1").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar todas las categorías", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Cierra el EntityManagerFactory cuando ya no se necesita.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
