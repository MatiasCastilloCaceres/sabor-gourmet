package cl.ipss.sabor_gourmet.config;

import cl.ipss.sabor_gourmet.model.Cliente;
import cl.ipss.sabor_gourmet.model.Mesa;
import cl.ipss.sabor_gourmet.model.Reserva;
import cl.ipss.sabor_gourmet.repository.ClienteRepository;
import cl.ipss.sabor_gourmet.repository.MesaRepository;
import cl.ipss.sabor_gourmet.repository.ReservaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Configuración para cargar datos iniciales en la base de datos.
 * Se ejecuta al iniciar la aplicación y llena las tablas con datos de ejemplo.
 */
@Configuration
public class DataLoaderConfig {

    @Bean
    public CommandLineRunner loadData(ClienteRepository clienteRepository,
                                      MesaRepository mesaRepository,
                                      ReservaRepository reservaRepository) {
        return args -> {
            // Crear clientes de ejemplo
            Cliente cliente1 = new Cliente("Juan García", "juan.garcia@email.com", "912345678");
            Cliente cliente2 = new Cliente("María López", "maria.lopez@email.com", "987654321");
            Cliente cliente3 = new Cliente("Pedro Martínez", "pedro.martinez@email.com", "945612378");

            clienteRepository.save(cliente1);
            clienteRepository.save(cliente2);
            clienteRepository.save(cliente3);

            // Crear mesas de ejemplo
            Mesa mesa1 = new Mesa(1, 2, true);
            Mesa mesa2 = new Mesa(2, 4, true);
            Mesa mesa3 = new Mesa(3, 6, true);
            Mesa mesa4 = new Mesa(4, 8, true);
            Mesa mesa5 = new Mesa(5, 10, false); // Inactiva por mantenimiento

            mesaRepository.save(mesa1);
            mesaRepository.save(mesa2);
            mesaRepository.save(mesa3);
            mesaRepository.save(mesa4);
            mesaRepository.save(mesa5);

            // Crear reservas de ejemplo para hoy
            LocalDate hoy = LocalDate.now();
            Reserva reserva1 = new Reserva(hoy, LocalTime.of(19, 30), 2, cliente1, mesa1);
            Reserva reserva2 = new Reserva(hoy, LocalTime.of(20, 00), 4, cliente2, mesa2);
            Reserva reserva3 = new Reserva(hoy.plusDays(1), LocalTime.of(19, 00), 6, cliente3, mesa3);

            reservaRepository.save(reserva1);
            reservaRepository.save(reserva2);
            reservaRepository.save(reserva3);

            System.out.println("\n✓ Base de datos inicializada con datos de ejemplo");
            System.out.println("  - 3 clientes creados");
            System.out.println("  - 5 mesas creadas");
            System.out.println("  - 3 reservas de ejemplo creadas\n");
        };
    }
}
