/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.io.Serializable;
import java.util.List;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Michael
 */
public interface SpotNetDAO<E> extends Serializable {
    
    void create(E entity);
    void update(E entity) throws NonexistentEntityException, Exception;
    void delete(Integer id) throws NonexistentEntityException;
    List<E> findAll();
    E findById(Integer id);
    
}
