package cl.ipss.sabor_gourmet.model;

import jakarta.persistence.*;

/**
 * Entidad que representa una mesa del restaurante.
 * Mantiene información sobre la capacidad y disponibilidad.
 */
@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Boolean activa = true; // disponible en el restaurante

    // Constructores
    public Mesa() {
    }

    public Mesa(Integer numero, Integer capacidad) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.activa = true;
    }

    public Mesa(Integer numero, Integer capacidad, Boolean activa) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.activa = activa;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", capacidad=" + capacidad +
                ", activa=" + activa +
                '}';
    }
}
