package cl.ipss.sabor_gourmet.dto;

import jakarta.validation.constraints.*;

/**
 * DTO (Data Transfer Object) para el formulario de creación de reservas.
 * Incluye validaciones para asegurar datos válidos desde el cliente.
 */
public class ReservaForm {

    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombreCliente;

    @NotBlank(message = "El email es requerido")
    @Email(message = "Debe proporcionar un email válido")
    private String emailCliente;

    @NotBlank(message = "El teléfono es requerido")
    @Pattern(regexp = "^[0-9]{7,12}$", message = "El teléfono debe tener entre 7 y 12 dígitos")
    private String telefonoCliente;

    @NotNull(message = "La fecha es requerida")
    private String fecha;

    @NotNull(message = "La hora es requerida")
    private String hora;

    @NotNull(message = "El número de personas es requerido")
    @Min(value = 1, message = "Debe haber al menos 1 persona")
    @Max(value = 20, message = "Máximo 20 personas por reserva")
    private Integer numeroPersonas;

    @NotNull(message = "Debe seleccionar una mesa")
    private Long mesaId;

    // Constructores
    public ReservaForm() {
    }

    // Getters y Setters
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }
}
