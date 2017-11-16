/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;

/**
 *
 * @author safwan
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    public CheckComboBox v;
    Locale locale  = new Locale("en", "UK");
    DoubleProperty hourlyWage = new SimpleDoubleProperty(0.0);
    public void setHourlyWage(double s){
        if(s>=0)
            hourlyWage.set(s);
        else
            hourlyWage.set(0);
    }
    Day monday = new Day("monday");
    Day tuesday = new Day("tuesday");
    Day wednesday = new Day("wednesday");
    Day thursday = new Day("thursday");
    Day friday = new Day("friday");
    Day saturday = new Day("saturday");
    Day sunday = new Day("sunday");
    Day[] daylist = {monday, tuesday, wednesday, thursday, friday, saturday, sunday};
    
    
    @FXML
    TextField hourly_wage_field_A, hourly_wage_field_B, hourly_wage_field_C;
    ArrayList<TextField> hourlyWageList = new ArrayList<TextField>();
    //monday text fields declarations
    @FXML
    TextField mon_start_hr, mon_start_min, mon_end_hr, mon_end_min, mon_break, mon_earned;
    
    //tuesday text fields declarations
    @FXML
    TextField tue_start_hr, tue_start_min, tue_end_hr, tue_end_min, tue_break, tue_earned;
    
    //wednesday text fields declarations
    @FXML
    TextField wed_start_hr, wed_start_min, wed_end_hr, wed_end_min, wed_break, wed_earned;
    
    //thursday text fields declarations
    @FXML
    TextField thu_start_hr, thu_start_min, thu_end_hr, thu_end_min, thu_break, thu_earned;
    
    //friday text fields declarations
    @FXML
    TextField fri_start_hr, fri_start_min, fri_end_hr, fri_end_min, fri_break, fri_earned;
    
    //saturday text fields declarations
    @FXML
    TextField sat_start_hr, sat_start_min, sat_end_hr, sat_end_min, sat_break, sat_earned;
    
    //sunday text fields declarations
    @FXML
    TextField sun_start_hr, sun_start_min, sun_end_hr, sun_end_min, sun_break, sun_earned;
    
    @FXML
    Label total_hour, total_earned;
    
    @FXML
    VBox vbox_mon;
    
    @FXML
    Group days_group;
    
    //create wage_selector radio buttons
    @FXML
    RadioButton wage_selector_A, wage_selector_B, wage_selector_C;
    
    @FXML
    Button clearAllDayBtn;
    
/*
  Clear class stuff starts
*/
//    ClearClass clearObj = new ClearClass();
    
/*
  Clear class stuff ends
*/
    
//    Funtion: whenever a textfield in the rows starttime, endtime, break is changed,
//             this function get the id of textfield, find that Day onbject and change appropiate value.
//             And then it will recalculate the amount earned for that specific day.

   @FXML
    public void dayTimeChange(String id, String value){
        
        System.out.println("In dayTimeChange method ");
        // get respective day object using substr of the textfield id
        Day day = getDayObject(id.substring(0,3));
        
        // if empty string, set value to 0;
        int val;
        if("".equals(value))
            val = 0;
        else
            val = Integer.parseInt(value);
        
        // find the suffix of textfield and perform update accordingly
        String substr  = id.substring(3);
        if(substr.contains("_start")){
            
            if(substr.contains("_hr")){
                day.startTime.setHr(val);
            }else{
                day.startTime.setMin(val);
            }
        }
        else if(substr.contains("_end"))
        {
            if(substr.contains("_hr")){
                day.endTime.setHr(val);
            }else{
                day.endTime.setMin(val);
            }
        }else if(substr.contains("_break")){
            day.setBreakTime(val);
        }
        calculateAmountEarned(day);    
    }
    
/*
 Fucntion: whenever the hourly wage textfield is changed, it all call this function
           and any day's amount, that doesn't have specifc wage, will be recalculated again.
           Also sets the HourlyWage variable to the field value.
 */
   @FXML
   public void hourlyWageChange(String newValue){
        System.out.println("In hourly wage change");
        if(!newValue.equals(""))
        {
            setHourlyWage(Double.parseDouble(newValue));
            for(Day day: daylist ){
                day.setAmountEarned(day.endTime.getTimeDiff(day.startTime)*hourlyWage.get());
            }
        }else {
            setHourlyWage(0);
            for(Day day: daylist ){
                day.setAmountEarned(day.endTime.getTimeDiff(day.startTime)*hourlyWage.get());
            }
        }
   }
    
   
