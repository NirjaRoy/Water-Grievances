package com.example.demowaterresource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class grev extends AppCompatActivity {
    Spinner choose;
    Button All_info,back,out,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grev);
        All_info = (Button) findViewById(R.id.info);
        choose = (Spinner)findViewById ( R.id.list );
        back = (Button) findViewById(R.id.back);
        out = (Button) findViewById(R.id.out);
        search = (Button) findViewById(R.id.search);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grev.this, data.class);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(grev.this, admin_login_page.class);
                startActivity(intent);
            }
        });

        final List<String> option = new ArrayList<String>();
        option.add("Flood");
        option.add("Water Quality");
        option.add("Water Wastage");
        option.add("Agricultural Crop");
        option.add("Others");

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose.setAdapter(dataAdapter1);

        All_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "All";
                Intent intent = new Intent(grev.this, Grev_info.class);
                intent.putExtra("choice",choice);
                intent.putExtra("id",1);
                startActivity(intent);


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = choose.getSelectedItem().toString();

                if(val.equalsIgnoreCase("Flood")) {
                    String choice = "Flood";
                    Intent intent = new Intent(grev.this, Grev_info.class);
                    intent.putExtra("choice",choice);
                    intent.putExtra("id",1);
                    startActivity(intent);
                }
                else if (val.equalsIgnoreCase("Water Quality")) {
                    String choice = "Quality";
                    Intent intent = new Intent(grev.this, Grev_info.class);
                    intent.putExtra("choice",choice);
                    startActivity(intent);
                }
                else if (val.equalsIgnoreCase("Water Wastage")) {
                    String choice = "Wastage";
                    Intent intent = new Intent(grev.this, Grev_info.class);
                    intent.putExtra("choice",choice);
                    startActivity(intent);
                }
                else if (val.equalsIgnoreCase("Agricultural Crop")) {
                    String choice = "Crop";
                    Intent intent = new Intent(grev.this, Grev_info.class);
                    intent.putExtra("choice",choice);
                    startActivity(intent);
                }
                else if (val.equalsIgnoreCase("Others")) {
                    String choice = "Others";
                    Intent intent = new Intent(grev.this, Grev_info.class);
                    intent.putExtra("choice",choice);
                    startActivity(intent);
                }



            } });



    }


}

