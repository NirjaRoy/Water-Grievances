package com.example.demowaterresource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.content.ContentValues;
import com.example.demowaterresource.sql.DatabaseHelper;


public class login_page extends AppCompatActivity
{
    DatabaseHelper openHelper;
    //SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;
    final Context context = this;
    String stored_email = "";
    ProgressBar progressBar;
    //for pop-up
    TextView tc_terms, tc_pp, admin;
    Button signinbutton, signupbutton;
    LinearLayout loginLayout, signupLayout;
    //login
    EditText getlogin_email, getlogin_password;
    Button loginbutton, skip;
    TextView skiptv;
    //final ArrayList<SignUpModel> signUpModelList = new ArrayList<> ();
    String login_email = "";//replace by login_email
    String login_password = "";//replace by login_password
    Resources res;
    String pass="", emailid="";

    //signup
    EditText getsignup_username, getsignup_phone, getsignup_email, getsignup_password, getsignup_password2;
    Button submitbutton;


    String username= "", phoneNumber= "", email= "", password= "", password2= "";
    Boolean CheckEditTextEmpty;
    String SQLiteQuery;
    SQLiteDatabase SQLITEDATABASE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //db = new SQLiteDatabase();
        //db = getReadableDatabase();
        openHelper = new DatabaseHelper(this);        //SQLiteDBHelper
        getsignup_username = (EditText)findViewById(R.id.getsignup_username);
        getsignup_phone = (EditText) findViewById(R.id.getsignup_phone);
        getsignup_email = (EditText) findViewById(R.id.getsignup_email);
        getsignup_password = (EditText) findViewById(R.id.getsignup_password);
        getsignup_password2 = (EditText) findViewById(R.id.getsignup_password2);
        getlogin_email = (EditText) findViewById(R.id.getlogin_email);
        getlogin_password = (EditText) findViewById(R.id.getlogin_password);
        submitbutton = (Button) findViewById(R.id.submitbutton);                       // registration
        loginbutton = (Button) findViewById(R.id.loginbutton);                         // login
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tc_terms = (TextView) findViewById(R.id.tc_terms); //t&c
        tc_pp = (TextView) findViewById(R.id.tc_pp);    //pp
        signinbutton = (Button) findViewById(R.id.signinbutton);//login
        signupbutton = (Button) findViewById(R.id.signupbutton);
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        signupLayout = (LinearLayout) findViewById(R.id.signupLayout);
        admin = (TextView) findViewById(R.id.admin);
        skip = (Button) findViewById(R.id.skip);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, admin_login_page.class);
                startActivity(intent);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this, mainpage.class);
                startActivity(intent);
            }
        });

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.GONE);

                signupbutton.setBackgroundColor(getResources().getColor(R.color.colorSignUp));
                signinbutton.setBackgroundColor(getResources().getColor(R.color.colorSignIn));
            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.GONE);
                signupLayout.setVisibility(View.VISIBLE);

                signupbutton.setBackgroundColor(getResources().getColor(R.color.colorSignIn));
                signinbutton.setBackgroundColor(getResources().getColor(R.color.colorSignUp));
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {                     //login
            @Override
            public void onClick(View view) {
                try {
                    db = openHelper.getWritableDatabase();
                    String email = getlogin_email.getText().toString();
                    String pass = getlogin_password.getText().toString();

                    cursor = db.rawQuery("SELECT email ,password, username FROM " + DatabaseHelper.demoTable + " WHERE " + DatabaseHelper.email + "=? AND " + DatabaseHelper.password + " =? " , new String[]{email,pass});          //+ "=? AND " + DatabaseHelper.password
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {

                            cursor.moveToFirst();
                            //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                            String fname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.username));
                           //String eemail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.email));

                            Toast.makeText(login_page.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login_page.this, mainpage.class);
                            intent.putExtra("email",fname);
                            startActivity(intent);

                            //Removing MainActivity[Login Screen] from the stack for preventing back button press.
                            finish();
                        }

                        else {
                            //I am showing Alert Dialog Box here for alerting user about wrong credentials
                            final AlertDialog.Builder builder = new AlertDialog.Builder(login_page.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Username or Password is wrong.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    dialogInterface.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                            //-------Alert Dialog Code Snippet End Here
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Exception occured : " + ex);
                }

            }
        });


        submitbutton.setOnClickListener(new View.OnClickListener()  //register
        {

            @Override
            public void onClick(View v) {

                try {
                    db = openHelper.getWritableDatabase();
                    String email = getsignup_email.getText().toString();
                    String password = getsignup_password.getText().toString();
                    String username = getsignup_username.getText().toString();
                    String phone = getsignup_phone.getText().toString();
                    String password2 = getsignup_password2.getText().toString();


                    CheckEditTextIsEmptyOrNot(username, phone, email, password, password2);
                    if (CheckEditTextEmpty == true) {
                        //Calling InsertData Method - Defined below
                        InsertData(email, password, username, phone);

                        //Alert dialog after clicking the Register Account
                        final AlertDialog.Builder builder = new AlertDialog.Builder(login_page.this);
                        builder.setTitle("Information");
                        builder.setMessage("Your Account is Successfully Created.");
                        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //Finishing the dialog and removing Activity from stack.
                                dialogInterface.dismiss();
                                Intent intent = new Intent(login_page.this, login_page.class);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                else {

                    Toast.makeText(login_page.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }   }
                catch(Exception ex){
                        System.out.println("Exception occured : " + ex);
                    }


            }
        });

    }

    //Inserting Data into database - Like INSERT INTO QUERY.
    public void InsertData( String email,String password, String fullName , String mobile) {

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.email, email);
        values.put(DatabaseHelper.password,password );
        values.put(DatabaseHelper.username, fullName);
        values.put(DatabaseHelper.phoneNumber, mobile);
        long id = db.insert(DatabaseHelper.demoTable, null, values);

    }
    public void CheckEditTextIsEmptyOrNot(String username,String phoneNumber, String email, String password, String password2  ){

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)|| TextUtils.isEmpty(password2)) {

            CheckEditTextEmpty = false ;
        }
        else {
            CheckEditTextEmpty = true ;
        }

}   }






