package com.example.demo.Repository;

import com.example.demo.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    //Buscar categoria por nombre
    Optional<Categoria> findByNombre(String nombre);

    //Buscar categoria por nombre
    boolean existsByNombre(String nombre);
}
