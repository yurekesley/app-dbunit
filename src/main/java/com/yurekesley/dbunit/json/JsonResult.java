package com.yurekesley.dbunit.json;

import lombok.Data;

@Data
public class JsonResult {

	private Status status;
	private Object data;
	private String message;
	private Integer code;
	

}
