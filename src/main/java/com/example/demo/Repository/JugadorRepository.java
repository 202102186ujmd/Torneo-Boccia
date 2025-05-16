package com.example.demo.Repository;

import com.example.demo.Models.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    List<Jugador> findByEquipoId(Long equipoId);
    List<Jugador> findByNombreContainingIgnoreCase(String nombre);
}
