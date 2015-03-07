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
public class Like extends Model {

  public Like(){
    super(ModelType.LIKE);
  }
  
  @Override
  public void select() {
    String query = "select * from likes where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 1));
    this.set(this.db.executeQuery(query, params));
  }

  @Override
  public void update() {
    throw new UnsupportedOperationException("Cannot update Like");
  }

  @Override
  public void save() {
    String query = "insert into likes(post,owner) values(?,?)";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("post"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("owner"), Types.VARCHAR, 2));
    this.db.executeUpdate(query, params);
  }

  @Override
  public boolean execute(String action) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void delete() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
