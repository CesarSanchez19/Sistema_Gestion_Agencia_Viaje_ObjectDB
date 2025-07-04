package com.myagency.travelagencyobjectdb.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class ReservaHotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoReserva;

    private String nombreHotel;

    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;

    @ManyToOne
    private Turista turista;  // Relación con Turista

    public ReservaHotel() {}

    public ReservaHotel(String codigoReserva, String nombreHotel, LocalDate fechaEntrada, LocalDate fechaSalida, Turista turista) {
        this.codigoReserva = codigoReserva;
        this.nombreHotel = nombreHotel;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.turista = turista;
    }

    // Getters y Setters

    public Long getId() { return id; }

    public String getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(String codigoReserva) { this.codigoReserva = codigoReserva; }

    public String getNombreHotel() { return nombreHotel; }
    public void setNombreHotel(String nombreHotel) { this.nombreHotel = nombreHotel; }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    public LocalDate getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDate fechaSalida) { this.fechaSalida = fechaSalida; }

    public Turista getTurista() { return turista; }
    public void setTurista(Turista turista) { this.turista = turista; }

    @Override
    public String toString() {
        return codigoReserva + " - " + nombreHotel + " (" + fechaEntrada + " a " + fechaSalida + ")";
    }
}
