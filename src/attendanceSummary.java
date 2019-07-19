/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanVillaflores
 */
public class attendanceSummary {
    private int ID;
    private String EmployeeID, DaysRendered, SpecialHoliday,RegularHoliday, Month, Year;

    attendanceSummary(int ID, String EmployeeID, String DaysRendered, String SpecialHoliday, String RegularHoliday, String Month, String Year) {
        
        this.ID = ID;
        this.EmployeeID = EmployeeID;
        this.DaysRendered = DaysRendered;
        this.SpecialHoliday = SpecialHoliday;
        this.RegularHoliday = RegularHoliday;
        this.Month = Month;
        this.Year = Year;
    }
    
    public int getID(){
        return ID;
    }
    public String getEmployeeID(){
        return EmployeeID;
    }
    public String getDaysRendered(){
        return DaysRendered;
    }
    public String getSpecialHoliday(){
        return SpecialHoliday;
    }
    public String getRegularHoliday(){
        return RegularHoliday;
    }
    public String getMonth(){
        return Month;
    }
    public String getYear(){
        return Year;
    }
}
