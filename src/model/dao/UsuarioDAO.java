/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.entity.Playlist;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Usuario;

/**
 *
 * @author Michael
 */
public class UsuarioDAO implements SpotNetDAO<Usuario> {

    private EntityManagerFactory emf = null;

    public UsuarioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Usuario usuario) {
        if (usuario.getPlaylistList() == null) {
            usuario.setPlaylistList(new ArrayList<Playlist>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Playlist> attachedPlaylistList = new ArrayList<Playlist>();
            for (Playlist playlistListPlaylistToAttach : usuario.getPlaylistList()) {
                playlistListPlaylistToAttach = em.getReference(playlistListPlaylistToAttach.getClass(), playlistListPlaylistToAttach.getIdPlaylist());
                attachedPlaylistList.add(playlistListPlaylistToAttach);
            }
            usuario.setPlaylistList(attachedPlaylistList);
            em.persist(usuario);
            for (Playlist playlistListPlaylist : usuario.getPlaylistList()) {
                Usuario oldUsuarioOfPlaylistListPlaylist = playlistListPlaylist.getUsuario();
                playlistListPlaylist.setUsuario(usuario);
                playlistListPlaylist = em.merge(playlistListPlaylist);
                if (oldUsuarioOfPlaylistListPlaylist != null) {
                    oldUsuarioOfPlaylistListPlaylist.getPlaylistList().remove(playlistListPlaylist);
                    oldUsuarioOfPlaylistListPlaylist = em.merge(oldUsuarioOfPlaylistListPlaylist);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void update(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<Playlist> playlistListOld = persistentUsuario.getPlaylistList();
            List<Playlist> playlistListNew = usuario.getPlaylistList();
            List<Playlist> attachedPlaylistListNew = new ArrayList<Playlist>();
            for (Playlist playlistListNewPlaylistToAttach : playlistListNew) {
                playlistListNewPlaylistToAttach = em.getReference(playlistListNewPlaylistToAttach.getClass(), playlistListNewPlaylistToAttach.getIdPlaylist());
                attachedPlaylistListNew.add(playlistListNewPlaylistToAttach);
            }
            playlistListNew = attachedPlaylistListNew;
            usuario.setPlaylistList(playlistListNew);
            usuario = em.merge(usuario);
            for (Playlist playlistListOldPlaylist : playlistListOld) {
                if (!playlistListNew.contains(playlistListOldPlaylist)) {
                    playlistListOldPlaylist.setUsuario(null);
                    playlistListOldPlaylist = em.merge(playlistListOldPlaylist);
                }
            }
            for (Playlist playlistListNewPlaylist : playlistListNew) {
                if (!playlistListOld.contains(playlistListNewPlaylist)) {
                    Usuario oldUsuarioOfPlaylistListNewPlaylist = playlistListNewPlaylist.getUsuario();
                    playlistListNewPlaylist.setUsuario(usuario);
                    playlistListNewPlaylist = em.merge(playlistListNewPlaylist);
                    if (oldUsuarioOfPlaylistListNewPlaylist != null && !oldUsuarioOfPlaylistListNewPlaylist.equals(usuario)) {
                        oldUsuarioOfPlaylistListNewPlaylist.getPlaylistList().remove(playlistListNewPlaylist);
                        oldUsuarioOfPlaylistListNewPlaylist = em.merge(oldUsuarioOfPlaylistListNewPlaylist);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findById(id) == null) {
                    throw new NonexistentEntityException("O usuário com o id " + id + " não existe.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void delete(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("O usuário com o id " + id + " não existe.", enfe);
            }
            List<Playlist> playlistList = usuario.getPlaylistList();
            for (Playlist playlistListPlaylist : playlistList) {
                playlistListPlaylist.setUsuario(null);
                playlistListPlaylist = em.merge(playlistListPlaylist);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Usuario> findAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Usuario.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Usuario> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Usuario.findByNome");
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Usuario findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

//    public List<Usuario> findUsuarioEntities() {
//        return findUsuarioEntities(true, -1, -1);
//    }
//
//    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
//        return findUsuarioEntities(false, maxResults, firstResult);
//    }
//
//    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Usuario.class));
//            Query q = em.createQuery(cq);
//            if (!all) {
//                q.setMaxResults(maxResults);
//                q.setFirstResult(firstResult);
//            }
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//    
//    public int getUsuarioCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Usuario> rt = cq.from(Usuario.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
}
