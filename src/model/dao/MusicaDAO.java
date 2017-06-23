/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import javax.persistence.EntityNotFoundException;
import model.entity.Autor;
import model.entity.PlaylistMusica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Disco;
import model.entity.DiscoMusica;
import model.entity.Musica;

/**
 *
 * @author Michael
 */
public class MusicaDAO implements SpotNetDAO<Musica> {

    private EntityManagerFactory emf = null;
    
    public MusicaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    @Override
    public void create(Musica musica) {
        if (musica.getPlaylistMusicaList() == null) {
            musica.setPlaylistMusicaList(new ArrayList<PlaylistMusica>());
        }
        if (musica.getDiscoMusicaList() == null) {
            musica.setDiscoMusicaList(new ArrayList<DiscoMusica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor autor = musica.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getIdAutor());
                musica.setAutor(autor);
            }
            List<PlaylistMusica> attachedPlaylistMusicaList = new ArrayList<PlaylistMusica>();
            for (PlaylistMusica playlistMusicaListPlaylistMusicaToAttach : musica.getPlaylistMusicaList()) {
                playlistMusicaListPlaylistMusicaToAttach = em.getReference(playlistMusicaListPlaylistMusicaToAttach.getClass(), playlistMusicaListPlaylistMusicaToAttach.getIdPlaylistMusica());
                attachedPlaylistMusicaList.add(playlistMusicaListPlaylistMusicaToAttach);
            }
            musica.setPlaylistMusicaList(attachedPlaylistMusicaList);
            List<DiscoMusica> attachedDiscoMusicaList = new ArrayList<DiscoMusica>();
            for (DiscoMusica discoMusicaListDiscoMusicaToAttach : musica.getDiscoMusicaList()) {
                discoMusicaListDiscoMusicaToAttach = em.getReference(discoMusicaListDiscoMusicaToAttach.getClass(), discoMusicaListDiscoMusicaToAttach.getIdDiscoMusica());
                attachedDiscoMusicaList.add(discoMusicaListDiscoMusicaToAttach);
            }
            musica.setDiscoMusicaList(attachedDiscoMusicaList);
            em.persist(musica);
            if (autor != null) {
                autor.getMusicaList().add(musica);
                autor = em.merge(autor);
            }
            for (PlaylistMusica playlistMusicaListPlaylistMusica : musica.getPlaylistMusicaList()) {
                Musica oldMusicaOfPlaylistMusicaListPlaylistMusica = playlistMusicaListPlaylistMusica.getMusica();
                playlistMusicaListPlaylistMusica.setMusica(musica);
                playlistMusicaListPlaylistMusica = em.merge(playlistMusicaListPlaylistMusica);
                if (oldMusicaOfPlaylistMusicaListPlaylistMusica != null) {
                    oldMusicaOfPlaylistMusicaListPlaylistMusica.getPlaylistMusicaList().remove(playlistMusicaListPlaylistMusica);
                    oldMusicaOfPlaylistMusicaListPlaylistMusica = em.merge(oldMusicaOfPlaylistMusicaListPlaylistMusica);
                }
            }
            for (DiscoMusica discoMusicaListDiscoMusica : musica.getDiscoMusicaList()) {
                Musica oldMusicaOfDiscoMusicaListDiscoMusica = discoMusicaListDiscoMusica.getMusica();
                discoMusicaListDiscoMusica.setMusica(musica);
                discoMusicaListDiscoMusica = em.merge(discoMusicaListDiscoMusica);
                if (oldMusicaOfDiscoMusicaListDiscoMusica != null) {
                    oldMusicaOfDiscoMusicaListDiscoMusica.getDiscoMusicaList().remove(discoMusicaListDiscoMusica);
                    oldMusicaOfDiscoMusicaListDiscoMusica = em.merge(oldMusicaOfDiscoMusicaListDiscoMusica);
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
    public void update(Musica musica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Musica persistentMusica = em.find(Musica.class, musica.getIdMusica());
            Autor autorOld = persistentMusica.getAutor();
            Autor autorNew = musica.getAutor();
            List<PlaylistMusica> playlistMusicaListOld = persistentMusica.getPlaylistMusicaList();
            List<PlaylistMusica> playlistMusicaListNew = musica.getPlaylistMusicaList();
            List<DiscoMusica> discoMusicaListOld = persistentMusica.getDiscoMusicaList();
            List<DiscoMusica> discoMusicaListNew = musica.getDiscoMusicaList();
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getIdAutor());
                musica.setAutor(autorNew);
            }
            List<PlaylistMusica> attachedPlaylistMusicaListNew = new ArrayList<PlaylistMusica>();
            for (PlaylistMusica playlistMusicaListNewPlaylistMusicaToAttach : playlistMusicaListNew) {
                playlistMusicaListNewPlaylistMusicaToAttach = em.getReference(playlistMusicaListNewPlaylistMusicaToAttach.getClass(), playlistMusicaListNewPlaylistMusicaToAttach.getIdPlaylistMusica());
                attachedPlaylistMusicaListNew.add(playlistMusicaListNewPlaylistMusicaToAttach);
            }
            playlistMusicaListNew = attachedPlaylistMusicaListNew;
            musica.setPlaylistMusicaList(playlistMusicaListNew);
            List<DiscoMusica> attachedDiscoMusicaListNew = new ArrayList<DiscoMusica>();
            for (DiscoMusica discoMusicaListNewDiscoMusicaToAttach : discoMusicaListNew) {
                discoMusicaListNewDiscoMusicaToAttach = em.getReference(discoMusicaListNewDiscoMusicaToAttach.getClass(), discoMusicaListNewDiscoMusicaToAttach.getIdDiscoMusica());
                attachedDiscoMusicaListNew.add(discoMusicaListNewDiscoMusicaToAttach);
            }
            discoMusicaListNew = attachedDiscoMusicaListNew;
            musica.setDiscoMusicaList(discoMusicaListNew);
            musica = em.merge(musica);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getMusicaList().remove(musica);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getMusicaList().add(musica);
                autorNew = em.merge(autorNew);
            }
            for (PlaylistMusica playlistMusicaListOldPlaylistMusica : playlistMusicaListOld) {
                if (!playlistMusicaListNew.contains(playlistMusicaListOldPlaylistMusica)) {
                    playlistMusicaListOldPlaylistMusica.setMusica(null);
                    playlistMusicaListOldPlaylistMusica = em.merge(playlistMusicaListOldPlaylistMusica);
                }
            }
            for (PlaylistMusica playlistMusicaListNewPlaylistMusica : playlistMusicaListNew) {
                if (!playlistMusicaListOld.contains(playlistMusicaListNewPlaylistMusica)) {
                    Musica oldMusicaOfPlaylistMusicaListNewPlaylistMusica = playlistMusicaListNewPlaylistMusica.getMusica();
                    playlistMusicaListNewPlaylistMusica.setMusica(musica);
                    playlistMusicaListNewPlaylistMusica = em.merge(playlistMusicaListNewPlaylistMusica);
                    if (oldMusicaOfPlaylistMusicaListNewPlaylistMusica != null && !oldMusicaOfPlaylistMusicaListNewPlaylistMusica.equals(musica)) {
                        oldMusicaOfPlaylistMusicaListNewPlaylistMusica.getPlaylistMusicaList().remove(playlistMusicaListNewPlaylistMusica);
                        oldMusicaOfPlaylistMusicaListNewPlaylistMusica = em.merge(oldMusicaOfPlaylistMusicaListNewPlaylistMusica);
                    }
                }
            }
            for (DiscoMusica discoMusicaListOldDiscoMusica : discoMusicaListOld) {
                if (!discoMusicaListNew.contains(discoMusicaListOldDiscoMusica)) {
                    discoMusicaListOldDiscoMusica.setMusica(null);
                    discoMusicaListOldDiscoMusica = em.merge(discoMusicaListOldDiscoMusica);
                }
            }
            for (DiscoMusica discoMusicaListNewDiscoMusica : discoMusicaListNew) {
                if (!discoMusicaListOld.contains(discoMusicaListNewDiscoMusica)) {
                    Musica oldMusicaOfDiscoMusicaListNewDiscoMusica = discoMusicaListNewDiscoMusica.getMusica();
                    discoMusicaListNewDiscoMusica.setMusica(musica);
                    discoMusicaListNewDiscoMusica = em.merge(discoMusicaListNewDiscoMusica);
                    if (oldMusicaOfDiscoMusicaListNewDiscoMusica != null && !oldMusicaOfDiscoMusicaListNewDiscoMusica.equals(musica)) {
                        oldMusicaOfDiscoMusicaListNewDiscoMusica.getDiscoMusicaList().remove(discoMusicaListNewDiscoMusica);
                        oldMusicaOfDiscoMusicaListNewDiscoMusica = em.merge(oldMusicaOfDiscoMusicaListNewDiscoMusica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = musica.getIdMusica();
                if (findById(id) == null) {
                    throw new NonexistentEntityException("A música com o id " + id + " não existe.");
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
            Musica musica;
            try {
                musica = em.getReference(Musica.class, id);
                musica.getIdMusica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("A música com o id " + id + " não existe.", enfe);
            }
            Autor autor = musica.getAutor();
            if (autor != null) {
                autor.getMusicaList().remove(musica);
                autor = em.merge(autor);
            }
            List<PlaylistMusica> playlistMusicaList = musica.getPlaylistMusicaList();
            for (PlaylistMusica playlistMusicaListPlaylistMusica : playlistMusicaList) {
                playlistMusicaListPlaylistMusica.setMusica(null);
                playlistMusicaListPlaylistMusica = em.merge(playlistMusicaListPlaylistMusica);
            }
            List<DiscoMusica> discoMusicaList = musica.getDiscoMusicaList();
            for (DiscoMusica discoMusicaListDiscoMusica : discoMusicaList) {
                discoMusicaListDiscoMusica.setMusica(null);
                discoMusicaListDiscoMusica = em.merge(discoMusicaListDiscoMusica);
            }
            em.remove(musica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Musica> findAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Musica.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Musica findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Musica.findByNome");
            query.setParameter("nome", nome);
            return (Musica) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    
    
    @Override
    public Musica findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Musica.class, id);
        } finally {
            em.close();
        }
    }
    
//    public List<Musica> findMusicaEntities() {
//        return findMusicaEntities(true, -1, -1);
//    }
//
//    public List<Musica> findMusicaEntities(int maxResults, int firstResult) {
//        return findMusicaEntities(false, maxResults, firstResult);
//    }
//
//    private List<Musica> findMusicaEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Musica.class));
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

//    public int getMusicaCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Musica> rt = cq.from(Musica.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
    
}
