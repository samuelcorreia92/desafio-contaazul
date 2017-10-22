package com.desafio.contaazul.services.impl;

import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;

class RobotImpl implements Robot {

	private static final long serialVersionUID = 1L;

	private String name;
	private int positionX;
	private int positionY;
	private Direction direction;
	private Zone zone;

	private RobotImpl(String name, int positionX, int positionY, Direction direction, Zone zone) {
		this.name = name;
		this.positionX = positionX;
		this.positionY = positionY;
		this.direction = direction;
		this.zone = zone;
	}

	static Robot create(String name, int positionX, int positionY, Direction direction, Zone zone) {
		return new RobotImpl(name, positionX, positionY, direction, zone);
	}

	void moveForward() {
		switch (direction) {
		case NORTH:
			positionY++;
			break;
		case SOUTH:
			positionY--;
			break;
		case EAST:
			positionX++;
			break;
		case WEST:
			positionX--;
			break;
		}
	}

	void turnLeft() {
		direction = direction.getLeftDirection();
	}

	void turnRight() {
		direction = direction.getRightDiretion();
	}

	public String getName() {
		return name;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public Direction getDirection() {
		return direction;
	}

	public Zone getZone() {
		return zone;
	}

}
