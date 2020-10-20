package com.example.demowaterresource;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.demowaterresource.sql.DatabaseHelper;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.Drawable.*;

public class Grev_info extends AppCompatActivity {
    private Context context;
    DatabaseHelper openHelper;
    TableLayout tableLayout,tableLayout1;
    Cursor cursor;
    Spinner month,location;
    TextView category;
    SQLiteDatabase db;
    Button back, out,search;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grev_info);

        context = this;
        openHelper = new DatabaseHelper(this);
        back = (Button) findViewById(R.id.back);
        month = (Spinner) findViewById(R.id.month);
        location = (Spinner) findViewById(R.id.location);
        out = (Button) findViewById(R.id.out);
        search = (Button) findViewById(R.id.search);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grev_info.this, grev.class);
                startActivity(intent);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Grev_info.this, admin_login_page.class);
                startActivity(intent);
            }
        });

        // Reference to TableLayout
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        tableLayout1 = (TableLayout) findViewById(R.id.tablelayout1);

        tableLayout.setVisibility(View.VISIBLE);
        tableLayout1.setVisibility(View.GONE);
        category = (TextView) findViewById(R.id.category);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c2eeff"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"Date  ", "State","District", "Description", "Image"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);


        db = openHelper.getWritableDatabase();

        // Start the transaction.
        try {
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            if (b != null) {
                String j = (String) b.get("choice");
                String k = (String) b.get("i");
                category.setText(j);
            }


            final String i;
            i = (String) category.getText();

            if (i.equals("All")) {
                String selectQuery2 ="SELECT dates,state,district,description,image1,image2,image3,image4,image5 FROM " + DatabaseHelper.infoTable;
                cursor = db.rawQuery(selectQuery2, null);
            } else {
                cursor = db.rawQuery("SELECT dates,state,district,description,image1,image2,image3,image4,image5 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.category + " =? ", new String[]{i});
            }

            //date Adapter
            final List<String> option2 = new ArrayList<String>();
            option2.add("Month : ALL");
            option2.add("January");
            option2.add("Februray");
            option2.add("March");
            option2.add("April");
            option2.add("May");
            option2.add("June");
            option2.add("July");
            option2.add("Augast");
            option2.add("September");
            option2.add("October");
            option2.add("November");
            option2.add("December");


            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option2);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            month.setAdapter(dataAdapter2);

// Location Adapter
            final List<String> option1 = new ArrayList<String>();
            option1.add("Location : ALL");
            option1.add("Andhra Pradesh");
            option1.add("Arunachal Pradesh");
            option1.add("Assam");
            option1.add("Bihar");
            option1.add("Chhattisgarh");
            option1.add("Goa");
            option1.add("Gujarat");
            option1.add("Haryana");
            option1.add("Himachal Pradesh");
            option1.add("Jammu and Kashmir");
            option1.add("Jharkhand");
            option1.add("Karnataka");
            option1.add("Kerela");
            option1.add("Madhya Pradesh");
            option1.add("Maharashtra");
            option1.add("Manipur");
            option1.add("Meghalaya");
            option1.add("Mizoram");
            option1.add("Nagaland");
            option1.add("Odisha");
            option1.add("Punjab");
            option1.add("Rajasthan");
            option1.add("Sikkim");
            option1.add("Tamil Nadu");
            option1.add("Telangana");
            option1.add("Tripura");
            option1.add("Uttar Pradesh");
            option1.add("Uttrakhand");
            option1.add("West Bengal");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option1);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            location.setAdapter(dataAdapter);


            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String dates = cursor.getString(cursor.getColumnIndex("dates"));
                //    String location = cursor.getString(cursor.getColumnIndex("location"));
                    String state = cursor.getString(cursor.getColumnIndex("state"));
                    String district = cursor.getString(cursor.getColumnIndex("district"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    final byte[] blob = cursor.getBlob(cursor.getColumnIndex("image1"));
                    final byte[] blob2 = cursor.getBlob(cursor.getColumnIndex("image2"));
                    final byte[] blob3 = cursor.getBlob(cursor.getColumnIndex("image3"));
                    final byte[] blob4 = cursor.getBlob(cursor.getColumnIndex("image4"));
                    final byte[] blob5 = cursor.getBlob(cursor.getColumnIndex("image5"));
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(blob2);
                    ByteArrayInputStream inputStream3 = new ByteArrayInputStream(blob3);
                    ByteArrayInputStream inputStream4 = new ByteArrayInputStream(blob4);
                    ByteArrayInputStream inputStream5 = new ByteArrayInputStream(blob5);
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    final Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
                    final Bitmap bitmap3 = BitmapFactory.decodeStream(inputStream);
                    final Bitmap bitmap4 = BitmapFactory.decodeStream(inputStream);
                    final Bitmap bitmap5 = BitmapFactory.decodeStream(inputStream);

                    //  rows
                    TableRow row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                    String[] colText = {dates, state,district, description};



                    for (String c : colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(15);
                        tv.setMaxWidth(40);
                        tv.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_border_bg));
                        tv.setPadding(0, 5, 5, 5);
                        tv.setText(c);
                        row.addView(tv);
                    }

                    int j;
                    for (j = 0; j < 1; j++) {

                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_border_bg));
                        tv.setTextSize(15);
                        tv.setMaxWidth(20);
                        tv.setId(R.id.admin);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText("View \n image");
                        row.addView(tv);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Dialog builder = new Dialog(Grev_info.this);
                                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {

                                    }
                                });
                                Bitmap[] colText2 = {bitmap, bitmap2, bitmap3,bitmap4,bitmap5};
                                for (Bitmap c : colText2) {
                                    ImageView imageView = new ImageView(Grev_info.this);
                                    imageView.setImageBitmap(c);
                                    builder.addContentView(imageView, new LinearLayout.LayoutParams(1000, 1000));
                                    builder.show();
                                }
                                /*
                                ImageView imageView = new ImageView(Grev_info.this);
                                ImageView imageView2 = new ImageView(Grev_info.this);
                                ImageView imageView3 = new ImageView(Grev_info.this);
                                ImageView imageView4 = new ImageView(Grev_info.this);
                                ImageView imageView5 = new ImageView(Grev_info.this);


                                imageView.setImageBitmap(bitmap);
                                imageView2.setImageBitmap(bitmap2);
                                imageView3.setImageBitmap(bitmap3);
                                imageView4.setImageBitmap(bitmap4);
                                imageView5.setImageBitmap(bitmap5);


                                builder.addContentView(imageView2, new LinearLayout.LayoutParams(100, 100));
                                builder.addContentView(imageView3, new LinearLayout.LayoutParams(100, 100));
                                builder.addContentView(imageView4, new LinearLayout.LayoutParams(100, 100));
                                builder.addContentView(imageView5, new LinearLayout.LayoutParams(100, 100)); //ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                */

                            }
                        });
                    }
                    tableLayout.addView(row);
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.close();
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout1.removeAllViews();
                tableLayout1.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);
                category = (TextView) findViewById(R.id.category);


                TableRow rowHeader = new TableRow(context);
                rowHeader.setBackgroundColor(Color.parseColor("#c2eeff"));
                rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                String[] headerText = {"Date  ", "State","District", "Description", "Image"};
                for (String c : headerText) {
                    TextView tv = new TextView(Grev_info.this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(15);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(c);
                    rowHeader.addView(tv);
                }


                tableLayout1.addView(rowHeader);
                String val = month.getSelectedItem().toString();
                String val2 = location.getSelectedItem().toString();
                db = openHelper.getWritableDatabase();


                final String i;
                i = (String) category.getText();
                if(i.equals("All")) {
                    if (val.equalsIgnoreCase("Month : ALL") & val2.equalsIgnoreCase("Location : ALL")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable ;
                        cursor = db.rawQuery(selectQuery2, null);
                        //cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable );
                    }

                    else if (val.equalsIgnoreCase("January")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-1%'" ; //+ "AND'" + DatabaseHelper.state + "'=" + val2 ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("Februray")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-2%'";
                        cursor = db.rawQuery(selectQuery2, null);
                    }

                    else if (val.equalsIgnoreCase("March")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-3%'";
                        cursor = db.rawQuery(selectQuery2, null);
                    }

                    else if (val.equalsIgnoreCase("April")) {
                        //cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___04%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-4%'" ;
                        cursor = db.rawQuery(selectQuery2, null);
                    }
                    else if (val.equalsIgnoreCase("May")) {
                        // cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___05%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-5%'" ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("June")) {
                        //  cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___06%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable; // + " WHERE " + DatabaseHelper.dates + " LIKE '__-6%' " ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("July")) {
                        //cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___07%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-7%' " ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("Augast")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-8%'";
                        cursor = db.rawQuery(selectQuery2, null);
                    }
                    else if (val.equalsIgnoreCase("September")) {
                        //cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___09%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-9%' " ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("October")) {
                        // cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___10%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-07%' " ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("November")) {
                        // cursor = db.rawQuery("SELECT dates,location,description,image FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '___11%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-09%' " ;
                        cursor = db.rawQuery(selectQuery2, null);

                    }
                    else if (val.equalsIgnoreCase("December")) {
                        String selectQuery2 ="SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-07%' " ;
                        cursor = db.rawQuery(selectQuery2, null);
                    }
                }
                else if (val.equalsIgnoreCase("Month : ALL")) {

                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.category + " =? ", new String[]{i});

                }

                else if (val.equalsIgnoreCase("January")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-1%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("Februray")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-2%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                }

                else if (val.equalsIgnoreCase("March")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-3%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                }

                else if (val.equalsIgnoreCase("April")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-4%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                }
                else if (val.equalsIgnoreCase("May")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-5%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("June")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-6%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("July")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-7%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("Augast")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-8%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("September")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-9%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("October")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-10%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                }
                else if (val.equalsIgnoreCase("November")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-11%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});

                }
                else if (val.equalsIgnoreCase("December")) {
                    cursor = db.rawQuery("SELECT dates,state,district,description,image1 FROM " + DatabaseHelper.infoTable + " WHERE " + DatabaseHelper.dates + " LIKE '%-12%'" + " AND " + DatabaseHelper.category + " =? ", new String[]{i});
                }

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        // Read columns data
                        String dates = cursor.getString(cursor.getColumnIndex("dates"));
