package com.challengejava.litealura.repository;

import com.challengejava.litealura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
List<Libro> findByIdioma(String idioma);
    Optional<Libro> findByTitulo(String titulo);
}
