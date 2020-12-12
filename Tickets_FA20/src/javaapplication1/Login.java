import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaapplication1.Dao;
import javaapplication1.Login;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
  Dao conn;
  
  public Login() {
    super("IIT HELP DESK LOGIN");
    this.conn = new Dao();
    this.conn.createTables();
    setSize(400, 210);
    setLayout(new GridLayout(4, 2));
    setLocationRelativeTo(null);
    JLabel lblUsername = new JLabel("Username", 2);
    JLabel lblPassword = new JLabel("Password", 2);
    JLabel lblStatus = new JLabel(" ", 0);
    JTextField txtUname = new JTextField(10);
    JPasswordField txtPassword = new JPasswordField();
    JButton btn = new JButton("Submit");
    JButton btnExit = new JButton("Exit");
    lblStatus.setToolTipText("Contact help desk to unlock password");
    lblUsername.setHorizontalAlignment(0);
    lblPassword.setHorizontalAlignment(0);
    add(lblUsername);
    add(txtUname);
    add(lblPassword);
    add(txtPassword);
    add(btn);
    add(btnExit);
    add(lblStatus);
    btn.addActionListener((ActionListener)new Object(this, txtUname, txtPassword, lblStatus));
    btnExit.addActionListener(e -> System.exit(0));
    setVisible(true);
  }
  
  public static void main(String[] args) {}
}