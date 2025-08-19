import Login.login;
import conexion.Conexion;

public class Main {
    public static void main(String[] args) {
        new login().setVisible(true);
        new Conexion();
    }
}