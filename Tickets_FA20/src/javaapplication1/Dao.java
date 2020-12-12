
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javaapplication1.Dao;

public class Dao {
  static Connection connect = null;
  
  Statement statement = null;
  
  public Connection getConnection() {
    try {
      connect = 
        DriverManager.getConnection("jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false&user=fp411&password=411");
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return connect;
  }
  
  public void createTables() {
    String createTicketsTable = "CREATE TABLE jgriff_tickets(ticket_id INT AUTO_INCREMENT PRIMARY KEY, ticket_issuer VARCHAR(30), ticket_description VARCHAR(200))";
    String createUsersTable = "CREATE TABLE jgriff_users(uid INT AUTO_INCREMENT PRIMARY KEY, uname VARCHAR(30), upass VARCHAR(30), admin int)";
    try {
      this.statement = getConnection().createStatement();
      this.statement.executeUpdate("CREATE TABLE jgriff_tickets(ticket_id INT AUTO_INCREMENT PRIMARY KEY, ticket_issuer VARCHAR(30), ticket_description VARCHAR(200))");
      this.statement.executeUpdate("CREATE TABLE jgriff_users(uid INT AUTO_INCREMENT PRIMARY KEY, uname VARCHAR(30), upass VARCHAR(30), admin int)");
      System.out.println("Created tables in given database...");
      this.statement.close();
      connect.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
    addUsers();
  }
  
  public void addUsers() {
    List<List<String>> array = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File("./userlist.csv")));
      String line;
      while ((line = br.readLine()) != null)
        array.add(Arrays.asList(line.split(","))); 
    } catch (Exception e) {
      String line;
      System.out.println("There was a problem loading the file");
    } 
    try {
      Statement statement = getConnection().createStatement();
      for (List<String> rowData : array) {
        String sql = "insert into jgriff_users(uname,upass,admin) values('" + (String)rowData.get(0) + "'," + " '" + 
          (String)rowData.get(1) + "','" + (String)rowData.get(2) + "');";
        statement.executeUpdate(sql);
      } 
      System.out.println("Inserts completed in the given database...");
      statement.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } 
  }
  
  public int insertRecords(String ticketName, String ticketDesc) {
    int id = 0;
    try {
      this.statement = getConnection().createStatement();
      this.statement.executeUpdate("Insert into jgriff_tickets(ticket_issuer, ticket_description) values( '" + 
          ticketName + "','" + ticketDesc + "')", 1);
      ResultSet resultSet = null;
      resultSet = this.statement.getGeneratedKeys();
      if (resultSet.next())
        id = resultSet.getInt(1); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return id;
  }
  
  public ResultSet readRecords() {
    ResultSet results = null;
    try {
      this.statement = connect.createStatement();
      results = this.statement.executeQuery("SELECT * FROM jgriff_tickets");
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    return results;
  }


public ResultSet selectRecords(int ticketID) {
    ResultSet results = null;
    try {
      this.statement = connect.createStatement();
      results = this.statement.executeQuery("SELECT * FROM jgriff_tickets WHERE ticket_id = " + ticketID);
      connect.close();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    return results;
  }
}

