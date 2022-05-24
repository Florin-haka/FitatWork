package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DailyTip {
    @PrimaryKey
    private final int id;
    @ColumnInfo
    private final String title;
    @ColumnInfo
    private final String text;

    public DailyTip(int id, String title, String text){
        this.id=id;
        this.title=title;
        this.text=text;
    }
    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getText(){
        return this.text;
    }
}
