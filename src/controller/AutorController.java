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
import model.dao.AutorDAO;
import model.entity.Autor;

/**
 *
 * @author contdiego
 */
public class AutorController {
    AutorDAO objDAO=new AutorDAO();
    List<Autor> autores = new ArrayList();
    public List<Autor> BuscaAutor(String nome)
    {
        return objDAO.BuscaAutor(nome);
    }
    
    
     public void InsereAutor(String nome, File selFile) throws SQLException
    {
         objDAO.InsereAutor(nome, selFile);
    }
     
     
       public void AlteraAutor(String nome, Autor objAutor) throws SQLException
    {
         objDAO.AlteraAutor(nome,objAutor);
    }
       
    public void ExcluiAutor(Autor objAutor) throws SQLException
    {
         objDAO.ExcluiAutor(objAutor);
    }
       
       
     
     
    public void ImportaAutores(BufferedReader br) throws FileNotFoundException, IOException
    {        
        objDAO.ImportaAutores(br);
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
