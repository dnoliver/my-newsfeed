/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.QueryParameter;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dnoliver
 */
public class User extends Model {
  
  public User(){
    super(ModelType.USER);
  }
  
  @Override
  public void select() {
    String query = "select * from users where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    this.set(this.db.executeQuery(query, params));
  }
  
  public boolean execute(String action){
    String query = "select * from users where id = ? and password= ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("password"), Types.VARCHAR, 2));
    
    List<Map<String, Object>> resultSet = this.db.executeQuery(query, params);
    return resultSet.size() == 1;
  }

  @Override
  public void update() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void save() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void delete() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
