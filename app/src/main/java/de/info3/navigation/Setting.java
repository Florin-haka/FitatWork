package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

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
        //default Settings:
        this.showDailyTip=true;
        this.levelUsable=123;
        this.bodyPartsUsable="ARM~BEIN~RUECKEN";//vermustlich gleiche codierung wie bei den Übungen

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

    public void changeShowDailyTip(){
        this.showDailyTip= !this.showDailyTip;
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
    public void changeLevelsUsabele(int levelToChange){
        /*Integer levelToChange1=levelToChange;
        boolean elementFound=false;
        for(int i=0;i<this.levelUsable.size();i++){
            if(this.levelUsable.get(i).equals(levelToChange1)){
                elementFound=true;
                this.levelUsable.remove(i);
            }
        }
        if(!elementFound){
            this.levelUsable.add(levelToChange1);
        }*/
    }


    public void changeBodyPartsusable(String bodyPart){
       /* boolean elementFound=false;
        for(int i=0;i<this.bodyPartsUsable.size();i++){
            if(this.bodyPartsUsable.get(i).equals(bodyPart)){
                elementFound=true;
                this.bodyPartsUsable.remove(i);
            }

        }
        if(!elementFound){
            this.bodyPartsUsable.add(bodyPart);
        }*/
    }

}
