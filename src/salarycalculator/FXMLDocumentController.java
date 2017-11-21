/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
    ArrayList<TextField> hourlyWageList = new ArrayList<>();
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
            double dval = Double.parseDouble(val);
            switch(id) {
              case "A":
                  if(!Wage.setHourlyWageA(dval))
                      this.showWarningOrError(faultType.WARNING, "Wage must be greater than 0 ");
                  break;
              case "B":
                  if(!Wage.setHourlyWageB(dval))
                      this.showWarningOrError(faultType.WARNING, "Wage must be greater than 0 ");
                  break;
              case "C":
                  if(!Wage.setHourlyWageC(dval))
                      this.showWarningOrError(faultType.WARNING, "Wage must be greater than 0 ");
                  break;
            }
        }
        catch (NumberFormatException nfe)
        {
            if(!val.equals(""))
                this.showWarningOrError(faultType.ERROR, "Wage must be a number greater than 0");
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
      

/**
 * Function: Causes the Model to save user info in a .txt or .properties file in users desired location.
 * Stimuli: When Save btn is clicked
 */
    public void saveBtnListener() throws IOException{
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterProperties = new FileChooser.ExtensionFilter("Properties, TXT files (*.properties)", "*.properties", "*.txt");
        FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilterProperties);
        fileChooser.getExtensionFilters().add(extFilterTxt);

        //Show save file dialog
        File saveFile = fileChooser.showSaveDialog(null);
            
        if(saveFile != null) {
            Model.SaveProperties saver = theModel.new SaveProperties(saveFile);
            saver.saveUserProperties();
            saver.closeFile();
        }else{
            System.out.println("Invalid file");
        }
    }
    
/**
 * Function: Causes the Model to load user info from a .txt or .properties file in users desired location.
 * Stimuli: When load btn is clicked
 */
    public void loadBtnListener() throws IOException{
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterProperties = new FileChooser.ExtensionFilter("Properties, TXT files (*.properties)", "*.properties", "*.txt");
        FileChooser.ExtensionFilter extFilterTxt = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilterProperties);
        fileChooser.getExtensionFilters().add(extFilterTxt);

        //Show save file dialog
        File loadFile = fileChooser.showOpenDialog(null);
        if(loadFile != null) {
            Model.LoadProperties loader = theModel.new LoadProperties(loadFile);
            if(loader.getCompatibility()) {
                loader.loadUserProperties();
                loader.closeFile();
                this.reloadGUI();
            }
        }else{
            System.out.println("Invalid file");
        }
    }
    
    
/**
 * Function: Reloads all the user inputable objects in the GUI to whats in the model currently.
 * Stimuli: Can be called when user loads a userInfo.properties files
 */
    public void reloadGUI() {
        // reload wages
        this.reloadWages();
        
        // reload days
        this.reloadDays();
    }
    
/**
   * Function: Reloads all wages textfield
   * Stimuli: Called by reloadGUI()
   */
    private void reloadWages() {
        if(Wage.getHourlyWageA() > 0)
            hourlyWageList.get(0).setText(Double.toString(Wage.getHourlyWageA()));
        
        if(Wage.getHourlyWageB() > 0)
            hourlyWageList.get(1).setText(Double.toString(Wage.getHourlyWageB()));
        
        if(Wage.getHourlyWageC() > 0)
            hourlyWageList.get(2).setText(Double.toString(Wage.getHourlyWageC()));
    }
    
    
/**
 * Function: Reloads days, start, end, break and (radio) wage selection buttons
 * Stimuli: Called by reloadGUI()
 */
    private void reloadDays() {
        // contains start and end hours/mins for all days
        List<TextField> theList = this.getAllDaysTextFieldOf("work times");
        // contains break time for all days
        List<TextField> breakList = this.getAllDaysTextFieldOf("break times");
        //3x radio btns for all 7 days
        List<RadioButton> radioBtnList = this.getAllDayRadioButtons();
            
            for(int i = 0; i < theModel.daylist.length; i++) {
                Day day = theModel.daylist[i];
                
                for(int j = i*4 ; j < ((i*4) + 4); j+=4) {
                    //for seg fault
                    if(j+1 >= theList.size())
                        break;
                
                   
                    // start time 
                    if(day.startTime.getHr() > 0) {
                        theList.get(j).setText(Integer.toString(day.startTime.getHr()));
                        System.out.println("!!!!!!"+day.startTime.getHr());
                    }
                    if(day.startTime.getMin() > 0) {
                        theList.get(j+1).setText(Integer.toString(day.startTime.getMin()));
                        System.out.println("!!!!!!"+day.startTime.getMin());
                    }

                    
                    // end time
                    if(day.endTime.getHr() > 0) {
                        theList.get(j+2).setText(Integer.toString(day.endTime.getHr()));
                        System.out.println("!!!!!!"+day.endTime.getHr());
                    }

                    if(day.endTime.getMin() > 0) {
                        theList.get(j+3).setText(Integer.toString(day.endTime.getMin()));
                    }
            }

            // break time
            if(day.getBreakTime()> 0) {
                breakList.get(i).setText(Integer.toString(day.getBreakTime()));
            }
            
            // wage selection
            for(int j = i*3; j < ((i*3) + 3); j+=3) {
                String str = day.getWageSelection();
                switch(str) {
                    case "A":
                        radioBtnList.get(j).setSelected(true);
                        break;
                    case "B":
                        radioBtnList.get(j+1).setSelected(true);
                        break;
                    case "C":
                        radioBtnList.get(j+2).setSelected(true);
                        break;
                }        
            }
        }
    }
    
    
/**
 * Function: Allows dev to show warnings and errors
 * 
 */
    public static enum faultType {WARNING,ERROR};
    
    
    public static void showWarningOrError(faultType code, String msg) {
        if(code == faultType.WARNING) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(msg);
            alert.showAndWait();
        }else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(msg);
            alert.showAndWait();
        }
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
//    Input: "work times", "break times" or "earned"

    public List<TextField> getAllDaysTextFieldOf(String fieldOf) {
        List<TextField> retList = new ArrayList<>();
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
    
    
    
/**
 * Function: Creates a list of radio buttons, 3 btns for all 7 days and puts them in a List
 * Stimuli: Can be called whenever radio buttons are needed.
 * Return: List<RadioButton>
 */
    public List<RadioButton> getAllDayRadioButtons() {
        Day day;
        List<RadioButton> radioBtnList = new ArrayList<RadioButton>();
         
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
                                    radioBtnList.add(rb);
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return radioBtnList;
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

