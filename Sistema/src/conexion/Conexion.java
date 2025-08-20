package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/banco?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // si tienes contraseña, escríbela aquí

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


/* package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/ventas_sistema";
    private static final String USER = "root";  // usuario por defecto de XAMPP
    private static final String PASS = "";      // contraseña (si la dejaste en blanco en phpMyAdmin)

    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Conexión exitosa a MySQL");
        } catch (SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
        }
        return conn;
    }
}
 */