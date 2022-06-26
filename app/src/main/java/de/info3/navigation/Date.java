package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

//wird nicht verwendet

@Entity
public class Date {
    @PrimaryKey
    private final int id;
    @ColumnInfo
    private final String calendarString; //umwandeler einfügen
    @ColumnInfo
    private String title;

    public Date(int id, String calendarString,String title){
        this.id=id;
        this.calendarString=calendarString;
        this.title=title;
    }
@Ignore
    public Date(int id, String calendarString){
        this.id=id;
        this.calendarString=calendarString;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }
    public String getCalendarString(){
        return this.calendarString;
    }

    public void setTitle(String title){
        this.title=title;
    }
}
