package com.desafio.contaazul.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desafio.contaazul.entities.Command;
import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;
import com.desafio.contaazul.services.NavigationService;

@Service
public class NavigationServiceImpl implements NavigationService {

	private List<Zone> zones = new ArrayList<>();

	public Zone createZone(String zoneName, int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Zone width and height must be greater than zero");
		}

		Zone zone = ZoneImpl.create(zoneName, width, height);
		zones.add(zone);
		return zone;
	}

	public Robot addRobotToZone(String robotName, int positionX, int positionY, Direction direction, Zone zone) {
		return ((ZoneImpl) zone).addRobot(robotName, positionX, positionY, direction);
	}

	public void moveRobot(Robot robot, String commands) {
		List<Command> commandList = getCommandListFromString(commands);
		for (Command command : commandList) {
			if (Command.MOVE.equals(command)) {
				moveRobot((RobotImpl) robot);
			} else {
				turnRobot((RobotImpl) robot, command);
			}
			validateRobotPosition(robot);
		}
	}

	private void moveRobot(RobotImpl robot) {
		robot.moveForward();
	}

	private void turnRobot(RobotImpl robot, Command command) {
		if (Command.RIGHT.equals(command)) {
			robot.turnRight();
		} else {
			robot.turnLeft();
		}
	}

	private void validateRobotPosition(Robot robot) {
		validateIfRobotIsOutsideZone(robot);
		validateIfRobotCrashedIntoAnother(robot);
	}

	private void validateIfRobotIsOutsideZone(Robot robot) {
		Zone zone = robot.getZone();

		if (robot.getPositionX() < 0 || robot.getPositionY() < 0 || robot.getPositionX() >= zone.getWidth()
				|| robot.getPositionY() >= zone.getHeight()) {
			throw new IllegalStateException(
					String.format("The robot %s is outside the zone area (%s)", robot.getName(), zone.getName()));
		}
	}

	private void validateIfRobotCrashedIntoAnother(Robot robot) {
		List<Robot> robots = ((ZoneImpl) robot.getZone()).getRobots();

		for (Robot robotToValidade : robots) {
			if (robotToValidade != robot) {
				if (robotToValidade.getPositionX() == robot.getPositionX()
						&& robotToValidade.getPositionY() == robot.getPositionY()) {
					throw new IllegalStateException(String.format("The robot %s crashed into robot %s in zone (%s)",
							robot.getName(), robotToValidade.getName(), robot.getZone().getName()));
				}
			}
		}
	}

	private List<Command> getCommandListFromString(String commands) {
		if (commands == null || commands.isEmpty()) {
			throw new IllegalArgumentException("Commands must not be null");
		}

		char[] commandsArray = commands.toCharArray();
		List<Command> commandList = new ArrayList<>(commandsArray.length);
		for (char command : commandsArray) {
			commandList.add(Command.valueByCommandLetter(command));
		}
		return commandList;
	}

}
