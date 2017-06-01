package controller;

import java.util.List;

import model.dao.Dao;

public abstract class Controller<E> {

	protected Dao<E> dao;
	
	abstract List<E> buscar(E entity);

	abstract void alterar(E entity);

	abstract void excluir(E entity);

	abstract void inserir(E entity);

}
