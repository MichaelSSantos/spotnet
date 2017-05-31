package model.dao;

import java.util.List;

public interface Dao<E> {

	List<E> buscar(E entity);

	void alterar(E entity);

	void excluir(E entity);

	void inserir(E entity);
}
