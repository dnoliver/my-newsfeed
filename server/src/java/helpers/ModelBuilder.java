/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;
import java.util.Map;
import java.util.HashMap;


/**
 *
 * @author dnoliver
 */
public class ModelBuilder {
    Map<String,Map<String,String>> models;
    Map<String,String> user;
    Map<String,String> comment;
    Map<String,String> newsfeed;
    
    public ModelBuilder(){
        models = new HashMap();
        user = new HashMap();
        comment = new HashMap();
        newsfeed = new HashMap();
        
        user.put("type", "User");
        user.put("name", "user");
        user.put("category", "student");
        user.put("connected", "true");
        
        comment.put("type","Comment");
        comment.put("parent",null);
        comment.put("user", "user");
        comment.put("content", "content");
        comment.put("likes", "0");
        comment.put("visible", "true");
        comment.put("available", "true");
  
        newsfeed.put("type", "Newsfeed");
        newsfeed.put("title", "Newsfeed");
        newsfeed.put("visible", "true");
        newsfeed.put("available", "true");        

        models.put("User", user);
        models.put("Comment", comment);
        models.put("Newsfeed", newsfeed);
    }
    
    public Map<String,String> Build(String key){
        HashMap val = (HashMap)models.get(key);
        return (Map<String, String>) val.clone();
    }
}
