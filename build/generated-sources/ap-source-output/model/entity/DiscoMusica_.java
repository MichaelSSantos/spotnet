package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.Disco;
import model.entity.Musica;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(DiscoMusica.class)
public class DiscoMusica_ { 

    public static volatile SingularAttribute<DiscoMusica, Disco> disco;
    public static volatile SingularAttribute<DiscoMusica, Integer> idDiscoMusica;
    public static volatile SingularAttribute<DiscoMusica, Musica> musica;

}