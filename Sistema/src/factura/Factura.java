package factura;

import conexion.Conexion;
import menu.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Factura extends JFrame {
    private JPanel panelFactura;
    private JTextField txtCedula, txtCodigo, txtCantidad;
    private JButton btnAgregar, btnGuardar, btnexit;
    private JTable tabla;
    private JLabel lblSubtotal, lblDescuento, lblTotal;

    private DefaultTableModel modelo;
    private double subtotal = 0;

    public Factura() {
        setTitle("Facturación");
        setContentPane(panelFactura);
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(new String[]{"Código", "Nombre", "Cantidad", "Precio", "Subtotal"}, 0);
        tabla.setModel(modelo);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnGuardar.addActionListener(e -> guardarFactura());
        btnexit.addActionListener(e -> new Menu().setVisible(true));
    }

    private void agregarProducto() {
        try (Connection con = Conexion.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM PRODUCTO WHERE codigo=?");
            ps.setString(1, txtCodigo.getText().trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                double sub = precio * cantidad;
                subtotal += sub;

                modelo.addRow(new Object[]{txtCodigo.getText().trim(), nombre, cantidad, precio, sub});

                double descuento = subtotal * 0.15;
                double total = subtotal - descuento;

                lblSubtotal.setText("Subtotal: $" + subtotal);
                lblDescuento.setText("Descuento: $" + descuento);
                lblTotal.setText("Total: $" + total);
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void guardarFactura() {
        try (Connection con = Conexion.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement("INSERT INTO FACTURA(cedula_cliente, subtotal, descuento, total) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            double descuento = subtotal * 0.15;
            double total = subtotal - descuento;

            ps.setString(1, txtCedula.getText().trim());
            ps.setDouble(2, subtotal);
            ps.setDouble(3, descuento);
            ps.setDouble(4, total);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int idFactura = rs.getInt(1);

            for (int i = 0; i < modelo.getRowCount(); i++) {
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO DETALLE_FACTURA(id_factura,codigo_producto,cantidad,precio_unitario,subtotal) VALUES(?,?,?,?,?)");
                ps2.setInt(1, idFactura);
                ps2.setString(2, modelo.getValueAt(i, 0).toString());
                ps2.setInt(3, (int) modelo.getValueAt(i, 2));
                ps2.setDouble(4, (double) modelo.getValueAt(i, 3));
                ps2.setDouble(5, (double) modelo.getValueAt(i, 4));
                ps2.executeUpdate();
            }

            con.commit();
            JOptionPane.showMessageDialog(this, "Factura guardada con éxito");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

/*public void registrarFactura(int idCliente, List<Producto> productos) {
    String sqlFactura = "INSERT INTO facturas(id_cliente, subtotal, descuento, total) VALUES(?,?,?,?)";
    String sqlDetalle = "INSERT INTO detalle_factura(id_factura,id_producto,cantidad,precio_unitario,total_linea) VALUES(?,?,?,?,?)";

    try (Connection conn = ConexionBD.conectar()) {
        conn.setAutoCommit(false); // transacción

        double subtotal = productos.stream().mapToDouble(p -> p.getPrecio() * p.getCantidad()).sum();
        double descuento = subtotal * 0.15;
        double total = subtotal - descuento;

        // Insertar cabecera
        PreparedStatement psFactura = conn.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS);
        psFactura.setInt(1, idCliente);
        psFactura.setDouble(2, subtotal);
        psFactura.setDouble(3, descuento);
        psFactura.setDouble(4, total);
        psFactura.executeUpdate();

        ResultSet rs = psFactura.getGeneratedKeys();
        rs.next();
        int idFactura = rs.getInt(1);

        // Insertar detalle
        PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
        for (Producto p : productos) {
            psDetalle.setInt(1, idFactura);
            psDetalle.setInt(2, p.getId());
            psDetalle.setInt(3, p.getCantidad());
            psDetalle.setDouble(4, p.getPrecio());
            psDetalle.setDouble(5, p.getCantidad() * p.getPrecio());
            psDetalle.addBatch();
        }
        psDetalle.executeBatch();

        conn.commit();
        JOptionPane.showMessageDialog(null, "Factura registrada con éxito. Total: $" + total);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
 */

