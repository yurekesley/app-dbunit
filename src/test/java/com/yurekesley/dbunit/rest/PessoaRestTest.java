package com.yurekesley.dbunit.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.yurekesley.dbunit.SpringConfigTestCase;
import com.yurekesley.dbunit.entity.Pessoa;

public class PessoaRestTest extends SpringConfigTestCase {

	private static final String PATH_DATASET_PESSOA = "pessoas/";

	@Test
	public void deveSalvarEntidade() throws Exception {
		
		Pessoa pessoa = new Pessoa("Yure Kesley", "04338166355");
		
		mvc.perform(post("/pessoas/salvar").contentType(MediaType.APPLICATION_JSON)
				.content(writeValueObject(pessoa))).andExpect(status().isOk())
				.andExpect(jsonPath("message").value("msg.crud.salvar.sucesso"))
				.andExpect(jsonPath("data.id").isNotEmpty());
		
	}
	
	
	@Test
	public void deveConsultarPorId() throws Exception {

		this.cleanAndInsert(dataSet(PATH_DATASET_PESSOA + "dataset-pessoas-deveConsultarPorId.xml"));

		mvc.perform(get("/pessoas/consultar/2")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("data.nome").value("John Petrucci"));
	}
	
	@Test
	public void deveDeletarPorId() throws Exception {
		this.cleanAndInsert(dataSet(PATH_DATASET_PESSOA + "dataset-pessoas-deveDeletarPorId.xml"));
		
		mvc.perform(delete("/pessoas/deletar/2")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("message").value("msg.crud.deletar.sucesso"));
	}
	
	@Test
	public void deveEditarEntidade() throws Exception {
		
		this.cleanAndInsert(dataSet(PATH_DATASET_PESSOA + "dataset-pessoas-deveEditarPorId.xml"));

		Pessoa pessoa = new Pessoa("José da Silva Sauro", "04338166355");
		pessoa.setId(2l);

		mvc.perform(put("/pessoas/atualizar").contentType(MediaType.APPLICATION_JSON)
				.with(loggedInUser())
				.content(writeValueObject(pessoa)))
				.andExpect(status().isOk()).andExpect(jsonPath("message").value("msg.crud.salvar.sucesso"))
				.andExpect(jsonPath("data.id").value(pessoa.getId()))
				.andExpect(jsonPath("data.nome").value(pessoa.getNome()));
	}
}
