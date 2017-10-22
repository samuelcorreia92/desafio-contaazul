package com.desafio.contaazul.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.contaazul.entities.Direction;
import com.desafio.contaazul.entities.Robot;
import com.desafio.contaazul.entities.Zone;
import com.desafio.contaazul.services.NavigationService;

@RestController
@RequestMapping("/rest/mars")
public class MarsNavigationController {

	@Autowired
	private NavigationService navigationService;

	@PostMapping("/{commands}")
	public ResponseEntity<Robot> navigate(
			@PathVariable String commands,
			@RequestParam(defaultValue = "5") int width,
			@RequestParam(defaultValue = "5") int height,
			@RequestParam(defaultValue = "0") int x,
			@RequestParam(defaultValue = "0") int y,
			@RequestParam(defaultValue = "N") char direction) {
		try {
			Zone area51 = navigationService.createZone("Area 51", width, height);
			Robot r2d2 = navigationService.addRobotToZone("R2-D2", x, y, Direction.valueByDirectionLetter(direction), area51);

			navigationService.moveRobot(r2d2, commands);
			return ResponseEntity.ok(r2d2);
		} catch (IllegalStateException | IllegalArgumentException e) {
			e.printStackTrace(); // Just to validade
			return ResponseEntity.badRequest().body(null);
		}
	}

}
