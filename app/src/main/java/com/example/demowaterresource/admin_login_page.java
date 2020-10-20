package com.example.demowaterresource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class admin_login_page extends AppCompatActivity {
    Button b1;
    EditText ed1,ed2;
    TextView tx1,user;
    int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
        b1 = (Button)findViewById(R.id.loginbutton);
        ed1 = (EditText)findViewById(R.id.get_username);
        ed2 = (EditText)findViewById(R.id.get_password);
        user = (TextView) findViewById(R.id.user);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_login_page.this, login_page.class);
                startActivity(intent);
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("admin") && ed2.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(admin_login_page.this, data.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

    }
}


