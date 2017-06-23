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
@Table(name = "disco")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disco.findAll", query = "SELECT d FROM Disco d")
    , @NamedQuery(name = "Disco.findByIdDisco", query = "SELECT d FROM Disco d WHERE d.idDisco = :idDisco")
    , @NamedQuery(name = "Disco.findByNome", query = "SELECT d FROM Disco d WHERE d.nome = :nome")})
public class Disco implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_disco")
    private Integer idDisco;
    @Column(name = "nome")
    private String nome;
    @OneToMany(mappedBy = "disco")
    private List<DiscoMusica> discoMusicaList;

    public Disco() {
    }

    public Disco(Integer idDisco) {
        this.idDisco = idDisco;
    }

    public Integer getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(Integer idDisco) {
        this.idDisco = idDisco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        hash += (idDisco != null ? idDisco.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disco)) {
            return false;
        }
        Disco other = (Disco) object;
        if ((this.idDisco == null && other.idDisco != null) || (this.idDisco != null && !this.idDisco.equals(other.idDisco))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.entity.Disco[ idDisco=" + idDisco + " ]";
    }
    
}
