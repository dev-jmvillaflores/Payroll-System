
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.view.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import javax.swing.Timer;


//import org.apache.commons.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public final class mainPage extends javax.swing.JFrame implements ActionListener{
    public Connection cn;
    public Statement st;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    int yMouse, xMouse;
    
    public String printAttendance;
    public String generateChoice;
    
    /**
     * Creates new form mainPage
     */
    public mainPage() {
            initComponents();
            updateClock();
            new Timer (1000, this).start();
            
             try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/citysquare_payroll?zeroDateTimeBehavior=convertToNull","root","");
            st = cn.createStatement();
            show_employeesList();
            show_attendanceLog();
            
            
        } catch (Exception ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actionPerformed(ActionEvent e) {
        updateClock();
    }
    private void updateClock() {
        DatePL.setText(dateFormat.format(Calendar.getInstance().getTime()));
        TimePL.setText(timeFormat.format(Calendar.getInstance().getTime()));
    }
   
    @SuppressWarnings("NonPublicExported")
    public ArrayList<employee> employeeList(){
            ArrayList<employee> employeesList = new ArrayList<>();
            try{
            String query = "SELECT * FROM `employeeinfo_tb`";
            ResultSet rs = st.executeQuery(query);
            int i = 0;
            employee emp;
            while(rs.next()){
                emp = new employee(rs.getInt("ID"),rs.getString("EmployeeID"), rs.getString("First_Name"), rs.getString("Middle_Name"), rs.getString("Last_Name"), rs.getString("Gender"), rs.getString("Birth_Date"),
                        rs.getString("Address"), rs.getString("Job_Title"),rs.getString("RatePerDay"),rs.getString("Contact_No"),rs.getString("Tin_No"),rs.getString("SSS_No"),
                        rs.getString("PhilHealth_No"),rs.getString("PAGIBIG_No"),rs.getString("Date_Start"),rs.getString("Age"));
                employeesList.add(emp); i++;
            }
            numberOfEmployee.setText(String.valueOf(i));
                    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
           return employeesList;
    }
    public void show_employeesList(){
        ArrayList<employee> List = employeeList();
        DefaultTableModel model =  (DefaultTableModel)empTable.getModel();
        Object[] row = new Object[13];
        for(int i=0; i<List.size();i++){
            row[0] = List.get(i).getEmployeeID(); 
            row[1] = List.get(i).getEmpName();
            row[2] = List.get(i).getEmpJobTitle();
            row[3] = List.get(i).getEmpAge();
            model.addRow(row);
        }
         
    }
    
    
    @SuppressWarnings("NonPublicExported")
    public ArrayList<AttendanceLog> attendanceLog(){
        ArrayList<AttendanceLog> attendanceList = new ArrayList<>();
        try {
            String query = "SELECT * FROM `attendancelog_perday_tb`";
            ResultSet rs1 = st.executeQuery(query);
            
            AttendanceLog aLog;
            while(rs1.next()){
                aLog = new AttendanceLog(rs1.getInt("ID"),rs1.getString("EmployeeID"),rs1.getString("Employee_Name"),rs1.getString("Employee_Position"),rs1.getString("Log_In"),rs1.getString("Log_Out"),
                rs1.getString("Month"),rs1.getString("Day"),rs1.getString("Year"),rs1.getString("Number_of_Hours"));
                attendanceList.add(aLog);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendanceList;
    }
    public void show_attendanceLog(){
        try{
        ArrayList<AttendanceLog> attendanceList = attendanceLog();
        DefaultTableModel model =  (DefaultTableModel)attendanceLogTable.getModel();
        Object[] row = new Object[13];
        for(int l=0; l<attendanceList.size();l++){
            row[0] = attendanceList.get(l).getEmployeeID();
            row[1] = attendanceList.get(l).getEmployeeName();
            row[2] = attendanceList.get(l).getEmployeePosition();
            row[3] = attendanceList.get(l).getLogin();
            row[4] = attendanceList.get(l).getLogout();
            model.addRow(row);
       
        }
        
         }catch(Exception e){
              System.out.println(e);  
        }
    }
    public ArrayList<attendanceSummary> attendanceSummary(){
        ArrayList<attendanceSummary> attendanceSummaryList = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM attendance_summary";
            ResultSet rs = st.executeQuery(query);
            
            attendanceSummary as;
            while(rs.next()){
                as = new attendanceSummary(rs.getInt("ID"), rs.getString("EmployeeID"),rs.getString("DaysRendered"), rs.getString("SpecialHoliday"), rs.getString("RegularHoliday")
                ,rs.getString("Month"),rs.getString("Year"));
                attendanceSummaryList.add(as);
            }
            
            
        }catch(SQLException ex){
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendanceSummaryList;
    }
    
     @SuppressWarnings("NonPublicExported")
     public ArrayList<Salary_Deduction> Salary_Deduction(){
        ArrayList<Salary_Deduction> Salary_DeductionList = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM `salary_deduction`";
            ResultSet rs2 = st.executeQuery(query);
            
            Salary_Deduction sD;
            while(rs2.next()){
                sD = new Salary_Deduction(rs2.getInt("DeducID"),rs2.getString("DeducName"),rs2.getString("salaryRangeFrom"),rs2.getString("salaryRangeTo"),rs2.getString("DeducValue"));
                Salary_DeductionList.add(sD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Salary_DeductionList;
     }
     
     public ArrayList<Employee_Deduction> Employee_Deduction(){
        ArrayList<Employee_Deduction> Employee_DeductionList = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM `Employee_Deduction`";
            ResultSet rs2 = st.executeQuery(query);
            
            Employee_Deduction eD;
            while(rs2.next()){
                eD = new Employee_Deduction(rs2.getInt("ID"),rs2.getString("EmployeeID"),rs2.getString("SSS"),rs2.getString("PH"),rs2.getString("PAGIBIG"));
                Employee_DeductionList.add(eD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Employee_DeductionList;
     }
     
    
    public void update_attendance_table(){
        String newAttendance = "SELECT * FROM `attendancelog_perday_tb`";
        ResultSet aRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                aRs = st.executeQuery(newAttendance);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));
        }catch(SQLException e){
        }
    }
    public void update_employee_table(){
        String newEmployee = "SELECT * FROM `employeeinfo_tb`";
        ResultSet eRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                eRs = st.executeQuery(newEmployee);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(eRs));
        }catch(SQLException e){
        }
    }
    public void update_attendance_summary_table(){
        String newEmployee = "SELECT * FROM `attendance_summary`";
        ResultSet eRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                eRs = st.executeQuery(newEmployee);
                attendanceSummaryTable.setModel(DbUtils.resultSetToTableModel(eRs));
        }catch(SQLException e){
        }
    }
    
    
    public String get_month_array(String indexNum){
        
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        String[] monthArr = new String[] { "January","February",
                                           "March","April",
                                           "May","June",
                                            "July","August",
                                            "September","October",
                                            "November","December"};
        
        String newMonth = monthArr[Integer.parseInt(indexNum)];
        
        return newMonth;
    }
    public int get_PH_Deduc(int SubTotal){
        int deduc = 0;
         try {
            ArrayList<Salary_Deduction> Salary_DeductionList = Salary_Deduction();
            for(int i=0; i<Salary_DeductionList.size();i++){
                if(Salary_DeductionList.get(i).getDeducName().equals("PhilHealth")){
                    int rangeFrom = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeFrom());
                    int rangeTo = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeTo());
                    if(rangeFrom<=SubTotal&&rangeTo>=SubTotal){
                            deduc = Integer.parseInt(Salary_DeductionList.get(i).getDeducValue());
                    }
                }
               
            }
        } catch (Exception ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deduc;
    }
    public int get_SSS_Deduc(int SubTotal){
        int deduc = 0;
         try {
            ArrayList<Salary_Deduction> Salary_DeductionList = Salary_Deduction();
            for(int i=0; i<Salary_DeductionList.size();i++){
                if(Salary_DeductionList.get(i).getDeducName().equals("SSS")){
                    int rangeFrom = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeFrom());
                    int rangeTo = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeTo());
                    if(rangeFrom<=SubTotal&&rangeTo>=SubTotal){
                            deduc = Integer.parseInt(Salary_DeductionList.get(i).getDeducValue());
                    }
                }
               
            }
        } catch (Exception ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deduc;
    }
    public int get_PAGIBIG_Deduc(int SubTotal){
        int deduc = 0;
         try {
            ArrayList<Salary_Deduction> Salary_DeductionList = Salary_Deduction();
            for(int i=0; i<Salary_DeductionList.size();i++){
                if(Salary_DeductionList.get(i).getDeducName().equals("PAGIBIG")){
                    int rangeFrom = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeFrom());
                    int rangeTo = Integer.parseInt(Salary_DeductionList.get(i).getSalaryRangeTo());
                    if(rangeFrom<=SubTotal&&rangeTo>=SubTotal){
                            deduc = Integer.parseInt(Salary_DeductionList.get(i).getDeducValue());
                    }
                }
               
            }
        } catch (Exception ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deduc;
    }
    
    public void auto_generate_salary(){
        validateID.setForeground(Color.white);

        final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            //Convert the value of month from number to word
            String Month1 = monthFormat.format(Calendar.getInstance().getTime());
            int intMonth = Integer.parseInt(Month1);
            String newMonth = get_month_array(String.valueOf(intMonth - 1));    
            String newYear = yearFormat.format(Calendar.getInstance().getTime());
            
        
        //employeeinfo_tb
        String EmployeeID, RatePerDay, EmployeeName, EmployeePosition;
        //attendance_summary
        String daysRendered, SpecialHoliday, RegularHoliday;
               
        
     try{
            ArrayList<employee> employeeList = employeeList();
            for(int j=0; j<employeeList.size();j++){
                        validateID.setForeground(Color.white);
                EmployeeID = employeeList.get(j).getEmployeeID();
                RatePerDay = employeeList.get(j).getEmpRatePerDay();
                EmployeePosition = employeeList.get(j).getEmpJobTitle();
                EmployeeName = employeeList.get(j).getEmpName();

                    ArrayList<attendanceSummary> attendanceSummaryList =  attendanceSummary();
                    for(int k=0; k<attendanceSummaryList.size();k++){
                        
                                
                        if(attendanceSummaryList.get(k).getEmployeeID().equals(EmployeeID)&&
                                attendanceSummaryList.get(k).getMonth().equals(newMonth)&&
                                attendanceSummaryList.get(k).getYear().equals(newYear)){
                            
                            daysRendered = attendanceSummaryList.get(k).getDaysRendered();
                            SpecialHoliday = attendanceSummaryList.get(k).getSpecialHoliday();
                            RegularHoliday = attendanceSummaryList.get(k).getRegularHoliday();
                                
                            int SpecialHolidayTotal;
                            int RegularHolidayTotal;
                            
                            //Check if employee works on special holiday
                            if(Integer.parseInt(SpecialHoliday)==0){
                                SpecialHolidayTotal = 0;
                            }else{
                                SpecialHolidayTotal = (Integer.parseInt(SpecialHoliday) * 3) * Integer.parseInt(RatePerDay);
                            }
                            //check if employee works on regular holiday
                            if(Integer.parseInt(RegularHoliday)==0){
                                RegularHolidayTotal = 0;
                            }else{
                                RegularHolidayTotal = (Integer.parseInt(RegularHoliday) * 2) * Integer.parseInt(RatePerDay);
                            }
                           
                            //computations for basic salary
                            int dr =Integer.parseInt(daysRendered), rp =Integer.parseInt(RatePerDay);
                            
                            int BasicSalary = dr * rp ; 
                            //Computation for SubTotal
                            int SubTotal = BasicSalary + SpecialHolidayTotal + RegularHolidayTotal;
                            
                            //Getting all deductions
                            int PHdeduc = get_PH_Deduc(SubTotal);
                            int SSSdeduc = get_SSS_Deduc(SubTotal);
                            int PAGIBIGdeduc = get_PAGIBIG_Deduc(SubTotal);
                             if(PAGIBIGdeduc==1){
                                    PAGIBIGdeduc = (int) (BasicSalary * 0.01);
                                }else if(PAGIBIGdeduc==2){
                                    PAGIBIGdeduc = (int) (BasicSalary * 0.01);   
                                }
                            //sum of all deductions 
                            int TotalDeduction = PHdeduc + SSSdeduc + PAGIBIGdeduc;
                            //computation of netpay
                            int NetPay = SubTotal - TotalDeduction;
                               
                                       String insertSalaryQuery = "INSERT INTO salary(EmployeeID, EmployeeName, EmployeePosition, BasicSalary, DaysRendered, RegularHoliday, "
                                            + "SpecialHoliday, SubTotal, SSSdeduction, PHdeduction, PAGIBIG, TotalDeduction, NetPay, Month, Year)"

                                            //inserting all values to fields
                                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                        PreparedStatement insertSalary = cn.prepareStatement(insertSalaryQuery);
                                        insertSalary.setString(1, EmployeeID);
                                        insertSalary.setString(2, EmployeeName);
                                        insertSalary.setString(3, EmployeePosition);
                                        insertSalary.setString(4, String.valueOf(BasicSalary));
                                        insertSalary.setString(5, daysRendered);
                                        insertSalary.setString(6, String.valueOf(RegularHolidayTotal));
                                        insertSalary.setString(7, String.valueOf(SpecialHolidayTotal));
                                        insertSalary.setString(8, String.valueOf(SubTotal));
                                        insertSalary.setString(9, String.valueOf(SSSdeduc));
                                        insertSalary.setString(10, String.valueOf(PHdeduc));
                                        insertSalary.setString(11, String.valueOf(PAGIBIGdeduc));
                                        insertSalary.setString(12, String.valueOf(TotalDeduction));
                                        insertSalary.setString(13, String.valueOf(NetPay));
                                        insertSalary.setString(14, newMonth);
                                        insertSalary.setString(15, newYear);
                                        insertSalary.execute();     
                                
                        }
                    }
            }
            JOptionPane.showMessageDialog(this, "Generate All SuccessFully!");

            
        }catch(Exception ex){
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
            
    public void manual_generate_salary(String EmployeeID){
        final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            //Convert the value of month from number to word
            String Month1 = monthFormat.format(Calendar.getInstance().getTime());
            int intMonth = Integer.parseInt(Month1);
            String newMonth = get_month_array(String.valueOf(intMonth - 1));    
            String newYear = yearFormat.format(Calendar.getInstance().getTime());
            
        
        //employeeinfo_tb
        String RatePerDay1, EmployeeName1, EmployeePosition1;
        //attendance_summary
        String daysRendered, SpecialHoliday, RegularHoliday;
        boolean valid = false;       
        
     try{
            ArrayList<employee> employeeList = employeeList();
            for(int j=0; j<employeeList.size();j++){
                if(! employeeList.get(j).getEmployeeID().equals(EmployeeID)){
                    validateID.setForeground(Color.red);
                    validateID.setText("EmployeeID Doesn't Exist");
                }
                
                if(employeeList.get(j).getEmployeeID().equals(EmployeeID)){
                    valid = true;
                    validateID.setForeground(Color.white);

                    RatePerDay1 = employeeList.get(j).getEmpRatePerDay();
                    EmployeePosition1 = employeeList.get(j).getEmpJobTitle();
                    EmployeeName1 = employeeList.get(j).getEmpName();

                    ArrayList<attendanceSummary> attendanceSummaryList =  attendanceSummary();
                    for(int k=0; k<attendanceSummaryList.size();k++){
                                
                        if(attendanceSummaryList.get(k).getEmployeeID().equals(EmployeeID)&&
                                attendanceSummaryList.get(k).getMonth().equals(newMonth)&&
                                attendanceSummaryList.get(k).getYear().equals(newYear)){
                            
                            daysRendered = attendanceSummaryList.get(k).getDaysRendered();
                            SpecialHoliday = attendanceSummaryList.get(k).getSpecialHoliday();
                            RegularHoliday = attendanceSummaryList.get(k).getRegularHoliday();
                                
                            int SpecialHolidayTotal;
                            int RegularHolidayTotal;
                            
                            //Check if employee works on special holiday
                            if(Integer.parseInt(SpecialHoliday)==0){
                                SpecialHolidayTotal = 0;
                            }else{
                                SpecialHolidayTotal = (Integer.parseInt(SpecialHoliday) * 3) * Integer.parseInt(RatePerDay1);
                            }
                            //check if employee works on regular holiday
                            if(Integer.parseInt(RegularHoliday)==0){
                                RegularHolidayTotal = 0;
                            }else{
                                RegularHolidayTotal = (Integer.parseInt(RegularHoliday) * 2) * Integer.parseInt(RatePerDay1);
                            }
                           
                            //computations for basic salary
                            int dr =Integer.parseInt(daysRendered), rp =Integer.parseInt(RatePerDay1);
                            
                            int BasicSalary = dr * rp ; 
                            //Computation for SubTotal
                            int SubTotal = BasicSalary + SpecialHolidayTotal + RegularHolidayTotal;
                            
                            //Getting all deductions
                            int PHdeduc = get_PH_Deduc(SubTotal);
                            int SSSdeduc = get_SSS_Deduc(SubTotal);
                            int PAGIBIGdeduc = get_PAGIBIG_Deduc(SubTotal);
                             if(PAGIBIGdeduc==1){
                                    PAGIBIGdeduc = (int) (BasicSalary * 0.01);
                                }else if(PAGIBIGdeduc==2){
                                    PAGIBIGdeduc = (int) (BasicSalary * 0.01);   
                                }
                            //sum of all deductions 
                            int TotalDeduction = PHdeduc + SSSdeduc + PAGIBIGdeduc;
                            //computation of netpay
                            int NetPay = SubTotal - TotalDeduction;
                               
                                       String insertSalaryQuery = "INSERT INTO salary(EmployeeID, EmployeeName, EmployeePosition, BasicSalary, DaysRendered, RegularHoliday, "
                                            + "SpecialHoliday, SubTotal, SSSdeduction, PHdeduction, PAGIBIG, TotalDeduction, NetPay, Month, Year)"

                                            //inserting all values to fields
                                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                        PreparedStatement insertSalary = cn.prepareStatement(insertSalaryQuery);
                                        insertSalary.setString(1, EmployeeID);
                                        insertSalary.setString(2, EmployeeName1);
                                        insertSalary.setString(3, EmployeePosition1);
                                        insertSalary.setString(4, String.valueOf(BasicSalary));
                                        insertSalary.setString(5, daysRendered);
                                        insertSalary.setString(6, String.valueOf(RegularHolidayTotal));
                                        insertSalary.setString(7, String.valueOf(SpecialHolidayTotal));
                                        insertSalary.setString(8, String.valueOf(SubTotal));
                                        insertSalary.setString(9, String.valueOf(SSSdeduc));
                                        insertSalary.setString(10, String.valueOf(PHdeduc));
                                        insertSalary.setString(11, String.valueOf(PAGIBIGdeduc));
                                        insertSalary.setString(12, String.valueOf(TotalDeduction));
                                        insertSalary.setString(13, String.valueOf(NetPay));
                                        insertSalary.setString(14, newMonth);
                                        insertSalary.setString(15, newYear);
                                        insertSalary.execute();     
                                        JOptionPane.showMessageDialog(this, "Generate SuccessFully!");
                                        empIDgen.setText("");
                                
                        }
                    }
            }
            if(valid==true){
                break;
            }    
            
        }
            
        }catch(Exception ex){
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        homePage = new javax.swing.JPanel();
        homeLabel = new javax.swing.JLabel();
        empInfoPage = new javax.swing.JPanel();
        empInfoLabel = new javax.swing.JLabel();
        attendancePage = new javax.swing.JPanel();
        attendanceLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IDnumberLabel = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        SettingPage = new javax.swing.JPanel();
        SettingLabel = new javax.swing.JLabel();
        logoutPage = new javax.swing.JPanel();
        logoutLabel = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        closeBtn = new javax.swing.JLabel();
        minBtn = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bodyPanel = new javax.swing.JPanel();
        employeeInfoPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        empTable = new javax.swing.JTable();
        searchFld = new javax.swing.JTextField();
        addEmployeeBtn = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        searchBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        attendanceLogPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        attendanceLogTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        attendanceSummaryTable = new javax.swing.JTable();
        monthCB = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        searchMonth = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        YearFld = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        attendanceLogPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        attendanceLogTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        attendanceSummaryTable1 = new javax.swing.JTable();
        monthCB1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        searchMonth1 = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        numberOfEmployee = new javax.swing.JLabel();
        generateBtn = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        empIDgen = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        DatePL = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        TimePL = new javax.swing.JLabel();
        rbtn_generateAll = new javax.swing.JRadioButton();
        rbtn_generateManual = new javax.swing.JRadioButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        validateID = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        salaryHistoryPanel = new javax.swing.JPanel();
        BackBtn1 = new javax.swing.JButton();
        employeeNamePL2 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        dateStartPL1 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        basicSalaryPL1 = new javax.swing.JLabel();
        employeeIDPL2 = new javax.swing.JLabel();
        empPositionPL2 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        viewInfoPanel = new javax.swing.JPanel();
        BackBtn = new javax.swing.JButton();
        employeeNamePL = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        empTINPL = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        empPHPL = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        empPAGIBIGPL = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        empSSSPL = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        empGender = new javax.swing.JLabel();
        empAge = new javax.swing.JLabel();
        birthDatePL = new javax.swing.JLabel();
        empAddressPL = new javax.swing.JLabel();
        empContactNoPL = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        dateStartPL = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        basicSalaryPL = new javax.swing.JLabel();
        employeeIDPL = new javax.swing.JLabel();
        empPositionPL = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 500));
        setUndecorated(true);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(900, 500));
        mainPanel.setMinimumSize(new java.awt.Dimension(900, 500));
        mainPanel.setPreferredSize(new java.awt.Dimension(900, 500));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBar.setBackground(new java.awt.Color(102, 102, 102));
        sideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homePage.setBackground(new java.awt.Color(102, 102, 102));
        homePage.setForeground(new java.awt.Color(102, 102, 102));
        homePage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homePage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homePageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homePageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homePageMouseExited(evt);
            }
        });
        homePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        homeLabel.setForeground(new java.awt.Color(51, 51, 51));
        homeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Home_24px_1_1.png"))); // NOI18N
        homeLabel.setText(" Home");
        homePage.add(homeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(homePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 200, 40));

        empInfoPage.setBackground(new java.awt.Color(102, 102, 102));
        empInfoPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        empInfoPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empInfoPageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                empInfoPageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                empInfoPageMouseExited(evt);
            }
        });
        empInfoPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        empInfoLabel.setBackground(new java.awt.Color(102, 102, 102));
        empInfoLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        empInfoLabel.setForeground(new java.awt.Color(51, 51, 51));
        empInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Set_As_Resume_24px_3.png"))); // NOI18N
        empInfoLabel.setText(" Employee List");
        empInfoPage.add(empInfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(empInfoPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 200, 40));

        attendancePage.setBackground(new java.awt.Color(102, 102, 102));
        attendancePage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        attendancePage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendancePageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                attendancePageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                attendancePageMouseExited(evt);
            }
        });
        attendancePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attendanceLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        attendanceLabel.setForeground(new java.awt.Color(51, 51, 51));
        attendanceLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Today_24px_1_1.png"))); // NOI18N
        attendanceLabel.setText(" Attendance Log");
        attendancePage.add(attendanceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(attendancePage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 200, 40));

        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_MySQL_24px_1.png"))); // NOI18N
        sideBar.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, -1, -1));

        jLabel4.setFont(new java.awt.Font("Felix Titling", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CITY SQUARE");
        sideBar.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Source_Code_24px_3.png"))); // NOI18N
        jLabel3.setText(" Team Array");
        sideBar.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Org_Unit_96px_7.png"))); // NOI18N
        sideBar.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Java_24px_2.png"))); // NOI18N
        sideBar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, -1));

        IDnumberLabel.setForeground(new java.awt.Color(102, 102, 102));
        IDnumberLabel.setText("ID");
        sideBar.add(IDnumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        jLabel42.setFont(new java.awt.Font("Felix Titling", 1, 12)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("MANAGEMENT");
        sideBar.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        SettingPage.setBackground(new java.awt.Color(102, 102, 102));
        SettingPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SettingPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SettingPageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SettingPageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SettingPageMouseExited(evt);
            }
        });
        SettingPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SettingLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        SettingLabel.setForeground(new java.awt.Color(51, 51, 51));
        SettingLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Settings_24px_1.png"))); // NOI18N
        SettingLabel.setText(" Setting");
        SettingPage.add(SettingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(SettingPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 200, 40));

        logoutPage.setBackground(new java.awt.Color(102, 102, 102));
        logoutPage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutPage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutPageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutPageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutPageMouseExited(evt);
            }
        });
        logoutPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutLabel.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        logoutLabel.setForeground(new java.awt.Color(51, 51, 51));
        logoutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Logout_Rounded_Left_24px_3.png"))); // NOI18N
        logoutLabel.setText(" Log Out");
        logoutPage.add(logoutLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 6, -1, -1));

        sideBar.add(logoutPage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 200, 40));

        mainPanel.add(sideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 200, 470));

        headerPanel.setBackground(new java.awt.Color(51, 51, 51));
        headerPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerPanelMouseDragged(evt);
            }
        });
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerPanelMousePressed(evt);
            }
        });
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        closeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Close_Window_24px_1.png"))); // NOI18N
        closeBtn.setAlignmentY(0.0F);
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtnMouseClicked(evt);
            }
        });
        headerPanel.add(closeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 0, -1, -1));

        minBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Minimize_Window_24px_1.png"))); // NOI18N
        minBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minBtnMouseClicked(evt);
            }
        });
        headerPanel.add(minBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 0, -1, -1));

        jLabel1.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CITY SQUARE PAYROLL SYSTEM v1");
        headerPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 500, -1));

        mainPanel.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 30));

        bodyPanel.setBackground(new java.awt.Color(255, 255, 255));
        bodyPanel.setMaximumSize(new java.awt.Dimension(633, 430));
        bodyPanel.setMinimumSize(new java.awt.Dimension(633, 430));
        bodyPanel.setPreferredSize(new java.awt.Dimension(633, 430));
        bodyPanel.setLayout(new java.awt.CardLayout());

        employeeInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        empTable.setAutoCreateRowSorter(true);
        empTable.setBackground(new java.awt.Color(204, 204, 204));
        empTable.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N
        empTable.setForeground(new java.awt.Color(51, 51, 51));
        empTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeID", "EmployeeName", "Job Title"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        empTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                empTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(empTable);

        searchFld.setFont(new java.awt.Font("HelvLight", 0, 12)); // NOI18N

        addEmployeeBtn.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        addEmployeeBtn.setForeground(new java.awt.Color(102, 102, 102));
        addEmployeeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Add_User_Male_24px.png"))); // NOI18N
        addEmployeeBtn.setText("Add Employee");
        addEmployeeBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addEmployeeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addEmployeeBtnMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("EMPLOYEE INFORMATION");

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("ENTER EMPLOYEE ID");

        jButton2.setText("Print");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout employeeInfoPanelLayout = new javax.swing.GroupLayout(employeeInfoPanel);
        employeeInfoPanel.setLayout(employeeInfoPanelLayout);
        employeeInfoPanelLayout.setHorizontalGroup(
            employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                        .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                                    .addComponent(searchFld, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(searchBtn)
                                    .addGap(43, 43, 43)
                                    .addComponent(addEmployeeBtn)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                        .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        employeeInfoPanelLayout.setVerticalGroup(
            employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(employeeInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(employeeInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn)
                    .addComponent(addEmployeeBtn)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        bodyPanel.add(employeeInfoPanel, "card3");

        attendanceLogPanel.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        attendanceLogTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeID", "Name", "Position", "Log In", "Log Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceLogTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendanceLogTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(attendanceLogTable);

        jTabbedPane1.addTab("Log Per Day", jScrollPane2);

        attendanceSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(attendanceSummaryTable);

        jTabbedPane1.addTab("Attendance Summary", jScrollPane3);

        monthCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel23.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Attendance Log");

        searchMonth.setText("Search");
        searchMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMonthActionPerformed(evt);
            }
        });

        jLabel19.setText("Month");

        YearFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YearFldActionPerformed(evt);
            }
        });

        jLabel38.setText("Year");

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Print_24px.png"))); // NOI18N
        jLabel40.setText("Print Table");
        jLabel40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel40MouseClicked(evt);
            }
        });

        attendanceLogPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        attendanceLogTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "EmployeeID", "Name", "Position", "Log In", "Log Out"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendanceLogTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendanceLogTable1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(attendanceLogTable1);

        jTabbedPane2.addTab("Log Per Day", jScrollPane4);

        attendanceSummaryTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(attendanceSummaryTable1);

        jTabbedPane2.addTab("Attendance Summary", jScrollPane5);

        monthCB1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        jLabel31.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Attendance Log");

        searchMonth1.setText("Search");
        searchMonth1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMonth1ActionPerformed(evt);
            }
        });

        jLabel34.setText("Month");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel41.setText("Year");

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Print_24px.png"))); // NOI18N
        jLabel43.setText("Print Table");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout attendanceLogPanel1Layout = new javax.swing.GroupLayout(attendanceLogPanel1);
        attendanceLogPanel1.setLayout(attendanceLogPanel1Layout);
        attendanceLogPanel1Layout.setHorizontalGroup(
            attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceLogPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(attendanceLogPanel1Layout.createSequentialGroup()
                            .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(monthCB1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel34))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel41)
                                .addGroup(attendanceLogPanel1Layout.createSequentialGroup()
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(searchMonth1)))
                            .addGap(266, 266, 266)
                            .addComponent(jLabel43)
                            .addGap(11, 11, 11))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        attendanceLogPanel1Layout.setVerticalGroup(
            attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attendanceLogPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(attendanceLogPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthCB1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchMonth1)
                    .addComponent(jLabel43))
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout attendanceLogPanelLayout = new javax.swing.GroupLayout(attendanceLogPanel);
        attendanceLogPanel.setLayout(attendanceLogPanelLayout);
        attendanceLogPanelLayout.setHorizontalGroup(
            attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                            .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel38)
                                .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                                    .addComponent(YearFld, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(searchMonth)))
                            .addGap(266, 266, 266)
                            .addComponent(jLabel40)
                            .addGap(11, 11, 11))))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(attendanceLogPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        attendanceLogPanelLayout.setVerticalGroup(
            attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attendanceLogPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(YearFld, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchMonth)
                    .addComponent(jLabel40))
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(attendanceLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(attendanceLogPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(attendanceLogPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        bodyPanel.add(attendanceLogPanel, "card4");

        homePanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Dashboard");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel24.setFont(new java.awt.Font("HelvLight", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Number of Employee");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel24)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 230, -1));

        numberOfEmployee.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        numberOfEmployee.setForeground(new java.awt.Color(51, 51, 51));
        numberOfEmployee.setText("0");
        jPanel5.add(numberOfEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, 60));

        generateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Paycheque_24px_3.png"))); // NOI18N
        generateBtn.setText("Generate Payslip");
        generateBtn.setEnabled(false);
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Generate Payslip");

        empIDgen.setEnabled(false);
        empIDgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empIDgenActionPerformed(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Enter Employee ID");
        jLabel9.setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Planner_24px_4.png"))); // NOI18N
        jLabel13.setText("Today:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 10, 82, -1));

        DatePL.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        DatePL.setForeground(new java.awt.Color(51, 51, 51));
        DatePL.setText("DATE");
        jPanel1.add(DatePL, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 102, -1));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("HelvLight", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Clock_24px_4.png"))); // NOI18N
        jLabel17.setText("Time:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 10, 70, -1));

        TimePL.setFont(new java.awt.Font("HelvLight", 1, 18)); // NOI18N
        TimePL.setForeground(new java.awt.Color(51, 51, 51));
        TimePL.setText("TIME");
        jPanel2.add(TimePL, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 104, 25));

        rbtn_generateAll.setText("Generate All ");
        rbtn_generateAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_generateAllActionPerformed(evt);
            }
        });

        rbtn_generateManual.setText("Manual");
        rbtn_generateManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_generateManualActionPerformed(evt);
            }
        });

        jLabel48.setForeground(new java.awt.Color(153, 153, 153));
        jLabel48.setText("Generates all the payslip automatically for all employees");

        jLabel49.setForeground(new java.awt.Color(153, 153, 153));
        jLabel49.setText("Generates manually by entering the EmployeeID");

        validateID.setForeground(new java.awt.Color(255, 255, 255));
        validateID.setText("validate");

        jLabel50.setForeground(new java.awt.Color(51, 51, 51));
        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Planner_24px_3.png"))); // NOI18N
        jLabel50.setText("Check Holidays ");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(generateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addComponent(empIDgen, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(validateID, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(homePanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(homePanelLayout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addComponent(jLabel50))))
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel9))
                            .addGroup(homePanelLayout.createSequentialGroup()
                                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(rbtn_generateManual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rbtn_generateAll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel49)
                                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_generateAll)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_generateManual)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addComponent(generateBtn)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePanelLayout.createSequentialGroup()
                        .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empIDgen, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(validateID, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))))
        );

        bodyPanel.add(homePanel, "card2");

        salaryHistoryPanel.setBackground(new java.awt.Color(255, 255, 255));

        BackBtn1.setText("Back");
        BackBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtn1ActionPerformed(evt);
            }
        });

        employeeNamePL2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        employeeNamePL2.setForeground(new java.awt.Color(51, 51, 51));
        employeeNamePL2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_User_48px.png"))); // NOI18N
        employeeNamePL2.setText("Employee Name");

        jLabel51.setForeground(new java.awt.Color(51, 51, 51));
        jLabel51.setText("Position");

        jLabel56.setForeground(new java.awt.Color(51, 51, 51));
        jLabel56.setText("Employee ID");

        jLabel62.setForeground(new java.awt.Color(51, 51, 51));
        jLabel62.setText("Date Start :");

        dateStartPL1.setText("number");

        jLabel63.setForeground(new java.awt.Color(51, 51, 51));
        jLabel63.setText("Rate/Day");

        basicSalaryPL1.setText("number");

        employeeIDPL2.setText("number");

        empPositionPL2.setText("number");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable1);

        javax.swing.GroupLayout salaryHistoryPanelLayout = new javax.swing.GroupLayout(salaryHistoryPanel);
        salaryHistoryPanel.setLayout(salaryHistoryPanelLayout);
        salaryHistoryPanelLayout.setHorizontalGroup(
            salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(BackBtn1))
                    .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                            .addGap(68, 68, 68)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel56)
                                .addComponent(jLabel51))
                            .addGap(49, 49, 49)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(employeeIDPL2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(empPositionPL2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel63)
                                .addComponent(jLabel62))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dateStartPL1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addComponent(basicSalaryPL1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(employeeNamePL2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                                    .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 4, Short.MAX_VALUE)
                                        .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(45, 45, 45))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        salaryHistoryPanelLayout.setVerticalGroup(
            salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackBtn1)
                .addGap(18, 18, 18)
                .addComponent(employeeNamePL2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(jLabel62)
                    .addComponent(dateStartPL1)
                    .addComponent(employeeIDPL2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jLabel63)
                    .addComponent(basicSalaryPL1)
                    .addComponent(empPositionPL2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(salaryHistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel65)
                        .addGap(66, 66, 66)
                        .addComponent(jLabel66)
                        .addGap(62, 62, 62)
                        .addComponent(jLabel67))
                    .addGroup(salaryHistoryPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        bodyPanel.add(salaryHistoryPanel, "card5");

        viewInfoPanel.setBackground(new java.awt.Color(255, 255, 255));

        BackBtn.setText("Back");
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        employeeNamePL.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        employeeNamePL.setForeground(new java.awt.Color(51, 51, 51));
        employeeNamePL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_User_48px.png"))); // NOI18N
        employeeNamePL.setText("Employee Name");

        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Position");

        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("TIN :");

        empTINPL.setText("number");

        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("PhilHealth :");

        empPHPL.setText("number");

        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("PAGIBIG :");

        empPAGIBIGPL.setText("number");

        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("SSS :");

        empSSSPL.setText("number");

        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Employee ID");

        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Gender :");

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Address :");

        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Contact Number :");

        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Age :");

        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Birth Date :");

        empGender.setText("number");

        empAge.setText("number");

        birthDatePL.setText("number");

        empAddressPL.setText("number");

        empContactNoPL.setText("number");

        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Date Start :");

        dateStartPL.setText("number");

        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("Rate/Day");

        basicSalaryPL.setText("number");

        employeeIDPL.setText("number");

        empPositionPL.setText("number");

        jLabel28.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("Benefits");

        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Transaction_List_24px.png"))); // NOI18N
        jLabel37.setText("Check Salary History");

        jLabel39.setForeground(new java.awt.Color(51, 51, 51));
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_Edit_Property_24px.png"))); // NOI18N
        jLabel39.setText("Edit Information");

        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("Edit");

        javax.swing.GroupLayout viewInfoPanelLayout = new javax.swing.GroupLayout(viewInfoPanel);
        viewInfoPanel.setLayout(viewInfoPanelLayout);
        viewInfoPanelLayout.setHorizontalGroup(
            viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(BackBtn))
                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                            .addGap(68, 68, 68)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addComponent(jLabel11))
                            .addGap(49, 49, 49)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(employeeIDPL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(empPositionPL, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(56, 56, 56)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel35)
                                .addComponent(jLabel33))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dateStartPL, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addComponent(basicSalaryPL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                            .addGap(607, 607, 607)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(employeeNamePL, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(empContactNoPL, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(164, 164, 164))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                    .addGap(45, 45, 45)
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel26)
                                        .addComponent(jLabel27)
                                        .addComponent(jLabel22))
                                    .addGap(42, 42, 42)
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(birthDatePL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                            .addComponent(empAge, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(empGender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(empAddressPL, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(41, 41, 41))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewInfoPanelLayout.createSequentialGroup()
                                    .addGap(42, 42, 42)
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel39)
                                        .addComponent(jLabel37))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(empTINPL, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel14)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(empPHPL, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(13, 13, 13))
                                        .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel16)
                                                .addComponent(jLabel18))
                                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(empPAGIBIGPL, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                                    .addGap(108, 108, 108)
                                                    .addComponent(empSSSPL, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                                        .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewInfoPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel30))
                                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(45, 45, 45))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        viewInfoPanelLayout.setVerticalGroup(
            viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackBtn)
                .addGap(18, 18, 18)
                .addComponent(employeeNamePL, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel33)
                    .addComponent(dateStartPL)
                    .addComponent(employeeIDPL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel35)
                    .addComponent(basicSalaryPL)
                    .addComponent(empPositionPL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel30)
                        .addGap(11, 11, 11)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empTINPL)
                            .addComponent(jLabel29)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(empPHPL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(empPAGIBIGPL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(empSSSPL)
                                .addComponent(jLabel32)))
                        .addGap(46, 46, 46)
                        .addComponent(jLabel36))
                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel21)
                                            .addComponent(empGender))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel26)
                                            .addComponent(empAge))
                                        .addGap(5, 5, 5))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewInfoPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel28)
                                        .addGap(18, 18, 18)))
                                .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(birthDatePL))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22))
                            .addGroup(viewInfoPanelLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(empAddressPL, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(viewInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(empContactNoPL, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(27, 27, 27)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        bodyPanel.add(viewInfoPanel, "card5");

        mainPanel.add(bodyPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 700, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(900, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void empInfoPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseEntered
        // TODO add your handling code here:
       empInfoPage.setBackground(new Color(51,51,51));
       empInfoLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_empInfoPageMouseEntered

    private void empInfoPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseExited
       
      empInfoPage.setBackground(new Color(102,102,102));
      empInfoLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_empInfoPageMouseExited

    private void attendancePageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseEntered
      
        attendancePage.setBackground(new Color(51,51,51));
        attendanceLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_attendancePageMouseEntered

    private void attendancePageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseExited
        attendancePage.setBackground(new Color(102,102,102));
        attendanceLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_attendancePageMouseExited

    private void homePageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseEntered
       
      homePage.setBackground(new Color(51,51,51));
      homeLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_homePageMouseEntered

    private void homePageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseExited
     
        homePage.setBackground(new Color(102,102,102));
        homeLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_homePageMouseExited

    private void empTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empTableMouseClicked
      
          homePage.setEnabled(false);
          empInfoPage.setEnabled(false);
          attendancePage.setEnabled(false);
            int emp = empTable.getSelectedRow();
        

        ArrayList<employee> List = employeeList();
        for(int i=0; i<List.size();i++) {
            if(i==emp){
                try {
                    IDnumberLabel.setText(List.get(i).getEmployeeID());
                    
                    bodyPanel.removeAll();
                    bodyPanel.repaint();
                    bodyPanel.revalidate();
                    bodyPanel.add(viewInfoPanel);
                    bodyPanel.repaint();
                    bodyPanel.revalidate();
                    
                    String query = "SELECT * FROM `employeeinfo_tb` WHERE employeeID = " + IDnumberLabel.getText();
                    ResultSet rs = st.executeQuery(query);
                   
                   while(rs.next()){
                       employeeNamePL.setText(rs.getString("First_Name")+" "+rs.getString("Middle_Name")+" "+rs.getString("Last_Name"));
                       employeeIDPL.setText(rs.getString("EmployeeID"));
                       empPositionPL.setText(rs.getString("Job_Title"));
                       empGender.setText(rs.getString("Gender"));
                       empAge.setText(rs.getString("Age"));
                       birthDatePL.setText(rs.getString("Birth_Date"));
                       empAddressPL.setText(rs.getString("Address"));
                       empContactNoPL.setText(rs.getString("Contact_No"));
                       dateStartPL.setText(rs.getString("Date_Start"));
                       basicSalaryPL.setText(rs.getString("RatePerDay"));
                       empTINPL.setText(rs.getString("TIN_No"));
                       empPHPL.setText(rs.getString("PhilHealth_No"));
                       empPAGIBIGPL.setText(rs.getString("PAGIBIG_No"));
                       empSSSPL.setText(rs.getString("SSS_No"));
                   }
                   
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        }
    }//GEN-LAST:event_empTableMouseClicked

    private void homePageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homePageMouseClicked
        // TODO add your handling code here:
        
        homeLabel.setForeground(new Color(102,102,102));
        
        homePage.setBackground(new Color(51,51,51));
        empInfoPage.setBackground(new Color(102,102,102));
        attendancePage.setBackground(new Color(102,102,102));
        
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(homePanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
           
    }//GEN-LAST:event_homePageMouseClicked

    private void empInfoPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_empInfoPageMouseClicked
        // TODO add your handling code here:
        empInfoLabel.setForeground(new Color(102,102,102));
        
        homePage.setBackground(new Color(102,102,102));
        empInfoPage.setBackground(new Color(51,51,51));
        attendancePage.setBackground(new Color(102,102,102));
        
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(employeeInfoPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        
        update_employee_table();
    }//GEN-LAST:event_empInfoPageMouseClicked

    private void attendancePageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancePageMouseClicked
        // TODO add your handling code here:
        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(attendanceLogPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        update_attendance_table();
        
    }//GEN-LAST:event_attendancePageMouseClicked

    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeBtnMouseClicked

    private void headerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerPanelMousePressed

    private void headerPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerPanelMouseDragged

    private void minBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minBtnMouseClicked
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_minBtnMouseClicked

    private void addEmployeeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addEmployeeBtnMouseClicked
       new addEmployeeForm().setVisible(true);
    }//GEN-LAST:event_addEmployeeBtnMouseClicked
    
    @SuppressWarnings("IncompatibleEquals")
    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBtnActionPerformed
      // TODO add your handling code here:
      
      if(generateChoice.equals("All")){
          auto_generate_salary();  
      }else if(generateChoice.equals("Manual")){
          manual_generate_salary(empIDgen.getText());
      }  
    }//GEN-LAST:event_generateBtnActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        // TODO add your handling code here:
        String keyword = searchFld.getText();
        String newEmployee = "SELECT EmployeeID, First_Name, Middle_Name,Last_Name FROM `employeeinfo_tb` WHERE EmployeeID = "+keyword;
        ResultSet eRs ;
        try{
            st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                eRs = st.executeQuery(newEmployee);
                empTable.setModel(DbUtils.resultSetToTableModel(eRs));
        }catch(SQLException e){
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void attendanceLogTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceLogTableMouseClicked
        // TODO add your handling code here:
        ArrayList<AttendanceLog> attendanceList = attendanceLog();
        Object[] row = new Object[13];
        for(int i=0; i<attendanceList.size();i++) {
            row[0] = attendanceList.get(i).getEmployeeID();
            row[1] = attendanceList.get(i).getEmployeeName();
            row[2] = attendanceList.get(i).getEmployeePosition();
            row[3] = attendanceList.get(i).getLogin();
            row[4] = attendanceList.get(i).getLogout();
        }
    }//GEN-LAST:event_attendanceLogTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here
        String accessibleName = jTabbedPane1.getAccessibleContext().getAccessibleName(); 
        update_attendance_summary_table();
        update_attendance_table();
        
        if(accessibleName.equals("Log Per Day")){
            printAttendance = "Log";
        }else if(accessibleName.equals("Attendance Summary")){
            printAttendance = "sum";
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void YearFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YearFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YearFldActionPerformed

    private void searchMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMonthActionPerformed
        // TODO add your handling code here:

        String month = (String) monthCB.getSelectedItem();
        String year = YearFld.getText();
        if(month.equals(" ")&&year.equals("")){
            String newAttendance = "SELECT * FROM `attendancelog_perday_tb`";
            ResultSet aRs ;
            try{

                st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                aRs = st.executeQuery(newAttendance);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));

            }catch(SQLException e){
                System.out.println(e);
            }
               String newSumAttendance ="SELECT * FROM `attendance_summary`";
              ResultSet sRs ;
            try{

                st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                sRs = st.executeQuery(newSumAttendance);
                attendanceSummaryTable.setModel(DbUtils.resultSetToTableModel(sRs));

            }catch(SQLException e){
                System.out.println(e);
            }
        }else{
            String newAttendance = "SELECT * FROM `attendancelog_perday_tb` WHERE Month = '"+month+"' AND Year = '"+year+"'";
            ResultSet aRs ;
            try{

                st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                aRs = st.executeQuery(newAttendance);
                attendanceLogTable.setModel(DbUtils.resultSetToTableModel(aRs));

            }catch(SQLException e){
                System.out.println(e);
            }
            String newSumAttendance ="SELECT * FROM `attendance_summary` WHERE Month = '"+month+"' OR Year = '"+year+"'";
              ResultSet sRs ;
            try{

                st = cn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                sRs = st.executeQuery(newSumAttendance);
                attendanceSummaryTable.setModel(DbUtils.resultSetToTableModel(sRs));

            }catch(SQLException e){
                System.out.println(e);
            }

        }
    }//GEN-LAST:event_searchMonthActionPerformed

    private void jLabel40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel40MouseClicked
        // TODO add your handling code here:
        
        if(printAttendance.equals("Log")){
            try{
                attendanceLogTable.print();
            }catch(Exception e){
                System.out.println(e);
            } 
        }else if(printAttendance.equals("Sum")){
            try{
                attendanceSummaryTable.print();
            }catch(Exception e){
                System.out.println(e);
            } 
        }
    }//GEN-LAST:event_jLabel40MouseClicked

    private void attendanceLogTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendanceLogTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_attendanceLogTable1MouseClicked

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void searchMonth1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMonth1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchMonth1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel43MouseClicked

    private void rbtn_generateAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_generateAllActionPerformed
        // TODO add your handling code here:
        rbtn_generateAll.setSelected(true);
        rbtn_generateManual.setSelected(false);
        generateChoice = "All";
        generateBtn.setEnabled(true);
        empIDgen.setEnabled(false);
        jLabel9.setEnabled(false);
    }//GEN-LAST:event_rbtn_generateAllActionPerformed

    private void rbtn_generateManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_generateManualActionPerformed
        // TODO add your handling code here:
        rbtn_generateAll.setSelected(false);
        rbtn_generateManual.setSelected(true);
        generateChoice = "Manual";
        empIDgen.setEnabled(true);
        generateBtn.setEnabled(true);
        jLabel9.setEnabled(true);
        
        String employeeID = empIDgen.getText();
        
    }//GEN-LAST:event_rbtn_generateManualActionPerformed

    private void empIDgenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empIDgenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empIDgenActionPerformed

    private void SettingPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingPageMouseClicked
        // TODO add your handling code here:
        
        new Setting().setVisible(true);
    }//GEN-LAST:event_SettingPageMouseClicked

    private void SettingPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingPageMouseEntered
        // TODO add your handling code here:
        SettingPage.setBackground(new Color(51,51,51));
      SettingLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_SettingPageMouseEntered

    private void SettingPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SettingPageMouseExited
        // TODO add your handling code here:
        SettingPage.setBackground(new Color(102,102,102));
        SettingLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_SettingPageMouseExited

    private void logoutPageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPageMouseClicked
        // TODO add your handling code here:
        new LogoutConfirmation().setVisible(true);
    }//GEN-LAST:event_logoutPageMouseClicked

    private void logoutPageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPageMouseEntered
        // TODO add your handling code here:
         SettingPage.setBackground(new Color(51,51,51));
            SettingLabel.setForeground(new Color(102,102,102));
    }//GEN-LAST:event_logoutPageMouseEntered

    private void logoutPageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutPageMouseExited
        // TODO add your handling code here:
        logoutPage.setBackground(new Color(102,102,102));
        logoutLabel.setForeground(new Color(51,51,51));
    }//GEN-LAST:event_logoutPageMouseExited

    private void BackBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BackBtn1ActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
        // TODO add your handling code here:
        IDnumberLabel.setText("");
        homePage.setEnabled(true);
        empInfoPage.setEnabled(true);
        attendancePage.setEnabled(true);

        bodyPanel.removeAll();
        bodyPanel.repaint();
        bodyPanel.revalidate();
        bodyPanel.add(employeeInfoPanel);
        bodyPanel.repaint();
        bodyPanel.revalidate();

        int emp = empTable.getSelectedRow();

        ArrayList<employee> List = employeeList();
        Object[] row = new Object[13];
        for(int i=0; i<List.size();i++) {
            row[0] = List.get(i).getEmployeeID();
            row[1] = List.get(i).getEmpName();
            row[2] = List.get(i).getEmpJobTitle();
        }
    }//GEN-LAST:event_BackBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("dracula".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton BackBtn1;
    private javax.swing.JLabel DatePL;
    private javax.swing.JLabel IDnumberLabel;
    private javax.swing.JLabel SettingLabel;
    private javax.swing.JPanel SettingPage;
    private javax.swing.JLabel TimePL;
    private javax.swing.JTextField YearFld;
    private javax.swing.JLabel addEmployeeBtn;
    private javax.swing.JLabel attendanceLabel;
    private javax.swing.JPanel attendanceLogPanel;
    private javax.swing.JPanel attendanceLogPanel1;
    private javax.swing.JTable attendanceLogTable;
    private javax.swing.JTable attendanceLogTable1;
    private javax.swing.JPanel attendancePage;
    private javax.swing.JTable attendanceSummaryTable;
    private javax.swing.JTable attendanceSummaryTable1;
    private javax.swing.JLabel basicSalaryPL;
    private javax.swing.JLabel basicSalaryPL1;
    private javax.swing.JLabel birthDatePL;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel dateStartPL;
    private javax.swing.JLabel dateStartPL1;
    private javax.swing.JLabel empAddressPL;
    private javax.swing.JLabel empAge;
    private javax.swing.JLabel empContactNoPL;
    private javax.swing.JLabel empGender;
    private javax.swing.JTextField empIDgen;
    private javax.swing.JLabel empInfoLabel;
    private javax.swing.JPanel empInfoPage;
    private javax.swing.JLabel empPAGIBIGPL;
    private javax.swing.JLabel empPHPL;
    private javax.swing.JLabel empPositionPL;
    private javax.swing.JLabel empPositionPL2;
    private javax.swing.JLabel empSSSPL;
    private javax.swing.JLabel empTINPL;
    private javax.swing.JTable empTable;
    private javax.swing.JLabel employeeIDPL;
    private javax.swing.JLabel employeeIDPL2;
    private javax.swing.JPanel employeeInfoPanel;
    private javax.swing.JLabel employeeNamePL;
    private javax.swing.JLabel employeeNamePL2;
    private javax.swing.JButton generateBtn;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel homeLabel;
    private javax.swing.JPanel homePage;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JPanel logoutPage;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel minBtn;
    private javax.swing.JComboBox<String> monthCB;
    private javax.swing.JComboBox<String> monthCB1;
    private javax.swing.JLabel numberOfEmployee;
    private javax.swing.JRadioButton rbtn_generateAll;
    private javax.swing.JRadioButton rbtn_generateManual;
    private javax.swing.JPanel salaryHistoryPanel;
    private javax.swing.JButton searchBtn;
    private javax.swing.JTextField searchFld;
    private javax.swing.JButton searchMonth;
    private javax.swing.JButton searchMonth1;
    private javax.swing.JPanel sideBar;
    private javax.swing.JLabel validateID;
    private javax.swing.JPanel viewInfoPanel;
    // End of variables declaration//GEN-END:variables
}
