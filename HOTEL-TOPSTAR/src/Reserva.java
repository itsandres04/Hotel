import java.util.Date;
class Reserva {
    private Usuario usuario;
    private Habitacion habitacion;
    private Date fechaInicio;
    private Date fechaFin;
    
    public Reserva(Usuario usuario, Habitacion habitacion, Date fechaInicio, Date fechaFin) {
        this.usuario = usuario;
        this.habitacion = habitacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public Habitacion getHabitacion() {
        return habitacion;
    }
    
    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public Date getFechaFin() {
        return fechaFin;
    }
}