package controladores;

import entidades.Usuarios;
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
public class UsuariosController {

    private final EntityManagerFactory emf;

    public UsuariosController() {
        // Nombre de la unidad de persistencia definido en persistence.xml
        this.emf = Persistence.createEntityManagerFactory("notas");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param usuario El usuario a crear.
     */
    public void create(Usuarios usuario) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(usuario);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al crear el usuario", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id El ID del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     *
     * @return Una lista de usuarios.
     */
    public List<Usuarios> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Usuario.findAll", Usuarios.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param nombre El nombre de usuario.
     * @return El usuario encontrado o null si no existe.
     */
    public Usuarios findByNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuarios> query = em.createNamedQuery("Usuario.findByNombre", Usuarios.class);
            query.setParameter("nomUsuario", nombre);
            List<Usuarios> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario El usuario a actualizar.
     */
    public void update(Usuarios usuario) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(usuario);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar el usuario", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param id El ID del usuario a eliminar.
     */
    public void delete(Integer id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Usuarios usuario = em.find(Usuarios.class, id);
            if (usuario != null) {
                em.remove(usuario);
            }
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar el usuario", ex);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina todos los usuarios de la base de datos y reinicia el
     * autoincremento.
     */
    public void deleteAll() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createNativeQuery("DELETE FROM usuarios").executeUpdate();
            em.createNativeQuery("ALTER TABLE usuarios AUTO_INCREMENT = 1").executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al eliminar todos los usuarios", ex);
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
