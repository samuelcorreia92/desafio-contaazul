package com.desafio.contaazul.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Robot extends Serializable {

	String getName();

	int getPositionX();

	int getPositionY();

	Direction getDirection();

	@JsonIgnore
	Zone getZone();

}
