package com.example.demowaterresource;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class mainpage extends AppCompatActivity {
    Button flood, quality,wastage,crop,others,back,out;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        flood = (Button) findViewById(R.id.flood);
        quality = (Button) findViewById(R.id.quality);
        wastage = (Button) findViewById(R.id.wastage);
        crop = (Button) findViewById(R.id.agricultural);
        others = (Button) findViewById(R.id.others);
        back = (Button) findViewById(R.id.back);
        out = (Button) findViewById(R.id.out);
        name = (TextView) findViewById(R.id.msg2);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            String j = (String) b.get("email");
            name.setText(j);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, login_page.class);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainpage.this, Form.class);
                startActivity(intent);
            }
        });


        flood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Flood";
                Intent intent = new Intent(mainpage.this, Form.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

        quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Quality";
                Intent intent = new Intent(mainpage.this, Form.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

        wastage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Wastage";
                Intent intent = new Intent(mainpage.this, Form.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Crop";
                Intent intent = new Intent(mainpage.this, Form.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Others";
                Intent intent = new Intent(mainpage.this, Form.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });

    }
}

