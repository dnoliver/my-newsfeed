/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnoliver
 */
public class DataBaseActionTest {
    
    public DataBaseActionTest() {
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
    public void testLoginSuccess() {
        DataBaseAction instance = new DataBaseAction();
        assertTrue(instance.login("dnoliver", "intel123!"));
    }
    
    @Test
    public void testLoginFail() {
        DataBaseAction instance = new DataBaseAction();
        assertFalse(instance.login("asd", "asd"));
    }

  /**
   * Test of createSession method, of class DataBaseAction.
   */
  @Test
  public void testCreateSession() {
    System.out.println("createSession");
    String id = "1234123";
    String user = "dnoliver";
    DataBaseAction instance = new DataBaseAction();
    instance.createSession(id, user);
  }

  /**
   * Test of removeSession method, of class DataBaseAction.
   */
  @Test
  public void testRemoveSession() {
    System.out.println("removeSession");
    String id = "1234123";
    DataBaseAction instance = new DataBaseAction();
    instance.removeSession(id);
  }
    
}
