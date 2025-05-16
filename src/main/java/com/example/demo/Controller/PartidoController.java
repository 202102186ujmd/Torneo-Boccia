package com.example.demo.Controller;

import com.example.demo.DTO.PartidoDTO;
import com.example.demo.Models.Equipo;
import com.example.demo.Models.Partido;
import com.example.demo.Models.Torneo;
import com.example.demo.Services.PartidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @GetMapping
    public ResponseEntity<List<PartidoDTO>> listar() {
        List<PartidoDTO> partidos = partidoService.listar().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(partidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> buscar(@PathVariable Long id) {
        try {
            Partido partido = partidoService.buscarPorId(id);
            return ResponseEntity.ok(convertToDTO(partido));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PartidoDTO> crear(@Valid @RequestBody PartidoDTO dto) {
        Partido partido = convertToEntity(dto);
        Partido guardado = partidoService.guardar(partido);
        return ResponseEntity.status(201).body(convertToDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody PartidoDTO dto) {
        Partido partido = convertToEntity(dto);
        try {
            Partido actualizado = partidoService.actualizar(id, partido);
            return ResponseEntity.ok(convertToDTO(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            partidoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PartidoDTO convertToDTO(Partido partido) {
        PartidoDTO dto = new PartidoDTO();
        dto.setId(partido.getId());
        dto.setFechaHora(partido.getFechaHora());
        dto.setPuntosEquipo1(partido.getPuntosEquipo1());
        dto.setPuntosEquipo2(partido.getPuntosEquipo2());

        if (partido.getEquipo1() != null) {
            dto.setEquipo1Id(partido.getEquipo1().getId());
        }
        if (partido.getEquipo2() != null) {
            dto.setEquipo2Id(partido.getEquipo2().getId());
        }
        if (partido.getTorneo() != null) {
            dto.setTorneoId(partido.getTorneo().getId());
        }
        if (partido.getGanador() != null) {
            dto.setGanadorId(partido.getGanador().getId());
        }
        return dto;
    }

    private Partido convertToEntity(PartidoDTO dto) {
        Partido partido = new Partido();
        partido.setFechaHora(dto.getFechaHora());
        partido.setPuntosEquipo1(dto.getPuntosEquipo1());
        partido.setPuntosEquipo2(dto.getPuntosEquipo2());

        if (dto.getEquipo1Id() != null) {
            Equipo equipo1 = new Equipo();
            equipo1.setId(dto.getEquipo1Id());
            partido.setEquipo1(equipo1);
        }
        if (dto.getEquipo2Id() != null) {
            Equipo equipo2 = new Equipo();
            equipo2.setId(dto.getEquipo2Id());
            partido.setEquipo2(equipo2);
        }
        if (dto.getTorneoId() != null) {
            Torneo torneo = new Torneo();
            torneo.setId(dto.getTorneoId());
            partido.setTorneo(torneo);
        }
        if (dto.getGanadorId() != null) {
            Equipo ganador = new Equipo();
            ganador.setId(dto.getGanadorId());
            partido.setGanador(ganador);
        }
        return partido;
    }
}
