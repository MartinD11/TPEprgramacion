public class Main {
    public static void main(String[] args) {
        // rutas a los archivos csv
        String pathCamiones = "src/Camiones.csv";
        String pathPaquetes = "src/Paquetes.csv";

        Servicio servicios = new Servicio(pathCamiones, pathPaquetes);

        Backtracking backtracking = new Backtracking();
        Greedy greedy = new Greedy();

        long inicioBack = System.currentTimeMillis();

        Solucion solucionBacktracking = backtracking.resolver(servicios.getCamiones(), servicios.getPaquetes());

        long finBack = System.currentTimeMillis();
        solucionBacktracking.imprimirReporte("Backtracking");
        System.out.println("Tiempo de ejecución Backtracking: " + (finBack - inicioBack) + " ms");
        System.out.println("--------------------------------------------------\n");

        // 4. Ejecutamos y medimos Greedy
        System.out.println("🚀 Ejecutando algoritmo Greedy...");
        long inicioGreedy = System.currentTimeMillis();

        Solucion solucionGreedy = greedy.resolver(servicios.getCamiones(), servicios.getPaquetes());

        long finGreedy = System.currentTimeMillis();
        solucionGreedy.imprimirReporte("Greedy");
        System.out.println("⏱️ Tiempo de ejecución Greedy: " + (finGreedy - inicioGreedy) + " ms");
        System.out.println("--------------------------------------------------\n");
    }
}