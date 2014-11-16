/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import db.DataBase;
import db.QueryParameter;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dnoliver
 */
public class Session extends Model {
  
  public Session() {
    super(ModelType.SESSION);
  }
  
  @Override
  public void select() {
    String query = "select * from sessions where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    this.set(this.db.executeQuery(query, params));
  }
  
  @Override
  public void update() {
    String query = "update sessions set owner = ? where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("owner"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 2));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void save() {
    String query = "insert into sessions values(?,?)";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("owner"), Types.VARCHAR, 2));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void delete() {
    String query = "delete from sessions where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.VARCHAR, 1));
    this.db.executeUpdate(query, params);
  }
}
