package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.Autor;
import model.entity.DiscoMusica;
import model.entity.PlaylistMusica;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(Musica.class)
public class Musica_ { 

    public static volatile SingularAttribute<Musica, Integer> idMusica;
    public static volatile ListAttribute<Musica, DiscoMusica> discoMusicaList;
    public static volatile SingularAttribute<Musica, String> nome;
    public static volatile ListAttribute<Musica, PlaylistMusica> playlistMusicaList;
    public static volatile SingularAttribute<Musica, Autor> autor;

}