import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Servicio {

    //preguntar si seria viable agregar hahsmaps para cargar durante la lectura de los datos y hacer o(1) la complejidad de los metodos
    //getPaqueteByCodigo y getPaquetesConAlimentos

    private List<Camion> camiones;
    private List<Paquete> paquetes;

    public Servicio(String pathCamiones, String pathPaquetes) {
        this.camiones = new ArrayList<>();
        this.paquetes = new ArrayList<>();

        cargarCamiones(pathCamiones);
        cargarPaquetes(pathPaquetes);
    }

    private void cargarCamiones(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

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
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length >= 5) {
                    int id = Integer.parseInt(datos[0].trim());
                    String codigo = datos[1].trim();
                    double peso = Double.parseDouble(datos[2].trim());
                    boolean contieneAlimentos = datos[3].trim().equals("1");
                    int urgencia = Integer.parseInt(datos[4].trim());

                    paquetes.add(new Paquete(id, codigo, peso, contieneAlimentos, urgencia));
                }
            }
        } catch (IOException e) {
            System.err.println("error leyendo los paquetess: " + e.getMessage());
        }
    }

    /*Servicio 1: complejidad O(n) ya que en el peor de los casos, el paquete puede estar a lo ultimo o simplemenet ser null,
    * asi que recorreremos n elementos hasta encontrar el paquete indicado*/
    public Paquete getPaqueteByCodigo(String codigo){
        for (Paquete paquete : paquetes) {
            if(paquete.getCodigo().equals(codigo)){
                return paquete;
            }
        }
        return null;
    }

    /*Servicio 2: complejidad O(n) ya que debemos recorrer N elementos hasta el final*/
    public List<Paquete> getPaquetesConAlimentos(boolean contieneAlimentos){
        List<Paquete> paquetesConAlimentos = new ArrayList<>();
        for (Paquete paquete : paquetes) {
            if(paquete.isContieneAlimentos()==contieneAlimentos){
                paquetesConAlimentos.add(paquete);
            }
        }
        return paquetesConAlimentos;
    }

    /*Servicio 3: la complejidad es O(n) ya que vamos a recorrer  N paquetes hasta el final
    * para poder saber si cumplen con la condicion*/
    public List<Paquete> getPaquetesByRangoUrgencia(int min,int max){
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