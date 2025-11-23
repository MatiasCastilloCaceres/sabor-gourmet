package cl.ipss.sabor_gourmet.repository;

import cl.ipss.sabor_gourmet.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Cliente.
 * Proporciona operaciones CRUD y búsquedas personalizadas.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * Busca un cliente por su email.
     * @param email el email del cliente
     * @return Optional con el cliente si existe
     */
    Optional<Cliente> findByEmail(String email);
}
