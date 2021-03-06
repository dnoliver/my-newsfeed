/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author dnoliver
 */
public class ModelFactory {
  public static Model Create(ModelType type){
    Model model = null;
    
    switch(type){
      case USER:
        model = new User();
        break;
      case NEWSFEED:
        model = new Newsfeed();
        break;
      case POST:
        model = new Post();
        break;
      case COMMENT:
        model = new Comment();
        break;
      case LIKE:
        model = new Like();
        break;
      case SHARE: 
        model = new Share();
        break;
    }
    return model;
  }
  
  public static ModelType TypeFor(String name){
    ModelType type = null;
    
    switch(name){
      case "user":
        type = ModelType.USER;
        break;
      case "newsfeed":
        type = ModelType.NEWSFEED;
        break;
      case "post":
        type = ModelType.POST;
        break;
      case "comment":
        type = ModelType.COMMENT;
        break;
      case "like":
        type = ModelType.LIKE;
        break;
      case "share":
        type = ModelType.SHARE;
        break;  
    }
    
    return type;
  }
}
