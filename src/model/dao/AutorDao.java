/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import model.entity.Musica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Autor;

/**
 *
 * @author Michael
 */
public class AutorDAO implements SpotNetDAO<Autor> {

    private EntityManagerFactory emf = null;

    public AutorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Autor autor) {
        if (autor.getMusicaList() == null) {
            autor.setMusicaList(new ArrayList<Musica>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Musica> attachedMusicaList = new ArrayList<Musica>();
            for (Musica musicaListMusicaToAttach : autor.getMusicaList()) {
                musicaListMusicaToAttach = em.getReference(musicaListMusicaToAttach.getClass(), musicaListMusicaToAttach.getIdMusica());
                attachedMusicaList.add(musicaListMusicaToAttach);
            }
            autor.setMusicaList(attachedMusicaList);
            em.persist(autor);
            for (Musica musicaListMusica : autor.getMusicaList()) {
                Autor oldAutorOfMusicaListMusica = musicaListMusica.getAutor();
                musicaListMusica.setAutor(autor);
                musicaListMusica = em.merge(musicaListMusica);
                if (oldAutorOfMusicaListMusica != null) {
                    oldAutorOfMusicaListMusica.getMusicaList().remove(musicaListMusica);
                    oldAutorOfMusicaListMusica = em.merge(oldAutorOfMusicaListMusica);
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
    public void update(Autor autor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor persistentAutor = em.find(Autor.class, autor.getIdAutor());
            List<Musica> musicaListOld = persistentAutor.getMusicaList();
            List<Musica> musicaListNew = autor.getMusicaList();
            List<Musica> attachedMusicaListNew = new ArrayList<Musica>();
            for (Musica musicaListNewMusicaToAttach : musicaListNew) {
                musicaListNewMusicaToAttach = em.getReference(musicaListNewMusicaToAttach.getClass(), musicaListNewMusicaToAttach.getIdMusica());
                attachedMusicaListNew.add(musicaListNewMusicaToAttach);
            }
            musicaListNew = attachedMusicaListNew;
            autor.setMusicaList(musicaListNew);
            autor = em.merge(autor);
            for (Musica musicaListOldMusica : musicaListOld) {
                if (!musicaListNew.contains(musicaListOldMusica)) {
                    musicaListOldMusica.setAutor(null);
                    musicaListOldMusica = em.merge(musicaListOldMusica);
                }
            }
            for (Musica musicaListNewMusica : musicaListNew) {
                if (!musicaListOld.contains(musicaListNewMusica)) {
                    Autor oldAutorOfMusicaListNewMusica = musicaListNewMusica.getAutor();
                    musicaListNewMusica.setAutor(autor);
                    musicaListNewMusica = em.merge(musicaListNewMusica);
                    if (oldAutorOfMusicaListNewMusica != null && !oldAutorOfMusicaListNewMusica.equals(autor)) {
                        oldAutorOfMusicaListNewMusica.getMusicaList().remove(musicaListNewMusica);
                        oldAutorOfMusicaListNewMusica = em.merge(oldAutorOfMusicaListNewMusica);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autor.getIdAutor();
                if (findById(id) == null) {
                    throw new NonexistentEntityException("O autor com o id " + id + " não existe.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void delete(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autor autor;
            try {
                autor = em.getReference(Autor.class, id);
                autor.getIdAutor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("O autor com o id " + id + " não existe.", enfe);
            }
            List<Musica> musicaList = autor.getMusicaList();
            for (Musica musicaListMusica : musicaList) {
                musicaListMusica.setAutor(null);
                musicaListMusica = em.merge(musicaListMusica);
            }
            em.remove(autor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Autor> findAll() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Autor.findAll");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Autor> findByNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Autor.findByNome");
            query.setParameter("nome", "%" + nome + "%");
            return (List<Autor>) query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Autor findById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }

    public List<String> findAllNome() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Autor.findAllNome");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
