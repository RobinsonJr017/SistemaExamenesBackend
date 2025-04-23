package com.sistema.examenes.repositorio;

import com.sistema.examenes.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{

    public Usuario findByUsername(String username);
}
