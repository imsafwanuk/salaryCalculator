
package salarycalculator;

import java.util.List;
import javafx.scene.control.TextField;

/**
 * This class will be involved with everything that needs to be cleared from the UI. ie anything from text boxes.
 * @author safwan
 */
public class ClearClass {
    FXMLDocumentController fxmlobj = new FXMLDocumentController();
//  Function: This will clear all start, end and break time for all 7 days.
    public void clearAllDays() {
        //go through all days, then all textfield and then clear them
        List<TextField> clearWorkTimes = fxmlobj.getAllDaysTextFieldOf("work times");
        for(TextField dayTextField : clearWorkTimes){
            System.out.printf("Start and end time for %s\n",dayTextField.getId());
            dayTextField.setText("");
        }
        
        List<TextField> clearBreakTimes = fxmlobj.getAllDaysTextFieldOf("break times");
        for(TextField dayTextField : clearBreakTimes){
            System.out.printf("Clearing break time for %s\n",dayTextField.getId());
            dayTextField.setText("");
        }
    };

/*    
    
//  Function: This will clear selected break time for days.
    public void clearSelectedDays(List<Day> dayslist) {
        
    };    
    
    
//  Function: This will clear all break time for all 7 days.
    public void clearAllBreaks() {
        
    };
    
//  Function: This will clear selected break time for days.
    public void clearSelectedBreaks(List<Day> dayslist) {
        
    };  

*/
}
