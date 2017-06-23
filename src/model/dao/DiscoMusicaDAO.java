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
import model.entity.Disco;
import model.entity.DiscoMusica;
import model.entity.Musica;

/**
 *
 * @author Michael
 */
public class DiscoMusicaDAO implements Serializable {

    public DiscoMusicaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DiscoMusica discoMusica) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disco disco = discoMusica.getDisco();
            if (disco != null) {
                disco = em.getReference(disco.getClass(), disco.getIdDisco());
                discoMusica.setDisco(disco);
            }
            Musica musica = discoMusica.getMusica();
            if (musica != null) {
                musica = em.getReference(musica.getClass(), musica.getIdMusica());
                discoMusica.setMusica(musica);
            }
            em.persist(discoMusica);
            if (disco != null) {
                disco.getDiscoMusicaList().add(discoMusica);
                disco = em.merge(disco);
            }
            if (musica != null) {
                musica.getDiscoMusicaList().add(discoMusica);
                musica = em.merge(musica);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DiscoMusica discoMusica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DiscoMusica persistentDiscoMusica = em.find(DiscoMusica.class, discoMusica.getIdDiscoMusica());
            Disco discoOld = persistentDiscoMusica.getDisco();
            Disco discoNew = discoMusica.getDisco();
            Musica musicaOld = persistentDiscoMusica.getMusica();
            Musica musicaNew = discoMusica.getMusica();
            if (discoNew != null) {
                discoNew = em.getReference(discoNew.getClass(), discoNew.getIdDisco());
                discoMusica.setDisco(discoNew);
            }
            if (musicaNew != null) {
                musicaNew = em.getReference(musicaNew.getClass(), musicaNew.getIdMusica());
                discoMusica.setMusica(musicaNew);
            }
            discoMusica = em.merge(discoMusica);
            if (discoOld != null && !discoOld.equals(discoNew)) {
                discoOld.getDiscoMusicaList().remove(discoMusica);
                discoOld = em.merge(discoOld);
            }
            if (discoNew != null && !discoNew.equals(discoOld)) {
                discoNew.getDiscoMusicaList().add(discoMusica);
                discoNew = em.merge(discoNew);
            }
            if (musicaOld != null && !musicaOld.equals(musicaNew)) {
                musicaOld.getDiscoMusicaList().remove(discoMusica);
                musicaOld = em.merge(musicaOld);
            }
            if (musicaNew != null && !musicaNew.equals(musicaOld)) {
                musicaNew.getDiscoMusicaList().add(discoMusica);
                musicaNew = em.merge(musicaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = discoMusica.getIdDiscoMusica();
                if (findDiscoMusica(id) == null) {
                    throw new NonexistentEntityException("The discoMusica with id " + id + " no longer exists.");
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
            DiscoMusica discoMusica;
            try {
                discoMusica = em.getReference(DiscoMusica.class, id);
                discoMusica.getIdDiscoMusica();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The discoMusica with id " + id + " no longer exists.", enfe);
            }
            Disco disco = discoMusica.getDisco();
            if (disco != null) {
                disco.getDiscoMusicaList().remove(discoMusica);
                disco = em.merge(disco);
            }
            Musica musica = discoMusica.getMusica();
            if (musica != null) {
                musica.getDiscoMusicaList().remove(discoMusica);
                musica = em.merge(musica);
            }
            em.remove(discoMusica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DiscoMusica> findDiscoMusicaEntities() {
        return findDiscoMusicaEntities(true, -1, -1);
    }

    public List<DiscoMusica> findDiscoMusicaEntities(int maxResults, int firstResult) {
        return findDiscoMusicaEntities(false, maxResults, firstResult);
    }

    private List<DiscoMusica> findDiscoMusicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DiscoMusica.class));
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

    public DiscoMusica findDiscoMusica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DiscoMusica.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiscoMusicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DiscoMusica> rt = cq.from(DiscoMusica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
