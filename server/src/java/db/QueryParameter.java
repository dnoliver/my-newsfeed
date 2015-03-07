/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author dnoliver
 */
public class QueryParameter {
  private final Object object;
  private final int type;
  private final int index;
  
  public static QueryParameter Create(Object o, int t, int i){
    return new QueryParameter(o,t,i);
  }
  
  public QueryParameter(Object o, int t, int i){
    this.object = o;
    this.type = t;
    this.index = i;
  }
  
  public Object getObject(){
    return this.object;
  }
  
  public int getType(){
    return this.type;
  }
  
  public int getIndex(){
    return this.index;
  }
}
