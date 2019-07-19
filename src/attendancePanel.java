
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class attendancePanel extends javax.swing.JFrame implements ActionListener{
    public Connection cn;
    public Statement st;
    int yMouse, xMouse;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
// 
    public attendancePanel() throws SQLException {
        initComponents();
        updateClock();
        new Timer(1000, this).start();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/citysquare_payroll?zeroDateTimeBehavior=convertToNull","root","");
            st = cn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(attendancePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        updateClock();
    }
    private void updateClock() {
        datePL.setText(dateFormat.format(Calendar.getInstance().getTime()));
        timePL.setText(timeFormat.format(Calendar.getInstance().getTime()));
    }
    @SuppressWarnings("NonPublicExported")
    public ArrayList<employee> employeeList(){
            ArrayList<employee> employeesList = new ArrayList<>();
            try{
            String query = "SELECT * FROM `employeeinfo_tb`";
            ResultSet rs = st.executeQuery(query);
            
            employee emp;
            while(rs.next()){
                emp = new employee(rs.getInt("ID"),rs.getString("EmployeeID"), rs.getString("First_Name"), rs.getString("Middle_Name"), rs.getString("Last_Name"), rs.getString("Job_Title"), rs.getString("Birth_Date"),
                        rs.getString("Address"), rs.getString("Gender"),rs.getString("RatePerDay"),rs.getString("Contact_No"),rs.getString("Tin_No"),rs.getString("SSS_No"),
                        rs.getString("PhilHealth_No"),rs.getString("PAGIBIG_No"),rs.getString("Date_Start"),rs.getString("Age"));
                employeesList.add(emp);
            }
            
                    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
           return employeesList;
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
    public ArrayList<holiday> getHoliday(){
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        ArrayList<holiday> HolidayList = new ArrayList<>();
        try {
            String query = "SELECT * FROM `holiday`";
            ResultSet rs2 = st.executeQuery(query);
            
            holiday h;
            while(rs2.next()){
                h = new holiday(rs2.getInt("ID"),rs2.getString("Holiday"),rs2.getString("HolidayType"),rs2.getString("HolidayDate"));
                HolidayList.add(h);
            }
        } catch (SQLException ex) {
            Logger.getLogger(mainPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HolidayList;
    }
    
    public String get_month_array(String Month){
        
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
        String[] monthArr = new String[] { "January","February",
                                           "March","April",
                                           "May","June",
                                            "July","August",
                                            "September","October",
                                            "November","December"};
        
        String newMonth = monthArr[Integer.parseInt(Month)];
        
        return newMonth;
    }
    public String get_number_of_hours(String login, String logout){
        @SuppressWarnings("MismatchedReadAndWriteOfArray")
                
        int loginHour = Integer.parseInt(login.substring(0, 2));   
        int logoutHour = Integer.parseInt(logout.substring(0, 2)); 
        
        int loginMin = Integer.parseInt(login.substring(3, 5));  
        int logoutMin = Integer.parseInt(logout.substring(3, 5));
        String getMinutes = String.valueOf((logoutMin + loginMin)/60);
        
        String getNumOfHour;
        int min = Integer.parseInt(getMinutes);
        
        if(min>0){
             getNumOfHour = String.valueOf((logoutHour - loginHour)+min);
        }else{
             getNumOfHour = String.valueOf(logoutHour - loginHour);
        }
        
        
        return getNumOfHour;
    
    }
     @SuppressWarnings("NonPublicExported")
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
        public String check_attendance_summary(String EmployeeID, String Month, String Year){
            String flag=null;
            try{
                String sqlCheck = "SELECT COUNT(EmployeeID) FROM `attendance_summary` WHERE EmployeeID = '"+EmployeeID+"' AND Month = '"+Month+"' AND Year = '"+Year+"'";
                ResultSet rs = st.executeQuery(sqlCheck);
                while(rs.next()){
                   int count = rs.getInt("count(EmployeeID)");
                   System.out.println("Check Attendance: " + count);
                   if(count==1){
                       flag = "true";
                       
                   }else{
                       flag = "false";
                   }
                }

            }catch(SQLException e){
                System.out.println("Check Attendance: " + e);
        }
        System.out.println(flag);
        return flag;
        }
     
        public void update_attendance_summary_data(String ID, String DaysRendered, String SpecialHoliday, String RegularHoliday){

            try {
                String query = "UPDATE `attendance_summary` SET DaysRendered = ? , SpecialHoliday = ? , RegularHoliday = ? WHERE ID = ?";
                PreparedStatement update = cn.prepareStatement(query);
                update.setString(1, String.valueOf(DaysRendered));
                update.setString(2, String.valueOf(SpecialHoliday));
                update.setString(3, String.valueOf(RegularHoliday));
                update.setString(4, ID);
                update.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(attendancePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        public void insert_attendance_summary_data(String EmployeeID, String DaysRendered, String SpecialHoliday, String RegularHoliday,String Year, String Month){
            System.out.println("inserted f: "+EmployeeID +" " +DaysRendered+" "+SpecialHoliday+" "+RegularHoliday+" " + Year +" "+ Month);
            try{
                 String insertQuery = "INSERT INTO attendance_summary(EmployeeID, DaysRendered, SpecialHoliday,RegularHoliday, Month, Year)"
                                + "VALUES(?,?,?,?,?,?)";
                        PreparedStatement insert = cn.prepareStatement(insertQuery);
                        insert.setString(1, EmployeeID);
                        insert.setString(2, DaysRendered);
                        insert.setString(3, SpecialHoliday);
                        insert.setString(4, RegularHoliday);
                        insert.setString(5, Month);
                        insert.setString(6, Year);
                        insert.execute();
                         System.out.println("inserted success: "+EmployeeID +" " +DaysRendered+" "+SpecialHoliday+" "+RegularHoliday+" " + Year +" "+ Month);
            }catch(SQLException e){
                System.out.print("hindi NagInsert "+ e);
            }
            
        }
        public void update_attendanceSummary(String EmployeeID){
            
        final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            //Convert the value of month from number to word
            String Month = monthFormat.format(Calendar.getInstance().getTime());
            int intMonth = Integer.parseInt(Month);
            String newMonth = get_month_array(String.valueOf(intMonth - 1));    
            String Year = yearFormat.format(Calendar.getInstance().getTime());
            
            try{
                    
                ArrayList<attendanceSummary> attendanceSummaryList = attendanceSummary();
                for(int l=0; l<attendanceSummaryList.size();l++){
                    
                    String count = check_attendance_summary(attendanceSummaryList.get(l).getEmployeeID(),newMonth,Year);
                   System.out.println(attendanceSummaryList.get(l).getEmployeeID() + count);
                    if(count.equals("true")&&attendanceSummaryList.get(l).getEmployeeID().equals(EmployeeID)){
                       System.out.print("if");
                        String newDate = dateFormat.format(Calendar.getInstance().getTime());
                        int RegularHoliday = Integer.parseInt(attendanceSummaryList.get(l).getRegularHoliday());
                        int SpecialHoliday = Integer.parseInt(attendanceSummaryList.get(l).getSpecialHoliday());
                        int DaysRendered = Integer.parseInt(attendanceSummaryList.get(l).getDaysRendered());
                       String ID = String.valueOf(attendanceSummaryList.get(l).getID());
                      
                      ArrayList<holiday> HolidayList = getHoliday();
                      for(int h=0;h<HolidayList.size();h++){    
                          if(newDate.equals(HolidayList.get(h).getHolidayDate())&&HolidayList.get(h).getHolidayType().equals("RegularHoliday")){
                              
                              RegularHoliday += 1;
                              System.out.println("updated: " + ID + " " + RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                              update_attendance_summary_data(ID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday));
                              break;
                          }else if(newDate.equals(HolidayList.get(h).getHolidayDate())&&HolidayList.get(h).getHolidayType().equals("SpecialHoliday")){
                             SpecialHoliday += 1;
                             System.out.println("updated: " + ID + " " + RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                             update_attendance_summary_data(ID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday));
                             break;
                          }else{
                              DaysRendered += 1;
                              System.out.println("updated: " + ID + " " + RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                              update_attendance_summary_data(ID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday));
                              break;
                          }
                      }
                     // System.out.println("updated: " + ID + " " + RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                     break;
                    }else{
                        System.out.print("else");
                         String newDate = dateFormat.format(Calendar.getInstance().getTime());
                         int RegularHoliday = 0, SpecialHoliday = 0, DaysRendered = 0;
                      
                         
                      ArrayList<holiday> HolidayList = getHoliday();
                      for(int h=0;h<HolidayList.size();h++){
                          
                          System.out.println(newDate+ " " +HolidayList.get(h).getHolidayDate());
                          if(newDate.equals(HolidayList.get(h).getHolidayDate())&&HolidayList.get(h).getHolidayType().equals("RegularHoliday")){
                              RegularHoliday = 1;
                               System.out.println("inserted: "+RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                                insert_attendance_summary_data(EmployeeID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday), Year,  newMonth);
                                break;
                          }else if(newDate.equals(HolidayList.get(h).getHolidayDate())&&HolidayList.get(h).getHolidayType().equals("SpecialHoliday")){
                             SpecialHoliday = 1;
                             System.out.println("inserted: "+RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                             insert_attendance_summary_data(EmployeeID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday), Year,  newMonth);
                             break;
                          }else{
                              DaysRendered = 1;
                              System.out.println("inserted: "+RegularHoliday +" " +SpecialHoliday+" "+DaysRendered+" "+EmployeeID+" " + newMonth +" "+ Year);
                              insert_attendance_summary_data(EmployeeID, String.valueOf(DaysRendered), String.valueOf(SpecialHoliday), String.valueOf(RegularHoliday), Year,  newMonth);
                              break;
                          }
                          
                      }//for loop
                        break;
                    }//else if(count.equals("false")&&attendanceSummaryList.get(l).getEmployeeID().equals(EmployeeID))
                    
                }//for(int l=0; l<attendanceSummaryList.size();l++)

            }catch(Exception e){
                 System.out.println("wala: "+e);  
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

        jPanel1 = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        closeBtn = new javax.swing.JLabel();
        minBtn = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        timePL = new javax.swing.JLabel();
        datePL = new javax.swing.JLabel();
        loginTF = new javax.swing.JTextField();
        logoutTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        logBtn = new javax.swing.JButton();
        empIDTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        empNamePL = new javax.swing.JLabel();
        empPositionPL = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerPanel.setBackground(new java.awt.Color(102, 102, 102));
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

        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 30));

        timePL.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        timePL.setForeground(new java.awt.Color(51, 51, 51));
        timePL.setText("TIME");
        jPanel1.add(timePL, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 280, 40));

        datePL.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        datePL.setForeground(new java.awt.Color(51, 51, 51));
        datePL.setText("Date");
        jPanel1.add(datePL, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 111, -1, -1));

        loginTF.setEditable(false);
        jPanel1.add(loginTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 204, 270, 41));

        logoutTF.setEditable(false);
        jPanel1.add(logoutTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 285, 270, 41));

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("LOGIN");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 182, -1, -1));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logBtn.setForeground(new java.awt.Color(51, 51, 51));
        logBtn.setText("LOG");
        logBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logBtnActionPerformed(evt);
            }
        });
        jPanel2.add(logBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 220, -1, -1));

        empIDTF.setForeground(new java.awt.Color(51, 51, 51));
        jPanel2.add(empIDTF, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 230, 50));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("EMPLOYEE ID");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ATTENDANCE ");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 270, 30));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 340, 30));

        empNamePL.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        empNamePL.setForeground(new java.awt.Color(255, 255, 255));
        empNamePL.setText("EMPLOYEE NAME");
        jPanel2.add(empNamePL, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 240, 30));

        empPositionPL.setForeground(new java.awt.Color(255, 255, 255));
        empPositionPL.setText("EMPLOYEE POSITION");
        jPanel2.add(empPositionPL, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Module");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 30, 430, 470));

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("LOGOUT");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(95, 263, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeBtnMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeBtnMouseClicked

    private void minBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minBtnMouseClicked
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_minBtnMouseClicked

    private void headerPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerPanelMouseDragged

    private void headerPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerPanelMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerPanelMousePressed

    private void logBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logBtnActionPerformed
        // TODO add your handling code here:
        
            String time = timeFormat.format(Calendar.getInstance().getTime());
            String date = dateFormat.format(Calendar.getInstance().getTime());
            final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
            final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            //Convert the value of month from number to word
            String Month = monthFormat.format(Calendar.getInstance().getTime());
            int intMonth = Integer.parseInt(Month);
            String newMonth = get_month_array(String.valueOf(intMonth - 1));
            
            final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            String Day = dayFormat.format(Calendar.getInstance().getTime());
            String Year = yearFormat.format(Calendar.getInstance().getTime());
            
            String empID = empIDTF.getText();
            
            boolean flag = false;
            boolean flag1 = false;
            int emp = 0;
            
            
                    ArrayList<employee> employeesList = employeeList();
                    for(int e=0; e<employeesList.size();e++){
                        String empID3 = employeesList.get(e).getEmployeeID();   
                       
                        if(empID3.equals(empID)){
                           flag=true;
                           emp=e;
                           break;
                        }
                    }
                    int att= 0;
                     ArrayList<AttendanceLog> attendanceList1 = attendanceLog();
                    for(int ID=0; ID<attendanceList1.size();ID++){
                        String empID3 = attendanceList1.get(ID).getEmployeeID();   
                        String currentDay = attendanceList1.get(ID).getDay();
                        
                        if(empID3.equals(empID)&&currentDay.equals(Day)){
                           flag1=true;
                           att = ID;
                           break;
                        }
                    }
                    
                        if(flag==false && flag1==false){
                            JOptionPane.showMessageDialog(this, "EmployeeID Doesn't Exist");
                        }else if(flag==true && flag1==true){
                             ArrayList<AttendanceLog> attendanceList = attendanceLog();
                            for(int l=0; l<attendanceList.size();l++){

                            int AttLogID = attendanceList.get(l).getID();
                            String empID1 = attendanceList.get(l).getEmployeeID();
                            String empName = attendanceList.get(l).getEmployeeName();
                            String empPosition = attendanceList.get(l).getEmployeePosition();
                            String currentDay = attendanceList.get(l).getDay();
                            String login = attendanceList.get(l).getLogin();
                            String logout = attendanceList.get(l).getLogout();

                                if(att==l){

                                    if(logout.isEmpty()){
                                        
                                        update_attendanceSummary(empID1);
                                        
                                        try {
                                            String updateQuery = "UPDATE attendancelog_perday_tb SET Log_Out = ?, Number_Of_Hours = ? WHERE ID = ? ";
                                            PreparedStatement update = cn.prepareStatement(updateQuery);
                                            update.setString(1, time);
                                            update.setString(2, get_number_of_hours(login,time));
                                            update.setString(3, String.valueOf(AttLogID));
                                            update.executeUpdate();

                                            empNamePL.setText(empName);
                                            empPositionPL.setText(empPosition);
                                            loginTF.setText(login);
                                            logoutTF.setText(time); 
                                            JOptionPane.showMessageDialog(this, "You're Logout Successfully");
                                            empNamePL.setText("EMPLOYEE NAME");
                                            empPositionPL.setText("EMPLOYEE POSITION");
                                            loginTF.setText("");
                                            logoutTF.setText("");
                                            empIDTF.setText("");

                                        } catch (SQLException ex) {
                                            Logger.getLogger(attendancePanel.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                  
                                     }else{
                                        
                                        empNamePL.setText(empName);
                                        empPositionPL.setText(empPosition);
                                        loginTF.setText(login);
                                        logoutTF.setText(time); 
                                        JOptionPane.showMessageDialog(this, "You're Already Logout");
                                            empNamePL.setText("EMPLOYEE NAME");
                                            empPositionPL.setText("EMPLOYEE POSITION");
                                            loginTF.setText("");
                                            logoutTF.setText("");
                                            empIDTF.setText("");
                                        break;

                                    }
                                }
                            }
                        }else if(flag==true&&flag1==false){
                       
                        ArrayList<employee> employeesList1 = employeeList();
                        for(int e1=0; e1<employeesList1.size();e1++){
                            String empID2 = employeesList1.get(e1).getEmployeeID();
                            String empName1 = employeesList1.get(e1).getEmpName();
                            String empPosition1 = employeesList1.get(e1).getEmpJobTitle();
//                           /get_number_of_hours(String login, String logout)
                            if(emp==e1){
                                try {
                                    String insertQuery = "INSERT INTO attendancelog_perday_tb(EmployeeID, Employee_Name, Employee_Position, Log_in, Month, Day, Year, Log_Out, Number_Of_Hours)"
                                            + "VALUES(?,?,?,?,?,?,?,?,?)";
                                    PreparedStatement insert = cn.prepareStatement(insertQuery);
                                    insert.setString(1, empID2);
                                    insert.setString(2, empName1);
                                    insert.setString(3, empPosition1);
                                    insert.setString(4, time);
                                    
                                    insert.setString(5, newMonth);
                                    insert.setString(6, Day);
                                    insert.setString(7, Year);
                                    insert.setString(8, "");
                                    insert.setString(9, "");
                                    insert.execute();
                                    
                                     empNamePL.setText(empName1);
                                     empPositionPL.setText(empPosition1);
                                     loginTF.setText(time);
                                    JOptionPane.showMessageDialog(this, "You're Login Successfully");
                                    empNamePL.setText("EMPLOYEE NAME");
                                    empPositionPL.setText("EMPLOYEE POSITION");
                                    loginTF.setText("");
                                    logoutTF.setText(""); 
                                    empIDTF.setText("");
                                   
                                } catch (SQLException ex) {
                                    Logger.getLogger(attendancePanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }
                        }
                        }
    }//GEN-LAST:event_logBtnActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Dracula".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(attendancePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(attendancePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(attendancePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(attendancePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new attendancePanel().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(attendancePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel closeBtn;
    private javax.swing.JLabel datePL;
    private javax.swing.JTextField empIDTF;
    private javax.swing.JLabel empNamePL;
    private javax.swing.JLabel empPositionPL;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JButton logBtn;
    private javax.swing.JTextField loginTF;
    private javax.swing.JTextField logoutTF;
    private javax.swing.JLabel minBtn;
    private javax.swing.JLabel timePL;
    // End of variables declaration//GEN-END:variables

}
