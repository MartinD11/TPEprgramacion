import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Servicio {

    // Colecciones para almacenar los datos en memoria
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
            // Leemos la primera línea (cantidad total de camiones) y avanzamos
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                // Separamos por punto y coma según el formato: <id>;<patente>;<refrigerado>;<capacidad>
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
            System.err.println("Error al leer el archivo de camiones: " + e.getMessage());
        }
    }

    private void cargarPaquetes(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Leemos la primera línea (cantidad total de paquetes) y avanzamos
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                // Separamos por punto y coma según el formato: <id>;<codigo>;<peso>;<alimentos>;<urgencia>
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
            System.err.println("Error al leer el archivo de paquetes: " + e.getMessage());
        }
    }

    /*Servicio 3: Dados dos valores enteros que representan un nivel de urgencia mínimo y máximo,
     retornar todos los paquetes cuyo nivel de urgencia se encuentre dentro de ese rango (inclusive).*/
    public Paquete getPaqueteByCodigo(String codigo){
        for (Paquete paquete : paquetes) {
            if(paquete.getCodigo().equals(codigo)){
                return paquete;
            }
        }
        return null;
    }

    /*Servicio 2: Dado un booleano que indica si se buscan paquetes que contienen alimentos (true)
     o que no contienen alimentos (false), retornar el listado de paquetes correspondiente.*/
    public List<Paquete> getPaquetesConAlimentos(boolean contieneAlimentos){
        List<Paquete> paquetesConAlimentos = new ArrayList<>();
        for (Paquete paquete : paquetes) {
            if(paquete.isContieneAlimentos()==contieneAlimentos){
                paquetesConAlimentos.add(paquete);
            }
        }
        return paquetesConAlimentos;
    }

    /*Servicio 3: Dados dos valores enteros que representan un nivel de urgencia mínimo y máximo,
     retornar todos los paquetes cuyo nivel de urgencia se encuentre dentro de ese rango (inclusive).*/
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