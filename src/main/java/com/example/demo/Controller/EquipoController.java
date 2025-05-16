package com.example.demo.Controller;

import com.example.demo.DTO.EquipoDTO;
import com.example.demo.Models.Categoria;
import com.example.demo.Models.Equipo;
import com.example.demo.Services.EquipoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> listar() {
        List<EquipoDTO> equipos = equipoService.listar().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> buscar(@PathVariable Long id) {
        try {
            Equipo equipo = equipoService.buscarPorId(id);
            return ResponseEntity.ok(convertToDTO(equipo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> crear(@Valid @RequestBody EquipoDTO dto) {
        Equipo equipo = convertToEntity(dto);
        Equipo guardado = equipoService.guardar(equipo);
        return ResponseEntity.status(201).body(convertToDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody EquipoDTO dto) {
        Equipo equipo = convertToEntity(dto);
        try {
            Equipo actualizado = equipoService.actualizar(id, equipo);
            return ResponseEntity.ok(convertToDTO(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            equipoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EquipoDTO convertToDTO(Equipo equipo) {
        EquipoDTO dto = new EquipoDTO();
        dto.setId(equipo.getId());
        dto.setNombre(equipo.getNombre());
        dto.setPais(equipo.getPais());
        if (equipo.getCategoria() != null) {
            dto.setCategoriaId(equipo.getCategoria().getId());
        }
        return dto;
    }

    private Equipo convertToEntity(EquipoDTO dto) {
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre());
        equipo.setPais(dto.getPais());
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            equipo.setCategoria(categoria);
        }
        return equipo;
    }
}
