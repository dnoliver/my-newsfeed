/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dnoliver
 */
public class ObjectDataBaseTest {
    
    public ObjectDataBaseTest() {
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

    /**
     * Test of get method, of class ObjectDataBase.
     */
    @Test
    public void testGet_String() {
        System.out.println("get");
        String table = "users";
        ObjectDataBase instance = new ObjectDataBase();
        JSONArray result = instance.get(table);
        System.out.println(result.toString());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of get method, of class ObjectDataBase.
     */
    @Test
    public void testGet_String_String() {
        System.out.println("get");
        String table = "users";
        String id = "dnoliver";
        ObjectDataBase instance = new ObjectDataBase();
        JSONObject result = instance.get(table, id);
        System.out.println(result.toString());
    }

    /**
     * Test of put method, of class ObjectDataBase.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        String table = "users";
        ObjectDataBase instance = new ObjectDataBase();
        JSONObject object = new JSONObject();
        
        object.put("id","1");
        object.put("name","Nicolas");
        object.put("category","profesor");
        
        //instance.put(table, object );
    }
    
    @Test
    public void testPost() {
        System.out.println("post");
        String table = "users";
        ObjectDataBase instance = new ObjectDataBase();
        JSONObject object = new JSONObject();
        
        object.put("name","Nicolas");
        object.put("password","123");
        object.put("category","profesor");
        
        //instance.post(table, object);
    }
    
        @Test
    public void testQuery() {
      System.out.println("query");
      String table = "newsfeeds";
      String filter = "owner";
      String value = "cosimani";
      ObjectDataBase instance = new ObjectDataBase();

      JSONArray query = instance.query(table, filter, value);
      System.out.println(query.toString());
    }
}
