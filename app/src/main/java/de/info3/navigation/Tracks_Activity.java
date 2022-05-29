package de.info3.navigation;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Tracks_Activity extends AppCompatActivity {

    private DB_Helper database = new DB_Helper(Tracks_Activity.this);
    private int positionid;
    private boolean click = false;
    private JSONObject jameson = new JSONObject();
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        ListView lv = findViewById(R.id.list_view);
        ImageButton save = findViewById(R.id.track_button);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //?
        click = false;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionid = (int) lv.getItemIdAtPosition(i);
                try {
                    jameson.put("Tracknummer", lv.getItemAtPosition(positionid));
                    jameson.put("Modus", lv.getItemAtPosition(positionid+1));
                    jameson.put("Zeit", lv.getItemAtPosition(positionid+2));
                    jameson.put("Entfernung", lv.getItemAtPosition(positionid+3));
                    jameson.put("Datum", lv.getItemAtPosition(positionid+4));
                    jameson.put("Koordinaten", bundle.getString((String)lv.getItemAtPosition(positionid)));
                    toolbar.setTitle("Aktuell:"+lv.getItemAtPosition(positionid));
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                click = true;
            }
        });

        get9darter();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checker();

                String tracknumber = (String) lv.getItemAtPosition(positionid);
                String[] strings = tracknumber.split(" ");
                String onlyNumber=strings[1];

                if(click == true){
                    File file=new File("/storage/emulated/0/meinTrack_"+onlyNumber+".json");
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try{
                        FileOutputStream fos=new FileOutputStream(file);
                        fos.write(jameson.toString().getBytes(StandardCharsets.UTF_8));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

        Button button = findViewById(R.id.delete_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tent = new Intent(getApplicationContext(), MainActivityTrackingApp.class);
                database.deleteAll();
                startActivity(tent);

                }
            }
        );

    }

    public void get9darter(){
        ListView lv = findViewById(R.id.list_view);
        ArrayList<String> list = new ArrayList<>();
        Cursor localcursor = database.getData();
        localcursor.moveToFirst();
        int i=0;

            do {
                String startlocation;
                list.add(i, "---------------------------------------------------");
                String locations = localcursor.getString(5);
                String[] locationComponents = locations.split("~");
                String[] coordinates = locationComponents[0].split(";");
                try{
                startlocation = "Lat: " + coordinates[0] + " Long: " + coordinates[1];}
                catch(IndexOutOfBoundsException e){startlocation = "fail";}

                list.add(i, "Start-Koordinaten: " + startlocation);

                list.add(i, "Datum: " + localcursor.getString(4));
                list.add(i, "Entfernung: " + localcursor.getString(3));
                list.add(i, "Zeit: " + localcursor.getString(2));
                list.add(i, "Modus: " + localcursor.getString(1));
                list.add(i, "Tracknummer: " + localcursor.getString(0));
                bundle.putString("Tracknummer: "+localcursor.getString(0),locations);
                list.add(i, " ");
                i++;
            } while (localcursor.moveToNext());
        localcursor.close();

        for (int k=0;k<list.size();k++){
            for (int j=0;j<list.size();j++) {
                if (list.get(j).equals(" ")) {
                    list.remove(j);
                }
            }
        }

        ArrayAdapter<String> arr2;
        arr2 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,list);
        lv.setAdapter(arr2);


    }

    public void checker(){
        Permissions.check(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(Tracks_Activity.this, "Permission Granted", Toast.LENGTH_LONG).show();

            }
        });


    }
}