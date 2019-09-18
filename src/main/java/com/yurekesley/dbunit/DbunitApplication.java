package com.yurekesley.dbunit;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yurekesley.dbunit.util.LogUtil;
import com.yurekesley.dbunit.util.MessageUtil;

@SpringBootApplication
@EnableTransactionManagement
public class DbunitApplication implements CommandLineRunner {

	private static final LogUtil logger = new LogUtil(DbunitApplication.class);

	@Autowired
	private ResourceBundleMessageSource resourceBundle;

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper mapper) {
		return new MappingJackson2HttpMessageConverter(mapper);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		mapper.setSerializationInclusion(Include.NON_NULL);

		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(DbunitApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Locale.setDefault(new Locale("pt", "BR"));
		MessageUtil.setResourceBundleMessageSource(resourceBundle);
		try {
		} catch (Throwable ex) {
			logger.error("Não foi possível atualizar a lista", ex);
		}
	}

}
