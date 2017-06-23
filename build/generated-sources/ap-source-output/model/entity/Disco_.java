package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.DiscoMusica;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(Disco.class)
public class Disco_ { 

    public static volatile ListAttribute<Disco, DiscoMusica> discoMusicaList;
    public static volatile SingularAttribute<Disco, String> nome;
    public static volatile SingularAttribute<Disco, Integer> idDisco;

}