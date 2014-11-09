/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dnoliver
 */
public class JsonParserTest {
  
  public JsonParserTest() {
  }
  
  @BeforeClass
  public static void setUpClass() {
  }
  
  @AfterClass
  public static void tearDownClass() {
  }
  
  @Before
  public void setUp() {
  }
  
  @After
  public void tearDown() {
  }

  @Test
  public void jsonParserTest() {
    try {
      JSONParser parser = new JSONParser();
      String json = "{\"id\":\"1\",\"user\":1}";
      JSONObject obj = (JSONObject) parser.parse(json);
      String result = obj.toString();
      
      System.out.println(result);
    } catch (ParseException ex) {
      Logger.getLogger(JsonParserTest.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
