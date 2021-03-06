/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author safwan
 */
public class Time {
    private IntegerProperty hr = new SimpleIntegerProperty(0);
    private IntegerProperty min = new SimpleIntegerProperty(0);
    
    // hr
    public IntegerProperty hrProperty() {
        return hr;
    }
    
    public boolean setHr(Integer s) {
        if(s >= 0 && s <= 23) {
            this.hr.set(s);
            return true;
        }else {
            s = 0;
            return false;
        }
    }
    
    public Integer getHr() {
        return this.hr.get();
    }
    
// min
    public IntegerProperty minProperty() {
        return hr;
    }
    
    public boolean setMin(Integer s) {
        if(s >= 0 && s <= 59) {
            this.min.set(s);
            return true;
        }else {
            s = 0;
            return false;
        }
    }
    
    public Integer getMin() {
        return this.min.get();
    }
    
    //totalTime --- always have endTime.getTimeDIff(startTime)
    public double getTimeDiff(Time t) {
        double a = this.getHr()+this.getMin()/60.0;
        double b = t.getHr()+t.getMin()/60.0;
        
        //just for safety, ensuring that bigger hour - smaller hour happens
       // if(this.getHr() >= t.getHr()) {
            return a-b;
        //}else{
//            return b-a;
//        }
        
    }
}
