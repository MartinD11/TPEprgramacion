package algoritmos;

import java.util.*;

public class Greedy {

    /*
      Estrategia algoritmos.Greedy:
        nuestra estrategia greedy consiste en primero priorizar los paquetes que tienen alimentos ya que estos necesitan estar refrigerados
        y corremos el riesgo de no poder asignarlos(estos son prioritarios).
        si ambos paquetes son alimentos  o tambien si no lo son, desempatamos por peso y ubicamos el que tiene mayor peso primero, asi logramos
        meter la mayor cantidad de paquetes posibles en cada camion.
        luego de ordenar lo que vamos a hacer es agarrar el primer paquete y recorrer los camiones para ver en cual lo podemos asignar, siempre teniendo en cuenta
        el espacio disponible.
        luego al final, en caso de no ser asignado, se sumara al peso de paquetes no asignados para poder devolver en la solucion final.
     */
    public Solucion resolver(List<Camion> camiones, List<Paquete> paquetesTotales) {
        int candidatosConsiderados = 0;
        double pesoTotalNoAsignado = 0.0;

        Map<Camion, List<Paquete>> distribucion = new HashMap<>();
        Map<Camion, Double> pesoOcupado = new HashMap<>();

        for (Camion c : camiones) {
            distribucion.put(c, new ArrayList<>());
            pesoOcupado.put(c, 0.0);
        }

        List<Paquete> paquetesCandidatos = new ArrayList<>(paquetesTotales);
        Collections.sort(paquetesCandidatos);

        for (Paquete p : paquetesCandidatos) {
            candidatosConsiderados++;
            boolean asignado = false;
            int i = 0;

            // iteramos mientras haya camiones en la lista Y el paquete aún no haya sido asignado
            while (i < camiones.size() && !asignado) {
                Camion c = camiones.get(i);

                boolean cumpleRefrigeracion = !p.isContieneAlimentos() || c.estaRefrigerado();
                boolean cumpleCapacidad = (pesoOcupado.get(c) + p.getPesoKg()) <= c.getCapacidadKg();

                if (cumpleRefrigeracion && cumpleCapacidad) {
                    distribucion.get(c).add(p);
                    pesoOcupado.put(c, pesoOcupado.get(c) + p.getPesoKg());
                    asignado = true;
                }

                i++;
            }

            if (!asignado) {
                pesoTotalNoAsignado += p.getPesoKg();
            }
        }

        return new Solucion(distribucion, pesoTotalNoAsignado, candidatosConsiderados,"candidatos considerados");
    }
}