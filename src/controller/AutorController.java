/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import model.dao.AutorDao;
import model.entity.Autor;

/**
 * Classe de controle de autor
 *
 */
public class AutorController extends Controller<Autor>{
	
    List<Autor> autores = new ArrayList<>();
    
    public AutorController() {
    	dao = new AutorDao();
	}
    
    @Override
	public List<Autor> buscar(Autor autor) {
        return dao.buscar(autor);
    }
    
    @Override
	public void alterar(Autor autor) {
    	dao.alterar(autor);
	}

	@Override
	public void excluir(Autor autor) {
		dao.excluir(autor);
	}

	@Override
	public void inserir(Autor autor) {
		dao.inserir(autor);
	}
    
    public void importarAutores(BufferedReader br) {        
    	//dao.importarAutores(br);
    }
    
    public void playSound() {
    
    	try {
        
    		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
        		new File("C:\\Users\\contdiego\\Desktop\\infnet\\projetoIntegrado1\\JavaApplication42\\cow.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
    
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
    }
    
}
