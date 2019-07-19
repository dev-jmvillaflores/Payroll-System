/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class Employee_Deduction {
    private int ID;
    private String EmployeeID, SSS, PH, PAGIBIG;
    
    
    Employee_Deduction(int ID, String EmployeeID, String SSS, String PH, String PAGIBIG){
        
        this.ID=ID;
        this.EmployeeID=EmployeeID;
        this.SSS=SSS;
        this.PH=PH;
        this.PAGIBIG=PAGIBIG;
        
    }
    
    public int getID(){
        return ID;
    }
    public String getEmpID(){
        return EmployeeID;
    }
    public String getSSSDeduc(){
        return SSS;
    }
    public String getPHDeduc(){
        return PH;
    }
    public String getPAGIBIGDeduc(){
        return PAGIBIG;
    }
}
