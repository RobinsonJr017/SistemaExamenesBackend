package com.sistema.examenes.repositorio;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.modelo.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Long> {

    List<Examen> findByCategoria(Categoria categoria);
}
