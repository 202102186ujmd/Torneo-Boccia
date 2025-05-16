package com.example.demo.Controller;

import com.example.demo.DTO.JugadorDTO;
import com.example.demo.Models.Equipo;
import com.example.demo.Models.Jugador;
import com.example.demo.Services.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listar() {
        List<JugadorDTO> jugadores = jugadorService.listar().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jugadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> buscar(@PathVariable Long id) {
        try {
            Jugador jugador = jugadorService.buscarPorId(id);
            return ResponseEntity.ok(convertToDTO(jugador));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<JugadorDTO> crear(@Valid @RequestBody JugadorDTO dto) {
        Jugador jugador = convertToEntity(dto);
        Jugador guardado = jugadorService.guardar(jugador);
        return ResponseEntity.status(201).body(convertToDTO(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody JugadorDTO dto) {
        Jugador jugador = convertToEntity(dto);
        try {
            Jugador actualizado = jugadorService.actualizar(id, jugador);
            return ResponseEntity.ok(convertToDTO(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            jugadorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private JugadorDTO convertToDTO(Jugador jugador) {
        JugadorDTO dto = new JugadorDTO();
        dto.setId(jugador.getId());
        dto.setNombre(jugador.getNombre());
        dto.setApellido(jugador.getApellido());
        dto.setSexo(jugador.getSexo());
        dto.setFechaNacimiento(jugador.getFechaNacimiento());
        if (jugador.getEquipo() != null) {
            dto.setEquipoId(jugador.getEquipo().getId());
        }
        return dto;
    }

    private Jugador convertToEntity(JugadorDTO dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre());
        jugador.setApellido(dto.getApellido());
        jugador.setSexo(dto.getSexo());
        jugador.setFechaNacimiento(dto.getFechaNacimiento());
        if (dto.getEquipoId() != null) {
            Equipo equipo = new Equipo();
            equipo.setId(dto.getEquipoId());
            jugador.setEquipo(equipo);
        }
        return jugador;
    }
}
