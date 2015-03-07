/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;
import java.util.Map;
import org.json.simple.JSONAware;

/**
 *
 * @author dnoliver
 */
public interface AbstractModel extends JSONAware {
  public void set(String key, Object value);
  public void set(Map<String,Object> attributes);
  public void set(List<Map<String,Object>> rows);
  public Object get(String key);
  public void select();
  public void update();
  public void save();
  public void delete();
  public boolean execute(String action);
}
