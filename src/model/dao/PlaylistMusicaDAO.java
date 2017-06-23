/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Musica;
import model.entity.Playlist;
import model.entity.PlaylistMusica;

/**
 *
 * @author Michael
 */
public class PlaylistMusicaDAO implements Serializable {

    public PlaylistMusicaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlaylistMusica playlistMusica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Musica musica = playlistMusica.getMusica();
            if (musica != null) {
                musica = em.getReference(musica.getClass(), musica.getIdMusica());
                playlistMusica.setMusica(musica);
            }
            Playlist playlist = playlistMusica.getPlaylist();
            if (playlist != null) {
                playlist = em.getReference(playlist.getClass(), playlist.getIdPlaylist());
                playlistMusica.setPlaylist(playlist);
            }
            em.persist(playlistMusica);
            if (musica != null) {
                musica.getPlaylistMusicaList().add(playlistMusica);
                musica = em.merge(musica);
            }
            if (playlist != null) {
                playlist.getPlaylistMusicaList().add(playlistMusica);
                playlist = em.merge(playlist);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlaylistMusica playlistMusica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlaylistMusica persistentPlaylistMusica = em.find(PlaylistMusica.class, playlistMusica.getIdPlaylistMusica());
            Musica musicaOld = persistentPlaylistMusica.getMusica();
            Musica musicaNew = playlistMusica.getMusica();
            Playlist playlistOld = persistentPlaylistMusica.getPlaylist();
            Playlist playlistNew = playlistMusica.getPlaylist();
            if (musicaNew != null) {
                musicaNew = em.getReference(musicaNew.getClass(), musicaNew.getIdMusica());
                playlistMusica.setMusica(musicaNew);
            }
            if (playlistNew != null) {
                playlistNew = em.getReference(playlistNew.getClass(), playlistNew.getIdPlaylist());
                playlistMusica.setPlaylist(playlistNew);
            }
            playlistMusica = em.merge(playlistMusica);
            if (musicaOld != null && !musicaOld.equals(musicaNew)) {
                musicaOld.getPlaylistMusicaList().remove(playlistMusica);
                musicaOld = em.merge(musicaOld);
            }
            if (musicaNew != null && !musicaNew.equals(musicaOld)) {
                musicaNew.getPlaylistMusicaList().add(playlistMusica);
                musicaNew = em.merge(musicaNew);
            }
            if (playlistOld != null && !playlistOld.equals(playlistNew)) {
                playlistOld.getPlaylistMusicaList().remove(playlistMusica);
                playlistOld = em.merge(playlistOld);
            }
            if (playlistNew != null && !playlistNew.equals(playlistOld)) {
                playlistNew.getPlaylistMusicaList().add(playlistMusica);
                playlistNew = em.merge(playlistNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = playlistMusica.getIdPlaylistMusica();
                if (findPlaylistMusica(id) == null) {
                    throw new NonexistentEntityException("The playlistMusica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlaylistMusica playlistMusica;
            try {
                playlistMusica = em.getReference(PlaylistMusica.class, id);
                playlistMusica.getIdPlaylistMusica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The playlistMusica with id " + id + " no longer exists.", enfe);
            }
            Musica musica = playlistMusica.getMusica();
            if (musica != null) {
                musica.getPlaylistMusicaList().remove(playlistMusica);
                musica = em.merge(musica);
            }
            Playlist playlist = playlistMusica.getPlaylist();
            if (playlist != null) {
                playlist.getPlaylistMusicaList().remove(playlistMusica);
                playlist = em.merge(playlist);
            }
            em.remove(playlistMusica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlaylistMusica> findPlaylistMusicaEntities() {
        return findPlaylistMusicaEntities(true, -1, -1);
    }

    public List<PlaylistMusica> findPlaylistMusicaEntities(int maxResults, int firstResult) {
        return findPlaylistMusicaEntities(false, maxResults, firstResult);
    }

    private List<PlaylistMusica> findPlaylistMusicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlaylistMusica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PlaylistMusica findPlaylistMusica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlaylistMusica.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlaylistMusicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlaylistMusica> rt = cq.from(PlaylistMusica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
