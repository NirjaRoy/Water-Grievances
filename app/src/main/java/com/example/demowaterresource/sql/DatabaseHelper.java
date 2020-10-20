package com.example.demowaterresource.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.demowaterresource.modal.UserModel;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 20;
    // Database Name
    private static final String DATABASE_NAME = "WaterResource.db";


    // User table name
    public static final String demoTable = "user";
    public static final String infoTable = "info";
    public static final String rating = "rating";

    // User Table Columns names
    public static final String email = "email";                        //COLUMN_USER_EMAIL
    public static final String password = "password";                  //COLUMN_USER_PASSWORD
    public static final String username = "username";                 //COLUMN_USER_NAME
    public static final String phoneNumber = "phoneNumber";           //COLUMN_USER_ID
    public static final String dates = "dates";
    public static final String state = "state";
    public static final String district = "district";
    public static final String description = "description";
    public static final String image1 = "image1";
    public static final String image2 = "image2";
    public static final String image3 = "image3";
    public static final String image4 = "image4";
    public static final String image5 = "image5";
    public static final String category = "category";
    public static final String ratings = "ratings";
    public static final String feedback = "feedback";


    // create table sql query
    private String CREATE_INFO_TABLE = "CREATE TABLE " + infoTable + "(" + category + " TEXT," + dates + " TEXT," + state + " TEXT," + district + " TEXT," + description + " TEXT," +
            image1 + " BLOB," + image2 + " BLOB," + image3 + " BLOB," + image4 + " BLOB," + image5 + " BLOB" + ")";


    private String CREATE_USER_TABLE = "CREATE TABLE " + demoTable + "("        //TABLE_USER
            + email + " TEXT PRIMARY KEY," + password + " TEXT,"
            + username + " TEXT ," + phoneNumber + " TEXT" + ")";

    private String CREATE_RATING_TABLE = "CREATE TABLE " + rating + "("
            + ratings + " INTEGER," + feedback + " TEXT PRIMARY KEY " + ")";
    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INFO_TABLE);
        db.execSQL(CREATE_RATING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 20) {
            db.execSQL("DROP TABLE IF EXISTS " + rating);
            db.execSQL(CREATE_RATING_TABLE);
        }
    }

}

