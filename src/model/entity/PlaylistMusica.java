/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Michael
 */
@Entity
@Table(name = "playlist_musica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlaylistMusica.findAll", query = "SELECT p FROM PlaylistMusica p")
    , @NamedQuery(name = "PlaylistMusica.findByIdPlaylistMusica", query = "SELECT p FROM PlaylistMusica p WHERE p.idPlaylistMusica = :idPlaylistMusica")})
public class PlaylistMusica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_playlist_musica")
    private Integer idPlaylistMusica;
    @JoinColumn(name = "musica", referencedColumnName = "id_musica")
    @ManyToOne
    private Musica musica;
    @JoinColumn(name = "playlist", referencedColumnName = "id_playlist")
    @ManyToOne
    private Playlist playlist;

    public PlaylistMusica() {
    }

    public PlaylistMusica(Integer idPlaylistMusica) {
        this.idPlaylistMusica = idPlaylistMusica;
    }

    public Integer getIdPlaylistMusica() {
        return idPlaylistMusica;
    }

    public void setIdPlaylistMusica(Integer idPlaylistMusica) {
        this.idPlaylistMusica = idPlaylistMusica;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaylistMusica != null ? idPlaylistMusica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaylistMusica)) {
            return false;
        }
        PlaylistMusica other = (PlaylistMusica) object;
        if ((this.idPlaylistMusica == null && other.idPlaylistMusica != null) || (this.idPlaylistMusica != null && !this.idPlaylistMusica.equals(other.idPlaylistMusica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entity.PlaylistMusica[ idPlaylistMusica=" + idPlaylistMusica + " ]";
    }
    
}
