package algoritmos;

import java.util.List;
import java.util.Map;

public class Solucion {
    private Map<Camion, List<Paquete>> distribucion;
    private double pesoNoAsignado;
    private int metricaCosto;
    private String nombreMetrica;

    public Solucion(Map<Camion, List<Paquete>> distribucion, double pesoNoAsignado, int metricaCosto, String nombreMetrica) {
        this.distribucion = distribucion;
        this.pesoNoAsignado = pesoNoAsignado;
        this.metricaCosto = metricaCosto;
        this.nombreMetrica = nombreMetrica;
    }

    public String obtenerReporte(String titulo) {
        StringBuilder sb = new StringBuilder();

        sb.append("--- ").append(titulo).append(" ---\n");
        sb.append("Solución obtenida:\n");

        for (Map.Entry<Camion, List<Paquete>> entry : distribucion.entrySet()) {
            Camion c = entry.getKey();
            sb.append("Camión ").append(c.getPatente()).append(": [");

            for (int i = 0; i < entry.getValue().size(); i++) {
                sb.append(entry.getValue().get(i).getCodigo());
                if (i < entry.getValue().size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }

        sb.append("Peso no asignado: ").append(pesoNoAsignado).append(" kg.\n");

        sb.append("Métrica de costo (").append(nombreMetrica).append("): ").append(metricaCosto).append("\n");

        return sb.toString();
    }
}