/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "musica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Musica.findAll", query = "SELECT m FROM Musica m")
    , @NamedQuery(name = "Musica.findByIdMusica", query = "SELECT m FROM Musica m WHERE m.idMusica = :idMusica")
    , @NamedQuery(name = "Musica.findByNome", query = "SELECT m FROM Musica m WHERE m.nome = :nome")})
public class Musica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_musica")
    private Integer idMusica;
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "autor", referencedColumnName = "id_autor")
    @ManyToOne
    private Autor autor;
    @OneToMany(mappedBy = "musica")
    private List<PlaylistMusica> playlistMusicaList;
    @OneToMany(mappedBy = "musica")
    private List<DiscoMusica> discoMusicaList;

    public Musica() {
    }

    public Musica(Integer idMusica) {
        this.idMusica = idMusica;
    }

    public Integer getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(Integer idMusica) {
        this.idMusica = idMusica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @XmlTransient
    public List<PlaylistMusica> getPlaylistMusicaList() {
        return playlistMusicaList;
    }

    public void setPlaylistMusicaList(List<PlaylistMusica> playlistMusicaList) {
        this.playlistMusicaList = playlistMusicaList;
    }

    @XmlTransient
    public List<DiscoMusica> getDiscoMusicaList() {
        return discoMusicaList;
    }

    public void setDiscoMusicaList(List<DiscoMusica> discoMusicaList) {
        this.discoMusicaList = discoMusicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMusica != null ? idMusica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Musica)) {
            return false;
        }
        Musica other = (Musica) object;
        if ((this.idMusica == null && other.idMusica != null) || (this.idMusica != null && !this.idMusica.equals(other.idMusica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entity.Musica[ idMusica=" + idMusica + " ]";
    }
    
}
