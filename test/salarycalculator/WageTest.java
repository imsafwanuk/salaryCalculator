/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author safwan
 */
public class WageTest {
    
    public WageTest() {
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
     * Test of getHourlyWageA method, of class Wage.
     */
    @Test
    public void testGetHourlyWageA() {
        System.out.println("getHourlyWageA");
        double expResult, result;
        
        // test < 0
        Wage.setHourlyWageA(-3.4);
        expResult = 0.0;
        result = Wage.getHourlyWageA();
        assertEquals(expResult, result, 0.0);
        
        // test == 0
        Wage.setHourlyWageA(0);
        expResult = 0.0;
        result = Wage.getHourlyWageA();
        assertEquals(expResult, result, 0.0);
        
        // test > 0
        Wage.setHourlyWageA(3.4);
        expResult = 3.4;
        result = Wage.getHourlyWageA();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getHourlyWageB method, of class Wage.
     */
    @Test
    public void testGetHourlyWageB() {
        System.out.println("getHourlyWageB");
        double expResult, result;
        
        // test < 0
        Wage.setHourlyWageB(-3.4);
        expResult = 0.0;
        result = Wage.getHourlyWageB();
        assertEquals(expResult, result, 0.0);
        
        // test == 0
        Wage.setHourlyWageB(0);
        expResult = 0.0;
        result = Wage.getHourlyWageB();
        assertEquals(expResult, result, 0.0);
        
        // test > 0
        Wage.setHourlyWageB(3.4);
        expResult = 3.4;
        result = Wage.getHourlyWageB();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getHourlyWageC method, of class Wage.
     */
    @Test
    public void testGetHourlyWageC() {
        System.out.println("getHourlyWageC");
        double expResult, result;
        
        // test < 0
        Wage.setHourlyWageC(-3.4);
        expResult = 0.0;
        result = Wage.getHourlyWageC();
        assertEquals(expResult, result, 0.0);
        
        // test == 0
        Wage.setHourlyWageC(0);
        expResult = 0.0;
        result = Wage.getHourlyWageC();
        assertEquals(expResult, result, 0.0);
        
        // test > 0
        Wage.setHourlyWageC(3.4);
        expResult = 3.4;
        result = Wage.getHourlyWageC();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getWage method, of class Wage.
     */
    @Test
    public void testGetWage() {
        System.out.println("getWage");
        double expResult, result;
        Wage instance = new Wage();
        
        // test A and <0
        Wage.setHourlyWageA(3.4);
        instance.setWage("A");
        expResult = 3.4;
        result = instance.getWage();
        assertEquals(expResult, result, 0.0);
        
        // test B and == 0
        Wage.setHourlyWageB(0);
        instance.setWage("B");
        expResult = 0;
        result = instance.getWage();
        assertEquals(expResult, result, 0.0);
        
        // test C and > 0
        Wage.setHourlyWageC(100.01);
        instance.setWage("C");
        expResult = 100.01;
        result = instance.getWage();
        assertEquals(expResult, result, 0.0);
        
    }
    
}
