package com.sistema.examenes;

import com.sistema.examenes.modelo.Rol;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SistemaExamenesBackendApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* Usuario usuario = new Usuario();

		usuario.setNombre("Robinson");
		usuario.setApellido("Lopez");
		usuario.setUsername("Parca17");
		usuario.setPassword("12345");
		usuario.setEmail("c1@gmail.com");
		usuario.setTelefono("3244232786");
		usuario.setPerfil("foto.png");

		Rol rol = new Rol();
		rol.setRolId(1L);
		rol.setNombre("ADMIN");

		//Relacionamos estos 2
		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);

		//Guardamos esta lista conjunto en la base de datos
		Usuario usuarioGuardado = usuarioService.guardarUusario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername());*/
	}
}
