package cl.ipss.sabor_gourmet.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa una reserva en el restaurante.
 * Vincula un cliente con una mesa en una fecha y hora específica.
 */
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer numeroPersonas;

    @Column(nullable = false, length = 20)
    private String estado = "ACTIVA"; // ACTIVA, CANCELADA

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    // Constructores
    public Reserva() {
    }

    public Reserva(LocalDate fecha, LocalTime hora, Integer numeroPersonas, 
                   Cliente cliente, Mesa mesa) {
        this.fecha = fecha;
        this.hora = hora;
        this.numeroPersonas = numeroPersonas;
        this.cliente = cliente;
        this.mesa = mesa;
        this.estado = "ACTIVA";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", numeroPersonas=" + numeroPersonas +
                ", estado='" + estado + '\'' +
                ", cliente=" + cliente +
                ", mesa=" + mesa +
                '}';
    }
}
