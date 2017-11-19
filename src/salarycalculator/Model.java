/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import java.text.DecimalFormat;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;

/**
 *
 * @author safwan
 */

public class Model {
    Day monday = new Day("monday");
    Day tuesday = new Day("tuesday");
    Day wednesday = new Day("wednesday");
    Day thursday = new Day("thursday");
    Day friday = new Day("friday");
    Day saturday = new Day("saturday");
    Day sunday = new Day("sunday");
    Day[] daylist = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
    
    Model() {
        
    }
    
//    Funtion: whenever a textfield in the rows starttime, endtime, break is changed,
//             this function get the id of textfield, find that Day onbject and change appropiate value.
//             And then it will recalculate the amount earned for that specific day.
    
    public void dayTimeChange(String id, String value) {
        
        System.out.println("In dayTimeChange method ");
        // get respective day object using substr of the textfield id
        Day day = this.getDayObject(id.substring(0,3));
        
        // if empty string, set value to 0;
        int val;
        if("".equals(value))
            val = 0;
        else
            val = Integer.parseInt(value);
        
        // find the suffix of textfield and perform update accordingly
        String substr  = id.substring(3);
        if(substr.contains("_start")) {
            
            if(substr.contains("_hr")) {
                day.startTime.setHr(val);
            }else{
                day.startTime.setMin(val);
            }
        }
        else if(substr.contains("_end"))
        {
            if(substr.contains("_hr")) {
                day.endTime.setHr(val);
            }else{
                day.endTime.setMin(val);
            }
        }else if(substr.contains("_break")) {
            day.setBreakTime(val);
        }
        calculateAmountEarned(day);    
    }
    
/**
 *  Function: Calculates the total hours worked on 7days and the amount earned.
 *  Stimuli: Gets called when calculate button is clicked
 *  Return: An array[2] containing total hours and total earned, respectively.
 */
    public double[] operateCalculateBtn() {
       double[] tot_hr_and_earned = new double[2];
       tot_hr_and_earned[0] = setTotalHours();
       tot_hr_and_earned[1] = setTotalEarned();
       return tot_hr_and_earned;
   }
    

   private double setTotalHours() {
       double totalHr = 0;
       for(Day day : daylist) {
           totalHr+= day.getTotalTime();
       }
       //format double value into 0.2 decimal points
       DecimalFormat df = new DecimalFormat("#.##");
       return totalHr;
   }
   
   private double setTotalEarned() {
       double totalAmount = 0;
       for(Day day : daylist) {
           totalAmount+= day.getAmountEarned();
       }
       //format double value into 0.2 decimal points
       DecimalFormat df = new DecimalFormat("#.##");
       return totalAmount;
   }
   
   
   
/**
 * Function: calculates the amount earned on a specific day.
 * Input: String day, name of the day that needs to be recalculated.
 *        If dayName == "all", then calculate for all days.
*/
    public void calculateAmountEarned(String str) {
        if(!str.equals("all"))
            return;
        
        for(Day day: daylist ) {
            // as hourly wage changed, set the day's wage again.
            day.refreshDayWage();
            day.setAmountEarned(day.endTime.getTimeDiff(day.startTime)*day.dayWage.getWage());
        }
            
    }
    
    public void calculateAmountEarned(Day day)
    {
       System.out.format("------------\nStart Time: %d:%d\n",day.startTime.getHr(),day.startTime.getMin());
       System.out.format("End Time: %d:%d\n",day.endTime.getHr(),day.endTime.getMin());
       System.out.format("Break Time: %d min, Avg. Wage $%.2f\n------------\n",day.getBreakTime(),day.getDayWage());
       
        day.setTotalTime(day.endTime.getTimeDiff(day.startTime)- day.getBreakTime()/60.0);
        day.setAmountEarned((day.getTotalTime()*day.getDayWage()));
        System.out.println("Get wage: "+day.getDayWage());
    }
    
     
/**
 * Function: Assigns a 1 wage, out of the 3, to a particular day.
 *           Then calculates everything again for that day.
 * Input: String dayStr-> tells which day was picked
 *        String radio-> tells which wage was picked
 */
    public void wageSelector(String dayStr, String radio) {
        Day day = this.getDayObject(dayStr);
        day.setWageSelection(radio);
        this.calculateAmountEarned(day);
    }
    
/**
 * Function: Returns a Day object of the String day that is given as input.
 * Input: String day, from mon-sun, seven 3 letter day name.
*/ 
    public Day getDayObject(String dayName)
   {
       switch(dayName) {
           case "mon":
               return monday;
           case "tue":
               return tuesday;
           case "wed":
               return wednesday;
           case "thu":
               return thursday;
           case "fri":
               return friday;
           case "sat":
               return saturday;
           case "sun":
               return sunday;
           default:
               return null;
       }
   }
    
}
