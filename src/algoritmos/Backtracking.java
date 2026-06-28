package algoritmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {

    private double mejorPesoNoAsignado;
    private Map<Camion, List<Paquete>> mejorDistribucion;
    private int estadosGenerados;

    /*
     * estrategia backtracking:
     * exploramos el arbol de decisiones completo evaluando tres opciones para cada paquete:
     * asignarlo a un camión que cumpla las restricciones (capacidad y refrigeración), o dejarlo sin asignar.
     * la poda consiste en  llevar  el cálculo del peso total no asignado acumulado. si en una rama el peso de los paquetes
     * que decidimos no asignar ya iguala o supera nuestro mejorPesoNoAsignado histórico, cortamos esa rama
     * porque es imposible mejorar la solución actual y no tiene sentido seguir explorando ramas.
     *
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

        return new Solucion(mejorDistribucion, mejorPesoNoAsignado, estadosGenerados,"estados generados");
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