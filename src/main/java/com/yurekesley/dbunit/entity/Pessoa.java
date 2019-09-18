package com.yurekesley.dbunit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "PESSOAS")
public @Data class Pessoa extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public Pessoa(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa_id")
	@SequenceGenerator(sequenceName = "seq_pessoa_id", allocationSize = 1, name = "seq_pessoa_id")
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cpf;
	
}
