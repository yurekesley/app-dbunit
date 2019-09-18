package com.yurekesley.dbunit.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yurekesley.dbunit.entity.IBaseEntity;
import com.yurekesley.dbunit.exception.ValidationException;
import com.yurekesley.dbunit.util.LogUtil;

import lombok.Getter;

@Component
public abstract class CrudService<T extends IBaseEntity, ID extends Serializable>
		implements ICrudService<T, ID> {

	@Getter
	private JpaRepository<IBaseEntity, ID> repositorioGenerico;

	@Getter
	private final LogUtil logUtil;

	public CrudService() {
		logUtil = new LogUtil(this.getClass());
	}

	@Getter
	private JpaRepository<T, ID> repositorio;

	@Autowired
	@SuppressWarnings("unchecked")
	public void setRepositorio(JpaRepository<T, ID> repositorio) {
		this.repositorioGenerico = (JpaRepository<IBaseEntity, ID>) repositorio;
		this.repositorio = repositorio;
	}

	@Override
	@Transactional
	public void salvar(T e) {
		Assert.notNull(e, "msg.entidade.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.salvar", null, e.toString());
		getRepositorioGenerico().save(e);
		this.posSalvar(e);
	}

	@Override
	@Transactional
	public void salvar(List<T> entidades) {
		for (T t : entidades) {
			if (doSalvar(t)) {
				salvar(t);
			} else {
				throw new ValidationException("msg.crud.salvar.error");
			}
		}
	}

	@Override
	@Transactional
	public final void salvarFlush(T e) {
		Assert.notNull(e, "msg.entidade.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.salvar", null, e.toString());
		getRepositorioGenerico().saveAndFlush(e);
		this.posSalvar(e);
	}

	@Override
	@Transactional
	public void posSalvar(T e) {
	}

	@Override
	@Transactional
	public void posDeletar(T e) {
	}

	@Override
	@Transactional
	public void deletar(T e) {
		Assert.notNull(e, "msg.entidade.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.deletar", null, e.toString());
		getRepositorioGenerico().delete(e);
		this.posDeletar(e);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void deletar(ID id) {
		Assert.notNull(id, "msg.entidade.id.obrigatorio");
		T entidade = (T) getRepositorioGenerico().findById(id).get();
		getRepositorioGenerico().delete(entidade);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T consultarPor(ID id) {
		Assert.notNull(id, "msg.entidade.id.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.consultar", null, id);

		T entidade = (T) getRepositorioGenerico().findById(id).get();
		this.posConsultarPor(entidade);

		return entidade;
	}

	public void posConsultarPor(T entidade) {
	}

	@Override
	public List<T> consultarPor(T e) {
		Assert.notNull(e, "msg.entidade.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.consultar", null, e.toString());

		Example<T> example = criarFiltroBasico(e);
		List<T> entidades = (List<T>) getRepositorioGenerico().findAll(example);
		posConsultarPor(entidades);

		return entidades;
	}

	protected Example<T> criarFiltroBasico(T e) {
		return Example.of((T) e,
				ExampleMatcher.matchingAll()
				.withIgnoreCase()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING));
	}

	public void posConsultarPor(List<T> entidades) {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> consultarTodos() {
		List<T> entidades = (List<T>) getRepositorioGenerico().findAll();
		posConsultarTodos(entidades);
		return entidades;
	}

	public void posConsultarTodos(List<T> entidades) {
	}

	@Override
	public Page<T> filtrar(Pageable paginacao, T entidade) {
		Page<T> pagina = doFiltrar(paginacao, entidade);
		posFiltrar(pagina);
		return pagina;
	}

	public Page<T> doFiltrar(Pageable paginacao, T entidade) {
		Assert.notNull(entidade, "msg.entidade.obrigatorio");
		getLogUtil().info("msg.entidade.persistencia.consultar", null, entidade.toString());

		Example<T> filtroBasico = this.criarFiltroBasico(entidade);
		return getRepositorioGenerico().findAll(filtroBasico, paginacao);
	}

	public void posFiltrar(Page<T> pagina) {
	}

	public boolean doSalvar(T t) {
		return Boolean.TRUE;
	}

	public boolean doDeletar(T t) {
		return Boolean.TRUE;
	}

}
