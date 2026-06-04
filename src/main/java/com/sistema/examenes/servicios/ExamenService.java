package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Categoria;
import com.sistema.examenes.modelo.Examen;

import java.util.List;
import java.util.Set;

public interface ExamenService {
    Examen agregarExamen(Examen examen);

    Examen actualizarExamen(Examen examen);

    Set<Examen> obtenerExamenes();

    Examen obteneterExamen(Long examenId);

    void eliminarExamen(long examenId);

    List<Examen> listarExamenesDeUnaCategoria(Categoria categoria);
}
