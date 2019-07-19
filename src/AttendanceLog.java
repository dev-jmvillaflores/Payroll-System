/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
class AttendanceLog {
    private int ID;
    private String EmployeeID, EmployeeName, EmployeePosition, Login, Logout, Month, Day, Year, NumberOfHours;
   
    AttendanceLog(int ID, String EmployeeID, String EmployeeName, String EmployeePosition, String Login, String Logout, String Month, String Day, String Year, String NumberOfHours) {
         
        this.ID = ID;
        this.EmployeeID = EmployeeID;
        this.EmployeeName = EmployeeName;
        this.EmployeePosition = EmployeePosition ;       
        this.Login = Login;
        this.Logout = Logout;
        this.Month = Month;      
        this.Day = Day;
        this.Year = Year;
        this.NumberOfHours = NumberOfHours;
    }
    public int getID(){
        return ID;
    }
    public String getEmployeeName(){
        return EmployeeName;
    }
    public String getEmployeeID(){
        return EmployeeID;
    }
    public String getEmployeePosition(){
        return EmployeePosition;
    }
    public String getLogin(){
        return Login;
    }
    public String getLogout(){
        return Logout;
    }
    public String getMonth(){
        return Month;
    }
    public String getDay(){
        return Day;
    }
    public String getYear(){
        return Year;
    }
    public String getNumberOfHours(){
        return NumberOfHours;
    }
}
