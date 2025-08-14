import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton limpiarButton;
    private JButton ingresarButton;

    public Login(){
        setContentPane(panel1);
        setTitle("Sistema");
        setSize(500,250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setEnabled(true);

        String usuario = textField1.getText();
        String clave = new String(passwordField1.getPassword());

        ingresarButton.addActionListener(e ->  {
            if (textField1.isEmpty() || passwordField1.isEmpty()){
                JOptionPane.showMessageDialog(null," Usuario o Contraseña incompletos");
            } else {
                if (usuario.equals("admin") && clave.equals("admin123")){
                    new Sistema().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null," Usuario o Contraseña incorrectos");
                }
            }

        });

        limpiarButton.addActionListener(e ->  {
            textField1.setText("");
            passwordField1.setText("");
        });
    }



}

