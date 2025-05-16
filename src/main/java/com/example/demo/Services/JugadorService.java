package com.example.demo.Services;

import com.example.demo.Models.Equipo;
import com.example.demo.Models.Jugador;
import com.example.demo.Repository.EquipoRepository;
import com.example.demo.Repository.JugadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;

    public JugadorService(JugadorRepository jugadorRepository, EquipoRepository equipoRepository) {
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    public Jugador buscarPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));
    }

    public Jugador guardar(Jugador jugador) {
        if (jugador.getEquipo() != null) {
            Long equipoId = jugador.getEquipo().getId();
            Equipo equipo = equipoRepository.findById(equipoId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
            jugador.setEquipo(equipo);
        } else {
            throw new IllegalArgumentException("El equipo es obligatorio");
        }
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizar(Long id, Jugador jugador) {
        Jugador existente = buscarPorId(id);

        existente.setNombre(jugador.getNombre());
        existente.setApellido(jugador.getApellido());
        existente.setSexo(jugador.getSexo());
        existente.setFechaNacimiento(jugador.getFechaNacimiento());

        if (jugador.getEquipo() != null) {
            Long equipoId = jugador.getEquipo().getId();
            Equipo equipo = equipoRepository.findById(equipoId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
            existente.setEquipo(equipo);
        } else {
            throw new IllegalArgumentException("El equipo es obligatorio");
        }

        return jugadorRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!jugadorRepository.existsById(id)) {
            throw new IllegalArgumentException("Jugador no encontrado");
        }
        jugadorRepository.deleteById(id);
    }
}
