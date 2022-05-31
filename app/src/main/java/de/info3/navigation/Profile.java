package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {
    @PrimaryKey
    private final int userId;
    @ColumnInfo
    private String userName;
    @ColumnInfo
    private int levelPoints; //100 Punkte: 0-33=1,33-66=2,66-100=3
    @ColumnInfo
    private int bonusPoints;
    @ColumnInfo
    private String linkProfileFoto;

    public Profile(int userId){
        this.userId=userId;
        this.bonusPoints=0;
        this.levelPoints=0;
        this.userName="Anonymes Murmeltier";
        //this.linkProfileFoto=standartbild
    }

    public int getUserId(){
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public int getLevelPoints(){
        return levelPoints;
    }

    public int getBonusPoints(){
        return bonusPoints;
    }

    public String getLinkProfileFoto(){
        return linkProfileFoto;
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public void setLevelPoints(int levelPoints){     //besser die Add-Methoden verwenden
        this.levelPoints=levelPoints;
    }

    public void setBonusPoints(int bonusPoints){    //besser die Add-Methoden verwenden
        this.bonusPoints=bonusPoints;
    }

    public void setLinkProfileFoto(String linkProfileFoto){
        this.linkProfileFoto=linkProfileFoto;
    }
    public int addLevelpoints(int additionalPoints){
        this.levelPoints=this.levelPoints+additionalPoints;
        return this.levelPoints;
    }

    public int addBonusPoints(int additionalBonuspoints){
        this.bonusPoints=this.bonusPoints+additionalBonuspoints;
        return this.bonusPoints;
    }


}
