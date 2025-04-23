package com.sistema.examenes.servicios;

import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;

import java.util.Set;

public interface UsuarioService {

    public Usuario guardarUusario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;
}
