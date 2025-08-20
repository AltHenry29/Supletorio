package producto;

import conexion.Conexion;
import menu.Menu;

import javax.swing.*;
import java.sql.*;

public class Producto extends JFrame {
    private JPanel panelProducto;
    private JTextField txtCodigo, txtNombre, txtPrecio, txtStock;
    private JButton btnBuscar, btnGuardar, btnexit;

    public Producto() {
        setTitle("Productos");
        setContentPane(panelProducto);
        setSize(400, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        btnBuscar.addActionListener(e -> buscarProducto());
        btnGuardar.addActionListener(e -> guardarProducto());
        btnexit.addActionListener(e -> new Menu().setVisible(true));
    }

    private void buscarProducto() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de producto");
            return;
        }

        try (Connection con = Conexion.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM producto WHERE codigo=?");
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtPrecio.setText(rs.getString("precio"));
                txtStock.setText(rs.getString("stock"));
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void guardarProducto() {
        try (Connection con = Conexion.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO PRODUCTO(codigo,nombre,precio,stock) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE nombre=?, precio=?, stock=?");
            ps.setString(1, txtCodigo.getText().trim());
            ps.setString(2, txtNombre.getText().trim());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText().trim()));
            ps.setInt(4, Integer.parseInt(txtStock.getText().trim()));
            ps.setString(5, txtNombre.getText().trim());
            ps.setDouble(6, Double.parseDouble(txtPrecio.getText().trim()));
            ps.setInt(7, Integer.parseInt(txtStock.getText().trim()));

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto guardado con éxito");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/*public Producto buscarProducto(String codigo) {
    String sql = "SELECT * FROM productos WHERE codigo=?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Producto(rs.getInt("id"), rs.getString("codigo"),
                                rs.getString("nombre"), rs.getDouble("precio"),
                                rs.getInt("stock"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
 */