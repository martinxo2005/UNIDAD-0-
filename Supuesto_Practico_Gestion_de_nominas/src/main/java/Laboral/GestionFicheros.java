package Laboral;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionFicheros {

    /**
     * 1.1 y 1.2 Metodos para leer empleados desde un fichero de texto
     */
    public static List<Empleados> leerEmpleadosTexto(String rutaFichero) {
        List<Empleados> lista = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String nombre = partes[0].trim();
                    String dni = partes[1].trim();
                    char sexo = partes[2].trim().charAt(0);
                    int categoria = Integer.parseInt(partes[3].trim());
                    int anyos = Integer.parseInt(partes[4].trim());

                    Empleados emp = new Empleados(categoria, anyos, nombre, dni, sexo);
                    lista.add(emp);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de texto: " + e.getMessage());
        } catch (DatosNoCorrectosException e) {
            System.err.println("Datos incorrectos en fichero de empleados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Formato numérico incorrecto en el fichero: " + e.getMessage());
        }
        
        return lista;
    }

    /**
     * Metodo para escribir la lista de empleados en un fichero de texto (Backup)
     */
    public static void escribirEmpleadosTexto(List<Empleados> lista, String rutaFichero) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaFichero))) {
            for (Empleados emp : lista) {
                pw.println(emp.nombre + "," + emp.dni + "," + emp.sexo + "," + emp.getCategoria() + "," + emp.anyos);
            }
            System.out.println("Fichero texto '" + rutaFichero + "' guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al escribir el fichero de texto: " + e.getMessage());
        }
    }
}