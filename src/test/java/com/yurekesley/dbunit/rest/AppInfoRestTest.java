package com.yurekesley.dbunit.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.yurekesley.dbunit.SpringConfigTestCase;

public class AppInfoRestTest extends SpringConfigTestCase {
	
	@Test
	public void deveRetornarInformacoesDoSistema() throws Exception {

		mvc.perform(get("/app/info")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());		
	}

}