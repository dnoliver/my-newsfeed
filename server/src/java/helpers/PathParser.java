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
public class PathParser {
  String path;
  String id;
  String resource;

  public PathParser(String path){
    this.path = path;
    String[] params = path.split("/");

    this.resource = params.length >= 2? params[1] : null;
    this.id = params.length >= 3? params[2] : null;
  }

  public String id(){
    return id;
  }

  public String resource(){
    return resource;
  }
}
