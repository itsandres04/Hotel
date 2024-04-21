import java.util.Date;
import java.util.List;
interface GestiónDeReservas {
    void reservarHabitacion(Usuario usuario, Habitacion habitacion, Date fechaInicio, Date fechaFin);
    void cancelarReserva(Usuario usuario, Reserva reserva);
    List<Habitacion> habitacionesDisponibles(Date fechaInicio, Date fechaFin);
    void eliminarHabitacion(String tipoHabitacionEliminar);
    void guardarHabitaciones(List<Habitacion> habitaciones);
    List<Habitacion> cargarHabitaciones();
}