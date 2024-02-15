package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.DetalleReserva;

public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Integer>{
	List<DetalleReserva> findByReservaIdReserva(Integer idReserva);
}
