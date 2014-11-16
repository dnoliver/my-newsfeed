/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONAware;
import org.json.simple.JSONValue;

/**
 *
 * @author dnoliver
 */
public class DataBaseQuery implements JSONAware {

  protected Map<String, Object> attrs;
  protected List<Map<String,Object>> results;
  protected DataBase db;
  
  public DataBaseQuery(){
    this.attrs = new HashMap();
    this.results = new LinkedList();
    this.db = new DataBase();
  }
  
  public void set(String key, Object value){
    this.attrs.put(key, value);
  }
  
  public Object get(String key){
    return this.attrs.get(key);
  }
  
  public void execute(){
    String query = DataBase.procedures.get(this.get("table")).get(this.get("filter"));
    List<QueryParameter> params = new LinkedList();
    
    params.add(QueryParameter.Create(this.get("value"), Types.VARCHAR, 1));
    this.results.addAll(this.db.executeProcedure(query, params));
  }
  
  @Override
  public String toJSONString() {
    return JSONValue.toJSONString(this.results);
  }
}
