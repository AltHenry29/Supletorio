package cliente;

import conexion.Conexion;
import menu.Menu;

import javax.swing.*;
import java.sql.*;

public class Cliente extends JFrame {
    private JPanel panelcl;
    private JTextField txtCedula, txtNombre, txtApellido, txtTelefono;
    private JButton btnBuscar, btnGuardar, btnexit;

    public Cliente() {
        setTitle("Clientes");
        setContentPane(panelcl);
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnBuscar.addActionListener(e -> buscarCliente());
        btnGuardar.addActionListener(e -> guardarCliente());
        btnexit.addActionListener(e -> new Menu().setVisible(true));
    }

    private void buscarCliente() {
        String cedula = txtCedula.getText().trim();
        try (Connection con = Conexion.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM CLIENTE WHERE cedula=?");
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                txtTelefono.setText(rs.getString("telefono"));
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void guardarCliente() {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cédula y Nombre son obligatorios");
            return;
        }

        try (Connection con = Conexion.getConnection()) {
            // Verificar duplicados
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cliente WHERE cedula=?");
            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Cliente ya existe con esa cédula");
                return;
            }

            // Insertar
            ps = con.prepareStatement("INSERT INTO cliente(cedula, nombre, apellido, telefono) VALUES (?,?,?,?)");
            ps.setString(1, cedula);
            ps.setString(2, nombre);
            ps.setString(3, txtApellido.getText().trim());
            ps.setString(4, txtTelefono.getText().trim());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

