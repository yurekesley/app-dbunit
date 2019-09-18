package com.yurekesley.dbunit.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.yurekesley.dbunit.entity.IBaseEntity;


public interface ICrudService<E extends IBaseEntity, ID extends Serializable> {

	public void salvar(E e);

	public boolean doSalvar(E e);
	
	public void salvar(List<E> entidades);

	public void posSalvar(E e);
	
	public void salvarFlush(E e);
	
	public boolean doDeletar(E e);

	public void posDeletar(E e);

	public void deletar(E e);

	public void deletar(ID id);

	public E consultarPor(ID id);

	public List<E> consultarPor(E e);
	
	public void posConsultarPor(List<E> entidades);

	public List<E> consultarTodos();
	
	public void posConsultarTodos(List<E> entidades);
	
	public void posFiltrar(Page<E> pagina);

	public Page<E> filtrar(Pageable paginacao, E e);
	

}
