/*
 * and open the template in the editor.
 */
package model.entity;

import java.io.File;

import javax.swing.ImageIcon;

/**
 * Bean Autor
 */
public class Autor {

	private Integer id_autor;
	private String nome;
	private ImageIcon foto;
	private File selFile;
	
	public Integer getId_autor() {
		return id_autor;
	}

	public void setId_autor(Integer id_autor) {
		this.id_autor = id_autor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ImageIcon getFoto() {
		return foto;
	}

	public void setFoto(ImageIcon foto) {
		this.foto = foto;
	}

	public File getSelFile() {
		return selFile;
	}

	public void setSelFile(File selFile) {
		this.selFile = selFile;
	}

}
