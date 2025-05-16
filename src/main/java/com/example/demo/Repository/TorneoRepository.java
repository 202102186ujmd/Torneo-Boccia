package com.example.demo.Repository;

import com.example.demo.Models.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Optional<Torneo> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<Torneo> findByCategoriaId(Long categoriaId);
}
