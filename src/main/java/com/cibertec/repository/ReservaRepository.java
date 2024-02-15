package com.cibertec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cibertec.model.Reserva;
import com.cibertec.model.ReservasAlumnoDTO;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	@Query(value = "SELECT DISTINCT re.id_reserva AS idReserva, c.nombre AS nombreCurso, c.fecha_dictada AS fechaDictada,s.nombre AS nombreAula, concat(p.nombres,' ', p.apellidos) AS nombreProfesor "
			+ "FROM user u " + "INNER JOIN reserva re ON u.id = re.id "
			+ "INNER JOIN detalle_reserva dr ON re.id_reserva = dr.id_reserva "
			+ "INNER JOIN curso c ON dr.id_curso = c.id_curso "
			+ "INNER JOIN profesor p ON dr.id_profesor = p.id_profesor "
			+ "INNER JOIN salon s ON re.id_salon = s.id_salon " + "WHERE u.id = :userId", nativeQuery = true)
	List<ReservasAlumnoDTO> obtenerReservasPorUsuario(@Param("userId") Integer userId);

}
