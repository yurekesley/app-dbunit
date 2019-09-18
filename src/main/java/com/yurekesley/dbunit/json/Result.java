package com.yurekesley.dbunit.json;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer total_pages;
	private Integer total_results;
	private List<T> results;
	private List<T> genres;


}
