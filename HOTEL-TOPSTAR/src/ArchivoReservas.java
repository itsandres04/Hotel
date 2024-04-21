import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ArchivoReservas {
    private static final String ARCHIVO_RESERVAS = "reservas.txt";
    
    public static List<Reserva> cargarReservas() {
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
}