package com.example.demowaterresource;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class data extends AppCompatActivity {
    Button user, greivance,feedback,out,outs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        user = (Button) findViewById(R.id.user);
        greivance = (Button) findViewById(R.id.grievance);
        feedback = (Button) findViewById(R.id.feedback);

        out = (Button) findViewById(R.id.out);


        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(data.this, admin_login_page.class);
                startActivity(intent);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(data.this, user_info.class);
                startActivity(intent);
            }
        });

        greivance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(data.this, grev.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(data.this, feedback_info.class);
                startActivity(intent);
            }
        });
    }
}
