package com.sistema.examenes.controladores;

import com.sistema.examenes.modelo.Rol;
import com.sistema.examenes.modelo.Usuario;
import com.sistema.examenes.modelo.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
        // Validación de los campos del usuario
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            // Configuración inicial del usuario
            usuario.setPerfil("default.png");
            usuario.setEnabled(true);

            Set<UsuarioRol> usuarioRoles = new HashSet<>();

            Rol rol = new Rol();
            rol.setRolId(2L);
            rol.setNombre("NORMAL");

            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);

            usuarioRoles.add(usuarioRol);

            Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
            return ResponseEntity.ok(usuarioGuardado);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensaje", "Error al guardar el usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable("username") String username) {
        try {
            Usuario usuario = usuarioService.obtenerUsuario(username);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al buscar el usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
        try {
            usuarioService.eliminarUsuario(usuarioId);
            return ResponseEntity.ok().body("Usuario eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
