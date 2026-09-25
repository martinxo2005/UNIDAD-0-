package Laboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionDB {

    private Nomina calculaNomina = new Nomina();

    /**
     * 2.1, 2.2 y 2.3 Metodo para guardar o actualizar un empleado y su sueldo en la base de datos
     */
    public void guardarOActualizarEmpleado(Empleados emp) throws SQLException {
        String sqlEmpleado = "INSERT INTO Empleados (dni, nombre, sexo, categoria, anyos) " +
                             "VALUES (?, ?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE nombre=?, sexo=?, categoria=?, anyos=?";
                             
        String sqlNomina = "INSERT INTO Nominas (dni, sueldo) VALUES (?, ?) " +
                           "ON DUPLICATE KEY UPDATE sueldo=?";

        try (Connection conn = ConexionDB.getConexion()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement psEmp = conn.prepareStatement(sqlEmpleado)) {
                    psEmp.setString(1, emp.dni);
                    psEmp.setString(2, emp.nombre);
                    psEmp.setString(3, String.valueOf(emp.sexo));
                    psEmp.setInt(4, emp.getCategoria());
                    psEmp.setInt(5, emp.anyos);

                    psEmp.setString(6, emp.nombre);
                    psEmp.setString(7, String.valueOf(emp.sexo));
                    psEmp.setInt(8, emp.getCategoria());
                    psEmp.setInt(9, emp.anyos);

                    psEmp.executeUpdate();
                }

                int sueldoCalculado = calculaNomina.sueldo(emp);
                try (PreparedStatement psNom = conn.prepareStatement(sqlNomina)) {
                    psNom.setString(1, emp.dni);
                    psNom.setInt(2, sueldoCalculado);
                    psNom.setInt(3, sueldoCalculado);

                    psNom.executeUpdate();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    /**
     * 5.1 Metodo para obtener todos los empleados registrados en la base de datos
     */
    public List<Empleados> obtenerTodosEmpleados() throws SQLException, DatosNoCorrectosException {
        List<Empleados> lista = new ArrayList<>();
        String sql = "SELECT dni, nombre, sexo, categoria, anyos FROM Empleados";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");

                Empleados emp = new Empleados(categoria, anyos, nombre, dni, sexo);
                lista.add(emp);
            }
        }
        return lista;
    }

    /**
     * Metodo para consultar un empleado por su DNI
     */
    public Empleados obtenerEmpleadoPorDni(String dni) throws SQLException, DatosNoCorrectosException {
        String sql = "SELECT dni, nombre, sexo, categoria, anyos FROM Empleados WHERE dni = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    char sexo = rs.getString("sexo").charAt(0);
                    int categoria = rs.getInt("categoria");
                    int anyos = rs.getInt("anyos");

                    return new Empleados(categoria, anyos, nombre, dni, sexo);
                }
            }
        }
        return null;
    }

    /**
     * 5.2 Metodo para obtener el salario de un empleado especifico por su DNI desde la base de datos
     */
    public Integer obtenerSueldoPorDni(String dni) throws SQLException {
        String sql = "SELECT sueldo FROM Nominas WHERE dni = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("sueldo");
                }
            }
        }
        return null;
    }

    /**
     * 5.4 Metodo para recalcular y actualizar el sueldo de un empleado
     */
    public boolean recalcularSueldoEmpleado(String dni) throws SQLException, DatosNoCorrectosException {
        Empleados emp = obtenerEmpleadoPorDni(dni);
        if (emp != null) {
            guardarOActualizarEmpleado(emp);
            return true;
        }
        return false;
    }

    /**
     * 5.5 Metodo para recalcular y actualizar los sueldos de todos los empleados
     */
    public void recalcularTodosSueldos() throws SQLException, DatosNoCorrectosException {
        List<Empleados> lista = obtenerTodosEmpleados();
        for (Empleados emp : lista) {
            guardarOActualizarEmpleado(emp);
        }
    }
}