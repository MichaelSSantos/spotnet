/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.entity.Usuario;
import model.entity.PlaylistMusica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Playlist;

/**
 *
 * @author Michael
 */
public class PlaylistDAO implements SpotNetDAO<Playlist> {

    private EntityManagerFactory emf = null;

    public PlaylistDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Playlist playlist) {
        if (playlist.getPlaylistMusicaList() == null) {
            playlist.setPlaylistMusicaList(new ArrayList<PlaylistMusica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = playlist.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                playlist.setUsuario(usuario);
            }
            List<PlaylistMusica> attachedPlaylistMusicaList = new ArrayList<PlaylistMusica>();
            for (PlaylistMusica playlistMusicaListPlaylistMusicaToAttach : playlist.getPlaylistMusicaList()) {
                playlistMusicaListPlaylistMusicaToAttach = em.getReference(playlistMusicaListPlaylistMusicaToAttach.getClass(), playlistMusicaListPlaylistMusicaToAttach.getIdPlaylistMusica());
                attachedPlaylistMusicaList.add(playlistMusicaListPlaylistMusicaToAttach);
            }
            playlist.setPlaylistMusicaList(attachedPlaylistMusicaList);
            em.persist(playlist);
            if (usuario != null) {
                usuario.getPlaylistList().add(playlist);
                usuario = em.merge(usuario);
            }
            for (PlaylistMusica playlistMusicaListPlaylistMusica : playlist.getPlaylistMusicaList()) {
                Playlist oldPlaylistOfPlaylistMusicaListPlaylistMusica = playlistMusicaListPlaylistMusica.getPlaylist();
                playlistMusicaListPlaylistMusica.setPlaylist(playlist);
                playlistMusicaListPlaylistMusica = em.merge(playlistMusicaListPlaylistMusica);
                if (oldPlaylistOfPlaylistMusicaListPlaylistMusica != null) {
                    oldPlaylistOfPlaylistMusicaListPlaylistMusica.getPlaylistMusicaList().remove(playlistMusicaListPlaylistMusica);
                    oldPlaylistOfPlaylistMusicaListPlaylistMusica = em.merge(oldPlaylistOfPlaylistMusicaListPlaylistMusica);
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
    public void update(Playlist playlist) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Playlist persistentPlaylist = em.find(Playlist.class, playlist.getIdPlaylist());
            Usuario usuarioOld = persistentPlaylist.getUsuario();
            Usuario usuarioNew = playlist.getUsuario();
            List<PlaylistMusica> playlistMusicaListOld = persistentPlaylist.getPlaylistMusicaList();
            List<PlaylistMusica> playlistMusicaListNew = playlist.getPlaylistMusicaList();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                playlist.setUsuario(usuarioNew);
            }
            List<PlaylistMusica> attachedPlaylistMusicaListNew = new ArrayList<PlaylistMusica>();
            for (PlaylistMusica playlistMusicaListNewPlaylistMusicaToAttach : playlistMusicaListNew) {
                playlistMusicaListNewPlaylistMusicaToAttach = em.getReference(playlistMusicaListNewPlaylistMusicaToAttach.getClass(), playlistMusicaListNewPlaylistMusicaToAttach.getIdPlaylistMusica());
                attachedPlaylistMusicaListNew.add(playlistMusicaListNewPlaylistMusicaToAttach);
            }
            playlistMusicaListNew = attachedPlaylistMusicaListNew;
            playlist.setPlaylistMusicaList(playlistMusicaListNew);
            playlist = em.merge(playlist);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPlaylistList().remove(playlist);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPlaylistList().add(playlist);
                usuarioNew = em.merge(usuarioNew);
            }
            for (PlaylistMusica playlistMusicaListOldPlaylistMusica : playlistMusicaListOld) {
                if (!playlistMusicaListNew.contains(playlistMusicaListOldPlaylistMusica)) {
                    playlistMusicaListOldPlaylistMusica.setPlaylist(null);
                    playlistMusicaListOldPlaylistMusica = em.merge(playlistMusicaListOldPlaylistMusica);
                }
            }
            for (PlaylistMusica playlistMusicaListNewPlaylistMusica : playlistMusicaListNew) {
                if (!playlistMusicaListOld.contains(playlistMusicaListNewPlaylistMusica)) {
                    Playlist oldPlaylistOfPlaylistMusicaListNewPlaylistMusica = playlistMusicaListNewPlaylistMusica.getPlaylist();
                    playlistMusicaListNewPlaylistMusica.setPlaylist(playlist);
                    playlistMusicaListNewPlaylistMusica = em.merge(playlistMusicaListNewPlaylistMusica);
                    if (oldPlaylistOfPlaylistMusicaListNewPlaylistMusica != null && !oldPlaylistOfPlaylistMusicaListNewPlaylistMusica.equals(playlist)) {
                        oldPlaylistOfPlaylistMusicaListNewPlaylistMusica.getPlaylistMusicaList().remove(playlistMusicaListNewPlaylistMusica);
                        oldPlaylistOfPlaylistMusicaListNewPlaylistMusica = em.merge(oldPlaylistOfPlaylistMusicaListNewPlaylistMusica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = playlist.getIdPlaylist();
                if (findById(id) == null) {
                    throw new NonexistentEntityException("A playlist com o id " + id + " não existe.");
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
            Playlist playlist;
            try {
                playlist = em.getReference(Playlist.class, id);
                playlist.getIdPlaylist();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("A playlist com o id " + id + " não existe.", enfe);
            }
            Usuario usuario = playlist.getUsuario();
            if (usuario != null) {
                usuario.getPlaylistList().remove(playlist);
                usuario = em.merge(usuario);
            }
            List<PlaylistMusica> playlistMusicaList = playlist.getPlaylistMusicaList();
            for (PlaylistMusica playlistMusicaListPlaylistMusica : playlistMusicaList) {
                playlistMusicaListPlaylistMusica.setPlaylist(null);
                playlistMusicaListPlaylistMusica = em.merge(playlistMusicaListPlaylistMusica);
            }
            em.remove(playlist);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List findAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Playlist.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Playlist findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Playlist.findByNome");
            query.setParameter("nome", nome);
            return (Playlist) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Playlist findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Playlist.class, id);
        } finally {
            em.close();
        }
    }

//    public List<Playlist> findPlaylistEntities() {
//        return findPlaylistEntities(true, -1, -1);
//    }
//
//    public List<Playlist> findPlaylistEntities(int maxResults, int firstResult) {
//        return findPlaylistEntities(false, maxResults, firstResult);
//    }
//
//    private List<Playlist> findPlaylistEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Playlist.class));
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
//    public int getPlaylistCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Playlist> rt = cq.from(Playlist.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
}
