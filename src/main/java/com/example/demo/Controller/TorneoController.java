package com.example.demo.Controller;

import com.example.demo.DTO.TorneoDTO;
import com.example.demo.Models.Categoria;
import com.example.demo.Models.Torneo;
import com.example.demo.Services.TorneoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/torneos")
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping
    public ResponseEntity<List<TorneoDTO>> listar() {
        List<TorneoDTO> torneos = torneoService.listar().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(torneos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneoDTO> buscar(@PathVariable Long id) {
        try {
            Torneo torneo = torneoService.buscarPorId(id);
            return ResponseEntity.ok(convertToDTO(torneo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TorneoDTO> crear(@Valid @RequestBody TorneoDTO dto) {
        Torneo torneo = convertToEntity(dto);
        Torneo guardado = torneoService.guardar(torneo);
        return ResponseEntity.status(201).body(convertToDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TorneoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TorneoDTO dto) {
        Torneo torneo = convertToEntity(dto);
        try {
            Torneo actualizado = torneoService.actualizar(id, torneo);
            return ResponseEntity.ok(convertToDTO(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            torneoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private TorneoDTO convertToDTO(Torneo torneo) {
        TorneoDTO dto = new TorneoDTO();
        dto.setId(torneo.getId());
        dto.setNombre(torneo.getNombre());
        dto.setFechaInicio(torneo.getFechaInicio());
        dto.setFechaFin(torneo.getFechaFin());
        if (torneo.getCategoria() != null) {
            dto.setCategoriaId(torneo.getCategoria().getId());
        }
        return dto;
    }

    private Torneo convertToEntity(TorneoDTO dto) {
        Torneo torneo = new Torneo();
        torneo.setNombre(dto.getNombre());
        torneo.setFechaInicio(dto.getFechaInicio());
        torneo.setFechaFin(dto.getFechaFin());
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            torneo.setCategoria(categoria);
        }
        return torneo;
    }
}
