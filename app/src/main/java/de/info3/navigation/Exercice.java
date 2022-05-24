package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Exercice {
    @PrimaryKey
    private final int id;
    @ColumnInfo
    private final String title;
    @ColumnInfo
    private final int timeInMs;
    @ColumnInfo
    private final String bodyPart; //evlt. codiert auch Schema 00101 immer 0 wenn nicht betroffen und 1 wenn betroffen
    @ColumnInfo
    private final int level; //1=leicht-3=schwer
    @ColumnInfo
    private final int bonusPoints;
    @ColumnInfo
    private final int levelPoints; //vermutlich unnötig
    @ColumnInfo
    private final String pictureLink;
    @ColumnInfo
    private final String shortDescription;
    @ColumnInfo
    private final String longDecription;
    @ColumnInfo
    private final String succesQuestion;
    @ColumnInfo
    private final String answer1;
    @ColumnInfo
    private final String answer2;
    @ColumnInfo
    private final String answer3;
    @ColumnInfo
    private final String answer4;
    @ColumnInfo
    private final String answer5;



    public Exercice(int id, String title, int timeInMs, String bodyPart,int level, int bonusPoints, int levelPoints, String pictureLink, String
                    shortDescription, String longDecription, String succesQuestion,String answer1,String answer2,String answer3,String answer4,String answer5){
        this.id=id;
        this.title=title;
        this.timeInMs=timeInMs;
        this. bodyPart=bodyPart;
        this.level=level;
        this.bonusPoints=bonusPoints;
        this.levelPoints=levelPoints;
        this.pictureLink=pictureLink;
        this.shortDescription=shortDescription;
        this.longDecription=longDecription;
        this.succesQuestion=succesQuestion;
        this.answer1 =answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;
        this.answer5=answer5;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public int getTimeInMs(){
        return this.timeInMs;
    }

    public String getBodyPart(){
        return this.bodyPart;
    }
    public int getLevel (){
        return level;
    }

    public int getBonusPoints(){
        return this.bonusPoints;
    }

    public int getLevelPoints(){
        return this.levelPoints;
    }

    public String getPictureLink(){
        return this.pictureLink;
    }

    public String getShortDescription(){
        return this.shortDescription;
    }

    public String getLongDecription(){
        return this.longDecription;
    }

    public String getSuccesQuestion(){
        return this.succesQuestion;
    }

    public String getAnswer1(){ return this.answer1;}

    public String getAnswer2(){ return this.answer2;}

    public String getAnswer3(){ return this.answer3;}

    public String getAnswer4(){ return this.answer4;}

    public String getAnswer5(){ return this.answer5;}
}
