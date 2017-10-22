package com.desafio.contaazul.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavigationServiceTests {

	@Autowired
	private NavigationService service;

	@Test
	public void createZoneAndRobotThanMoveRobotOK() {
		Zone zone = service.createZone("area 51", 5, 5);
		Assert.assertEquals(5, zone.getWidth());
		Assert.assertEquals(5, zone.getHeight());
		Assert.assertTrue(zone.getRobots().isEmpty());

		Robot robot = service.addRobotToZone("R2-D2", 0, 0, Direction.NORTH, zone);
		Assert.assertEquals(0, robot.getPositionX());
		Assert.assertEquals(0, robot.getPositionY());
		Assert.assertEquals(Direction.NORTH, robot.getDirection());
		Assert.assertEquals(zone, robot.getZone());

		service.moveRobot(robot, "MMMMRMMRMMRML");
		Assert.assertEquals(1, robot.getPositionX());
		Assert.assertEquals(2, robot.getPositionY());
		Assert.assertEquals(Direction.SOUTH, robot.getDirection());
		Assert.assertEquals(zone, robot.getZone());
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnCreateZoneWithInvalidWidth() {
		service.createZone("area 51", 0, 5);
		Assert.fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnCreateZoneWithInvalidHeight() {
		service.createZone("area 51", 10, -1);
		Assert.fail();
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnSendNullCommand() {
		Zone zone = service.createZone("Zone", 5, 5);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.NORTH, zone);
		service.moveRobot(robot, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnSendEmptyCommand() {
		Zone zone = service.createZone("Zone", 5, 5);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.NORTH, zone);
		service.moveRobot(robot, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnSendInexistentCommand() {
		Zone zone = service.createZone("Zone", 5, 5);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.NORTH, zone);
		service.moveRobot(robot, "MMLA");
	}

	@Test(expected = IllegalStateException.class)
	public void failOnRobotGetOutsideZoneCoordinateXLeft() {
		Zone zone = service.createZone("Zone", 2, 2);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.WEST, zone);
		service.moveRobot(robot, "M");
	}

	@Test(expected = IllegalStateException.class)
	public void failOnRobotGetOutsideZoneCoordinateXRight() {
		Zone zone = service.createZone("Zone", 2, 2);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.EAST, zone);
		service.moveRobot(robot, "MM");
	}

	@Test(expected = IllegalStateException.class)
	public void failOnRobotGetOutsideZoneCoordinateYBottom() {
		Zone zone = service.createZone("Zone", 2, 2);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.SOUTH, zone);
		service.moveRobot(robot, "M");
	}

	@Test(expected = IllegalStateException.class)
	public void failOnRobotGetOutsideZoneCoordinateYTop() {
		Zone zone = service.createZone("Zone", 2, 2);
		Robot robot = service.addRobotToZone("Robot", 0, 0, Direction.NORTH, zone);
		service.moveRobot(robot, "MM");
	}

	@Test(expected = IllegalArgumentException.class)
	public void failOnAddTwoRobotsInSameCoordinate() {
		Zone zone = service.createZone("Zone", 5, 5);
		service.addRobotToZone("Robot 1", 0, 0, Direction.NORTH, zone);
		service.addRobotToZone("Robot 2", 2, 4, Direction.SOUTH, zone);
		service.addRobotToZone("Robot 3", 4, 4, Direction.WEST, zone);
		service.addRobotToZone("Robot 4", 0, 0, Direction.EAST, zone);
	}

	@Test
	public void robotOnSameCoordinateButInDifferentZone() {
		Zone zone1 = service.createZone("Zone 1", 4, 3);
		service.addRobotToZone("Robot 1", 0, 0, Direction.SOUTH, zone1);

		Zone zone2 = service.createZone("Zone 2", 4, 3);
		service.addRobotToZone("Robot 2", 0, 0, Direction.SOUTH, zone2);
	}

}
