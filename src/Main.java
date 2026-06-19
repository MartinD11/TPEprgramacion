import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pathCamiones = "src/Camiones.csv";
        String pathPaquetes = "src/Paquetes.csv";

        Servicio servicios = new Servicio(pathCamiones, pathPaquetes);

        System.out.println("servicios:");
        Paquete p = servicios.getPaqueteByCodigo("P010");
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("No se encontró ningún paquete con el código P010.");
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("Paquetes con rango de urgencia entre 1 y 100:");
        List<Paquete> paquetesPorUrgencia = servicios.getPaquetesByRangoUrgencia(1, 100);
        for (Paquete paquete : paquetesPorUrgencia) {
            System.out.println(" * " + paquete);
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("Paquetes que contienen alimentos:");
        List<Paquete> paquetesConAlimentos = servicios.getPaquetesConAlimentos(true);
        for (Paquete paquete : paquetesConAlimentos) {
            System.out.println(" * " + paquete);
        }
        System.out.println("--------------------------------------------------\n");

        Backtracking backtracking = new Backtracking();
        Greedy greedy = new Greedy();
        //PREGUNTAR SI ES NECESARIO SABER LOS SEGUNDOS QUE TARDAN LOS METODOS O CON LOS ESTADOS GENERADOS Y CANT DE CANDIDATOS ES SUFICIENTE.
        // Ejecución y medición de Backtracking
        Solucion solucionBacktracking = backtracking.resolver(servicios.getCamiones(), servicios.getPaquetes());

        System.out.println(solucionBacktracking.obtenerReporte("Backtracking"));
        System.out.println("--------------------------------------------------\n");

        // Ejecución y medición de Greedy
        Solucion solucionGreedy = greedy.resolver(servicios.getCamiones(), servicios.getPaquetes());

        System.out.println(solucionGreedy.obtenerReporte("Greedy"));
        System.out.println("--------------------------------------------------\n");
    }
}