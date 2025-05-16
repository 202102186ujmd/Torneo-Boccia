package com.example.demo.Services;

import com.example.demo.Models.Categoria;
import com.example.demo.Models.Torneo;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.TorneoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final CategoriaRepository categoriaRepository;

    public TorneoService(TorneoRepository torneoRepository, CategoriaRepository categoriaRepository) {
        this.torneoRepository = torneoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Torneo> listar() {
        return torneoRepository.findAll();
    }

    public Torneo buscarPorId(Long id) {
        return torneoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Torneo no encontrado"));
    }

    public Torneo guardar(Torneo torneo) {
        if (torneoRepository.existsByNombre(torneo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un torneo con ese nombre");
        }

        if (torneo.getCategoria() != null) {
            Long catId = torneo.getCategoria().getId();
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            torneo.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        return torneoRepository.save(torneo);
    }

    public Torneo actualizar(Long id, Torneo torneo) {
        Torneo existente = buscarPorId(id);

        if (!existente.getNombre().equals(torneo.getNombre())
                && torneoRepository.existsByNombre(torneo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un torneo con ese nombre");
        }

        existente.setNombre(torneo.getNombre());
        existente.setFechaInicio(torneo.getFechaInicio());
        existente.setFechaFin(torneo.getFechaFin());

        if (torneo.getCategoria() != null) {
            Long catId = torneo.getCategoria().getId();
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        return torneoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!torneoRepository.existsById(id)) {
            throw new IllegalArgumentException("Torneo no encontrado");
        }
        torneoRepository.deleteById(id);
    }
}
