/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.DataBase;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONValue;

/**
 *
 * @author dnoliver
 */
public abstract class Model implements AbstractModel {
  protected Map<String, Object> attrs;
  protected DataBase db;
  private ModelType type;
  
  public Model(ModelType type){
    this.type = type;
    this.attrs = new HashMap();
    this.db = new DataBase();
  }
  
  @Override
  public void set(String key, Object value) {
    this.attrs.put(key, value);
  }
  
  @Override
  public void set(Map<String,Object> attributes) {
    this.attrs.putAll(attributes);
  }
  
  @Override
  public void set(List<Map<String,Object>> rows) {
    if(rows.size() >= 1){
      Map<String, Object> first = rows.get(0);
      this.attrs.putAll(first);
    }
  }
  
  @Override
  public Object get(String key) {
    return this.attrs.get(key);
  }
  
  @Override
  public String toJSONString(){
    return JSONValue.toJSONString(this.attrs);
  }
}
