package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.PlaylistMusica;
import model.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(Playlist.class)
public class Playlist_ { 

    public static volatile SingularAttribute<Playlist, Integer> idPlaylist;
    public static volatile SingularAttribute<Playlist, String> nome;
    public static volatile SingularAttribute<Playlist, Usuario> usuario;
    public static volatile ListAttribute<Playlist, PlaylistMusica> playlistMusicaList;

}