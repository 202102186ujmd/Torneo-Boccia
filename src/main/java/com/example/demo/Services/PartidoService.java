package com.example.demo.Services;

import com.example.demo.Models.Equipo;
import com.example.demo.Models.Partido;
import com.example.demo.Models.Torneo;
import com.example.demo.Repository.EquipoRepository;
import com.example.demo.Repository.PartidoRepository;
import com.example.demo.Repository.TorneoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;
    private final EquipoRepository equipoRepository;
    private final TorneoRepository torneoRepository;

    public PartidoService(PartidoRepository partidoRepository, EquipoRepository equipoRepository, TorneoRepository torneoRepository) {
        this.partidoRepository = partidoRepository;
        this.equipoRepository = equipoRepository;
        this.torneoRepository = torneoRepository;
    }

    public List<Partido> listar() {
        return partidoRepository.findAll();
    }

    public Partido buscarPorId(Long id) {
        return partidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partido no encontrado"));
    }

    public Partido guardar(Partido partido) {
        validarRelaciones(partido);
        actualizarGanador(partido);
        return partidoRepository.save(partido);
    }

    public Partido actualizar(Long id, Partido partido) {
        Partido existente = buscarPorId(id);
        validarRelaciones(partido);

        existente.setFechaHora(partido.getFechaHora());
        existente.setEquipo1(partido.getEquipo1());
        existente.setEquipo2(partido.getEquipo2());
        existente.setTorneo(partido.getTorneo());
        existente.setPuntosEquipo1(partido.getPuntosEquipo1());
        existente.setPuntosEquipo2(partido.getPuntosEquipo2());

        actualizarGanador(existente);

        return partidoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!partidoRepository.existsById(id)) {
            throw new IllegalArgumentException("Partido no encontrado");
        }
        partidoRepository.deleteById(id);
    }

    private void validarRelaciones(Partido partido) {
        if (partido.getEquipo1() == null || partido.getEquipo2() == null || partido.getTorneo() == null) {
            throw new IllegalArgumentException("Equipo 1, Equipo 2 y Torneo son obligatorios");
        }
        if (!equipoRepository.existsById(partido.getEquipo1().getId()) || !equipoRepository.existsById(partido.getEquipo2().getId())) {
            throw new IllegalArgumentException("Uno o ambos equipos no existen");
        }
        if (!torneoRepository.existsById(partido.getTorneo().getId())) {
            throw new IllegalArgumentException("Torneo no existe");
        }
    }

    private void actualizarGanador(Partido partido) {
        Integer puntos1 = partido.getPuntosEquipo1();
        Integer puntos2 = partido.getPuntosEquipo2();

        if (puntos1 == null || puntos2 == null) {
            partido.setGanador(null);
            return;
        }

        if (puntos1 > puntos2) {
            partido.setGanador(partido.getEquipo1());
        } else if (puntos2 > puntos1) {
            partido.setGanador(partido.getEquipo2());
        } else {
            partido.setGanador(null); // Empate
        }
    }
}
