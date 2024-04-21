import java.util.Date;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
class GestionDeReservasTXT implements GestiónDeReservas {
    private static final String ARCHIVO_RESERVAS = "reservas.txt";
    
    @Override
    public void reservarHabitacion(Usuario usuario, Habitacion habitacion, Date fechaInicio, Date fechaFin) {
        // Implementación de reserva de habitación utilizando archivos de texto
        try (FileWriter writer = new FileWriter(ARCHIVO_RESERVAS, true)) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            writer.write(usuario.getUsername() + "," + habitacion.getTipo() + "," + formatter.format(fechaInicio) + "," + formatter.format(fechaFin) + "\n");
            System.out.println("Reserva realizada para el usuario " + usuario.getUsername() + " en la habitación " + habitacion.getTipo());
        } catch (IOException e) {
            System.out.println("Error al guardar la reserva.");
            e.printStackTrace();
        }
    }

    public void cancelarReserva(Usuario usuario, Reserva reserva) {
        // Implementación de cancelación de reserva utilizando archivos de texto
        List<Reserva> reservas = cargarReservas();
        boolean reservaEncontrada = false;
        for (Reserva r : reservas) {
            if (r.getUsuario().getUsername().equals(usuario.getUsername()) && r.getHabitacion().getTipo().equals(reserva.getHabitacion().getTipo())
                && r.getFechaInicio().equals(reserva.getFechaInicio()) && r.getFechaFin().equals(reserva.getFechaFin())) {
                reservas.remove(r);
                reservaEncontrada = true;
                break;
            }
        }
        if (reservaEncontrada) {
            try (PrintWriter writer = new PrintWriter(ARCHIVO_RESERVAS)) {
                for (Reserva r : reservas) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    writer.println(r.getUsuario().getUsername() + "," + r.getHabitacion().getTipo() + "," + formatter.format(r.getFechaInicio()) + "," + formatter.format(r.getFechaFin()));
                }
                System.out.println("Reserva cancelada para el usuario " + usuario.getUsername() + " en la habitación " + reserva.getHabitacion().getTipo());
            } catch (IOException e) {
                System.out.println("Error al cancelar la reserva.");
                e.printStackTrace();
            }
        } else {
            System.out.println("No se encontró la reserva para cancelar.");
        }
    }
    @Override
public List<Habitacion> habitacionesDisponibles(Date fechaInicio, Date fechaFin) {
    List<Habitacion> habitacionesDisponibles = new ArrayList<>();
    
    // Leer las reservas del archivo
    List<Reserva> reservas = ArchivoReservas.cargarReservas();
    
    // Obtener todas las habitaciones
    List<Habitacion> todasLasHabitaciones = obtenerTodasLasHabitaciones();
    
    // Iterar sobre todas las habitaciones
    for (Habitacion habitacion : todasLasHabitaciones) {
        boolean disponible = true;
        
        // Iterar sobre todas las reservas
        for (Reserva reserva : reservas) {
            // Verificar si la habitación está reservada durante el período especificado
            if (reserva.getHabitacion().equals(habitacion) &&
                !(fechaInicio.after(reserva.getFechaFin()) || fechaFin.before(reserva.getFechaInicio()))) {
                disponible = false;
                break;
            }
        }
        
        // Si la habitación está disponible, agregarla a la lista de habitaciones disponibles
        if (disponible) {
            habitacionesDisponibles.add(habitacion);
        }
    }
    
    return habitacionesDisponibles;
}

// Método para obtener todas las habitaciones (puedes leerlas de un archivo o base de datos)
private List<Habitacion> obtenerTodasLasHabitaciones() {
    List<Habitacion> todasLasHabitaciones = new ArrayList<>();
    // Aquí puedes cargar todas las habitaciones desde un archivo o base de datos
    todasLasHabitaciones.add(new Habitacion("individual", 100.0f, 1, "WiFi, TV"));
    todasLasHabitaciones.add(new Habitacion("doble", 150.0f, 2, "WiFi, TV, Mini Bar"));
    todasLasHabitaciones.add(new Habitacion("suite", 200.0f, 2, "WiFi, TV, Mini Bar, Jacuzzi"));
    return todasLasHabitaciones;
}

    private List<Reserva> cargarReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_RESERVAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                Usuario usuario = new Usuario(partes[0], ""); // No tenemos la contraseña en el archivo
                Habitacion habitacion = new Habitacion(partes[1], 0.0f, 0, ""); // No tenemos más detalles en el archivo
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date fechaInicio = formatter.parse(partes[2]);
                Date fechaFin = formatter.parse(partes[3]);
                reservas.add(new Reserva(usuario, habitacion, fechaInicio, fechaFin));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las reservas.");
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public void eliminarHabitacion(String tipoHabitacionEliminar) {
        // Implementación para eliminar una habitación del archivo
        List<Habitacion> habitaciones = obtenerTodasLasHabitaciones();
        try (FileWriter writer = new FileWriter("habitaciones.txt")) {
            for (Habitacion habitacion : habitaciones) {
                if (!habitacion.getTipo().equals(tipoHabitacionEliminar)) {
                    writer.write(habitacion.getTipo() + "," + habitacion.getPrecioPorNoche() + "," + habitacion.getNumMaxHuespedes() + "," + habitacion.getComodidades() + "\n");
                }
            }
            System.out.println("Habitación eliminada exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al eliminar la habitación.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("habitaciones.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                String tipo = partes[0];
                float precioPorNoche = Float.parseFloat(partes[1]);
                int numMaxHuespedes = Integer.parseInt(partes[2]);
                String comodidades = partes[3];
                habitaciones.add(new Habitacion(tipo, precioPorNoche, numMaxHuespedes, comodidades));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las habitaciones.");
            e.printStackTrace();
        }
        return habitaciones;
    }

    @Override
    public void guardarHabitaciones(List<Habitacion> habitaciones) {
        try (FileWriter writer = new FileWriter("habitaciones.txt")) {
            for (Habitacion habitacion : habitaciones) {
                writer.write(habitacion.getTipo() + "," + habitacion.getPrecioPorNoche() + "," + habitacion.getNumMaxHuespedes() + "," + habitacion.getComodidades() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las habitaciones.");
            e.printStackTrace();
        }
       
    

}
}