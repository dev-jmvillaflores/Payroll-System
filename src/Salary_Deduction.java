/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class Salary_Deduction {
    private int DeducID;
    private String DeducName, DeducValue;
    private String salaryRangeFrom, salaryRangeTo;
    
    public Salary_Deduction(int DeducID, String DeducName,String salaryRangeFrom,String salaryRangeTo, String DeducValue){
    
        this.DeducID=DeducID;
        this.DeducName=DeducName;
        this.salaryRangeFrom=salaryRangeFrom;
        this.salaryRangeTo=salaryRangeTo;
        this.DeducValue=DeducValue;
    
    }
    public int getDeducID(){
        return DeducID;
    }
    public String getDeducName(){
        return DeducName;
    }
    public String getSalaryRangeFrom(){
        return salaryRangeFrom;
    }
    public String getSalaryRangeTo(){
        return salaryRangeTo;
    }
    public String getDeducValue(){
        return DeducValue;
    }
}
