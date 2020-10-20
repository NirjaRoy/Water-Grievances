package com.example.demowaterresource;

        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.graphics.Color;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.example.demowaterresource.sql.DatabaseHelper;

public class feedback_info extends AppCompatActivity {
    private Context context;
    DatabaseHelper openHelper;
    Button back,out;
    TableLayout tableLayout;
    Cursor cursor, cursor2;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_info);
        context=this;
        openHelper = new DatabaseHelper(this);

        // Reference to TableLayout
        tableLayout=(TableLayout)findViewById(R.id.tablelayout);
        back = (Button) findViewById(R.id.back);
        out = (Button) findViewById(R.id.out);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback_info.this, data.class);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedback_info.this, admin_login_page.class);
                startActivity(intent);
            }
        });

        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c2eeff"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"Username","Rating [out of 5]","Feedback"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        db = openHelper.getWritableDatabase();

        // Start the transaction.
        try
        {
            String selectQuery = "SELECT ratings,feedback FROM "+ DatabaseHelper.rating;
            String selectQuery2 = "SELECT username FROM "+ DatabaseHelper.demoTable;
            cursor = db.rawQuery(selectQuery,null);
            cursor2 = db.rawQuery(selectQuery2,null);


                while (cursor2.moveToNext()&cursor.moveToNext())
                {
                    String name= cursor2.getString(cursor2.getColumnIndex("username"));
                    String rating= cursor.getString(cursor.getColumnIndex("ratings"));
                    String feedback= cursor.getString(cursor.getColumnIndex("feedback"));

                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    tableLayout.addView(row);
                    String[] colText={name,rating,feedback};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(18);
                        tv.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_border_bg));
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);

                    }

                }

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.close();
            // Close database
        }
    }

}

