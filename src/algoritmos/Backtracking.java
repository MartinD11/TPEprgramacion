package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {

    private double mejorPesoNoAsignado;
    private Map<Camion, List<Paquete>> mejorDistribucion;
    private int estadosGenerados;
    private static final String TIPO_BACKTRACKING = "estados generados";
    /*
     * estrategia backtracking:
       La idea es recorrer el arbol de exploracion bucando la smejores soluciones, entonces para cada paquete vamos a hacer lo siguiente:
       -por un lado vamos a asignar a un camion recorriendo los mismos y si el paquete respeta las restricciones(refrigerado o no y el espacio)
       lo asignamos al camion y cuando volvemos hacia atras deshacemos los cambios..
       -por otro lado decidimos no asignar si vemos que no entra en ningun lado o que conviene dejarlo afuera y lo dejamos como pesonoasignado.

       las podas que utilizamos y sus explicaciones:
       poda simple:se lleva el registro del mejorPesoNoAsignado global. si en cualquier camino que estamos probando vemos que superamos o que
       igualamos ese peso,cortamos esa rama, ya que no tiene sentido seguir explorando una rama que nos va a dar una peor solucion que la ay encontrada.

       poda por encaje perfecto: esta consiste en que si al meter un paquete en un camion el espacio queda en cero justo, quiere decir que encontre la mejor
       solucion posible para ese paquete. por lo tanto activamos la bandera booleana esIgual=true para dejar de probar otros camiones y no perder el tiempo
       generando permutaciones inncesarias.

       a su vez para que el algoritmo sea mas eficiente y no tengamos que hacer recorridos por demas en listas, utilizamos los hashmaps para podr tener acceso
       o(1) consultando el peso de cada camion
     */

    public Solucion resolver(List<Camion> camiones, List<Paquete> paquetes) {
        this.mejorPesoNoAsignado = Double.MAX_VALUE;
        this.mejorDistribucion = new HashMap<>();
        this.estadosGenerados = 0;

        Map<Camion, List<Paquete>> distribucionActual = new HashMap<>();
        Map<Camion, Double> pesoOcupado = new HashMap<>();

        for (Camion c : camiones) {
            distribucionActual.put(c, new ArrayList<>());
            //utilizamos un hashmap  para poder acceder de forma 0(1) al peso de cada camion sin tener que
            //recorrer y hacer la suma, cada vez que necesitamos saber si es posible asignar un paquete o no
            pesoOcupado.put(c, 0.0);
        }

        buscarSolucion(camiones, paquetes, 0, distribucionActual, pesoOcupado, 0.0);
        //preguntar si esta bien poner este string hardcoedeado para que en el stringbuilder aparezca estados generados o candidatos dependiendo del algoritmo
        return new Solucion(mejorDistribucion, mejorPesoNoAsignado, estadosGenerados,TIPO_BACKTRACKING);
    }

    private void buscarSolucion(List<Camion> camiones, List<Paquete> paquetes, int indice, Map<Camion, List<Paquete>> distribucionActual,
                                Map<Camion, Double> pesoOcupado, double pesoNoAsignadoActual) {
        estadosGenerados++;

        //poda: si me paso o igualo el peso, quiere decir que no tiene sentido seguir explorando dicha rama
        if (pesoNoAsignadoActual >= mejorPesoNoAsignado) {
            return;
        }

        // condicion de corte, mientras no tengamos mas paquetes para asignar y tengamos un mejor peso, entonces agregamos
        if (indice == paquetes.size()) {
            if (pesoNoAsignadoActual < mejorPesoNoAsignado) {
                mejorPesoNoAsignado = pesoNoAsignadoActual;
                mejorDistribucion = clonarDistribucion(distribucionActual);
            }
            return;
        }

        boolean esIgual = false;
        Paquete paqueteActual = paquetes.get(indice);
        //comenzamos el recorrido para poder asignar los paquetes a los camiones
        for (int i = 0; i < camiones.size() && !esIgual; i++) {
            Camion c = camiones.get(i);
            boolean cumpleRefrigeracion = !paqueteActual.isContieneAlimentos() || c.estaRefrigerado();
            boolean cumpleCapacidad = (pesoOcupado.get(c) + paqueteActual.getPesoKg()) <= c.getCapacidadKg();

            //si cumplen las condiciones necesarias, entonces agregamos los paquetes
            if (cumpleRefrigeracion && cumpleCapacidad) {

                distribucionActual.get(c).add(paqueteActual);
                pesoOcupado.put(c, pesoOcupado.get(c) + paqueteActual.getPesoKg());

                buscarSolucion(camiones, paquetes, indice + 1, distribucionActual, pesoOcupado, pesoNoAsignadoActual);

                distribucionActual.get(c).remove(distribucionActual.get(c).size() - 1);
                pesoOcupado.put(c, pesoOcupado.get(c) - paqueteActual.getPesoKg());

                //si el peso es perfecto, ponemos la bandera en true dandonos a entender que no tiene sentido seguir buscando en la rama si logramos un
                //encaje perfecto(esto nos ahorra seguir preguntando por otros camiones)
                if (pesoOcupado.get(c) + paqueteActual.getPesoKg() == c.getCapacidadKg()) {
                    esIgual = true;
                }
            }
        }
        //si no es posibel asignar un paquete, lo que hacemos es llamar nuevamente a la recursion
        if(!esIgual) {
            buscarSolucion(camiones, paquetes, indice + 1, distribucionActual, pesoOcupado, pesoNoAsignadoActual + paqueteActual.getPesoKg());
        }

    }

    //hacemos una copia, ya  que si igualamos, estariamos igualando a lugar en memoria y no estariamos copiando el objeto de "verdad"
    private Map<Camion, List<Paquete>> clonarDistribucion(Map<Camion, List<Paquete>> original) {
        Map<Camion, List<Paquete>> copia = new HashMap<>();
        for (Map.Entry<Camion, List<Paquete>> entry : original.entrySet()) {
            copia.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copia;
    }
}