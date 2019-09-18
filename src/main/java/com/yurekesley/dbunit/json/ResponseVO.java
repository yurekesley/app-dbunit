package com.yurekesley.dbunit.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public @Data class ResponseVO {

	@NonNull
	private Integer codigo;

	@NonNull
	private String mensagem;
	private Object retorno;
}
