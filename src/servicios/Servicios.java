package servicios;

import algoritmos.Camion;
import algoritmos.Paquete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servicios {

    //preguntar si seria viable agregar hahsmaps para cargar durante la lectura de los datos y hacer o(1) la complejidad de los metodos
    private List<Camion> camiones;
    private List<Paquete> paquetes;
    private Map<Boolean, List<Paquete>> mapaPaquetesPorAlimento;
    private Map<String, Paquete> mapaPaquetesPorCodigo;

    //complejidad de o(p + c) donde vamos a depender de la cantidad de paquetes y camiones que tengamos que cargar
    public Servicios(String pathCamiones, String pathPaquetes) {
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.mapaPaquetesPorCodigo = new HashMap<>();
        this.mapaPaquetesPorAlimento = new HashMap<>();
        this.mapaPaquetesPorAlimento.put(true, new ArrayList<>());
        this.mapaPaquetesPorAlimento.put(false, new ArrayList<>());
        cargarCamiones(pathCamiones);
        cargarPaquetes(pathPaquetes);
    }

    private void cargarCamiones(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                if (datos.length >= 4) {
                    int id = Integer.parseInt(datos[0].trim());
                    String patente = datos[1].trim();
                    boolean estaRefrigerado = datos[2].trim().equals("1");
                    double capacidad = Double.parseDouble(datos[3].trim());

                    camiones.add(new Camion(id, patente, estaRefrigerado, capacidad));
                }
            }
        } catch (IOException e) {
            System.err.println("error leyendo los archivos: " + e.getMessage());
        }
    }

    private void cargarPaquetes(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();

            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                if (datos.length >= 5) {
                    int id = Integer.parseInt(datos[0].trim());
                    String codigo = datos[1].trim();
                    double peso = Double.parseDouble(datos[2].trim());
                    boolean contieneAlimentos = datos[3].trim().equals("1");
                    int urgencia = Integer.parseInt(datos[4].trim());

                    Paquete nuevoPaquete = new Paquete(id, codigo, peso, contieneAlimentos, urgencia);

                    paquetes.add(nuevoPaquete);

                    mapaPaquetesPorCodigo.put(codigo, nuevoPaquete);

                    mapaPaquetesPorAlimento.get(contieneAlimentos).add(nuevoPaquete);
                }
            }
        } catch (IOException e) {
            System.err.println("error leyendo los paquetess: " + e.getMessage());
        }
    }

    /*Servicio 1: complejidad O(1) ya que esta guardado en un hashmap en el cual se accede mediante el codigo*/
    public Paquete servicio1(String codigo){
        return mapaPaquetesPorCodigo.get(codigo);
    }

    /*Servicio 2: complejidad O(1)ya que tenemos cargada la lista desde que leemos los datos del csv*/
    public List<Paquete> servicio2(boolean contieneAlimentos){
        return mapaPaquetesPorAlimento.get(contieneAlimentos);
    }

    //SERIA NECESARIO UTILIZAR UN ARBOL ROJO/NEGRO(LOG N + K) PARA MEJORAR LA EFICIENCIA O CON ESTA BUSQUEDA O(N) ESTA BIEN?
    /*Servicio 3: la complejidad es O(n) ya que vamos a recorrer  N paquetes hasta el final
    * para poder saber si cumplen con la condicion*/
    public List<Paquete> servicio3(int min,int max){
        List<Paquete> paquetesByRangoUrgencia = new ArrayList<>();
        for (Paquete paquete : paquetes) {
            if(paquete.getNivelUrgencia() >= min && paquete.getNivelUrgencia() <= max){
                paquetesByRangoUrgencia.add(paquete);
            }
        }
        return paquetesByRangoUrgencia;
    }

    public List<Camion> getCamiones() {
        return this.camiones;
    }

    public List<Paquete> getPaquetes() {
        return this.paquetes;
    }
}