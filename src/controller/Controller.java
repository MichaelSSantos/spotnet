package controller;

import java.util.List;

import model.dao.Dao;

public abstract class Controller<E> {

	protected Dao<E> dao;
	
	public abstract List<E> buscar(E entity);

	public abstract void alterar(E entity);

	public abstract void excluir(E entity);

	public abstract void inserir(E entity);

}
