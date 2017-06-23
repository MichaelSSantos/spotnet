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
import model.dao.UsuarioDAO;
import model.dao.exceptions.NonexistentEntityException;
import model.entity.Usuario;

/**
 *
 * @author contdiego
 */
public class UsuarioController {

    UsuarioDAO objDAO = new UsuarioDAO(Persistence.createEntityManagerFactory("SpotNetPU"));
    List<Usuario> autores = new ArrayList();

    public List<Usuario> BuscaUsuario(String nome) {
        return objDAO.findByNome(nome);
    }

    public void InsereUsuario(Usuario autor) throws SQLException {
        objDAO.create(autor);
    }

    public void AlteraUsuario(Usuario objUsuario) throws SQLException {
        try {
            objDAO.update(objUsuario);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ExcluiUsuario(Usuario objUsuario) throws SQLException {
        try {
            objDAO.delete(objUsuario.getIdUsuario());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ImportaUsuarioes(BufferedReader br) throws FileNotFoundException, IOException {
        //objDAO.ImportaUsuarioes(br);
    }

}
