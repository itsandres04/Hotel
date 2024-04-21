class Habitacion {
    private String tipo;
    private float precioPorNoche;
    private int numMaxHuespedes;
    private String comodidades;
    
    public Habitacion() {
    }

    public Habitacion(String tipo, float precioPorNoche, int numMaxHuespedes, String comodidades) {
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.numMaxHuespedes = numMaxHuespedes;
        this.comodidades = comodidades;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(float precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public int getNumMaxHuespedes() {
        return numMaxHuespedes;
    }

    public void setNumMaxHuespedes(int numMaxHuespedes) {
        this.numMaxHuespedes = numMaxHuespedes;
    }

    public String getComodidades() {
        return comodidades;
    }

    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }
    
    
}