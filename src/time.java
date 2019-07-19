/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhovan
 */
public class time {
    public static void main(String[] args){
        String login = "10:12:00",
               logout = "11:12:00";
        
        int loginHour = Integer.parseInt(login.substring(0, 2));   
        int logoutHour = Integer.parseInt(logout.substring(0, 2)); 
        String getNumOfHour = String.valueOf(logoutHour - loginHour);
        int loginMin = Integer.parseInt(login.substring(3, 5));  
        int logoutMin = Integer.parseInt(logout.substring(3, 5));
        String getNumOfHourAdd = String.valueOf((logoutMin + loginMin)/60);
        System.out.println(loginHour +" " + logoutHour + "="+getNumOfHour);
        System.out.println(loginMin +" " + logoutMin +"="+getNumOfHourAdd);
        
    }
}
