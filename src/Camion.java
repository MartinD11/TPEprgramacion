public class Camion {
    private int id;
    private String patente;
    private boolean refrigerado; // 1 si, 0 no
    private double capacidadKg;

    public Camion(int id, String patente, boolean refrigerado, double capacidadKg) {
        this.id = id;
        this.patente = patente;
        this.refrigerado = refrigerado;
        this.capacidadKg = capacidadKg;
    }

    public int getId() { return id; }
    public String getPatente() { return patente; }
    public boolean estaRefrigerado() { return refrigerado; }
    public double getCapacidadKg() { return capacidadKg; }

    // Necesarios para poder usar Camion como Key en el HashMap de la Solución
    @Override
    public int hashCode() { return Integer.hashCode(id); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return id == ((Camion) obj).id;
    }
}