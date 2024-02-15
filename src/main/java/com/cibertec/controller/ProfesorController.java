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

import com.cibertec.model.Profesor;
import com.cibertec.repository.ProfesorRepository;

@RestController
@RequestMapping("/profesores")
@CrossOrigin("*")
public class ProfesorController {

	@Autowired
	private ProfesorRepository repo;

	@GetMapping
	public ResponseEntity<Object> getAllProfesores() {
		List<Profesor> profesores = repo.findAll();
		if (!profesores.isEmpty()) {
			return ResponseEntity.ok(profesores);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron profesores.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProfesor(@PathVariable Integer id) {
		Optional<Profesor> optionalProfesor = repo.findById(id);
		if (optionalProfesor.isPresent()) {
			return ResponseEntity.ok(optionalProfesor.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el profesor con ID " + id);
		}
	}

	@PostMapping
	public ResponseEntity<String> createProfesor(@RequestBody Profesor profesor) {
		repo.save(profesor);
		String mensaje = "Profesor: " + profesor.getNombres() + " " + profesor.getApellidos() + ", creado correctamente.";
		return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateProfesor(@PathVariable Integer id, @RequestBody Profesor profesor) {
		Optional<Profesor> optionalProfesor = repo.findById(id);
		if (optionalProfesor.isPresent()) {
			Profesor existingProfesor = optionalProfesor.get();
			existingProfesor.setNombres(profesor.getNombres());
			existingProfesor.setApellidos(profesor.getApellidos());
			existingProfesor.setDni(profesor.getDni());
			existingProfesor.setCelular(profesor.getCelular());
			existingProfesor.setDireccion(profesor.getDireccion());
			existingProfesor.setCarrera(profesor.getCarrera());
			existingProfesor.setUniversidad(profesor.getUniversidad());
			repo.save(existingProfesor);
			String mensaje = "Profesor con ID " + id + " ha sido actualizado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado con ID " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProfesor(@PathVariable Integer id) {
		Optional<Profesor> optionalProfesor = repo.findById(id);
		if (optionalProfesor.isPresent()) {
			repo.delete(optionalProfesor.get());
			String mensaje = "Profesor con ID " + id + " ha sido eliminado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profesor no encontrado con ID " + id);
		}
	}
}