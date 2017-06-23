package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.Musica;
import model.entity.Playlist;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(PlaylistMusica.class)
public class PlaylistMusica_ { 

    public static volatile SingularAttribute<PlaylistMusica, Integer> idPlaylistMusica;
    public static volatile SingularAttribute<PlaylistMusica, Playlist> playlist;
    public static volatile SingularAttribute<PlaylistMusica, Musica> musica;

}