/*
   Function: calculates the amount earned on a specific day.
   Input: String day, name of the day that needs to be recalculated.
          If dayName == "all", then calculate for all days.
*/
   public void calculateAmountEarned(Day day)
   {
       System.out.format("------------\nStart Time: %d:%d\n",day.startTime.getHr(),day.startTime.getMin());
       System.out.format("End Time: %d:%d\n",day.endTime.getHr(),day.endTime.getMin());
       System.out.format("Break Time: %d min, Avg. Wage $%.2f\n------------\n",day.getBreakTime(),hourlyWage.get());
       
        day.setTotalTime(day.endTime.getTimeDiff(day.startTime)- day.getBreakTime()/60.0);
        day.setAmountEarned((day.getTotalTime()*getWage(day)));
        System.out.println("Get wage: "+getWage(day));
//       day.setAmountEarned((day.getTotalTime()*hourlyWage.get()));
   }
   
   /*
   Fuction: Fires when calculate button is clicked on the GUI and calls the following function
            - setTotalHours()
            - setTotalEarned()
   Input: Event from GUI
   
   */
   public void operateCalculateBtn(ActionEvent evt){
       setTotalHours();
       setTotalEarned();
   }
 
   
   public void setTotalHours() {
       double totalHr = 0;
       for(Day day : daylist){
           totalHr+= day.getTotalTime();
       }
       //format double value into 0.2 decimal points
       DecimalFormat df = new DecimalFormat("#.##");
       total_hour.setText(df.format(totalHr));
   }
   
   public void setTotalEarned() {
       double totalAmount = 0;
       for(Day day : daylist){
           totalAmount+= day.getAmountEarned();
       }
       //format double value into 0.2 decimal points
       DecimalFormat df = new DecimalFormat("#.##");
       total_earned.setText(df.format(totalAmount));
   }
   
