package com.yurekesley.dbunit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TypeError {
	
	GENERIC(0), INFO(1), WARNING(2), DANGER (3);
	
	@Getter
	private final int code;
	
}

