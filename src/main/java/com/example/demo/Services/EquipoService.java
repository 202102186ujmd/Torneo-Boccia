package com.example.demo.Services;

import com.example.demo.Models.Categoria;
import com.example.demo.Models.Equipo;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final CategoriaRepository categoriaRepository;

    public EquipoService(EquipoRepository equipoRepository, CategoriaRepository categoriaRepository) {
        this.equipoRepository = equipoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Equipo> listar() {
        return equipoRepository.findAll();
    }

    public Equipo buscarPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
    }

    public Equipo guardar(Equipo equipo) {
        if (equipoRepository.existsByNombre(equipo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un equipo con ese nombre");
        }

        // Validar categoría
        if (equipo.getCategoria() != null) {
            Long catId = equipo.getCategoria().getId();
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            equipo.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        return equipoRepository.save(equipo);
    }

    public Equipo actualizar(Long id, Equipo equipo) {
        Equipo existente = buscarPorId(id);

        if (!existente.getNombre().equals(equipo.getNombre())
                && equipoRepository.existsByNombre(equipo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un equipo con ese nombre");
        }

        existente.setNombre(equipo.getNombre());
        existente.setPais(equipo.getPais());

        if (equipo.getCategoria() != null) {
            Long catId = equipo.getCategoria().getId();
            Categoria categoria = categoriaRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            existente.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        return equipoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new IllegalArgumentException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }
}
