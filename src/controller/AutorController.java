/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import model.dao.AutorDAO;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Autor;

/**
 *
 * @author contdiego
 */
public class AutorController {

    AutorDAO objDAO = new AutorDAO(Persistence.createEntityManagerFactory("SpotNetPU"));
    List<Autor> autores = new ArrayList();

    public List<Autor> BuscaAutor(String nome) {
        return objDAO.findByNome(nome);
    }

    public void InsereAutor(Autor autor) throws SQLException {
        objDAO.create(autor);
    }

    public void AlteraAutor(Autor objAutor) throws SQLException {
        try {
            objDAO.update(objAutor);
        } catch (Exception ex) {
            Logger.getLogger(AutorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExcluiAutor(Autor objAutor) throws SQLException {
        try {
            objDAO.delete(objAutor.getIdAutor());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AutorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ImportaAutores(BufferedReader br) throws FileNotFoundException, IOException {
        //objDAO.ImportaAutores(br);
    }

    public List<String> BuscaAutorCombo() {
        return objDAO.findAllNome();
    }

}