//                        String location = cursor.getString(cursor.getColumnIndex("location"));
                        String state = cursor.getString(cursor.getColumnIndex("state"));
                        String district = cursor.getString(cursor.getColumnIndex("district"));
                        String description = cursor.getString(cursor.getColumnIndex("description"));
                        final byte[] blob = cursor.getBlob(cursor.getColumnIndex("image1"));
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(blob);
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);



                                    TableRow row = new TableRow(context);
                                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                                    String[] colText = {dates, state,district, description};



                                    for (String c : colText) {
                                        TextView tv = new TextView(Grev_info.this);
                                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                                        tv.setGravity(Gravity.CENTER);
                                        tv.setTextSize(15);
                                        tv.setMaxWidth(40);
                                        tv.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_border_bg));
                                        tv.setPadding(0, 5, 5, 5);
                                        tv.setText(c);
                                        row.addView(tv);
                                    }

                                    int j;
                                    for (j = 0; j < 1; j++) {

                                        TextView tv = new TextView(Grev_info.this);
                                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                                        tv.setGravity(Gravity.CENTER);
                                        tv.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.edittext_border_bg));
                                        tv.setTextSize(15);
                                        tv.setMaxWidth(20);
                                        tv.setId(R.id.admin);
                                        tv.setPadding(5, 5, 5, 5);
                                        tv.setText("View \n image");
                                        row.addView(tv);
                                        tv.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Dialog builder = new Dialog(Grev_info.this);
                                                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                    @Override
                                                    public void onDismiss(DialogInterface dialogInterface) {

                                                    }
                                                });

                                    ImageView imageView = new ImageView(Grev_info.this);

                                    imageView.setImageBitmap(bitmap);

                                    //imageView.setLayoutParams(new LinearLayout.LayoutParams(400, 320));
                                    builder.addContentView(imageView, new LinearLayout.LayoutParams(1000, 1000)); //ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                    builder.show();




                                }
                            });
                        }
                        tableLayout1.addView(row);

                    }
                }
            }
        });
    } //OnCreate
}