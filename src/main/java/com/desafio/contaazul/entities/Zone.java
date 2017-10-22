package com.desafio.contaazul.entities;

import java.io.Serializable;
import java.util.List;

public interface Zone extends Serializable {

	String getName();

	int getWidth();

	int getHeight();

	List<Robot> getRobots();

}
