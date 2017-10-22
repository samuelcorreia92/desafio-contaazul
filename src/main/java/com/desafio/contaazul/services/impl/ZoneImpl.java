package com.desafio.contaazul.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;

class ZoneImpl implements Zone {

	private static final long serialVersionUID = 1L;

	private String name;
	private int width;
	private int height;
	private List<Robot> robots;

	private ZoneImpl(String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.robots = new ArrayList<>();
	}

	static ZoneImpl create(String name, int width, int height) {
		return new ZoneImpl(name, width, height);
	}

	Robot addRobot(String robotName, int positionX, int positionY, Direction direction) {
		if (hasRobotOnPosition(positionX, positionY)) {
			throw new IllegalArgumentException("Already has another robot in this position");
		}

		Robot robot = RobotImpl.create(robotName, positionX, positionY, direction, this);
		robots.add(robot);
		return robot;
	}

	private boolean hasRobotOnPosition(int positionX, int positionY) {
		boolean hasRobotOnPosition = false;
		List<Robot> robotsOfZone = getRobots();
		for (Robot robot : robotsOfZone) {
			if (robot.getPositionX() == positionX && robot.getPositionY() == positionY) {
				hasRobotOnPosition = true;
				break;
			}
		}
		return hasRobotOnPosition;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<Robot> getRobots() {
		return robots;
	}

}
