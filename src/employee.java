/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Jhovan
 */
class employee {
    private int ID;
    private String employeeID, empFName,empMName,empLName, empJobTitle, empGender, empContactNo
                    , empBDate, empAddress,empRatePerDay, empTIN, empPH, empPAGIBIG, empSSS, empDStart,empAge;
    
    public employee(int ID,String employeeID, String empFName,String empMName,String empLName, String empGender, 
             String empBDate, String empAddress,String empJobTitle,  String empRatePerDay,String empContactNo,
                    String empTIN, String empPH, String empPAGIBIG, String empSSS, String empDStart,String empAge) {
        this.ID=ID;
        this.employeeID=employeeID;
        this.empFName=empFName;
        this.empMName=empMName;
        this.empLName=empLName;
        this.empJobTitle=empJobTitle;
        this.empGender=empGender;
        this.empContactNo=empContactNo;
        this.empBDate=empBDate;
        this.empAddress=empAddress;
        this.empRatePerDay=empRatePerDay;
        this.empTIN=empTIN;
        this.empPH=empPH;
        this.empPAGIBIG=empPAGIBIG;
        this.empSSS=empSSS;
        this.empDStart=empDStart;
        this.empAge=empAge;
    }
    
    public int ID(){
        return ID;
    }
    public String getEmployeeID(){
        return employeeID;
    }
    public String getEmpName(){
        return empLName+", "+empFName+" "+empMName;
    }
    public String getEmpJobTitle(){
        return empJobTitle;
    }
    public String getEmpGender(){
        return empJobTitle;
    }
    public String getEmpContactNo(){
        return empJobTitle;
    }
    public String getEmpBDate(){
        return empJobTitle;
    }
    public String getEmpAddress(){
        return empJobTitle;
    }
    public String getEmpRatePerDay(){
        return empRatePerDay;
    }
    public String getEmpTIN(){
        return empJobTitle;
    }
    public String getEmpPH(){
        return empJobTitle;
    }
    public String getEmpPAGIBIG(){
        return empJobTitle;
    }
    public String getEmpSSS(){
        return empJobTitle;
    }
    public String getEmpDStart(){
        return empJobTitle;
    }
    public String getEmpAge(){
        return empJobTitle;
    }
}
