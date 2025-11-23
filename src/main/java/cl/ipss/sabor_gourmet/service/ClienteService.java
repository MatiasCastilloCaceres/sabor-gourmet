package cl.ipss.sabor_gourmet.service;

import cl.ipss.sabor_gourmet.model.Cliente;
import cl.ipss.sabor_gourmet.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con clientes.
 * Encapsula la lógica de negocio de clientes.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Lista todos los clientes registrados.
     * @return lista de todos los clientes
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca un cliente por su ID.
     * @param id el ID del cliente
     * @return Optional con el cliente si existe
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Busca un cliente por su email.
     * @param email el email del cliente
     * @return Optional con el cliente si existe
     */
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    /**
     * Crea un nuevo cliente.
     * @param cliente el cliente a crear
     * @return el cliente creado
     */
    public Cliente crear(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente.
     * @param cliente el cliente a actualizar
     * @return el cliente actualizado
     */
    public Cliente actualizar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente por su ID.
     * @param id el ID del cliente a eliminar
     */
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
