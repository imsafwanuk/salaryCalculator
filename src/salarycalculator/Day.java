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
    public Wage dayWage = new Wage();
    private String wageSelection;

    
    
// constructor
    Day(String day)
    {
        this.wageList = new ArrayList<RadioButton>();
        this.dayName.set(day);
        this.setWageSelection("A");
        //this.salary.set(0.0);
    }
    
    /**
     * Function:
     * Stimuli:
     * Input:
     * Return:
     */
    
    /**
     * Function: Just updates day wage to most recent static wage for specified wage field selected.
     * Stimuli: Gets called when any hourly wage changes
     */
    public void refreshDayWage() {
        this.setWageSelection(this.getWageSelection());
    }
    
    //wageSelection
    
    /**
     * Return: For the specified radio A,B,C, it returns the amount for that wage field
     */
    public double getDayWage() {
        return this.dayWage.getWage();
    }
    
    
    /**
     * Return: The radio name, A,B,C, for which this day was selected with
     */
    public String getWageSelection() {
        return wageSelection;
   }

    /**
     * Function: Sets the radio button for this particular day and sets the wage for this day
     * Stimuli: Gets called when radio buttons for a day is changed
     * Input: "A", "B", "C"
     */
    public void setWageSelection(String wageSelection) {

        if("A".equals(wageSelection) || "B".equals(wageSelection) ||"C".equals(wageSelection)) {
            this.wageSelection = wageSelection;
            dayWage.setWage(wageSelection);
        }else {
            this.wageSelection = "A";
            System.out.println("Error in day class, set wage selection. Selection not A, B or C>> "+wageSelection);
        }
            
    }    
    
    
    
// dayName
    public StringProperty dayNameProperty() {
        return dayName;
    }
    
    public void setDayName(String s) {
        this.dayName.set(s);
    }
    
    public String getDayName() {
       return this.dayName.get();
    }
    
// salary
    public DoubleProperty salaryProperty() {
        return salary;
    }
    
    public void setSalary(Double s) {
        this.salary.set(s);
    }
    
    public double getSalary() {
        return this.salary.get();
    }
    

// breakTime
    public IntegerProperty breakTimeProperty() {
        return breakTime;
    }
    
    public void setBreakTime(Integer s) {
        System.out.println(s+":"+this.endTime.getTimeDiff(this.startTime));
        if((double)(s/60.0)>= this.endTime.getTimeDiff(this.startTime)) {
            System.out.println("Break time can't be more than the total hours worked.");
            this.breakTime.set((int) this.endTime.getTimeDiff(this.startTime)*60);
            return;
        }
        this.breakTime.set(s);
    }
    
    public Integer getBreakTime() {
        return this.breakTime.get();
    }


    
// amountEarned
    public DoubleProperty amountEarnedProperty() {
        return amountEarned;
    }
    
    public void setAmountEarned(Double s) {
        if(s>=0) {
            this.amountEarned.set(s);
        }else {
            this.amountEarned.set(0);
        }
    }
    
    public double getAmountEarned() {
        return this.amountEarned.get();
    }
    
    public void setTotalTime(double s) {
        if(s>=0)
            this.totalTime = s;
    }
    
    public double getTotalTime() {
        return this.totalTime;
    }
}
