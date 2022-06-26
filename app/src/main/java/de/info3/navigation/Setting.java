package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

//legt eigenschaften der einstellungen fest

@Entity
public class Setting {

    @PrimaryKey
    private final int settingId;
    @ColumnInfo
    private boolean showDailyTip;
    @ColumnInfo
    private String bodyPartsUsable;
    @ColumnInfo
    private int levelUsable;


    public Setting(int settingId){
        this.settingId=settingId;
        this.showDailyTip=true;
        this.levelUsable=123;
        this.bodyPartsUsable="11111";

    }

    public int getSettingId(){
        return this.settingId;
    }

    public boolean isShowDailyTip(){
        return this.showDailyTip;
    }

    public int getLevelUsable(){
        return this.levelUsable;
    }

    public String getBodyPartsUsable(){
       return this.bodyPartsUsable;
    }

    public void setShowDailyTip(boolean showDailyTip){
        this.showDailyTip=showDailyTip;
    }
    public void setBodyPartsUsable(String bodyPartsUsable){ //never use!
        this.bodyPartsUsable=bodyPartsUsable;
    }

    public void setLevelUsable(int levelUsable){ //never use!
        this.levelUsable=levelUsable;
    }




}
