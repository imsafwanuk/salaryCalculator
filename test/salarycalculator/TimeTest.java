/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import javafx.beans.property.IntegerProperty;
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
public class TimeTest {
    
    public TimeTest() {
    }
    

    /**
     * Test of getHr method, of class Time.
     */
    @Test
    public void testGetHr() {
        System.out.println("getHr");
        Integer expResult, result;
        Time instance = new Time();
        
        // test < 0
        instance.setHr(-34);
        expResult = 0;
        result = instance.getHr();
        assertEquals(expResult, result);
        
        // test == 0
        instance.setHr(0);
        expResult = 0;
        result = instance.getHr();
        assertEquals(expResult, result);
        
        // test > 0 && < 23
        instance.setHr(1);
        expResult = 1;
        result = instance.getHr();
        assertEquals(expResult, result);
        
        instance.setHr(23);
        expResult = 23;
        result = instance.getHr();
        assertEquals(expResult, result);
        
        // test >= 24
        instance.setHr(24);
        expResult = 0;
        result = instance.getHr();
        assertEquals(expResult, result);
        
        instance.setHr(600);
        expResult = 0;
        result = instance.getHr();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getMin method, of class Time.
     */
    @Test
    public void testGetMin() {
        System.out.println("getMin");
        Integer expResult, result;
        Time instance = new Time();
        
        // test < 0
        instance.setMin(-8);
        expResult = 0;
        result = instance.getMin();
        assertEquals(expResult, result);
        
        // test > 0 && < 60
        instance.setMin(3);
        expResult = 3;
        result = instance.getMin();
        assertEquals(expResult, result);
        
        instance.setMin(59);
        expResult = 59;
        result = instance.getMin();
        assertEquals(expResult, result);
        
        // test >= 60
        instance.setMin(600);
        expResult = 0;
        result = instance.getMin();
        assertEquals(expResult, result);
        
        instance.setMin(60);
        expResult = 0;
        result = instance.getMin();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getTimeDiff method, of class Time.
     */
    @Test
    public void testGetTimeDiff() {
        System.out.println("getTimeDiff");
        double expResult, result;
        
        Time endTime = new Time();
        Time startTime = new Time();
        
       // test < 0
        endTime.setHr(-23);
        endTime.setMin(59);
        startTime.setHr(1);
        startTime.setMin(59);
        expResult = 0.0;
        result = endTime.getTimeDiff(startTime);
        assertEquals(expResult, result, 0.0);
        
        endTime.setHr(23);
        endTime.setMin(59);
        startTime.setHr(-2);
        startTime.setMin(59);
        expResult = 23;
        result = endTime.getTimeDiff(startTime);
        assertEquals(expResult, result, 0.0);
        
        // test > 0 && < 23:59
        endTime.setHr(10);
        endTime.setMin(0);
        startTime.setHr(12);
        startTime.setMin(0);
        expResult = 0;
        result = endTime.getTimeDiff(startTime);
        assertEquals(expResult, result, 0.0);
                
        // test >= 23:59
        endTime.setHr(34);
        endTime.setMin(2);
        startTime.setHr(10);
        startTime.setMin(0);
        expResult = 0.0;
        result = endTime.getTimeDiff(startTime);
        assertEquals(expResult, result, 0.0);
         
        
        endTime.setHr(1);
        endTime.setMin(2);
        startTime.setHr(30);
        startTime.setMin(0);
        expResult = 1.02;
        result = endTime.getTimeDiff(startTime);
        assertEquals(expResult, result, 0.1);
        
        
    }
    
}
