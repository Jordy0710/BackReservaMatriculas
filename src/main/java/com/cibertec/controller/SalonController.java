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
import com.cibertec.model.Salon;
import com.cibertec.repository.SalonRepository;

@RestController
@RequestMapping("/salones")
@CrossOrigin("*")
public class SalonController {

	@Autowired
	private SalonRepository repo;

	@GetMapping
	public ResponseEntity<Object> getAllSalones() {
		List<Salon> salones = repo.findAll();
		if (!salones.isEmpty()) {
			return ResponseEntity.ok(salones);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron salones.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getSalon(@PathVariable Integer id) {
		Optional<Salon> optionalSalon = repo.findById(id);
		if (optionalSalon.isPresent()) {
			return ResponseEntity.ok(optionalSalon.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el salón con ID " + id);
		}
	}

	@PostMapping
	public ResponseEntity<String> createSalon(@RequestBody Salon salon) {
		repo.save(salon);
		String mensaje = "Salón: " + salon.getNombre() + ", creado correctamente.";
		return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateSalon(@PathVariable Integer id, @RequestBody Salon salon) {
		Optional<Salon> optionalSalon = repo.findById(id);
		if (optionalSalon.isPresent()) {
			Salon existingSalon = optionalSalon.get();
			existingSalon.setNombre(salon.getNombre());
			existingSalon.setCapacidad(salon.getCapacidad());
			repo.save(existingSalon);
			String mensaje = "Salón con ID " + id + " ha sido actualizado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salón no encontrado con ID " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSalon(@PathVariable Integer id) {
		Optional<Salon> optionalSalon = repo.findById(id);
		if (optionalSalon.isPresent()) {
			repo.delete(optionalSalon.get());
			String mensaje = "Salón con ID " + id + " ha sido eliminado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Salón no encontrado con ID " + id);
		}
	}
}