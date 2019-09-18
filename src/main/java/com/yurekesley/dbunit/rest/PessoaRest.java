package com.yurekesley.dbunit.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yurekesley.dbunit.entity.Pessoa;
import com.yurekesley.dbunit.util.RestUtil;

@RestController
@RequestMapping(value = "/pessoas", produces = RestUtil.JSON_PRODUCE)
public class PessoaRest extends CrudRestController<Pessoa, Long>{

}
