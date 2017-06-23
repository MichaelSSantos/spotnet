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
@Table(name = "playlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p")
    , @NamedQuery(name = "Playlist.findByIdPlaylist", query = "SELECT p FROM Playlist p WHERE p.idPlaylist = :idPlaylist")
    , @NamedQuery(name = "Playlist.findByNome", query = "SELECT p FROM Playlist p WHERE p.nome = :nome")})
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_playlist")
    private Integer idPlaylist;
    @Column(name = "nome")
    private String nome;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "playlist")
    private List<PlaylistMusica> playlistMusicaList;

    public Playlist() {
    }

    public Playlist(Integer idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public Integer getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Integer idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<PlaylistMusica> getPlaylistMusicaList() {
        return playlistMusicaList;
    }

    public void setPlaylistMusicaList(List<PlaylistMusica> playlistMusicaList) {
        this.playlistMusicaList = playlistMusicaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaylist != null ? idPlaylist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.idPlaylist == null && other.idPlaylist != null) || (this.idPlaylist != null && !this.idPlaylist.equals(other.idPlaylist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entity.Playlist[ idPlaylist=" + idPlaylist + " ]";
    }
    
}
