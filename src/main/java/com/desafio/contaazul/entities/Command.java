package com.desafio.contaazul.entities;

public enum Command {

	LEFT('L'), RIGHT('R'), MOVE('M');

	private char commandLetter;

	private Command(char commandLetter) {
		this.commandLetter = commandLetter;
	}

	public char getCommandLetter() {
		return commandLetter;
	}

	public static Command valueByCommandLetter(char commandLetter) {
		for (Command command : values()) {
			if (Character.toUpperCase(command.getCommandLetter()) == Character.toUpperCase(commandLetter)) {
				return command;
			}
		}

		throw new IllegalArgumentException("There is no command with this letter");
	}

}
