/*
 * and open the template in the editor.
 */
package model.entity;

import javax.swing.ImageIcon;

/**
 * Entidade Autor
 */
public class Autor {

	Integer id_autor;
	String nome;
	ImageIcon foto;

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

}
