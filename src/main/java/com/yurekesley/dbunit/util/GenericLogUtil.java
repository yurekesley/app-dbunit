package com.yurekesley.dbunit.util;

import org.apache.log4j.Logger;

public abstract class GenericLogUtil {

	private final Logger logger;

	public GenericLogUtil(Class<?> clazz) {
		logger = Logger.getLogger(clazz);
	}

	public void error(String message, Throwable ex, Object... params) {
		String messageProcessed = MessageUtil.makeMenssage(message, params);

		if (ex != null) {
			logger.error(messageProcessed, ex);
		} else {
			logger.error(messageProcessed);
		}
	}

	public void info(String mensagem) {
		this.info(mensagem, null);
	}

	public void info(String message, Throwable ex, Object... params) {
		String mensagemProcessada = MessageUtil.makeMenssage(message, params);

		if (ex != null) {
			logger.info(mensagemProcessada, ex);
		} else {
			logger.info(mensagemProcessada);
		}
	}

	public void debug(String message, Throwable ex, Object... params) {
		String mensagemProcessada = MessageUtil.makeMenssage(message, params);

		if (ex != null) {
			logger.debug(mensagemProcessada, ex);
		} else {
			logger.debug(mensagemProcessada);
		}
	}

	public void warning(String message, Throwable ex, Object... params) {
		String mensagemProcessada = MessageUtil.makeMenssage(message, params);

		if (ex != null) {
			logger.warn(mensagemProcessada, ex);
		} else {
			logger.warn(mensagemProcessada);
		}
	}

	public void fatal(String message, Throwable ex, Object... params) {
		String mensagemProcessada = MessageUtil.makeMenssage(message, params);

		if (ex != null) {
			logger.fatal(mensagemProcessada, ex);
		} else {
			logger.fatal(mensagemProcessada);
		}
	}
}
