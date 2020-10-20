package com.example.demowaterresource;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.demowaterresource.sql.DatabaseHelper;

public class rating extends AppCompatActivity {
    RatingBar rating_bar;  // initiate a rating bar
    Button Submit_button;
    Button temp;
    TextView feedback;
    DatabaseHelper openHelper;
    //SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_rating );
        openHelper = new DatabaseHelper(this);

        //  setCustomFont();
        Submit_button = (Button)findViewById ( R.id.Submit_button );
        feedback = (TextView) findViewById(R.id.feedback);
        temp = (Button)findViewById ( R.id.out );
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Flood";
                Intent intent = new Intent(rating.this, End.class);
                intent.putExtra("choice",choice);
                startActivity(intent);
            }
        });
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        Submit_button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                try {
                    db = openHelper.getWritableDatabase();
                    int stars = rating_bar.getNumStars();
                    String numberOfStars = Integer.toString(stars);
                    String feedbacks = feedback.getText().toString();
                    InsertData(numberOfStars, feedbacks);
                    startActivity(new Intent(rating.this, End.class));
                }
                catch (Exception ex) {
                    System.out.println("Exception occured : " + ex);
                }
            }
        });


    }

    private void InsertData(String rating, String feedbacks) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ratings, rating);
        values.put(DatabaseHelper.feedback,feedbacks );
        long id = db.insert(DatabaseHelper.rating, null, values);
    }

   /* private void setCustomFont()    {
        Typeface openSansBoldFont = Typeface.createFromAsset(getAssets(),"fonts/OpenSansBold.ttf");
        Typeface futuraLightFont = Typeface.createFromAsset(getAssets(),"fonts/FuturaLightBt.ttf");

        ((Button)findViewById ( R.id.Submit_button )).setTypeface(openSansBoldFont);

        ((TextView)findViewById(R.id.tv_rate)).setTypeface(futuraLightFont);
    }
    */
}

