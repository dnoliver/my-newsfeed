/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.List;
import java.util.Map;

/**
 *
 * @author dnoliver
 */
public class DataBaseAction extends DataBase {

  public DataBaseAction() {
    super();
  }

  public boolean login(String user,String password){
    List<Map<String, Object>> resultSet;
    String query = "select * from users where id='" + user + "' and password='"+ password +"'";
    resultSet = executeQuery(query);
    return resultSet.size() == 1;
  }

  public void createSession(String id, String user){
    executeUpdate("insert into sessions values('" + id + "','" + user + "')");
  }

  public void removeSession(String id){
    executeUpdate("delete from sessions where id='" + id + "'");
  }
}
