/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

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
public class PathParserTest {
    
    public PathParserTest() {
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
     * Test of id method, of class PathParser.
     */
    @Test
    public void testId() {
        System.out.println("id");
        PathParser instance = new PathParser("/user/1234");
        String expResult = "1234";
        String result = instance.id();
        assertEquals(expResult, result);
    }

    /**
     * Test of resource method, of class PathParser.
     */
    @Test
    public void testResource() {
        System.out.println("resource");
        PathParser instance = new PathParser("/user");
        String expResult = "user";
        String result = instance.resource();
        assertEquals(expResult, result);
    }
    
}
