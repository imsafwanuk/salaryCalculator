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
import java.util.Arrays;
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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    
    Model theModel = new Model();
    
    @FXML
    public CheckComboBox v;
    Locale locale  = new Locale("en", "UK");
    
    
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

/*
 Fucntion: Whenever the any wage textfield is changed, it all call this function.
           All the respective static wage field will be updated and earned amount for days, 
           working on that salary, will be updated too.
 */
    
    public void hourlyWageChangeListener(String id, String val) {
        try
        {
            Double dval = Double.parseDouble(val);
            switch(id) {
              case "A":
                  Wage.setHourlyWageA(dval);
                  break;
              case "B":
                  Wage.setHourlyWageB(dval);
                  break;
              case "C":
                  Wage.setHourlyWageC(dval);
                  break;
            }
        }
        catch (NumberFormatException nfe)
        {
              switch(id) {
              case "A":
                  Wage.setHourlyWageA(0);
                  break;
              case "B":
                  Wage.setHourlyWageB(0);
                  break;
              case "C":
                  Wage.setHourlyWageC(0);
                  break;
            }
              throw new NumberFormatException("The month entered, " + nfe+ " is invalid.");
        }
        
        theModel.calculateAmountEarned("all");
    }    
   
   /*
   Fuction: Fires when calculate button is clicked on the GUI and calls the following function
            - setTotalHours()
            - setTotalEarned()
   Input: Event from GUI
   
   */
   
   public void calculateBtnListener(ActionEvent evt) {
       System.out.println("hhii");
       double[] tot_hr_and_earned = theModel.operateCalculateBtn();
       System.out.println(Arrays.toString(tot_hr_and_earned));
       //format double value into 0.2 decimal points
       DecimalFormat df = new DecimalFormat("#.##");
       total_hour.setText(df.format(tot_hr_and_earned[0]));
       //format double value into 0.2 decimal points
       DecimalFormat df2 = new DecimalFormat("#.##");
       total_earned.setText(df2.format(tot_hr_and_earned[1]));
   }
      
/*
   Function: Gets called when the wage_selector radio buttons are clicked. 
             Will ensure the amountEarned for that particular day changes.
   Input:
*/   
    public void wageSelectorListener(ActionEvent event) {
        String day = event.getSource().toString().substring(31, 34);
        String radio = event.getSource().toString().substring(29, 30);
        theModel.wageSelector(day, radio);
    }
      

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //inserting 3 diff wage textfield in 1 arraylist for easy access
        hourlyWageList.add(hourly_wage_field_A);
        hourlyWageList.add(hourly_wage_field_B);
        hourlyWageList.add(hourly_wage_field_C);
        // bind 1st wage field to listener must do for the rest of 2 fields
        hourly_wage_field_A.textProperty().addListener((observable, oldValue, newValue)->{hourlyWageChangeListener("A",newValue);});
        hourly_wage_field_B.textProperty().addListener((observable, oldValue, newValue)->{hourlyWageChangeListener("B",newValue);});
        hourly_wage_field_C.textProperty().addListener((observable, oldValue, newValue)->{hourlyWageChangeListener("C",newValue);});
//        hourly_wage_field_A.textProperty().addListener((observable, oldValue, newValue)->{Wage.setHourlyWageA(Double.parseDouble(newValue));});
        

        addListenersToDays();
        
        System.out.println("Hey guys");
    }
    
//  Function: This function gets called when the clear all day button is clicked.
//            It is responsible for clearing all data stored in the textfields of all days.
//    public void clearAllDaysInfo() {
//        //go through all days, then all textfield and then clear them
//        List<TextField> clearWorkTimes = getAllDaysTextFieldOf("work times");
//        for(TextField dayTextField : clearWorkTimes) {
//            System.out.printf("Start and end time for %s\n",dayTextField.getId());
//            dayTextField.setText("");
//        }
//        
//        List<TextField> clearBreakTimes = getAllDaysTextFieldOf("break times");
//        for(TextField dayTextField : clearBreakTimes) {
//            System.out.printf("Clearing break time for %s\n",dayTextField.getId());
//            dayTextField.setText("");
//        }
//    }
            
//    @FXML
//    public void addListenersToButtons() {
//        
//        clearAllDayBtn.setOnAction((ActionEvent event) -> {
//            clearObj.clearAllDays();
//        });
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
        for(Node day_col: days_group.getChildren()) {
              day = theModel.getDayObject(((VBox)day_col).getId().substring(5));
             //get all children nodes for each day. aka get vertical column of each day
             //From: VBox, Get: all HBox and textfield for each day

             for(Node n : ((VBox)day_col).getChildren()) {
                 //for start and end time only
                 if(fieldOf.equals("work times") && n instanceof HBox) {
                     for(Node ni: ((HBox)n).getChildren()) {
                         if(ni instanceof TextField) {
                            retList.add((TextField)ni);
                         }
                     }
                 }else if(n instanceof TextField) {
                     //for rest of the textfield in that column
                     TextField dayTextField = (TextField)n;
                     String id = dayTextField.getId();
                     if(fieldOf.equals("earned") && id.contains("earned")) {
                         retList.add(dayTextField);
                     }else if(fieldOf.equals("break times") && id.contains("break")) {    
                         retList.add(dayTextField);
                     }
                 }
             }
        }
        return retList;
     }
    
//    does what it says
    public void addListenersToDays() {
        Day day;
        
        // add listeners on start and end time for all days
        List<TextField> workTimes = getAllDaysTextFieldOf("work times");
        for(int i=0;i<workTimes.size();i++) {
            TextField dayTextField = (TextField)workTimes.get(i);
            dayTextField.textProperty().addListener((observable, oldValue, newValue)->{theModel.dayTimeChange(dayTextField.getId(),newValue);});
        }
        
        // add listeners on break time for all days
        List<TextField> breakTimes = getAllDaysTextFieldOf("break times");
        for(int i=0;i<breakTimes.size();i++) {
            TextField dayTextField = (TextField)breakTimes.get(i);
            String id = dayTextField.getId();
            dayTextField.textProperty().addListener((observable, oldValue, newValue)->{theModel.dayTimeChange(id,newValue);});
        }
        
        // add listeners on earned for all days
        List<TextField> earned = getAllDaysTextFieldOf("earned");
        for(int i=0;i<earned.size();i++) {
            TextField dayTextField = (TextField)earned.get(i);
            String id = dayTextField.getId();
//            System.out.println(id.substring(0, 3));
            day = theModel.getDayObject(id.substring(0, 3));
            dayTextField.textProperty().bind(Bindings.format(locale, "%.2f", day.amountEarnedProperty()));
        }

       // add listeners on radio buttons for all days
       for(Node day_col: days_group.getChildren()) {
             day = theModel.getDayObject(((VBox)day_col).getId().substring(5));
            //get all children nodes for each day. aka get vertical column of each day
            //From: VBox, Get: all HBox and textfield for each day
            for(Node n : ((VBox)day_col).getChildren()) {
                //for start and end time only
                if( n instanceof HBox) {
                    for(Node ni: ((HBox)n).getChildren()) {
                        if(ni instanceof VBox) {    //for a day, when we find Vbox of radio buttons
                            for(Node nii : ((VBox)ni).getChildren()) {
                                if(nii instanceof RadioButton) {    //for a day, when we find radio button
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

