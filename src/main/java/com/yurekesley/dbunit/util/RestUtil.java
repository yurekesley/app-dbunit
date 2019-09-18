package com.yurekesley.dbunit.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RestUtil {

	public static final String JSON_PRODUCE = "application/json; charset=utf-8";
	public static final String OCTET_PRODUCE = "application/octet-stream";
	public static final String COD_APP = "ARCTOUCH CHALENG";

	public static HttpServletRequest getCurrentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request;
		}

		return null;
	}
}
