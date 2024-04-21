import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SistemaReservasHotel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Crear una instancia de gestión de reservas utilizando archivos de texto
        GestiónDeReservas gestorReservas = new GestionDeReservasTXT();
        
         
        while (true) {
            System.out.println("Bienvenido al sistema de reservas del hotel.");
            System.out.println("1. Iniciar sesión como Usuario");
            System.out.println("2. Iniciar sesión como Administrador");
            System.out.println("3. Registrarse como Usuario");
            System.out.println("4. Registrarse como Administrador");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            
            switch (opcion) {
                case 1:
                // Iniciar sesión como Usuario
                System.out.println("Inicio de sesión como Usuario:");
                System.out.print("Ingrese su nombre de usuario: ");
                String usernameUsuario = scanner.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String passwordUsuario = scanner.nextLine();
                // Verificar las credenciales del usuario
                if (verificarCredenciales(usernameUsuario, passwordUsuario, "usuarios.txt")) {
                    Usuario usuario = new Usuario(usernameUsuario, passwordUsuario);
                    // Menú de Usuario
                    menuUsuario(usuario, gestorReservas);
                } else {
                    System.out.println("Nombre de usuario o contraseña incorrectos.");
                }
                break;
            case 2:
                // Iniciar sesión como Administrador
                System.out.println("Inicio de sesión como Administrador:");
                System.out.print("Ingrese su nombre de usuario: ");
                String usernameAdmin = scanner.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String passwordAdmin = scanner.nextLine();
                // Verificar las credenciales del administrador
                if (verificarCredenciales(usernameAdmin, passwordAdmin, "admins.txt")) {
                    Administrador admin = new Administrador(usernameAdmin, passwordAdmin);
                    // Menú de Administrador
                    menuAdministrador(admin, gestorReservas);
                } else {
                    System.out.println("Nombre de usuario o contraseña incorrectos.");
                }
                break;
            
                case 3:
                    // Registro de Usuario
                    System.out.println("Registro de Usuario:");
                    System.out.print("Ingrese su nombre de usuario: ");
                    String nuevoUsernameUsuario = scanner.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    String nuevoPasswordUsuario = scanner.nextLine();
                    // Guardar datos de registro en archivo de texto
                    guardarRegistro(nuevoUsernameUsuario, nuevoPasswordUsuario, "usuarios.txt");
                    System.out.println("Registro exitoso.");
                    break;
                case 4:
                    // Registro de Administrador
                    System.out.println("Registro de Administrador:");
                    System.out.print("Ingrese su nombre de usuario: ");
                    String nuevoUsernameAdmin = scanner.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    String nuevoPasswordAdmin = scanner.nextLine();
                    // Guardar datos de registro en archivo de texto
                    guardarRegistro(nuevoUsernameAdmin, nuevoPasswordAdmin, "admins.txt");
                    System.out.println("Registro exitoso.");
                    break;
                case 5:
                    // Salir del programa
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
    
    private static boolean verificarCredenciales(String username, String password, String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                String usernameAlmacenado = partes[0];
                String passwordAlmacenado = partes[1];
                if (usernameAlmacenado.equals(username) && passwordAlmacenado.equals(password)) {
                    return true; // Las credenciales son válidas
                }
            }
        } catch (IOException e) {
            System.out.println("Error al verificar las credenciales.");
            e.printStackTrace();
        }
        return false; // Las credenciales no son válidas
    }
    

    private static void menuUsuario(Usuario usuario, GestiónDeReservas gestorReservas) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú de Usuario:");
            System.out.println("1. Ver habitaciones disponibles");
            System.out.println("2. Reservar habitación");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Ver reservas");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            
            switch (opcion) {
                case 1:
                    // Ver habitaciones disponibles
                    System.out.println("Habitaciones disponibles:");
    List<Habitacion> habitacionesDisponibles = gestorReservas.habitacionesDisponibles(new Date(), new Date());
    for (int i = 0; i < habitacionesDisponibles.size(); i++) {
        Habitacion habitacion = habitacionesDisponibles.get(i);
        System.out.println((i + 1) + ". Tipo: " + habitacion.getTipo() + ", Precio por noche: $" + habitacion.getPrecioPorNoche() + ", Máximo de huéspedes: " + habitacion.getNumMaxHuespedes() + ", Comodidades: " + habitacion.getComodidades());
                    }
                    break;
                    case 2:
                    // Reservar habitación
                    System.out.println("Reservar habitación:");
                    System.out.print("Ingrese el número de habitación que desea reservar: ");
                    int numeroHabitacion = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    List<Habitacion> habitacionesDisponiblesReserva = gestorReservas.habitacionesDisponibles(new Date(), new Date());
                    if (numeroHabitacion > 0 && numeroHabitacion <= habitacionesDisponiblesReserva.size()) {
                        Habitacion habitacionReserva = habitacionesDisponiblesReserva.get(numeroHabitacion - 1);
                        System.out.print("Ingrese la fecha de inicio de la reserva (DD/MM/AAAA): ");
                        String fechaInicioStr = scanner.nextLine();
                        System.out.print("Ingrese la fecha de fin de la reserva (DD/MM/AAAA): ");
                        String fechaFinStr = scanner.nextLine();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date fechaInicio = formatter.parse(fechaInicioStr);
                            Date fechaFin = formatter.parse(fechaFinStr);
                            gestorReservas.reservarHabitacion(usuario, habitacionReserva, fechaInicio, fechaFin);
                        } catch (Exception e) {
                            System.out.println("Error al procesar las fechas.");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Número de habitación no válido.");
                    }
                    break;
                    case 3:
                    // Cancelar reserva
                    System.out.println("Cancelar reserva:");
                    List<Reserva> reservasUsuario = cargarReservasUsuario(usuario);
                    if (reservasUsuario.isEmpty()) {
                        System.out.println("No tienes reservas activas para cancelar.");
                    } else {
                        System.out.println("Reservas activas:");
                        for (int i = 0; i < reservasUsuario.size(); i++) {
                            Reserva reserva = reservasUsuario.get(i);
                            System.out.println((i+1) + ". Habitación: " + reserva.getHabitacion().getTipo() + ", Fecha de inicio: " + reserva.getFechaInicio() + ", Fecha de fin: " + reserva.getFechaFin());
                        }
                        System.out.print("Seleccione el número de reserva que desea cancelar: ");
                        int numeroReserva = scanner.nextInt();
                        scanner.nextLine(); // Consumir la nueva línea
                        if (numeroReserva > 0 && numeroReserva <= reservasUsuario.size()) {
                            Reserva reservaCancelar = reservasUsuario.get(numeroReserva - 1);
                            gestorReservas.cancelarReserva(usuario, reservaCancelar);
                        } else {
                            System.out.println("Número de reserva no válido.");
                        }
                    }
                    break;
                    case 4:
                    // Ver reservas
                    System.out.println("Ver reservas:");
                    List<Reserva> reservasUsuarioVer = cargarReservasUsuario(usuario);
                    if (reservasUsuarioVer.isEmpty()) {
                        System.out.println("No tienes reservas activas.");
                    } else {
                        System.out.println("Reservas activas:");
                        for (Reserva reserva : reservasUsuarioVer) {
                            System.out.println("Habitación: " + reserva.getHabitacion().getTipo() + ", Fecha de inicio: " + reserva.getFechaInicio() + ", Fecha de fin: " + reserva.getFechaFin());
                        }
                    }
                    break;
                case 5:
                System.out.println("Cerrando sesión...");
                return;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
    
    private static List<Reserva> cargarReservasUsuario(Usuario usuario) {
        List<Reserva> reservas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("reservas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuario.getUsername())) {
                    Habitacion habitacion = new Habitacion(partes[1], 0.0f, 0, ""); // No tenemos más detalles en el archivo
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date fechaInicio = formatter.parse(partes[2]);
                    Date fechaFin = formatter.parse(partes[3]);
                    reservas.add(new Reserva(usuario, habitacion, fechaInicio, fechaFin));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las reservas.");
            e.printStackTrace();
        }
        return reservas;
    }

    private static void menuAdministrador(Administrador admin, GestiónDeReservas gestorReservas) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menú de Administrador:");
            System.out.println("1. Ver reservas");
            System.out.println("2. Añadir habitación");
            System.out.println("3. Eliminar habitación");
            System.out.println("4. Modificar habitación");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea
            
            switch (opcion) {
                case 1:
                // Ver reservas
                System.out.println("Reservas:");
                List<Reserva> reservas = ArchivoReservas.cargarReservas();
                if (reservas != null && !reservas.isEmpty()) {
                    for (Reserva reserva : reservas) {
                        System.out.println("Usuario: " + reserva.getUsuario().getUsername() + ", Habitación: " + reserva.getHabitacion().getTipo() +
                                ", Fecha de inicio: " + reserva.getFechaInicio() + ", Fecha de fin: " + reserva.getFechaFin());
                    }
                } else {
                    System.out.println("No hay reservas.");
                }
                break;
                case 2:
                // Añadir habitación
                System.out.println("Añadir habitación:");
                System.out.print("Ingrese el tipo de habitación: ");
                String tipoHabitacion = scanner.nextLine();
                System.out.print("Ingrese el precio por noche: ");
                float precioPorNoche = scanner.nextFloat();
                System.out.print("Ingrese el número máximo de huéspedes: ");
                int numMaxHuespedes = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                System.out.print("Ingrese las comodidades (separadas por comas): ");
                String comodidades = scanner.nextLine();
                // Guardar los detalles de la nueva habitación en el archivo de habitaciones disponibles
                guardarHabitacion(tipoHabitacion, precioPorNoche, numMaxHuespedes, comodidades, "habitaciones.txt");
                System.out.println("Habitación añadida correctamente.");
                break;
                case 3:
                System.out.println("Eliminar habitación:");
                System.out.print("Ingrese el tipo de habitación que desea eliminar: ");
                String tipoHabitacionEliminar = scanner.nextLine();
                gestorReservas.eliminarHabitacion(tipoHabitacionEliminar);
                    break;
                case 4:
                System.out.println("Modificar habitación:");
                List<Habitacion> habitaciones = gestorReservas.cargarHabitaciones();
                System.out.println("Habitaciones disponibles:");
                for (int i = 0; i < habitaciones.size(); i++) {
                    System.out.println((i+1) + ". " + habitaciones.get(i).getTipo());
                }
                System.out.print("Seleccione el número de habitación que desea modificar: ");
                int numeroHabitacion = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                if (numeroHabitacion > 0 && numeroHabitacion <= habitaciones.size()) {
                    Habitacion habitacion = habitaciones.get(numeroHabitacion - 1);
                    System.out.println("¿Qué desea modificar?");
                    System.out.println("1. Precio por noche");
                    System.out.println("2. Número máximo de huéspedes");
                    System.out.println("3. Comodidades");
                    System.out.print("Seleccione una opción: ");
                    int opcionModificar = scanner.nextInt();
                    scanner.nextLine(); // Consumir la nueva línea
                    switch (opcionModificar) {
                        case 1:
                            System.out.print("Nuevo precio por noche: ");
                            float nuevoPrecioPorNoche = scanner.nextFloat();
                            habitacion.setPrecioPorNoche(nuevoPrecioPorNoche);
                            break;
                        case 2:
                            System.out.print("Nuevo número máximo de huéspedes: ");
                            int nuevoNumMaxHuespedes = scanner.nextInt();
                            habitacion.setNumMaxHuespedes(nuevoNumMaxHuespedes);
                            break;
                        case 3:
                            System.out.print("Nuevas comodidades: ");
                            String nuevasComodidades = scanner.nextLine();
                            habitacion.setComodidades(nuevasComodidades);
                            break;
                        default:
                            System.out.println("Opción no válida.");
                            break;
                    }
                    gestorReservas.guardarHabitaciones(habitaciones);
                    System.out.println("Habitación modificada exitosamente.");
                } else {
                    System.out.println("Número de habitación no válido.");
                }
                break;
                    
                case 5:
                    // Cerrar sesión
                    return;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
    
    private static void guardarRegistro(String username, String password, String archivo) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar el registro.");
            e.printStackTrace();
        }
    }
    private static void guardarHabitacion(String tipo, float precioPorNoche, int numMaxHuespedes, String comodidades, String archivo) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(tipo + "," + precioPorNoche + "," + numMaxHuespedes + "," + comodidades + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar la habitación.");
            e.printStackTrace();
        }
    }
    
    
}