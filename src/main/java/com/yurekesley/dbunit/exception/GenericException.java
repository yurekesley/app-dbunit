package com.yurekesley.dbunit.exception;

import com.yurekesley.dbunit.util.LogUtil;
import com.yurekesley.dbunit.util.MessageUtil;

import lombok.Getter;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final static LogUtil logUtil = new LogUtil(GenericException.class);	

	@Getter
	private Object[] params;

	private TypeError typeError = TypeError.GENERIC;

	public GenericException() {
		super();
	}

	public GenericException(TypeError typeError, String message) {
		super(message);
		this.typeError = typeError;
		logUtil.info(message, this);
	}

	public GenericException(TypeError typeError, String message, Object... params) {
		this(typeError, message);
		this.params = params;
		logUtil.info(message, this, params);
	}

	public int getCodigo() {
		return typeError.getCode();
	}

	@Override
	public String getLocalizedMessage() {
		return this.getMensagem();
	}

	public String getMensagem() {
		try {
			return MessageUtil.makeMenssage(this.getMessage(), this.getParams());
		} catch (Throwable ex) {
			return this.getMessage();
		}
	}

}
