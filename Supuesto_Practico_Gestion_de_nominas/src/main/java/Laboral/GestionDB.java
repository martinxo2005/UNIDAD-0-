package Laboral;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestionDB {

    private Nomina calculaNomina = new Nomina();

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

    public boolean recalcularSueldoEmpleado(String dni) throws SQLException, DatosNoCorrectosException {
        Empleados emp = obtenerEmpleadoPorDni(dni);
        if (emp != null) {
            guardarOActualizarEmpleado(emp);
            return true;
        }
        return false;
    }

    public void recalcularTodosSueldos() throws SQLException, DatosNoCorrectosException {
        List<Empleados> lista = obtenerTodosEmpleados();
        for (Empleados emp : lista) {
            guardarOActualizarEmpleado(emp);
        }
    }

    /**
     * Búsqueda dinámica con filtros para la web
     */
    public List<Empleados> buscarFiltrado(String dni, String nombre, String sexo, String categoria, String anyos) 
            throws SQLException, DatosNoCorrectosException {
        
        List<Empleados> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT dni, nombre, sexo, categoria, anyos FROM Empleados WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (dni != null && !dni.trim().isEmpty()) {
            sql.append(" AND dni LIKE ?");
            params.add("%" + dni.trim() + "%");
        }
        if (nombre != null && !nombre.trim().isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + nombre.trim() + "%");
        }
        if (sexo != null && !sexo.trim().isEmpty()) {
            sql.append(" AND sexo = ?");
            params.add(sexo.trim());
        }
        if (categoria != null && !categoria.trim().isEmpty()) {
            sql.append(" AND categoria = ?");
            params.add(Integer.parseInt(categoria.trim()));
        }
        if (anyos != null && !anyos.trim().isEmpty()) {
            sql.append(" AND anyos = ?");
            params.add(Integer.parseInt(anyos.trim()));
        }

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String d = rs.getString("dni");
                    String n = rs.getString("nombre");
                    char s = rs.getString("sexo").charAt(0);
                    int c = rs.getInt("categoria");
                    int a = rs.getInt("anyos");

                    lista.add(new Empleados(c, a, n, d, s));
                }
            }
        }
        return lista;
    }
}