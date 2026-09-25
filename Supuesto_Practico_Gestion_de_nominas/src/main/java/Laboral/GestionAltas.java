package Laboral;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class GestionAltas {

    private GestionDB gestionDB = new GestionDB();

    /**
     * 3. Metodo altaEmpleado para dar de alta un empleado individual
     */
    public void altaEmpleado(Empleados emp) throws SQLException {
        gestionDB.guardarOActualizarEmpleado(emp);
        System.out.println("Empleado " + emp.nombre + " (" + emp.dni + ") dado de alta con éxito.");
    }

    /**
     * 3.1 Sobrecarga del metodo altaEmpleado para dar de alta por lotes desde un fichero de texto
     */
    public void altaEmpleado(File ficheroNuevos) {
        if (!ficheroNuevos.exists()) {
            System.err.println("El fichero " + ficheroNuevos.getName() + " no existe.");
            return;
        }

        List<Empleados> nuevosEmpleados = GestionFicheros.leerEmpleadosTexto(ficheroNuevos.getPath());

        int procesados = 0;
        for (Empleados emp : nuevosEmpleados) {
            try {
                altaEmpleado(emp);
                procesados++;
            } catch (SQLException e) {
                System.err.println("Error al insertar al empleado " + emp.dni + ": " + e.getMessage());
            }
        }
        System.out.println("Proceso de alta por lotes finalizado. Total procesados: " + procesados);
    }
}