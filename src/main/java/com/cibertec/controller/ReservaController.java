package com.cibertec.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cibertec.model.DetalleReserva;
import com.cibertec.model.Reserva;
import com.cibertec.model.ReservaRequest;
import com.cibertec.model.ReservasAlumnoDTO;
import com.cibertec.repository.DetalleReservaRepository;
import com.cibertec.repository.ReservaRepository;

@RestController
@RequestMapping("/reservas")
@CrossOrigin("*")
public class ReservaController {

	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private DetalleReservaRepository detalleReservaRepository;

	@GetMapping("/listarDetalles")
	public ResponseEntity<Object> getAllDetalleReservas() {
		List<DetalleReserva> detalleReservas = detalleReservaRepository.findAll();
		if (!detalleReservas.isEmpty()) {
			return ResponseEntity.ok(detalleReservas);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron detalles de reserva.");
		}
	}

	@GetMapping("/listarReservas")
	public ResponseEntity<Object> getAllReservas() {
		List<Reserva> reservas = reservaRepository.findAll();
		if (!reservas.isEmpty()) {
			return ResponseEntity.ok(reservas);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron reservas.");
		}
	}

	@GetMapping("/listarReservaPorId/{idReserva}")
	public ResponseEntity<Object> getReserva(@PathVariable Integer idReserva) {
		Optional<Reserva> optionalReserva = reservaRepository.findById(idReserva);
		if (optionalReserva.isPresent()) {
			return ResponseEntity.ok(optionalReserva.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la reserva con ID " + idReserva);
		}
	}

	@GetMapping("/listarDetallePorId/{idReserva}")
	public ResponseEntity<Object> getDetalleReservaById(@PathVariable Integer idReserva) {
		List<DetalleReserva> detalles = detalleReservaRepository.findByReservaIdReserva(idReserva);
		return ResponseEntity.ok(detalles);

	}

	@PostMapping("/crearReservaYDetalle")
	public ResponseEntity<String> crearReservaYDetalle(@RequestBody ReservaRequest reservaRequest) {
		// Extraer la información de la solicitud
		Reserva reserva = reservaRequest.getReserva();
		List<DetalleReserva> detalles = reservaRequest.getDetalles();

		LocalDateTime fechaActual = LocalDateTime.now();
		reserva.setFecha(fechaActual);

		// Guardar la reserva y obtener el objeto persistido
		Reserva savedReserva = reservaRepository.save(reserva);

		for (DetalleReserva detalle : detalles) {
			detalle.setReserva(savedReserva);
			detalleReservaRepository.save(detalle);
		}

		return ResponseEntity.ok("Reserva y detalle de reserva registrados con éxito.");
	}

	@GetMapping("/reservasAlumno/{idUsuario}")
	public ResponseEntity<?> obtenerReservasPorIdUsuario(@PathVariable Integer idUsuario) {
		List<ReservasAlumnoDTO> reservas = reservaRepository.obtenerReservasPorUsuario(idUsuario);
		return ResponseEntity.ok(reservas);
	}
}
