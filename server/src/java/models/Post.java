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

/**
 *
 * @author dnoliver
 */
public class Post extends Model {

  public Post(){
    super(ModelType.POST);
  }
  
  @Override
  public void select() {
    String query = "select * from posts where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 1));
    this.set(this.db.executeQuery(query, params));
  }

  @Override
  public void update() {
    String query = "update posts set deleted = ? where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("deleted"), Types.BIT, 1));
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 2));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void save() {
    String query = "insert into posts(text,owner,newsfeed,attachment) values(?,?,?,?)";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("text"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("owner"), Types.VARCHAR, 2));
    params.add(QueryParameter.Create(this.get("newsfeed"), Types.INTEGER, 3));
    params.add(QueryParameter.Create(this.get("attachment"), Types.VARCHAR, 4));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void delete() {
    String query = "delete from posts where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 1));
    this.db.executeUpdate(query, params);
  }
  
}
