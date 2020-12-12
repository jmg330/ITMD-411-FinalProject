import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javaapplication1.Dao;
import javaapplication1.Tickets;
import javaapplication1.ticketsJTable;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Tickets extends JFrame implements ActionListener {
  Dao dao = new Dao();
  
  Boolean chkIfAdmin = null;
  
  private JMenu mnuFile = new JMenu("File");
  
  private JMenu mnuAdmin = new JMenu("Admin");
  
  private JMenu mnuTickets = new JMenu("Tickets");
  
  JMenuItem mnuItemExit;
  
  JMenuItem mnuItemUpdate;
  
  JMenuItem mnuItemDelete;
  
  JMenuItem mnuItemOpenTicket;
  
  JMenuItem mnuItemViewTicket;
  
  public Tickets(Boolean isAdmin) {
	if (isAdmin != this.chkIfAdmin) {
		System.out.println("You are an admin");
	}
    this.chkIfAdmin = isAdmin;
    createMenu();
    prepareGUI();
  }
  
  private void createMenu() {
    this.mnuItemExit = new JMenuItem("Exit");
    this.mnuFile.add(this.mnuItemExit);
    this.mnuItemUpdate = new JMenuItem("Update Ticket");
    this.mnuAdmin.add(this.mnuItemUpdate);
    this.mnuItemDelete = new JMenuItem("Delete Ticket");
    this.mnuAdmin.add(this.mnuItemDelete);
    this.mnuItemOpenTicket = new JMenuItem("Open Ticket");
    this.mnuTickets.add(this.mnuItemOpenTicket);
    this.mnuItemViewTicket = new JMenuItem("View Ticket");
    this.mnuTickets.add(this.mnuItemViewTicket);
    this.mnuItemExit.addActionListener(this);
    this.mnuItemUpdate.addActionListener(this);
    this.mnuItemDelete.addActionListener(this);
    this.mnuItemOpenTicket.addActionListener(this);
    this.mnuItemViewTicket.addActionListener(this);
  }
  
  private void prepareGUI() {
    JMenuBar bar = new JMenuBar();
    bar.add(this.mnuFile);
    bar.add(this.mnuAdmin);
    bar.add(this.mnuTickets);
    setJMenuBar(bar);
    addWindowListener((WindowListener)new Object(this));
    setSize(400, 400);
    getContentPane().setBackground(Color.LIGHT_GRAY);
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.mnuItemExit) {
      System.exit(0);
    } else if (e.getSource() == this.mnuItemOpenTicket) {
      String ticketName = JOptionPane.showInputDialog((Component)null, "Enter your name");
      String ticketDesc = JOptionPane.showInputDialog((Component)null, "Enter a ticket description");
      int id = this.dao.insertRecords(ticketName, ticketDesc);
      if (id != 0) {
        System.out.println("Ticket ID : " + id + " created successfully!!!");
        JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
      } else {
        System.out.println("Ticket cannot be created!!!");
      } 
    } else if (e.getSource() == this.mnuItemViewTicket) {
      try {
        JTable jt = new JTable(ticketsJTable.buildTableModel(this.dao.readRecords()));
        jt.setBounds(30, 40, 200, 400);
        JScrollPane sp = new JScrollPane(jt);
        add(sp);
        setVisible(true);
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
    } 
  }
}
