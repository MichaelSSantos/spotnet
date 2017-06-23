/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.entity.DiscoMusica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Disco;

/**
 *
 * @author Michael
 */
public class DiscoDAO implements SpotNetDAO<Disco> {

    private EntityManagerFactory emf = null;

    public DiscoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Disco disco) {
        if (disco.getDiscoMusicaList() == null) {
            disco.setDiscoMusicaList(new ArrayList<DiscoMusica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DiscoMusica> attachedDiscoMusicaList = new ArrayList<DiscoMusica>();
            for (DiscoMusica discoMusicaListDiscoMusicaToAttach : disco.getDiscoMusicaList()) {
                discoMusicaListDiscoMusicaToAttach = em.getReference(discoMusicaListDiscoMusicaToAttach.getClass(), discoMusicaListDiscoMusicaToAttach.getIdDiscoMusica());
                attachedDiscoMusicaList.add(discoMusicaListDiscoMusicaToAttach);
            }
            disco.setDiscoMusicaList(attachedDiscoMusicaList);
            em.persist(disco);
            for (DiscoMusica discoMusicaListDiscoMusica : disco.getDiscoMusicaList()) {
                Disco oldDiscoOfDiscoMusicaListDiscoMusica = discoMusicaListDiscoMusica.getDisco();
                discoMusicaListDiscoMusica.setDisco(disco);
                discoMusicaListDiscoMusica = em.merge(discoMusicaListDiscoMusica);
                if (oldDiscoOfDiscoMusicaListDiscoMusica != null) {
                    oldDiscoOfDiscoMusicaListDiscoMusica.getDiscoMusicaList().remove(discoMusicaListDiscoMusica);
                    oldDiscoOfDiscoMusicaListDiscoMusica = em.merge(oldDiscoOfDiscoMusicaListDiscoMusica);
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
    public void update(Disco disco) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Disco persistentDisco = em.find(Disco.class, disco.getIdDisco());
            List<DiscoMusica> discoMusicaListOld = persistentDisco.getDiscoMusicaList();
            List<DiscoMusica> discoMusicaListNew = disco.getDiscoMusicaList();
            List<DiscoMusica> attachedDiscoMusicaListNew = new ArrayList<DiscoMusica>();
            for (DiscoMusica discoMusicaListNewDiscoMusicaToAttach : discoMusicaListNew) {
                discoMusicaListNewDiscoMusicaToAttach = em.getReference(discoMusicaListNewDiscoMusicaToAttach.getClass(), discoMusicaListNewDiscoMusicaToAttach.getIdDiscoMusica());
                attachedDiscoMusicaListNew.add(discoMusicaListNewDiscoMusicaToAttach);
            }
            discoMusicaListNew = attachedDiscoMusicaListNew;
            disco.setDiscoMusicaList(discoMusicaListNew);
            disco = em.merge(disco);
            for (DiscoMusica discoMusicaListOldDiscoMusica : discoMusicaListOld) {
                if (!discoMusicaListNew.contains(discoMusicaListOldDiscoMusica)) {
                    discoMusicaListOldDiscoMusica.setDisco(null);
                    discoMusicaListOldDiscoMusica = em.merge(discoMusicaListOldDiscoMusica);
                }
            }
            for (DiscoMusica discoMusicaListNewDiscoMusica : discoMusicaListNew) {
                if (!discoMusicaListOld.contains(discoMusicaListNewDiscoMusica)) {
                    Disco oldDiscoOfDiscoMusicaListNewDiscoMusica = discoMusicaListNewDiscoMusica.getDisco();
                    discoMusicaListNewDiscoMusica.setDisco(disco);
                    discoMusicaListNewDiscoMusica = em.merge(discoMusicaListNewDiscoMusica);
                    if (oldDiscoOfDiscoMusicaListNewDiscoMusica != null && !oldDiscoOfDiscoMusicaListNewDiscoMusica.equals(disco)) {
                        oldDiscoOfDiscoMusicaListNewDiscoMusica.getDiscoMusicaList().remove(discoMusicaListNewDiscoMusica);
                        oldDiscoOfDiscoMusicaListNewDiscoMusica = em.merge(oldDiscoOfDiscoMusicaListNewDiscoMusica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = disco.getIdDisco();
                if (findById(id) == null) {
                    throw new NonexistentEntityException("O disco com o id " + id + " não existe.");
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
            Disco disco;
            try {
                disco = em.getReference(Disco.class, id);
                disco.getIdDisco();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("O disco com o id " + id + " não existe.", enfe);
            }
            List<DiscoMusica> discoMusicaList = disco.getDiscoMusicaList();
            for (DiscoMusica discoMusicaListDiscoMusica : discoMusicaList) {
                discoMusicaListDiscoMusica.setDisco(null);
                discoMusicaListDiscoMusica = em.merge(discoMusicaListDiscoMusica);
            }
            em.remove(disco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Disco> findAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Disco.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Disco findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Disco.findByNome");
            query.setParameter("nome", nome);
            return (Disco) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public Disco findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disco.class, id);
        } finally {
            em.close();
        }
    }

//    public List<Disco> findDiscoEntities() {
//        return findDiscoEntities(true, -1, -1);
//    }
//
//    public List<Disco> findDiscoEntities(int maxResults, int firstResult) {
//        return findDiscoEntities(false, maxResults, firstResult);
//    }
//
//    private List<Disco> findDiscoEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Disco.class));
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
//    public int getDiscoCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Disco> rt = cq.from(Disco.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
}
