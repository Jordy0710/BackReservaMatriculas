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
import com.cibertec.model.Curso;
import com.cibertec.repository.CursoRepository;

@RestController
@RequestMapping("/cursos")
@CrossOrigin("*")
public class CursoController {

	@Autowired
	private CursoRepository repo;

	@GetMapping
	public ResponseEntity<Object> getAllCursos() {
		List<Curso> cursos = repo.findAll();
		if (!cursos.isEmpty()) {
			return ResponseEntity.ok(cursos);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron cursos.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getCurso(@PathVariable Integer id) {
		Optional<Curso> optionalCurso = repo.findById(id);
		if (optionalCurso.isPresent()) {
			return ResponseEntity.ok(optionalCurso.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el curso con ID " + id);
		}
	}

	@PostMapping
	public ResponseEntity<String> createCurso(@RequestBody Curso curso) {
		repo.save(curso);
		String mensaje = "Curso: " + curso.getNombre() + ", creado correctamente.";
		return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateCurso(@PathVariable Integer id, @RequestBody Curso curso) {
		Optional<Curso> optionalCurso = repo.findById(id);
		if (optionalCurso.isPresent()) {
			Curso existingCurso = optionalCurso.get();
			existingCurso.setNombre(curso.getNombre());
			existingCurso.setHoras(curso.getHoras());
			existingCurso.setDias(curso.getDias());
			existingCurso.setFechaDictada(curso.getFechaDictada());
			repo.save(existingCurso);
			String mensaje = "Curso con ID " + id + " ha sido actualizado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado con ID " + id);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCurso(@PathVariable Integer id) {
		Optional<Curso> optionalCurso = repo.findById(id);
		if (optionalCurso.isPresent()) {
			repo.delete(optionalCurso.get());
			String mensaje = "Curso con ID " + id + " ha sido eliminado correctamente.";
			return ResponseEntity.status(HttpStatus.OK).body(mensaje);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado con ID " + id);
		}
	}

}
