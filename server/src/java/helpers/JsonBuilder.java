/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helpers;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author dnoliver
 */
public class JsonBuilder {
    
    public JsonObjectBuilder BuildObject(Map<String,String> model){
        JsonObjectBuilder json = Json.createObjectBuilder();
        Iterator entries = model.entrySet().iterator();
        
        while (entries.hasNext()) {
            Map.Entry<String,String> thisEntry = (Entry) entries.next();
            json.add(thisEntry.getKey(), thisEntry.getValue());
        }
        
        return json;
    };
}
