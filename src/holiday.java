/*
 * Copyright (C) 2019 Jhovan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Jhovan
 */
public class holiday {
    private int ID;
    private String Holiday, HolidayType, HolidayDate;
    
    public holiday(int ID, String Holiday, String HolidayType, String HolidayDate){
    
        this.ID=ID;
        this.Holiday=Holiday;
        this.HolidayType=HolidayType;
        this.HolidayDate=HolidayDate;
    
    }
    public int getID(){
        return ID;
    }
    public String getHoliday(){
        return Holiday;
    }
    public String getHolidayType(){
        return HolidayType;
    }
    public String getHolidayDate(){
        return HolidayDate;
    }
}
