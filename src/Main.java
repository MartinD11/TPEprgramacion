import algoritmos.Backtracking;
import algoritmos.Greedy;
import algoritmos.Paquete;
import algoritmos.Solucion;
import servicios.Servicios;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String pathCamiones = "src/archivos/Camiones.csv";
        String pathPaquetes = "src/archivos/Paquetes.csv";

        Servicios servicios = new Servicios(pathCamiones, pathPaquetes);

        System.out.println("servicios:");
        Paquete p = servicios.servicio1("P010");
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("No se encontró ningún paquete con el código P010.");
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("Paquetes con rango de urgencia entre 1 y 100:");
        List<Paquete> paquetesPorUrgencia = servicios.servicio3(1, 100);
        for (Paquete paquete : paquetesPorUrgencia) {
            System.out.println(" * " + paquete);
        }
        System.out.println("--------------------------------------------------\n");

        System.out.println("Paquetes que contienen alimentos:");
        List<Paquete> paquetesConAlimentos = servicios.servicio2(true);
        for (Paquete paquete : paquetesConAlimentos) {
            System.out.println(" * " + paquete);
        }
        System.out.println("--------------------------------------------------\n");

        Backtracking backtracking = new Backtracking();
        Greedy greedy = new Greedy();
        //PREGUNTAR SI ES NECESARIO SABER LOS SEGUNDOS QUE TARDAN LOS METODOS O CON LOS ESTADOS GENERADOS Y CANT DE CANDIDATOS ES SUFICIENTE.
        // Ejecución y medición de algoritmos.Backtracking
        Solucion solucionBacktracking = backtracking.resolver(servicios.getCamiones(), servicios.getPaquetes());

        System.out.println(solucionBacktracking.obtenerReporte("algoritmos.Backtracking"));
        System.out.println("--------------------------------------------------\n");

        // Ejecución y medición de algoritmos.Greedy
        Solucion solucionGreedy = greedy.resolver(servicios.getCamiones(), servicios.getPaquetes());

        System.out.println(solucionGreedy.obtenerReporte("algoritmos.Greedy"));
        System.out.println("--------------------------------------------------\n");
    }
}