package com.example.demo.Repository;

import com.example.demo.Models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Equipo> findByCategoriaId(Long categoriaId);

    List<Equipo> findByPais(String pais);
}
