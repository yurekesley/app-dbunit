package com.yurekesley.dbunit.rest;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yurekesley.dbunit.entity.IBaseEntity;
import com.yurekesley.dbunit.json.Json;
import com.yurekesley.dbunit.json.JsonResult;
import com.yurekesley.dbunit.service.ICrudService;
import com.yurekesley.dbunit.util.MessageUtil;

import lombok.Getter;

public abstract class CrudRestController<T extends IBaseEntity , ID extends Serializable> {
	
	@Getter
	@Autowired
	private ICrudService<T, ID> servico;
	
	@PostMapping(value="/salvar")
	public JsonResult salvar(@Valid @RequestBody T t) {
		
		if (!getServico().doSalvar(t)) {
			throw new ValidationException("msg.crud.salvar.error");
		}
		
		this.getServico().salvar(t);
		
		return Json.success()
				.withMessage(i18n("msg.crud.salvar.sucesso"))
				.withData(posSalvar(t))
				.build();
	}
	
	@PutMapping(value="/atualizar")
	public JsonResult atualizar(@Valid @RequestBody T t) {
		
		if (!getServico().doSalvar(t)) {
			throw new ValidationException("msg.crud.salvar.error");
		}
		
		this.getServico().salvar(t);
		
		return Json.success()
				.withMessage(i18n("msg.crud.salvar.sucesso"))
				.withData(posSalvar(t))
				.build();
	}
	

	public Object posSalvar(T t) {
		return t;
	}
	
	@PostMapping(value="/salvar/lista")
	public JsonResult salvarLista(@Valid @RequestBody List<T> entidades) {
		
		if (entidades == null || entidades.isEmpty()) {
			return Json.fail()
					.withMessage(i18n("msg.crud.salvar.lista.vazia"))
					.withData(posSalvar(entidades))
					.build();
		}

		this.getServico().salvar(entidades);
		
		return Json.success()
				.withMessage(i18n("msg.crud.salvar.lista.sucesso"))
				.withData(posSalvar(entidades))
				.build();
	}
	
	@PutMapping(value="/arualizar/lista")
	public JsonResult updateList(@Valid @RequestBody List<T> entidades) {
		
		if (entidades == null || entidades.isEmpty()) {
			return Json.fail()
					.withMessage(i18n("msg.crud.salvar.lista.vazia"))
					.withData(posSalvar(entidades))
					.build();
		}
		
		this.getServico().salvar(entidades);
		
		return Json.success()
				.withMessage(i18n("msg.crud.salvar.lista.sucesso"))
				.withData(posSalvar(entidades))
				.build();
	}
	
	public Object posSalvar(List<T> entidades) {
		return entidades;
	}

	@DeleteMapping(value="/deletar/{id}")
	public JsonResult deletar(@PathVariable(value="id") ID id) {
		
		T t = this.getServico().consultarPor(id);
		
		if (!getServico().doDeletar(t)) {
			throw new ValidationException("msg.crud.deletar.error");
		}
		
		this.getServico().deletar(t);

		return Json.success()
				.withMessage(i18n("msg.crud.deletar.sucesso"))
				.build();
	}
	
	@PostMapping(value="/deletar")
	public JsonResult deletar(@RequestBody T t) {
		
		if (!getServico().doDeletar(t)) {
			throw new ValidationException("msg.crud.deletar.error");
		}
		
		this.getServico().deletar(t);
		
		return Json.success()
				.withMessage(i18n("msg.crud.deletar.sucesso"))
				.build();
	}
	
	@GetMapping(value="/consultar/{id}")
	public JsonResult consultarPorId(@PathVariable(value = "id") ID id) {
		
		T entidade = this.getServico().consultarPor(id);
		
		return Json.success()
				.withData(entidade)
				.build();
	}
	
	@PostMapping(value="/consultar")
	public JsonResult consultarPor(@RequestBody T e) {
		
		List<T> entidades = this.getServico().consultarPor(e);
		
		return Json.success()
				.withData(entidades)
				.build();
	}
	
	@GetMapping(value="/consultar/todos")
	public JsonResult consultarTodos() {
		
		List<T> todas = this.getServico().consultarTodos();
		
		return Json.success()
				.withData(todas)
				.build();
	}
	
	@PostMapping(value="/consultar/paginado")
	public JsonResult consultarPaginado(@RequestBody T entidade, Pageable pageable) {
		
		Page<T> page = this.getServico().filtrar(pageable, entidade);
		
		return Json.success()
				.withData(page)
				.build();
	}
	
	public String i18n(String key, Object...params) {
		String message = MessageUtil.makeMenssage(key, params);
		return message;
	}
}