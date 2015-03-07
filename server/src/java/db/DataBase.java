package db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnoliver
 */
public class DataBase {
  String user;
  String password;
  String server;

  public DataBase(){
    this.user = "developer";
    this.password = "intel123!";
    this.server = "jdbc:sqlserver://DNOLIVER-MOBL;databaseName=newsfeed;";
  }
  
  private Connection getConnection() throws SQLException{
    return DriverManager.getConnection(this.server,this.user,this.password);
  }
  
  private static void getHashMap( List<Map<String, Object>> row, ResultSet rs) 
          throws SQLException {
    ResultSetMetaData metaData = rs.getMetaData();
    int colCount = metaData.getColumnCount();
    while (rs.next()) {
      Map<String, Object> columns = new HashMap();
      for (int i = 1; i <= colCount; i++) {
        columns.put(metaData.getColumnLabel(i), rs.getObject(i));
      }
      row.add(columns);
    }
  }

  public int executeUpdate(String query,List<QueryParameter> params){
    Connection connection;
    PreparedStatement st;
    int result = -1;
    try {
      connection = this.getConnection();
      st = connection.prepareStatement(query);
      
      for(QueryParameter p : params){
        st.setObject(p.getIndex(), p.getObject(), p.getType());
      }
      
      result = st.executeUpdate();
      st.close();
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }
    return result;
  }
  
  public List<Map<String,Object>> executeQuery(String query,List<QueryParameter> params){
    PreparedStatement st;
    ResultSet rs;
    Connection connection;
    List result = new ArrayList();

    try {
      connection = this.getConnection();
      st = connection.prepareStatement(query);
      
      for(QueryParameter p : params){
        st.setObject(p.getIndex(), p.getObject(), p.getType());
      }
      
      rs = st.executeQuery();
      DataBase.getHashMap(result, rs);
      rs.close();
      st.close();
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
    }

    return result;
  }
  
  public List<Map<String,Object>> executeProcedure(String procedure,List<QueryParameter> params){
    List result = new ArrayList();
    Connection connection;
    CallableStatement proc;
    ResultSet rs;
            
    try {
      connection = this.getConnection();
      proc = connection.prepareCall(procedure);
      
      for(QueryParameter p : params){
        proc.setObject(p.getIndex(), p.getObject(), p.getType());
      }
      
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
}
