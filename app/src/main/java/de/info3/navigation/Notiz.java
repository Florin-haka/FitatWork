package de.info3.navigation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//definiert eigenschaften einer notiz


@Entity
public class Notiz {
    @PrimaryKey
    private final int id;
    @ColumnInfo
    private final String notiztext;
    @ColumnInfo
    private final int keycode;

    public Notiz(int id, String notiztext, int keycode){
        this.id=id;
        this.notiztext=notiztext;
        this.keycode=keycode;
    }
    public int getId(){
        return this.id;
    }


    public String getNotiztext(){
        return this.notiztext;
    }

    public int getKeycode() {
        return this.keycode;
    }
}
