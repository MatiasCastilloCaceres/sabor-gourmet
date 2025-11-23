package cl.ipss.sabor_gourmet.controller;

import cl.ipss.sabor_gourmet.model.Mesa;
import cl.ipss.sabor_gourmet.service.MesaService;
import cl.ipss.sabor_gourmet.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controlador para rutas administrativas de la aplicación.
 * Gestiona el dashboard, lista de mesas y reservas, y configuración.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MesaService mesaService;
    private final ReservaService reservaService;

    public AdminController(MesaService mesaService, ReservaService reservaService) {
        this.mesaService = mesaService;
        this.reservaService = reservaService;
    }

    /**
     * Dashboard principal del administrador con resumen del día.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("")
    public String dashboard(Model model) {
        LocalDate hoy = LocalDate.now();
        model.addAttribute("totalMesas", mesaService.listarTodas().size());
        model.addAttribute("mesasActivas", mesaService.listarActivas().size());
        model.addAttribute("reservasHoy", reservaService.listarActivasPorFecha(hoy).size());
        model.addAttribute("reservasTotal", reservaService.listarTodas().size());
        model.addAttribute("reservasDelDia", reservaService.listarActivasPorFecha(hoy));
        return "admin/dashboard";
    }

    /**
     * Lista todas las mesas del restaurante.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("mesas")
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.listarTodas());
        return "admin/mesas-lista";
    }

    /**
     * Muestra el formulario para crear una nueva mesa.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("mesas/nueva")
    public String mostrarFormularioMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "admin/mesa-form";
    }

    /**
     * Procesa la creación de una nueva mesa.
     * @param mesa mesa a crear con validaciones
     * @param result resultado de la validación
     * @param model modelo para pasar datos a la vista
     * @return redirección a lista de mesas o vuelta al formulario con errores
     */
    @PostMapping("mesas")
    public String crearMesa(@Valid @ModelAttribute Mesa mesa, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/mesa-form";
        }
        mesaService.crear(mesa);
        return "redirect:/admin/mesas";
    }

    /**
     * Muestra el formulario para editar una mesa existente.
     * @param id ID de la mesa a editar
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("mesas/{id}/editar")
    public String mostrarFormularioEditarMesa(@PathVariable Long id, Model model) {
        var mesa = mesaService.buscarPorId(id);
        if (mesa.isPresent()) {
            model.addAttribute("mesa", mesa.get());
            return "admin/mesa-form";
        }
        return "redirect:/admin/mesas";
    }

    /**
     * Procesa la actualización de una mesa.
     * @param id ID de la mesa a actualizar
     * @param mesa datos actualizado de la mesa
     * @param result resultado de la validación
     * @return redirección a lista de mesas
     */
    @PostMapping("mesas/{id}")
    public String actualizarMesa(@PathVariable Long id, @Valid @ModelAttribute Mesa mesa, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/mesa-form";
        }
        mesa.setId(id);
        mesaService.actualizar(mesa);
        return "redirect:/admin/mesas";
    }

    /**
     * Cambia el estado de activación de una mesa.
     * @param id ID de la mesa
     * @param activa true para activar, false para desactivar
     * @return redirección a lista de mesas
     */
    @PostMapping("mesas/{id}/cambiar-estado")
    public String cambiarEstadoMesa(@PathVariable Long id, @RequestParam Boolean activa) {
        mesaService.cambiarEstado(id, activa);
        return "redirect:/admin/mesas";
    }

    /**
     * Elimina una mesa.
     * @param id ID de la mesa a eliminar
     * @return redirección a lista de mesas
     */
    @PostMapping("mesas/{id}/eliminar")
    public String eliminarMesa(@PathVariable Long id) {
        mesaService.eliminar(id);
        return "redirect:/admin/mesas";
    }

    /**
     * Lista todas las reservas del restaurante.
     * @param model modelo para pasar datos a la vista
     * @return nombre de la plantilla
     */
    @GetMapping("reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "admin/reservas-lista";
    }

    /**
     * Cancela una reserva desde el panel administrativo.
     * @param id ID de la reserva a cancelar
     * @return redirección a lista de reservas
     */
    @PostMapping("reservas/{id}/cancelar")
    public String cancelarReserva(@PathVariable Long id) {
        reservaService.cancelar(id);
        return "redirect:/admin/reservas";
    }

    /**
     * Elimina una reserva completamente.
     * @param id ID de la reserva a eliminar
     * @return redirección a lista de reservas
     */
    @PostMapping("reservas/{id}/eliminar")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
        return "redirect:/admin/reservas";
    }
}