/*
   Function: Returns a Day object of the String day that is given as input.
   Input: String day, from mon-sun, seven 3 letter day name.
*/ 
   public Day getDayObject(String dayName)
   {
       switch(dayName){
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
   
   
/*
   Function: This function will check which wage_selector_X is selected and return that wage.
   Input: Day of which the wage is required
*/   
   
    public double getWage(Day day){
        double wageOfDay = 0;
        for(int i=0;i<3;i++){
            if(day.wageList.get(i).isSelected()){
                wageOfDay = Double.parseDouble(hourlyWageList.get(i).getText());
                break;  //only 1 radio button can be choosen in one go
            }
        }
        return wageOfDay;
    }

   
/*
   Function: Gets called when the wage_selector radio buttons are clicked. 
             Will ensure the amountEarned for that particular day changes.
   Input:
*/   
    public void wageSelectorListener(ActionEvent event){
       calculateAmountEarned(getDayObject(event.getSource().toString().substring(31, 34)));
       System.out.println(event);
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inserting 3 diff wage textfield in 1 arraylist for easy access
        hourlyWageList.add(hourly_wage_field_A);
        hourlyWageList.add(hourly_wage_field_B);
        hourlyWageList.add(hourly_wage_field_C);
        // bind 1st wage field to listener must do for the rest of 2 fields
        hourly_wage_field_A.textProperty().addListener((observable, oldValue, newValue)->{hourlyWageChange(newValue);});

        addListenersToDays();
        
        System.out.println("Hey guys");
    }
    
//  Function: This function gets called when the clear all day button is clicked.
//            It is responsible for clearing all data stored in the textfields of all days.
//    public void clearAllDaysInfo() {
//        //go through all days, then all textfield and then clear them
//        List<TextField> clearWorkTimes = getAllDaysTextFieldOf("work times");
//        for(TextField dayTextField : clearWorkTimes){
//            System.out.printf("Start and end time for %s\n",dayTextField.getId());
//            dayTextField.setText("");
//        }
//        
//        List<TextField> clearBreakTimes = getAllDaysTextFieldOf("break times");
//        for(TextField dayTextField : clearBreakTimes){
//            System.out.printf("Clearing break time for %s\n",dayTextField.getId());
//            dayTextField.setText("");
//        }
//    }
            
      
//    Function: This function will get all the textfield elements as required in by a list.
//              If start and end time is wanted, it will provide an array of start and end time for all days.
//              If break time is wanted, it will provide break time for all days.
//              If earned is wanted, it will provide earned textfield for all days.
//    Input: "work time", "break time" or "earned"
    public List<TextField> getAllDaysTextFieldOf(String fieldOf) {
        List<TextField> retList = new ArrayList<TextField>();
        if(!fieldOf.equals("work times") && !fieldOf.equals("break times") && !fieldOf.equals("earned")) {
            System.out.println("Bad input request. Use either work times to get start and end times, \n or break times or earned");
            return null;
        }
        
       Day day;
       //From: Group, Get: all VBox for each day
       for(Node day_col: days_group.getChildren()){
             day = getDayObject(((VBox)day_col).getId().substring(5));
            //get all children nodes for each day. aka get vertical column of each day
            //From: VBox, Get: all HBox and textfield for each day
            
            for(Node n : ((VBox)day_col).getChildren()){
                //for start and end time only
                if(fieldOf.equals("work times") && n instanceof HBox){
                    for(Node ni: ((HBox)n).getChildren()){
                        if(ni instanceof TextField){
                           retList.add((TextField)ni);
                        }
                    }
                }else if(n instanceof TextField){
                    //for rest of the textfield in that column
                    TextField dayTextField = (TextField)n;
                    String id = dayTextField.getId();
                    if(fieldOf.equals("earned") && id.contains("earned")){
                        retList.add(dayTextField);
                    }else if(fieldOf.equals("break times") && id.contains("break")){    
                        retList.add(dayTextField);
                    }
                }
            }
       }
       return retList;
    }
    
//    @FXML
//    public void addListenersToButtons() {
//        
//        clearAllDayBtn.setOnAction((ActionEvent event) -> {
//            clearObj.clearAllDays();
//        });
//    }

    
//    does what it says
    public void addListenersToDays() {
        Day day;
        
        // add listeners on start and end time for all days
        List<TextField> workTimes = getAllDaysTextFieldOf("work times");
        for(int i=0;i<workTimes.size();i++){
            TextField dayTextField = (TextField)workTimes.get(i);
            dayTextField.textProperty().addListener((observable, oldValue, newValue)->{dayTimeChange(dayTextField.getId(),newValue);});
        }
        
        // add listeners on break time for all days
        List<TextField> breakTimes = getAllDaysTextFieldOf("break times");
        for(int i=0;i<breakTimes.size();i++){
            TextField dayTextField = (TextField)breakTimes.get(i);
            String id = dayTextField.getId();
            dayTextField.textProperty().addListener((observable, oldValue, newValue)->{dayTimeChange(id,newValue);});
        }
        
        // add listeners on earned for all days
        List<TextField> earned = getAllDaysTextFieldOf("earned");
        for(int i=0;i<earned.size();i++){
            TextField dayTextField = (TextField)earned.get(i);
            String id = dayTextField.getId();
//            System.out.println(id.substring(0, 3));
            day = getDayObject(id.substring(0, 3));
            dayTextField.textProperty().bind(Bindings.format(locale, "%.2f", day.amountEarnedProperty()));
        }
//        
       // add listeners on radio buttons for all days
       for(Node day_col: days_group.getChildren()){
             day = getDayObject(((VBox)day_col).getId().substring(5));
            //get all children nodes for each day. aka get vertical column of each day
            //From: VBox, Get: all HBox and textfield for each day
            for(Node n : ((VBox)day_col).getChildren()){
                //for start and end time only
                if( n instanceof HBox){
                    for(Node ni: ((HBox)n).getChildren()){
                        if(ni instanceof VBox){    //for a day, when we find Vbox of radio buttons
                            for(Node nii : ((VBox)ni).getChildren()){
                                if(nii instanceof RadioButton){    //for a day, when we find radio button
                                    RadioButton rb = (RadioButton)nii;
                                    System.out.println(rb.toString());
                                    day.wageList.add(rb);
                                }
                            }
                        }
                    }
                }
            }
       }
    }
}

