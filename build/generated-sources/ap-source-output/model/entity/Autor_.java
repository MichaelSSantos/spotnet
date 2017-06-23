package model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.entity.Musica;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-23T18:12:41")
@StaticMetamodel(Autor.class)
public class Autor_ { 

    public static volatile SingularAttribute<Autor, byte[]> foto;
    public static volatile SingularAttribute<Autor, Integer> idAutor;
    public static volatile ListAttribute<Autor, Musica> musicaList;
    public static volatile SingularAttribute<Autor, String> nome;

}