package com.sistema.examenes.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Rol {

    @Id
    private Long id;

    private String rolId;
    private String nombre;
}
