package cl.ipss.sabor_gourmet.service;

import cl.ipss.sabor_gourmet.model.Mesa;
import cl.ipss.sabor_gourmet.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con mesas.
 * Encapsula la lógica de negocio de mesas.
 */
@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    /**
     * Lista todas las mesas del restaurante.
     * @return lista de todas las mesas
     */
    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }

    /**
     * Lista solo las mesas activas (disponibles).
     * @return lista de mesas activas
     */
    public List<Mesa> listarActivas() {
        return mesaRepository.findByActivaTrue();
    }

    /**
     * Busca una mesa por su ID.
     * @param id el ID de la mesa
     * @return Optional con la mesa si existe
     */
    public Optional<Mesa> buscarPorId(Long id) {
        return mesaRepository.findById(id);
    }

    /**
     * Busca una mesa por su número.
     * @param numero el número de mesa
     * @return Mesa con ese número
     */
    public Mesa buscarPorNumero(Integer numero) {
        return mesaRepository.findByNumero(numero);
    }

    /**
     * Crea una nueva mesa.
     * @param mesa la mesa a crear
     * @return la mesa creada
     */
    public Mesa crear(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    /**
     * Actualiza una mesa existente.
     * @param mesa la mesa a actualizar
     * @return la mesa actualizada
     */
    public Mesa actualizar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    /**
     * Activa o desactiva una mesa.
     * @param id el ID de la mesa
     * @param activa true para activar, false para desactivar
     */
    public void cambiarEstado(Long id, Boolean activa) {
        mesaRepository.findById(id).ifPresent(mesa -> {
            mesa.setActiva(activa);
            mesaRepository.save(mesa);
        });
    }

    /**
     * Elimina una mesa.
     * @param id el ID de la mesa a eliminar
     */
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }
}
