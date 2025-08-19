package Login;

import conexion.Conexion;
import menu.Menu;

import javax.swing.*;
import java.sql.*;

public class login extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton limpiarButton;
    private JButton ingresarButton;

    public login() {
        setTitle("Login - Sistema Facturación");
        setContentPane(panel1);
        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ingresarButton.addActionListener(e -> validarLogin());
    }

    private void validarLogin() {
        String user = textField1.getText().trim();
        String pass = new String(passwordField1.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Usuario y contraseña son obligatorios");
            return;
        }

        try (Connection con = Conexion.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario WHERE username=? AND password=?");
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + user);
                new Menu().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        limpiarButton.addActionListener(e ->  {
            textField1.setText("");
            passwordField1.setText("");
        });
    }

}
