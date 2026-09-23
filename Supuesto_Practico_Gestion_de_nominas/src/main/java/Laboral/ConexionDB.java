package Laboral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Configuracion de conexión a la bbdd
    private static final String URL = "jdbc:mysql://localhost:3306/empresa_nominas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";
    private static final String database = "empresa_nominas";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}

