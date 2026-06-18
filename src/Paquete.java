public class Paquete implements Comparable<Paquete>{
    private int id;
    private String codigo;
    private double pesoKg;
    private boolean contieneAlimentos;
    private int nivelUrgencia;

    public Paquete(int id, String codigo, double pesoKg, boolean contieneAlimentos, int nivelUrgencia) {
        this.id = id;
        this.codigo = codigo;
        this.pesoKg = pesoKg;
        this.contieneAlimentos = contieneAlimentos;
        this.nivelUrgencia = nivelUrgencia;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public boolean isContieneAlimentos() {
        return contieneAlimentos;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    @Override
    public int compareTo(Paquete otro) {
        // primero ordenamos por si tiene alimento
        if (this.isContieneAlimentos() && !otro.isContieneAlimentos()) {
            return -1;
        }
        if (!this.isContieneAlimentos() && otro.isContieneAlimentos()) {
            return 1;
        }

        //aca ordenamos de mayor a menor por peso
        return Double.compare(otro.getPesoKg(), this.getPesoKg());
    }

    @Override
    public String toString() {
        return "Paquete [" + codigo + "] - Peso: " + pesoKg + " kg | Alimentos: " + (contieneAlimentos ? "Sí" : "No") + " | Urgencia: " + nivelUrgencia;
    }
}