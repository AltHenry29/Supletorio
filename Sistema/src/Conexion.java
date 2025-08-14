

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conexion(){
        String url = "jdbc:mysql://3306/ventas_sistema";
        String user = "root";
        String password = "root";

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Estamos conectados");
        }
        catch(Exception e)

        {
            System.out.println(e.getMessage());
        }

        return conn;
    }


}
