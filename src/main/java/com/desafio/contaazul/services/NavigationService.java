package com.desafio.contaazul.services;

import org.springframework.stereotype.Service;

import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;

@Service
public interface NavigationService {

	Zone createZone(String zoneName, int width, int height);

	Robot addRobotToZone(String robotName, int positionX, int positionY, Direction direction, Zone zone);

	void moveRobot(Robot robot, String commands);

}
