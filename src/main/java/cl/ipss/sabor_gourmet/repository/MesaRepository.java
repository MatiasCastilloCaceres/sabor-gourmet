package cl.ipss.sabor_gourmet.repository;

import cl.ipss.sabor_gourmet.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Mesa.
 * Proporciona operaciones CRUD y búsquedas personalizadas.
 */
@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    /**
     * Busca todas las mesas activas (disponibles).
     * @return lista de mesas activas
     */
    List<Mesa> findByActivaTrue();

    /**
     * Busca una mesa por su número.
     * @param numero el número de mesa
     * @return Mesa con ese número
     */
    Mesa findByNumero(Integer numero);
}
