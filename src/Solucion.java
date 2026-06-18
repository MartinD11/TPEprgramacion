import java.util.List;
import java.util.Map;

public class Solucion {
    private Map<Camion, List<Paquete>> distribucion;
    private double pesoNoAsignado;
    private int metricaCosto;

    public Solucion(Map<Camion, List<Paquete>> distribucion, double pesoNoAsignado, int metricaCosto) {
        this.distribucion = distribucion;
        this.pesoNoAsignado = pesoNoAsignado;
        this.metricaCosto = metricaCosto;
    }

    public void imprimirReporte(String nombreAlgoritmo) {
        System.out.println("--- " + nombreAlgoritmo + " ---");
        System.out.println("Solución obtenida:");
        for (Map.Entry<Camion, List<Paquete>> entry : distribucion.entrySet()) {
            Camion c = entry.getKey();
            System.out.print("Camión " + c.getPatente() + ": [");
            for (Paquete p : entry.getValue()) {
                System.out.print(p.getCodigo() + " ");
            }
            System.out.println("]");
        }
        System.out.println("Peso no asignado: " + pesoNoAsignado + " kg.");
        System.out.println("Métrica de costo (" + (nombreAlgoritmo.equals("Backtracking") ? "estados generados" : "candidatos considerados") + "): " + metricaCosto);
        System.out.println();
    }
}