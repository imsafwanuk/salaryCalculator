/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salarycalculator;

/**
 * This class has 3 static wage fields for the 3 types of wages, weekdays, weekends(Sat,Sun) 
 * and has a main getWage method that gives the wage set for an instance of this class.
 * @author safwan
 */

public final class Wage {
    
    private static double hourlyWageA = 0.00;
    private static double hourlyWageB = 0.00;
    private static double hourlyWageC = 0.00;
    private double mainWage = 0;
    
    Wage() {}
    
    Wage(String val) {
        this.setWage(val);
    }

//setters
    public static void setHourlyWageA(double hourlyWageA) {
        System.out.println(hourlyWageA);
        if(hourlyWageA>=0.0)
            Wage.hourlyWageA = hourlyWageA;
        else
            Wage.hourlyWageA = 0.0;
    }

    public static void setHourlyWageB(double hourlyWageB) {
        System.out.println(hourlyWageB);
        if(hourlyWageB>=0.0)
            Wage.hourlyWageB = hourlyWageB;
        else
            Wage.hourlyWageB = 0.0;
    }

    public static void setHourlyWageC(double hourlyWageC) {
        System.out.println(hourlyWageC);
        if(hourlyWageC>=0.0)
            Wage.hourlyWageC = hourlyWageC;
        else
            Wage.hourlyWageC = 0.0;
    }

    public void setWage(String mainWage) {
        switch(mainWage) {
            case "A":
                this.mainWage = Wage.hourlyWageA;
                break;
            case "B":
                this.mainWage = Wage.hourlyWageB;
                break;
            case "C":
                this.mainWage = Wage.hourlyWageC;
                break;
        }
        
    }
    
//getters
    public static double getHourlyWageA() {
        return hourlyWageA;
    }

    public static double getHourlyWageB() {
        return hourlyWageB;
    }

    public static double getHourlyWageC() {
        return hourlyWageC;
    }

    public double getWage() {
        return mainWage;
    }
    
    
}
