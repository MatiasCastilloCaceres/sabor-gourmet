package cl.ipss.sabor_gourmet.controller;

import cl.ipss.sabor_gourmet.dto.ReservaForm;
import cl.ipss.sabor_gourmet.model.Cliente;
import cl.ipss.sabor_gourmet.model.Reserva;
import cl.ipss.sabor_gourmet.service.ClienteService;
import cl.ipss.sabor_gourmet.service.MesaService;
import cl.ipss.sabor_gourmet.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Controlador para rutas públicas de la aplicación.
 * Gestiona la página de inicio, formulario de reservas y confirmación.
 */
@Controller
@RequestMapping("/")
public class PublicController {

    private final ReservaService reservaService;
    private final MesaService mesaService;
    private final ClienteService clienteService;

    public PublicController(ReservaService reservaService, MesaService mesaService, ClienteService clienteService) {
        this.reservaService = reservaService;
        this.mesaService = mesaService;
        this.clienteService = clienteService;
    }

    /**
     * Página de inicio del restaurante.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("")
    public String inicio(Model model) {
        model.addAttribute("mesasActivas", mesaService.listarActivas().size());
        model.addAttribute("reservasHoy", reservaService.listarActivasPorFecha(LocalDate.now()).size());
        return "public/index";
    }

    /**
     * Muestra el formulario para crear una nueva reserva.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("reservas/nueva")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reservaForm", new ReservaForm());
        model.addAttribute("mesas", mesaService.listarActivas());
        return "public/reserva-form";
    }

    /**
     * Procesa la creación de una nueva reserva.
     * Valida el formulario, comprueba disponibilidad y crea la reserva si es posible.
     * @param form formulario de reserva validado
     * @param result resultado de la validación
     * @param model modelo para pasar datos a la vista
     * @return redirección a confirmación o vuelta al formulario con errores
     */
    @PostMapping("reservas")
    public String crearReserva(@Valid @ModelAttribute("reservaForm") ReservaForm form,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mesas", mesaService.listarActivas());
            return "public/reserva-form";
        }

        try {
            // Buscar o crear cliente
            Optional<Cliente> clienteOpt = clienteService.buscarPorEmail(form.getEmailCliente());
            Cliente cliente;
            if (clienteOpt.isPresent()) {
                cliente = clienteOpt.get();
            } else {
                cliente = new Cliente(form.getNombreCliente(), form.getEmailCliente(), form.getTelefonoCliente());
                cliente = clienteService.crear(cliente);
            }

            // Buscar mesa
            Optional<?> mesaOpt = mesaService.buscarPorId(form.getMesaId());
            if (mesaOpt.isEmpty()) {
                model.addAttribute("error", "La mesa seleccionada no existe");
                model.addAttribute("mesas", mesaService.listarActivas());
                return "public/reserva-form";
            }

            var mesa = mesaOpt.get();
            LocalDate fecha = LocalDate.parse(form.getFecha());
            LocalTime hora = LocalTime.parse(form.getHora());

            // Crear reserva
            Reserva reserva = new Reserva(fecha, hora, form.getNumeroPersonas(), cliente, (cl.ipss.sabor_gourmet.model.Mesa) mesa);
            Optional<Reserva> reservaCreada = reservaService.crear(reserva);

            if (reservaCreada.isPresent()) {
                model.addAttribute("reserva", reservaCreada.get());
                return "public/confirmacion";
            } else {
                model.addAttribute("error", "No hay disponibilidad para esa mesa en esa fecha y hora");
                model.addAttribute("mesas", mesaService.listarActivas());
                return "public/reserva-form";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la reserva: " + e.getMessage());
            model.addAttribute("mesas", mesaService.listarActivas());
            return "public/reserva-form";
        }
    }

    /**
     * Busca las reservas de un cliente por email.
     * @param email email del cliente
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("reservas/mis-reservas")
    public String misCancelacionesReservas(@RequestParam(required = false) String email, Model model) {
        if (email != null && !email.isEmpty()) {
            Optional<Cliente> cliente = clienteService.buscarPorEmail(email);
            if (cliente.isPresent()) {
                var reservas = reservaService.listarTodas().stream()
                        .filter(r -> r.getCliente().getId().equals(cliente.get().getId()))
                        .toList();
                model.addAttribute("reservas", reservas);
                model.addAttribute("cliente", cliente.get());
            } else {
                model.addAttribute("error", "Cliente no encontrado");
            }
        }
        return "public/mis-reservas";
    }

    /**
     * Cancela una reserva (solo si es propiedad del usuario).
     * @param id ID de la reserva a cancelar
     * @return redirección a la página anterior
     */
    @PostMapping("reservas/{id}/cancelar")
    public String cancelarReserva(@PathVariable Long id) {
        reservaService.cancelar(id);
        return "redirect:/reservas/mis-reservas";
    }
}
