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
@Table(name = "disco_musica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiscoMusica.findAll", query = "SELECT d FROM DiscoMusica d")
    , @NamedQuery(name = "DiscoMusica.findByIdDiscoMusica", query = "SELECT d FROM DiscoMusica d WHERE d.idDiscoMusica = :idDiscoMusica")})
public class DiscoMusica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disco_musica")
    private Integer idDiscoMusica;
    @JoinColumn(name = "disco", referencedColumnName = "id_disco")
    @ManyToOne
    private Disco disco;
    @JoinColumn(name = "musica", referencedColumnName = "id_musica")
    @ManyToOne
    private Musica musica;

    public DiscoMusica() {
    }

    public DiscoMusica(Integer idDiscoMusica) {
        this.idDiscoMusica = idDiscoMusica;
    }

    public Integer getIdDiscoMusica() {
        return idDiscoMusica;
    }

    public void setIdDiscoMusica(Integer idDiscoMusica) {
        this.idDiscoMusica = idDiscoMusica;
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public Musica getMusica() {
        return musica;
    }

    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDiscoMusica != null ? idDiscoMusica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiscoMusica)) {
            return false;
        }
        DiscoMusica other = (DiscoMusica) object;
        if ((this.idDiscoMusica == null && other.idDiscoMusica != null) || (this.idDiscoMusica != null && !this.idDiscoMusica.equals(other.idDiscoMusica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entity.DiscoMusica[ idDiscoMusica=" + idDiscoMusica + " ]";
    }
    
}
