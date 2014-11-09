package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnoliver
 */
public class DataBase {
    
  static String dbinstance;
  static boolean initialized = false;
  static Map<String,Map<String,String>> procedures;

  protected DataBase(){
    DataBase.getDriver();  
  }
  
  private static void getDriver(){
    try {
      if(!initialized){
        initialized = true;
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dbinstance = "jdbc:sqlserver://DNOLIVER-MOBL;" +
            "databaseName=newsfeed;user=developer;password=intel123!;";
        procedures = new HashMap<>();
        
        procedures.put("users", new HashMap<String,String>());
        procedures.get("users").put("newsfeed","getUsersForNewsfeed");
        procedures.get("users").put("session","getUsersForSession");
        
        procedures.put("newsfeeds",new HashMap<String,String>());
        procedures.get("newsfeeds").put("session","getNewsfeedsForSession");
        
        procedures.put("comments",new HashMap<String,String>());
        procedures.get("comments").put("post","getCommentsForPost");
        
        procedures.put("likes",new HashMap<String,String>());
        procedures.get("likes").put("post","getLikesForPost");
        
        procedures.put("posts",new HashMap<String,String>());
        procedures.get("posts").put("newsfeed","getPostsForNewsfeed");
      }
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private static void getHashMap( List<Map<String, Object>> row, ResultSet rs) 
          throws SQLException {
    ResultSetMetaData metaData = rs.getMetaData();
    int colCount = metaData.getColumnCount();
    while (rs.next()) {
      Map<String, Object> columns = new HashMap<>();
      for (int i = 1; i <= colCount; i++) {
        columns.put(metaData.getColumnLabel(i), rs.getObject(i));
      }
      row.add(columns);
    }
  }
    
  public List<Map<String,Object>> executeQuery(String query){
    Statement st;
    ResultSet rs;
    Connection connection;
    List result = new ArrayList();

    try {
      connection = DriverManager.getConnection(DataBase.dbinstance);
      st = connection.createStatement();
      rs = st.executeQuery(query);
      DataBase.getHashMap(result, rs);
      rs.close();
      st.close();
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
  }
  
  public List<Map<String,Object>> executeSelect(String table){
    return executeQuery("select * from " + table);
  }
  
  public List<Map<String,Object>> executeSelect(String table, String id){
    return executeQuery("select * from " + table + " where id='" + id + "'");
  }
  
  public List<Map<String,Object>> executeProcedure(String procedure, String param){
    List result = new ArrayList();
    Connection connection;
    CallableStatement proc;
    ResultSet rs;
            
    try {
      connection = DriverManager.getConnection(DataBase.dbinstance);
      proc = connection.prepareCall("{call " + procedure.toString() + "(?)}");
      proc.setString(1, param);
      rs = proc.executeQuery();
      DataBase.getHashMap(result, rs);
      rs.close();
      proc.close();
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return result;
  }
  
  public int executeUpdate(String query){
    Connection connection;
    Statement st;
    int result = -1;
    System.out.println(query);
    try {
      connection = DriverManager.getConnection(DataBase.dbinstance);
      st = connection.createStatement();
      result = st.executeUpdate(query);
      st.close();
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
  }
}
