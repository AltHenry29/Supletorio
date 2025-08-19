package menu;

import Login.login;
import cliente.Cliente;
import factura.Factura;
import producto.Producto;

import javax.swing.*;

public class Menu extends JFrame{
    private JButton clientesButton;
    private JPanel panelm;
    private JButton productosButton;
    private JButton facturaButton;
    private JButton salirButton;

    public Menu(){
        setContentPane(panelm);
        setTitle("Menu");
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(null);


        clientesButton.addActionListener(e ->  {
            new Cliente().setVisible(true);
        });

        productosButton.addActionListener(e ->  {
            new Producto().setVisible(true);
        });

        facturaButton.addActionListener(e ->  {
            new Factura().setVisible(true);
        });

        salirButton.addActionListener(e ->  {
            new login().setVisible(true);
        });





    }



}

