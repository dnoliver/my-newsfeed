/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author dnoliver
 */
public class QueryParser {
  String path;
  String value;
  String filter;
  String resource;

  public QueryParser(String path){
    this.path = path;
    String[] params = path.split("/");

    this.resource = params.length >= 2? params[1] : null;
    this.filter = params.length >= 3? params[2] : null;
    this.value = params.length >= 4? params[3] : null;
  }

  public String value(){
      return value;
  }

  public String filter(){
      return filter;
  }

  public String resource(){
      return resource;
  }
}
