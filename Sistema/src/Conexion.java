
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conexion() {
        String url = "jdbc:mysql:127.0.0.1//3306/ventas_sistema";
        String user = "root";
        String password = "";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Estamos conectados");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

        public static void main(String[]args){
            Conexion conexion=new Conexion();
            conexion.conexion();

    }

}
