package com.cibertec.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.auth.AuthService;
import com.cibertec.auth.UpdateRequest;
import com.cibertec.model.Role;
import com.cibertec.model.User;
import com.cibertec.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alumnos")
@CrossOrigin("*")
public class AlumnoController {

	@Autowired
	private final UserRepository repo;
	private final AuthService authService;

	@GetMapping
	public ResponseEntity<Object> getAllAlumnos() {
		List<User> alumnos = repo.findByRole(Role.STUDENT);
		if (!alumnos.isEmpty()) {
			return ResponseEntity.ok(alumnos);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron alumnos.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getAlumno(@PathVariable Integer id) {
		Optional<User> optionalAlumno = repo.findById(id);
		if (optionalAlumno.isPresent()) {
			return ResponseEntity.ok(optionalAlumno.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el alumno con ID " + id);
		}
	}

	@PostMapping
	public ResponseEntity<String> createAlumno(@RequestBody User alumno) {
		String randomPassword = generateRandomPassword();
		alumno.setPassword(randomPassword);
		alumno.setRole(Role.STUDENT);
		repo.save(alumno);
		String mensaje = "Alumno: " + alumno.getFirstname() + " " + alumno.getLastname() + ", creado correctamente.";
		return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UpdateRequest updateRequest) {
		return authService.updateUser(id, updateRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAlumno(@PathVariable Integer id) {
		Optional<User> optionalAlumno = repo.findById(id);
		if (optionalAlumno.isPresent()) {
			repo.delete(optionalAlumno.get());
			String mensaje = "Alumno con ID " + id + " ha sido eliminado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado con ID " + id);
		}
	}

	private String generateRandomPassword() {
		// Caracteres permitidos en la contraseña
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int passwordLength = 8;

		StringBuilder password = new StringBuilder();

		// Generar la contraseña aleatoria
		for (int i = 0; i < passwordLength; i++) {
			int index = new java.util.Random().nextInt(characters.length());
			password.append(characters.charAt(index));
		}

		return password.toString();
	}
}