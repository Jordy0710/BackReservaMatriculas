package com.cibertec.model;

import java.util.Objects;

import lombok.Data;

@Data
public class DetalleReservaId {
	private Integer reserva;
	private Integer profesor;
	private Integer curso;

	public DetalleReservaId(Integer reserva, Integer profesor, Integer curso) {
		super();
		this.reserva = reserva;
		this.profesor = profesor;
		this.curso = curso;
	}

	public DetalleReservaId() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalleReservaId other = (DetalleReservaId) obj;
		return Objects.equals(curso, other.curso) && Objects.equals(profesor, other.profesor)
				&& Objects.equals(reserva, other.reserva);
	}

	@Override
	public int hashCode() {
		return Objects.hash(curso, profesor, reserva);
	}

}
