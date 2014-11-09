package db;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
        
/**
 *
 * @author dnoliver
 */
public class ObjectDataBase extends DataBase {
  
  public ObjectDataBase(){
    super();
  }
  
  static private void getJSONArray(JSONArray array, List<Map<String, Object>> results){
    for (Map<String, Object> result : results) {
      JSONObject object = new JSONObject();
      for (Map.Entry<String,Object> entry : result.entrySet()) {
          object.put(entry.getKey(), entry.getValue());
      }
      array.add(object);
    }
  }
  
  public JSONArray query(String table,String filter,String val){
    List<Map<String, Object>> results = null;
    String procedure = DataBase.procedures.get(table).get(filter);
    results = this.executeProcedure(procedure,val);
    
    JSONArray array = new JSONArray();
    ObjectDataBase.getJSONArray(array, results);
    return array;
  }
  
  /*
   * select
   */
  public JSONArray get(String table){
    List<Map<String, Object>> results = this.executeSelect(table);
    JSONArray array = new JSONArray();
    ObjectDataBase.getJSONArray(array, results);
    return array;
  }
  
  /*
   * select by id
   */
  public JSONObject get(String table, String id){
    List<Map<String, Object>> results = this.executeSelect(table,id);
    JSONArray array = new JSONArray();
    ObjectDataBase.getJSONArray(array, results);
    return (JSONObject) (array.size() > 0? array.get(0) : null);
  }
    
  /*
   * update
   */
  public void put(String table,JSONObject object){
    String query = "update " + table + " set ";

    for (Iterator it = object.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      if( "id".equals(entry.getKey())){

      } else {
        if(entry.getValue() != null){
          query += entry.getKey() + "=";
          query += "'" + entry.getValue().toString() + "',";
        }
      }
    }
        
    query = query.substring(0,query.length()-1);
    query += " where id='" + object.get("id").toString() + "'";
    executeUpdate(query);
  }
    
  /*
   * insert
   */
  public void post(String table,JSONObject object){
    String query = "insert into " + table ;
    LinkedList keys = new LinkedList();
    LinkedList values = new LinkedList();

    for (Iterator it = object.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      if( "id".equals(entry.getKey())){

      } else {
        keys.add(entry.getKey());
        values.add("'" + entry.getValue().toString() + "'");
      }
    }
        
    query += "(" + keys.toString().replace("[", "").replace("]","") + ")";
    query += " values";
    query += "(" + values.toString().replace("[", "").replace("]","") + ")";
    executeUpdate(query);
  }
}