/////////////////////////////////////
/*
        loginbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBCreate();

                SubmitData2SQLiteDB();

            }
        });

    }

    public void DBCreate(){

        SQLITEDATABASE = openOrCreateDatabase("DemoDataBase", Context.MODE_PRIVATE, null);

        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demoTable( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, username VARCHAR, phoneNumber VARCHAR, email VARCHAR, password VARCHAR, password2 VARCHAR); ");
    }

    public void SubmitData2SQLiteDB(){

        username = getsignup_username.getText().toString();
        phoneNumber = getsignup_phone.getText().toString();
        email = getsignup_email.getText().toString();
        password= getsignup_password.getText().toString();
        password2= getsignup_password2.getText().toString();

        CheckEditTextIsEmptyOrNot( username,phoneNumber, email,password,password2);

        if(CheckEditTextEmpty == true) {

            SQLiteQuery = "INSERT INTO demoTable (username,phoneNumber,email,password,password2) VALUES('" + username + "', '" + phoneNumber + "', '" + email + "', '" + password + "', '" + password2 + "');";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(login_page.this, "Data Submit Successfully",
                    Toast.LENGTH_LONG).show();

            ClearEditTextAfterDoneTask();
        }
        else {

            Toast.makeText(login_page.this,"Please Fill All the Fields",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void CheckEditTextIsEmptyOrNot(String username,String phoneNumber, String email, String password, String password2  ){

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)|| TextUtils.isEmpty(password2)) {

            Toast.makeText(login_page.this,"Please fill all the details",Toast.LENGTH_LONG).show();
        }
        else {
            CheckEditTextEmpty = true ;
        }
    }

    public void ClearEditTextAfterDoneTask(){

        getsignup_username.getText().clear();
        getsignup_phone.getText().clear();
        getsignup_email.getText().clear();
        getsignup_password.getText().clear();
        getsignup_password2.getText().clear();

    }

        }

*/

