/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import javax.json.JsonArray;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import helpers.ModelBuilder;
import helpers.JsonBuilder;

/**
 *
 * @author dnoliver
 */
public class TempDAO implements DataAccessObject{

    ModelBuilder modelBuilder;
    JsonBuilder jsonBuilder;
    JsonArray users;
    JsonArray comments;
    JsonArray newsfeeds;
    JsonArray sessions;
    Map<String,JsonArray> db;
    
    public TempDAO(){
        modelBuilder = new ModelBuilder();
        jsonBuilder = new JsonBuilder();
        
        users = Json.createArrayBuilder()
              .add(jsonBuilder.BuildObject(modelBuilder.Build("User")))
              .add(jsonBuilder.BuildObject(modelBuilder.Build("User")))
              .add(jsonBuilder.BuildObject(modelBuilder.Build("User")))
              .build();
        
        newsfeeds = Json.createArrayBuilder()
              .add(jsonBuilder.BuildObject(modelBuilder.Build("Newsfeed")))
              .add(jsonBuilder.BuildObject(modelBuilder.Build("Newsfeed")))
              .add(jsonBuilder.BuildObject(modelBuilder.Build("Newsfeed")))
              .build();
        
        comments = Json.createArrayBuilder().build();
        sessions = Json.createArrayBuilder().build();
        
        db = new HashMap<>();
        
        db.put("users",users);
        db.put("comments",comments);
        db.put("newsfeeds",newsfeeds);
        db.put("sessions",sessions);
    }
    
    @Override
    public JsonArray get(String key) {
        return db.get(key);
    }
    
}
