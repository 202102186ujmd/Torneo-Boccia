package com.example.demo.Repository;

import com.example.demo.Models.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    List<Partido> findByTorneoId(Long torneoId);

    List<Partido> findByEquipo1IdOrEquipo2Id(Long equipo1Id, Long equipo2Id);
}
