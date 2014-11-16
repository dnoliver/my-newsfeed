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
  
  @Override
  public void update() {
    String query = "update users set password = ?, category = ? where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("password"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("category"), Types.VARCHAR, 2));
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 3));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void save() {
    String query = "insert into users values(?,?,?)";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("password"), Types.VARCHAR, 2));
    params.add(QueryParameter.Create(this.get("category"), Types.VARCHAR, 3));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void delete() {
    String query = "delete from users where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    this.db.executeUpdate(query, params);
  }
  
  public boolean login() {
    String query = "select * from users where id = ? and password= ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("password"), Types.VARCHAR, 2));
    
    List<Map<String, Object>> resultSet = this.db.executeQuery(query, params);
    return resultSet.size() == 1;
  }
}
