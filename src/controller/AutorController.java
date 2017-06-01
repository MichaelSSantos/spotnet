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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.dao.AutorDao;
import model.dao.Dao;
import model.entity.Autor;

/**
 * Classe de controle de autor
 *
 */
public class AutorController extends Controller<Autor>{
	
    List<Autor> autores = new ArrayList<>();
    Autor autor = new Autor();
    
    public AutorController() {
    	dao = new AutorDao();
	}
    
    @Override
    void alterar(Autor entity) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    List<Autor> buscar(Autor entity) {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    void excluir(Autor entity) {
    	// TODO Auto-generated method stub
    	
    }
    
    @Override
    void inserir(Autor entity) {
    	// TODO Auto-generated method stub
    	
    }
    
    public List<Autor> BuscaAutor(String nome)
    {
//        return objDAO.BuscaAutor(nome);
    	
    	autor.setNome(nome);
        return dao.buscar(autor);
    }
    
    
     public void InsereAutor(String nome, File selFile) throws SQLException
    {
       /*  objDAO.InsereAutor(nome, selFile);*/
    	//TODO em manutenção
    }
     
     
       public void AlteraAutor(String nome, Autor objAutor) throws SQLException
    {
//         objDAO.AlteraAutor(nome,objAutor);
         //TODO em manutenção
    }
       
    public void ExcluiAutor(Autor objAutor) throws SQLException
    {
//         objDAO.ExcluiAutor(objAutor);
         //TODO em manutenção
    }
       
       
     
     
    public void ImportaAutores(BufferedReader br) throws FileNotFoundException, IOException
    {        
//        objDAO.ImportaAutores(br);
        //TODO em manutenção
    }
    
    
    public void playSound() {
    try
    {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\contdiego\\Desktop\\infnet\\projetoIntegrado1\\JavaApplication42\\cow.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
   catch(Exception ex)
   {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
    
    
    
    
}
    
    
     
    
}
