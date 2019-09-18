package com.yurekesley.dbunit;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.triadworks.dbunit.DbUnitManager;
import br.com.triadworks.dbunit.dataset.ClassPathDataSetSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DbunitApplication.class, TestWebAppConfig.class })
public abstract class SpringConfigTestCase {

	@Autowired
	protected MockMvc mvc;

	@Autowired
	protected DataSource dataSource;

	@Autowired
	protected DbUnitManager dbunitManager;

	private static final Locale pt_BR = new Locale("pt", "BR");

	public static final String codUsuarioPadrao = System.getProperty("user.name");
	public static final String nomeUsuarioPadrao = "Teste";

	public static final String dataSetAllTables = "datasets/all-tables.xml";

	@Before
	public void setUp() {

		Locale.setDefault(pt_BR);
		
		this.deleteAll(dataSetAllTables);
		
	}

	public void deleteAll() {
		this.deleteAll(dataSetAllTables);
	}

	public void cleanAndInsert(String dataset) {
		this.dbunitManager.cleanAndInsert(new ClassPathDataSetSource(dataset));
	}

	public void limparDados(String dataset) {
		this.dbunitManager.deleteAll(new ClassPathDataSetSource(dataset));
	}

	public void deleteAll(String dataset) {
		this.dbunitManager.deleteAll(new ClassPathDataSetSource(dataset));
	}

	protected String dataSet(String xml) {
		return "datasets/" + xml;
	}

	protected RequestPostProcessor loggedInUser(final String username) {
		return new RequestPostProcessor() {
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setRemoteUser(username);
				return request;
			}
		};
	}

	protected RequestPostProcessor loggedInUser() {
		return loggedInUser(codUsuarioPadrao);
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	    
	    mapper.setSerializationInclusion(Include.NON_NULL);
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
	    mapper.setTimeZone(TimeZone.getTimeZone("America/Cayenne"));
	    
	    return new MappingJackson2HttpMessageConverter(mapper);
	}

	protected String writeValueObject(Object object) throws JsonProcessingException {
		return mappingJackson2HttpMessageConverter().getObjectMapper().writeValueAsString(object);
	}

	protected String writeValueObject(Object object, String encoding) throws JsonProcessingException {
		return new String(writeValueObject(object).getBytes(), Charset.forName(encoding));
	}
}
