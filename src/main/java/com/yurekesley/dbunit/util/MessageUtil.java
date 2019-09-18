package com.yurekesley.dbunit.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurekesley.dbunit.exception.TypeError;
import com.yurekesley.dbunit.json.ResponseVO;

public class MessageUtil {

	private static ResourceBundleMessageSource resourceBundleMessageSource;

	public static String makeMenssage(String mensagem, Locale locale, Object[] parametros) {
		try {
			if (locale == null) {
				HttpServletRequest requisicaoCorrente = RestUtil.getCurrentRequest();
				locale = Locale.getDefault();
				if (requisicaoCorrente != null && requisicaoCorrente.getLocale() != null) {
					locale = requisicaoCorrente.getLocale();
				}
			}

			return resourceBundleMessageSource.getMessage(mensagem, parametros, locale);
		} catch (NoSuchMessageException ex) {
			return mensagem;
		}
	}

	public static String makeMenssage(String message, Object... params) {
		return makeMenssage(message, null, params);
	}

	public static String montarRespostaMensagemJson(TypeError tipoError, String mensagem, Object... parametros) {
		ObjectMapper mapper = new ObjectMapper();
		String mensagemBundle = makeMenssage(mensagem, null, parametros);
		try {
			return mapper.writeValueAsString(
					ResponseVO.builder().codigo(tipoError.getCode()).mensagem(mensagemBundle).build());
		} catch (Throwable e) {
		}

		return mensagemBundle;
	}

	public static String montarRespostaMensagemJson(TypeError tipoError, String mensagem) {

		ObjectMapper mapper = new ObjectMapper();
		String mensagemBundle = makeMenssage(mensagem, null, null);
		try {
			return mapper.writeValueAsString(
					ResponseVO.builder().codigo(tipoError.getCode()).mensagem(mensagemBundle).build());
		} catch (Throwable e) {
		}

		return mensagemBundle;
	}

	public static void setResourceBundleMessageSource(ResourceBundleMessageSource resourceBundleMessageSource) {
		Assert.notNull(resourceBundleMessageSource, "msg.traducao.instancia.bundle");
		MessageUtil.resourceBundleMessageSource = resourceBundleMessageSource;
	}
}
