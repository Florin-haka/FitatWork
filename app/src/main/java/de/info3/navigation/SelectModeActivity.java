package de.info3.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//teil des tracking ähnlich zu letztm semester

public class SelectModeActivity extends AppCompatActivity {

    ListView l;
    String modes[]
            = { "Fuß", "Fahrrad"} //hier wurden die andern Optionen entfernt, schließlich soll Sport gemacht werden xD
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        l = findViewById(R.id.list_view);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                modes);
        l.setAdapter(arr);



        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),MainActivityTrackingApp.class);
                intent.putExtra("name",modes[i]);
                startActivity(intent);



            }
        });

    }
}

