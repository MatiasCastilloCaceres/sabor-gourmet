package cl.ipss.sabor_gourmet.service;

import cl.ipss.sabor_gourmet.model.Mesa;
import cl.ipss.sabor_gourmet.model.Reserva;
import cl.ipss.sabor_gourmet.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con reservas.
 * Incluye lógica de negocio para validar disponibilidad y crear/cancelar reservas.
 */
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Lista todas las reservas.
     * @return lista de todas las reservas
     */
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    /**
     * Lista reservas de una fecha específica.
     * @param fecha la fecha de búsqueda
     * @return lista de reservas en esa fecha
     */
    public List<Reserva> listarPorFecha(LocalDate fecha) {
        return reservaRepository.findByFecha(fecha);
    }

    /**
     * Lista reservas activas de una fecha específica.
     * @param fecha la fecha de búsqueda
     * @return lista de reservas activas en esa fecha
     */
    public List<Reserva> listarActivasPorFecha(LocalDate fecha) {
        return reservaRepository.findByFechaAndEstado(fecha, "ACTIVA");
    }

    /**
     * Busca una reserva por su ID.
     * @param id el ID de la reserva
     * @return Optional con la reserva si existe
     */
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Valida si una mesa está disponible en una fecha y hora específica.
     * Una mesa se considera no disponible si ya tiene una reserva ACTIVA en esa fecha y hora.
     * @param mesa la mesa a validar
     * @param fecha la fecha de la reserva
     * @param hora la hora de la reserva
     * @return true si la mesa está disponible, false en caso contrario
     */
    public boolean mesaDisponible(Mesa mesa, LocalDate fecha, LocalTime hora) {
        List<Reserva> reservasDelDia = reservaRepository.findByFechaAndEstado(fecha, "ACTIVA");
        return reservasDelDia.stream()
                .noneMatch(r -> r.getMesa().getId().equals(mesa.getId())
                        && r.getHora().equals(hora));
    }

    /**
     * Valida si una mesa tiene suficiente capacidad para el número de personas.
     * @param mesa la mesa a validar
     * @param numeroPersonas número de personas de la reserva
     * @return true si la mesa tiene capacidad suficiente
     */
    public boolean mesaTieneCapacidad(Mesa mesa, Integer numeroPersonas) {
        return mesa.getCapacidad() >= numeroPersonas;
    }

    /**
     * Crea una nueva reserva si la mesa está disponible y tiene capacidad.
     * @param reserva la reserva a crear
     * @return Optional con la reserva creada, o vacío si no es posible crearla
     */
    public Optional<Reserva> crear(Reserva reserva) {
        if (mesaDisponible(reserva.getMesa(), reserva.getFecha(), reserva.getHora())
                && mesaTieneCapacidad(reserva.getMesa(), reserva.getNumeroPersonas())) {
            reserva.setEstado("ACTIVA");
            return Optional.of(reservaRepository.save(reserva));
        }
        return Optional.empty();
    }

    /**
     * Actualiza una reserva existente.
     * @param reserva la reserva a actualizar
     * @return la reserva actualizada
     */
    public Reserva actualizar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    /**
     * Cancela una reserva por su ID.
     * @param id el ID de la reserva a cancelar
     */
    public void cancelar(Long id) {
        reservaRepository.findById(id).ifPresent(reserva -> {
            reserva.setEstado("CANCELADA");
            reservaRepository.save(reserva);
        });
    }

    /**
     * Elimina una reserva.
     * @param id el ID de la reserva a eliminar
     */
    public void eliminar(Long id) {
        reservaRepository.deleteById(id);
    }
}
