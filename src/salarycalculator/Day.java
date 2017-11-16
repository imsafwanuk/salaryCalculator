/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.RadioButton;

/**
 *
 * @author safwan
 */
public class Day {
    private DoubleProperty salary = new SimpleDoubleProperty();
    private IntegerProperty breakTime = new SimpleIntegerProperty(0);
    private DoubleProperty amountEarned = new SimpleDoubleProperty(0.0);
    private StringProperty dayName = new SimpleStringProperty(this,"dayName","");
    private double totalTime;
    public Time startTime  = new Time();
    public Time endTime = new Time();
    public ArrayList<RadioButton> wageList;
    
// constructor
    Day(String day)
    {
        this.wageList = new ArrayList<RadioButton>();
        this.dayName.set(day);
        //this.salary.set(0.0);
    }
    
// dayName
    public StringProperty dayNameProperty(){
        return dayName;
    }
    
    public void setDayName(String s){
        this.dayName.set(s);
    }
    
    public String getDayName(){
       return this.dayName.get();
    }
    
// salary
    public DoubleProperty salaryProperty(){
        return salary;
    }
    
    public void setSalary(Double s){
        this.salary.set(s);
    }
    
    public double getSalary(){
        return this.salary.get();
    }
    

// breakTime
    public IntegerProperty breakTimeProperty(){
        return breakTime;
    }
    
    public void setBreakTime(Integer s){
        this.breakTime.set(s);
    }
    
    public Integer getBreakTime(){
        return this.breakTime.get();
    }


    
// amountEarned
    public DoubleProperty amountEarnedProperty(){
        return amountEarned;
    }
    
    public void setAmountEarned(Double s){
        if(s>=0)
        {
            this.amountEarned.set(s);
        }
        else
        {
            this.amountEarned.set(0);
        }
    }
    
    public double getAmountEarned(){
        return this.amountEarned.get();
    }
    
    public void setTotalTime(double s){
        if(s>=0)
            this.totalTime = s;
    }
    
    public double getTotalTime(){
        return this.totalTime;
    }
}
