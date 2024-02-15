package com.cibertec.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_reserva")
@IdClass(DetalleReservaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReserva {
	@Id
	@ManyToOne
	@JoinColumn(name = "idReserva", referencedColumnName = "idReserva")
	private Reserva reserva;

	@Id
	@ManyToOne
	@JoinColumn(name = "idProfesor", referencedColumnName = "idProfesor")
	private Profesor profesor;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "idCurso", referencedColumnName = "idCurso")
	private Curso curso;
	
}
