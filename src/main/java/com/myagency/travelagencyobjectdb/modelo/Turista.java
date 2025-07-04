package com.myagency.travelagencyobjectdb.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Turista implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoTurista;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    private String direccion;

    private String telefono;

    public Turista() {
    }

    public Turista(String codigoTurista, String nombre, String apellidos, String direccion, String telefono) {
        this.codigoTurista = codigoTurista;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCodigoTurista() { return codigoTurista; }

    public void setCodigoTurista(String codigoTurista) { this.codigoTurista = codigoTurista; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }

    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + codigoTurista + ")";
    }
}
