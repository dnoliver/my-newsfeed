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
public class Newsfeed extends Model {

  public Newsfeed(){
    super(ModelType.NEWSFEED);
  }
  
  @Override
  public void select() {
    String query = "select * from newsfeeds where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 1));
    this.set(this.db.executeQuery(query, params));
  }

  @Override
  public void update() {
    String query = "update newsfeeds set enabled = ? where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("enabled"), Types.BIT, 1));
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 2));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void save() {
    String query = "insert into newsfeeds(career,subject,owner) values(?,?,?,?)";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("career"), Types.VARCHAR, 1));
    params.add(QueryParameter.Create(this.get("subject"), Types.VARCHAR, 2));
    params.add(QueryParameter.Create(this.get("owner"), Types.VARCHAR, 3));
    this.db.executeUpdate(query, params);
  }

  @Override
  public void delete() {
    String query = "delete from newsfeed where id = ?";
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("id"), Types.INTEGER, 1));
    this.db.executeUpdate(query, params);
  } 
}
