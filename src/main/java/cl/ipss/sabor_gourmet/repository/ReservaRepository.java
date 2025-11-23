package cl.ipss.sabor_gourmet.repository;

import cl.ipss.sabor_gourmet.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para la entidad Reserva.
 * Proporciona operaciones CRUD y búsquedas personalizadas.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    /**
     * Busca todas las reservas de una fecha específica.
     * @param fecha la fecha de búsqueda
     * @return lista de reservas en esa fecha
     */
    List<Reserva> findByFecha(LocalDate fecha);

    /**
     * Busca reservas activas de una fecha específica.
     * @param fecha la fecha de búsqueda
     * @param estado el estado a buscar
     * @return lista de reservas con ese estado en esa fecha
     */
    List<Reserva> findByFechaAndEstado(LocalDate fecha, String estado);
}
