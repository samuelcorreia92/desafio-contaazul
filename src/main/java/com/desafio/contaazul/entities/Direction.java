package com.desafio.contaazul.entities;

public enum Direction {

	NORTH('N'), SOUTH('S'), EAST('E'), WEST('W');

	static {
		NORTH.leftDirection = WEST;
		NORTH.rightDiretion = EAST;

		SOUTH.leftDirection = EAST;
		SOUTH.rightDiretion = WEST;

		EAST.leftDirection = NORTH;
		EAST.rightDiretion = SOUTH;

		WEST.leftDirection = SOUTH;
		WEST.rightDiretion = NORTH;
	}

	private char directionLetter;
	private Direction leftDirection;
	private Direction rightDiretion;

	private Direction(char directionLetter) {
		this.directionLetter = directionLetter;
	}

	public char getDirectionLetter() {
		return directionLetter;
	}

	public Direction getLeftDirection() {
		return leftDirection;
	}

	public Direction getRightDiretion() {
		return rightDiretion;
	}

	public static Direction valueByDirectionLetter(char directionLetter) {
		for (Direction direction : values()) {
			if (Character.toUpperCase(direction.getDirectionLetter()) == Character.toUpperCase(directionLetter)) {
				return direction;
			}
		}

		throw new IllegalArgumentException("There is no direction with this letter");
	}

}
