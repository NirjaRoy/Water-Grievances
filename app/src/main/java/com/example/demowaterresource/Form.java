package com.example.demowaterresource;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;;
import android.database.sqlite.SQLiteDatabase;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.demowaterresource.sql.DatabaseHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Form extends AppCompatActivity  implements View.OnClickListener {


    private static int RESULT_LOAD_IMAGE = 1;
    DatabaseHelper openHelper;
    //SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;


    final Context context = this;
    EditText  get_description,_editText,get_date;
    TextView msg,name,get_location;
    ImageView get_upload1, get_upload2, get_upload3, get_upload4, get_upload5, iv,calendar;
    Button submit, back, temp, location_button;
    android.widget.ImageButton ImageButton;
    Boolean CheckEditTextEmpty;
    Spinner states,district;
    LocationManager locationManager;
    private Calendar mcalendar = Calendar.getInstance();
    private int day,month,year;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String imagePath;
    private List<String> imagePathList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        openHelper = new DatabaseHelper(this);
        iv = (ImageView) findViewById(R.id.image2);
        get_date = (EditText) findViewById(R.id.today);
        get_location = (TextView) findViewById(R.id.Locations);
        get_description = (EditText) findViewById(R.id.descriptionInput);
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        submit = (Button) findViewById(R.id.submit);
        location_button = (Button) findViewById(R.id.location_button);
        temp = (Button) findViewById(R.id.out);
        get_upload1 = (ImageView) findViewById(R.id.img1);
        get_upload2 = (ImageView) findViewById(R.id.img2);
        get_upload3 = (ImageView) findViewById(R.id.img3);
        get_upload4 = (ImageView) findViewById(R.id.img4);
        get_upload5 = (ImageView) findViewById(R.id.img5);
        calendar = (ImageView) findViewById(R.id.image);
        states = (Spinner) findViewById(R.id.state);
        district = (Spinner) findViewById(R.id.district);
        msg = (TextView) findViewById(R.id.msg);
        back = (Button) findViewById(R.id.back);
        name = (TextView) findViewById(R.id.msg2);
        ImageButton = (ImageButton) findViewById(R.id.ImageButton);
        ImageButton.setOnClickListener(this);

        // temprorary
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = "Flood";
                Intent intent = new Intent(Form.this, rating.class);
                intent.putExtra("choice", choice);
                startActivity(intent);
            }
        });


        // Get category for header
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            String j = (String) b.get("choice");
            msg.setText(j);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Form.this, mainpage.class);
                startActivity(intent);
            }
        });


        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int count = 0;
                count++;
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog builder = new Dialog(Form.this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                    }
                });

                TextView textView = new TextView(Form.this);
                textView.setBackgroundColor(Color.rgb(247, 248, 250));
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD_ITALIC);

                textView.setText(" Coastal Flooding\n" +
                        "Coastal areas often bear the brunt of severe storms, especially if these have gathered pace over the oceans. Extreme weather and high tides can cause a rise in sea levels, sometimes resulting in coastal flooding.  \n \n \n" +
                        "River Flooding\n" +
                        "River flooding occurs when a body of water exceeds its capacity. When a river ‘bursts its banks’ - typically due to high rainfall over a prolonged period of time.");
                builder.addContentView(textView, new LinearLayout.LayoutParams(1000, 800));
                builder.show();
            }
        });

        //State Adapter
        final List<String> option1 = new ArrayList<String>();
        option1.add("State");
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

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(dataAdapter2);

        final List<String> option2 = new ArrayList<String>();
        option2.add("District");
       // ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, option2);





        states.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {

                String stateName = option1.get(position).toString();
                option2.removeAll(option2);//i haven't checked this.
                if (stateName.equals("Andhra Pradesh")) {
                    option2.add("Anantapur");
                    option2.add("Chittoor");
                    option2.add("East Godavari");
                    option2.add("Guntur");
                    option2.add("Kadapa");
                    option2.add("Krishna");
                    option2.add("Kurnool");
                    option2.add("Prakasam");
                    option2.add("Sri Potti Sriramulu Nellore");
                    option2.add("Srikakulam");
                    option2.add("Visakhapatnam");
                    option2.add("Vizianagaram");
                    option2.add("West Godavari");


                    //you can also get a cursor and add Strings as options to city_options instead of what i have done
                } else if (stateName.equals("Arunachal Pradesh")) {
                    option2.add("Anjaw");
                    option2.add("Changlang");
                    option2.add("East Kameng");
                    option2.add("East Siang");
                    option2.add("Kamle");
                    option2.add("Kra Daadi");
                    option2.add("Kurung Kumey");
                    option2.add("Lepa-Rada");
                    option2.add("Lohit");
                    option2.add("Longding");
                    option2.add("Lower Dibang Valley");
                    option2.add("Lower Siang");
                    option2.add("Lower Subansiri");
                    option2.add("Namsai");
                    option2.add("Pakke-Kessang");
                    option2.add("Papum Pare");
                    option2.add("Shi Yomi");
                    option2.add("Siang");
                    option2.add("Tawang");
                    option2.add("Tirap");
                    option2.add("Upper Dibang Valley");
                    option2.add("Upper Siang");
                    option2.add("Upper Subansiri");
                    option2.add("West Kameng");
                    option2.add("West Siang");
                }
             else if (stateName.equals("Assam")) {
                    option2.add("Baksa");
                    option2.add("Barpeta");
                    option2.add("Bishwanath");
                    option2.add("Bongaigaon");
                    option2.add("Cachar");
                    option2.add("Charaideo");
                    option2.add("Chirang");
                    option2.add("Darrang");
                    option2.add("Dhemaji");
                    option2.add("Dhubri");
                    option2.add("Dibrugarh");
                    option2.add("Dima Hasao");
                    option2.add("Goalpara");
                    option2.add("Golaghat");
                    option2.add("Hailakandi");
                    option2.add("Hojai");
                    option2.add("Jorhat");
                    option2.add("Kamrup");
                    option2.add("Kamrup Metropolitan");
                    option2.add("Karbi Anglong");
                    option2.add("Karimganj");
                    option2.add("Kokrajhar");
                    option2.add("Lakhimpur");
                    option2.add("Majuli");
                    option2.add("Morigaon");
                    option2.add("Nagaon");
                    option2.add("Nalbari");
                    option2.add("Sivasagar");
                    option2.add("South Salmara");
                    option2.add("Sonitpur");
                    option2.add("Tinsukia");
                    option2.add("Udalguri");
                    option2.add("West Karbi Anglong");

                }
                else if (stateName.equals("Bihar")) {
                    option2.add("Araria");
                    option2.add("Arwal");
                    option2.add("Aurangabad");
                    option2.add("Banka");
                    option2.add("Begusarai");
                    option2.add("Bhagalpur");
                    option2.add("Bhojpur");
                    option2.add("Buxar");
                    option2.add("Darbhanga");
                    option2.add("East Champaran");
                    option2.add("Gaya");
                    option2.add("Gopalganj");
                    option2.add("Jamui");
                    option2.add("Jehanabad");
                    option2.add("Kaimur");
                    option2.add("Katihar");
                    option2.add("Khagaria");
                    option2.add("Kishanganj");
                    option2.add("Lakhisarai");
                    option2.add("Madhepura");
                    option2.add("Madhubani");
                    option2.add("Munger");
                    option2.add("Muzaffarpur");
                    option2.add("Nalanda");
                    option2.add("Nawada");
                    option2.add("Patna");
                    option2.add("Purnia");
                    option2.add("Rohtas");
                    option2.add("Saharsa");
                    option2.add("Samastipur");
                    option2.add("Saran");
                    option2.add("Sheikhpura");
                    option2.add("Sheohar");
                    option2.add("Sitamarhi");
                    option2.add("Siwan");
                    option2.add("Supaul");
                    option2.add("Vaishali");
                    option2.add("West Champaran");

                }
                else if(stateName.equals("Chhattisgarh")) {
                    option2.add("Balod");
                    option2.add("Baloda Bazar");
                    option2.add("Balrampur");
                    option2.add("Bastar");
                    option2.add("Bemetara");
                    option2.add("Bijapur");
                    option2.add("Bilaspur");
                    option2.add("Dantewada");
                    option2.add("Dhamtari");
                    option2.add("Durg");
                    option2.add("Gariaband");
                    option2.add("Janjgir-Champa");
                    option2.add("Jashpur");
                    option2.add("Kabirdham");
                    option2.add("Kanker");
                    option2.add("Kondagaon");
                    option2.add("Korba");
                    option2.add("Korea");
                    option2.add("Mahasamund");
                    option2.add("Mungeli");
                    option2.add("Narayanpur");
                    option2.add("Raigarh");
                    option2.add("Raipur");
                    option2.add("Rajnandgaon");
                    option2.add("Sukma");
                    option2.add("Surajpur");
                    option2.add("Surguja");
                }else if(stateName.equals("Goa")) {
                    option2.add("North Goa");
                    option2.add("South Goa");
                }else if(stateName.equals("Gujarat")) {
                    option2.add("Ahmedabad");
                    option2.add("Amreli");
                    option2.add("Anand");
                    option2.add("Aravalli");
                    option2.add("Banaskantha");
                    option2.add("Bharuch");
                    option2.add("Bhavnagar");
                    option2.add("Botad");
                    option2.add("Chhota Udepur");
                    option2.add("Dahod");
                    option2.add("Dang");
                    option2.add("Devbhoomi Dwarka");
                    option2.add("Gandhinagar");
                    option2.add("Gir Somnath");
                    option2.add("Jamnagar");
                    option2.add("Junagadh");
                    option2.add("Kheda");
                    option2.add("Kutch");
                    option2.add("Mahisagar");
                    option2.add("Mehsana");
                    option2.add("Morbi");
                    option2.add("Narmada");
                    option2.add("Navsari");
                    option2.add("Panchmahal");
                    option2.add("Patan");
                    option2.add("Porbandar");
                    option2.add("Rajkot");
                    option2.add("Sabarkantha");
                    option2.add("Surat");
                    option2.add("Surendranagar");
                    option2.add("Vadodara");
                    option2.add("Valsad");

                }else if(stateName.equals("Haryana")) {
                    option2.add("Ambala");
                    option2.add("Bhiwani");
                    option2.add("Charkhi Dadri");
                    option2.add("Faridabad");
                    option2.add("Fatehabad");
                    option2.add("Gurugram");
                    option2.add("Hissar");
                    option2.add("Jhajjar");
                    option2.add("Jind");
                    option2.add("Kaithal");
                    option2.add("Karnal");
                    option2.add("Kurukshetra");
                    option2.add("Mahendragarh");
                    option2.add("Nuh");
                    option2.add("Palwal");
                    option2.add("Panchkula");
                    option2.add("Panipat");
                    option2.add("Rewari");
                    option2.add("Rohtak");
                    option2.add("Sirsa");
                    option2.add("Sonipat");
                    option2.add("Yamuna Nagar");

                }else if(stateName.equals("Himachal Pradesh")) {
                    option2.add("Bilaspur");
                    option2.add("Chamba");
                    option2.add("Hamirpur");
                    option2.add("Kangra");
                    option2.add("Kinnaur");
                    option2.add("Kullu");
                    option2.add("Lahaul and Spiti");
                    option2.add("Mandi");
                    option2.add("Shimla");
                    option2.add("Sirmaur");
                    option2.add("Solan");
                    option2.add("Una");

                }else if(stateName.equals("Jammu and Kashmir")) {
                    option2.add("Anantnag");
                    option2.add("Bandipora");
                    option2.add("Baramulla");
                    option2.add("Badgam");
                    option2.add("Doda");
                    option2.add("Ganderbal");
                    option2.add("Jammu");
                    option2.add("Kargil");
                    option2.add("Kathua");
                    option2.add("Kishtwar");
                    option2.add("Kulgam");
                    option2.add("Kupwara");
                    option2.add("Leh");
                    option2.add("Poonch");
                    option2.add("Pulwama");
                    option2.add("Rajouri");
                    option2.add("Ramban");
                    option2.add("Reasi");
                    option2.add("Samba");
                    option2.add("Shopian");
                    option2.add("Srinagar");
                    option2.add("Srinagar");

                }
                else if(stateName.equals("Jharkhand")) {
                    option2.add("Bokaro");
                    option2.add("Chatra");
                    option2.add("Deoghar");
                    option2.add("Dhanbad");
                    option2.add("Dumka");
                    option2.add("East Singhbhum");
                    option2.add("Garhwa");
                    option2.add("Giridih");
                    option2.add("Godda");
                    option2.add("Gumla");
                    option2.add("Hazaribag");
                    option2.add("Jamtara");
                    option2.add("Khunti");
                    option2.add("Koderma");
                    option2.add("Latehar");
                    option2.add("Lohardaga");
                    option2.add("Pakur");
                    option2.add("Palamu");
                    option2.add("Ramgarh");
                    option2.add("Ranchi");
                    option2.add("Sahibganj");
                    option2.add("Seraikela Kharsawan");
                    option2.add("Simdega");
                    option2.add("West Singhbhum");

                }
                else if(stateName.equals("Karnataka")) {
                    option2.add("Bagalkot");
                    option2.add("Ballari");
                    option2.add("Belagavi");
                    option2.add("Bengaluru Rural");
                    option2.add("Bengaluru Urban");
                    option2.add("Bidar");
                    option2.add("Chamarajnagar");
                    option2.add("Chikkaballapur");
                    option2.add("Chikkamagaluru");
                    option2.add("Chitradurga");
                    option2.add("Dakshina Kannada");
                    option2.add("Davanagere");
                    option2.add("Dharwad");
                    option2.add("Gadag");
                    option2.add("Hassan");
                    option2.add("Haveri");
                    option2.add("\tKalaburagi");
                    option2.add("Kodagu");
                    option2.add("Kolar");
                    option2.add("Koppal");
                    option2.add("Mandya");
                    option2.add("Mysuru");
                    option2.add("Raichur");
                    option2.add("Ramanagara");
                    option2.add("Shivamogga");
                    option2.add("Tumakuru");
                    option2.add("Udupi");
                    option2.add("Uttara Kannada");
                    option2.add("Vijayapura");
                    option2.add("Yadgir");

                }
                else if(stateName.equals("Kerala")) {
                    option2.add("Alappuzha");
                    option2.add("Ernakulam");
                    option2.add("Ernakulam");
                    option2.add("Kannur");
                    option2.add("Kasaragod");
                    option2.add("Kollam");
                    option2.add("Kottayam");
                    option2.add("Kozhikode");
                    option2.add("Malappuram");
                    option2.add("Palakkad");
                    option2.add("Pathanamthitta district");
                    option2.add("Thrissur");
                    option2.add("Thiruvananthapuram");
                    option2.add("Wayanad");

                }
                else if(stateName.equals("Madhya Pradesh")) {
                    option2.add("Agar Malwa");
                    option2.add("Alirajpur");
                    option2.add("Anuppur");
                    option2.add("Ashok Nagar");
                    option2.add("Balaghat");
                    option2.add("Barwani");
                    option2.add("Betul");
                    option2.add("Bhind");
                    option2.add("Bhopal");
                    option2.add("Burhanpur");
                    option2.add("Chhatarpur");
                    option2.add("Chhindwara");
                    option2.add("Damoh");
                    option2.add("Datia");
                    option2.add("Dewas");
                    option2.add("Dhar");
                    option2.add("Dindori");
                    option2.add("Guna");
                    option2.add("Gwalior");
                    option2.add("Harda");
                    option2.add("Hoshangabad\t");
                    option2.add("Indore");
                    option2.add("Jabalpur");
                    option2.add("Jhabua");
                    option2.add("Katni");
                    option2.add("Khandwa");
                    option2.add("Khargone");
                    option2.add("Mandla");
                    option2.add("Mandsaur");
                    option2.add("Morena");
                    option2.add("Narsinghpur");
                    option2.add("Neemuch");
                    option2.add("Niwari");
                    option2.add("Panna");
                    option2.add("Raisen");
                    option2.add("Rajgarh");
                    option2.add("Ratlam");
                    option2.add("Rewa");
                    option2.add("\tSagar");
                    option2.add("Satna");
                    option2.add("Sehore");
                    option2.add("Seoni");
                    option2.add("Shahdol");
                    option2.add("Shajapur");
                    option2.add("Sheopur");
                    option2.add("Shivpuri");
                    option2.add("Sidhi");
                    option2.add("Singrauli");
                    option2.add("Tikamgarh");
                    option2.add("Ujjain");
                    option2.add("Umaria");
                    option2.add("Vidisha");

                }
                else if(stateName.equals("Maharashtra")) {
                    option2.add("Ahmednagar");
                    option2.add("Akola");
                    option2.add("Amravati");
                    option2.add("Aurangabad");
                    option2.add("Beed");
                    option2.add("Bhandara");
                    option2.add("Buldhana");
                    option2.add("Chandrapur");
                    option2.add("Dhule");
                    option2.add("Gadchiroli");
                    option2.add("Gondia");
                    option2.add("Hingoli");
                    option2.add("Jalgaon");
                    option2.add("Jalna");
                    option2.add("Kolhapur");
                    option2.add("Latur");
                    option2.add("Mumbai City");
                    option2.add("Mumbai suburban");
                    option2.add("Nanded");
                    option2.add("Nandurbar");
                    option2.add("Nagpur");
                    option2.add("Nashik");
                    option2.add("Osmanabad");
                    option2.add("Palghar");
                    option2.add("Parbhani");
                    option2.add("Pune");
                    option2.add("Raigad");
                    option2.add("Ratnagiri");
                    option2.add("Sangli");
                    option2.add("Satara");
                    option2.add("Sindhudurg");
                    option2.add("Solapur");
                    option2.add("Thane");
                    option2.add("Wardha");
                    option2.add("Washim");
                    option2.add("Yavatmal");
                }
                else if(stateName.equals("Manipur")) {
                    option2.add("Bishnupur");
                    option2.add("Chandel");
                    option2.add("Churachandpur");
                    option2.add("Imphal East");
                    option2.add("Imphal West");
                    option2.add("Jiribam");
                    option2.add("Kakching");
                    option2.add("Kamjong");
                    option2.add("Kangpokpi");
                    option2.add("Noney");
                    option2.add("Pherzawl");
                    option2.add("Senapati");
                    option2.add("Tamenglong");
                    option2.add("Tengnoupal");
                    option2.add("Thoubal");
                    option2.add("Ukhrul");
                }
                else if(stateName.equals("Meghalaya")) {
                    option2.add("East Garo Hills");
                    option2.add("East Khasi Hills");
                    option2.add("East Jaintia Hills");
                    option2.add("North Garo Hills");
                    option2.add("Ri Bhoi");
                    option2.add("South Garo Hills");
                    option2.add("South West Garo Hills");
                    option2.add("South West Khasi Hills");
                    option2.add("West Jaintia Hills");
                    option2.add("West Garo Hills");
                    option2.add("West Khasi Hills");
                }
                else if(stateName.equals("Mizoram")) {
                    option2.add("Aizawl");
                    option2.add("Champhai");
                    option2.add("Kolasib");
                    option2.add("Lawngtlai");
                    option2.add("Lunglei");
                    option2.add("Mamit");
                    option2.add("Saiha");
                    option2.add("Serchhip");
                }
                else if(stateName.equals("Nagaland")) {
                    option2.add("Dimapur");
                    option2.add("Kiphire");
                    option2.add("Kohima");
                    option2.add("Longleng");
                    option2.add("Mokokchung");
                    option2.add("Mon");
                    option2.add("Noklak");
                    option2.add("Peren");
                    option2.add("Phek");
                    option2.add("Tuensang");
                    option2.add("Wokha");
                    option2.add("Zunheboto");
                }
                else if(stateName.equals("Odisha")) {
                    option2.add("Angul");
                    option2.add("Boudh");
                    option2.add("Bhadrak");
                    option2.add("Balangir");
                    option2.add("Bargarh");
                    option2.add("Balasore");
                    option2.add("Cuttack");
                    option2.add("Debagarh");
                    option2.add("Dhenkanal");
                    option2.add("Ganjam");
                    option2.add("Gajapati");
                    option2.add("Jharsuguda");
                    option2.add("Jajpur");
                    option2.add("Jagatsinghpur");
                    option2.add("Khordha");
                    option2.add("Kendujhar");
                    option2.add("Kalahandi");
                    option2.add("Kandhamal");
                    option2.add("Koraput");
                    option2.add("Kendrapara");
                    option2.add("Malkangiri");
                    option2.add("Mayurbhanj");
                    option2.add("Nabarangpur");
                    option2.add("Nuapada");
                    option2.add("Nayagarh");
                    option2.add("Puri");
                    option2.add("Rayagada");
                    option2.add("Sambalpur");
                    option2.add("Subarnapur");
                    option2.add("Sundargarh");
                }
                else if(stateName.equals("Punjab")) {
                    option2.add("Amritsar");
                    option2.add("\tBarnala");
                    option2.add("Bathinda");
                    option2.add("Firozpur");
                    option2.add("Faridkot");
                    option2.add("Fatehgarh Sahib");
                    option2.add("Fazilka[1");
                    option2.add("Gurdaspur");
                    option2.add("Hoshiarpur");
                    option2.add("Jalandhar");
                    option2.add("Kapurthala");
                    option2.add("Ludhiana");
                    option2.add("Mansa");
                    option2.add("Moga");
                    option2.add("Sri Muktsar Sahib");
                    option2.add("Pathankot");
                    option2.add("Patiala");
                    option2.add("Rupnagar");
                    option2.add("Sahibzada Ajit Singh Nagar");
                    option2.add("Shahid Bhagat Singh Nagar");
                    option2.add("Tarn Taran");

                }
                else if(stateName.equals("Rajasthan")) {
                    option2.add("Ajmer");
                    option2.add("Alwar");
                    option2.add("Bikaner");
                    option2.add("Barmer");
                    option2.add("Banswara");
                    option2.add("Bharatpur");
                    option2.add("Baran");
                    option2.add("Bundi");
                    option2.add("Bhilwara");
                    option2.add("Churu");
                    option2.add("Chittorgarh");
                    option2.add("Dausa");
                    option2.add("Dholpur");
                    option2.add("Dungarpur");
                    option2.add("Ganganagar");
                    option2.add("Hanumangarh");
                    option2.add("Jhunjhunu");
                    option2.add("Jalore");
                    option2.add("Jodhpur");
                    option2.add("Jaipur");
                    option2.add("Jaisalmer");
                    option2.add("Jhalawar");
                    option2.add("Karauli");
                    option2.add("Kota");
                    option2.add("Nagaur");
                    option2.add("Pali");
                    option2.add("Pratapgarh");
                    option2.add("Rajsamand");
                    option2.add("Sikar");
                    option2.add("Sawai Madhopur");
                    option2.add("Sirohi");
                    option2.add("Tonk");
                    option2.add("Udaipur");
                }
                else if(stateName.equals("Sikkim")) {
                    option2.add("East Sikkim");
                    option2.add("North Sikkim");
                    option2.add("South Sikkim");
                    option2.add("West Sikkim");
                }
                else if(stateName.equals("Tamil Nadu")) {
                    option2.add("Ariyalur");
                    option2.add("Chennai");
                    option2.add("Coimbatore");
                    option2.add("Cuddalore");
                    option2.add("Dharmapuri");
                    option2.add("Dindigul");
                    option2.add("Erode");
                    option2.add("Kallakurichi");
                    option2.add("Kanchipuram");
                    option2.add("Kanyakumari");
                    option2.add("Karur");
                    option2.add("Krishnagiri");
                    option2.add("Madurai");
                    option2.add("Nagapattinam");
                    option2.add("Nilgiris");
                    option2.add("Namakkal");
                    option2.add("Perambalur");
                    option2.add("Pudukkottai");
                    option2.add("Ramanathapuram");
                    option2.add("Salem");
                    option2.add("Sivaganga");
                    option2.add("Tirupur");
                    option2.add("Tiruchirappalli");
                    option2.add("Theni");
                    option2.add("Tirunelveli");
                    option2.add("Thanjavur");
                    option2.add("Thoothukudi");
                    option2.add("Tiruvallur");
                    option2.add("Tiruvarur");
                    option2.add("Tiruvannamalai");
                    option2.add("Vellore");
                    option2.add("Viluppuram");
                    option2.add("Virudhunagar");


                }
                else if(stateName.equals("Telangana")) {
                    option2.add("Adilabad");
                    option2.add("Komaram Bheem Asifabad");
                    option2.add("Bhadradri Kothagudem");
                    option2.add("Hyderabad");
                    option2.add("Jagtial");
                    option2.add("Jangaon");
                    option2.add("Jayashankar Bhupalpally");
                    option2.add("Jogulamba Gadwal");
                    option2.add("Kamareddy");
                    option2.add("Karimnagar");
                    option2.add("Khammam");
                    option2.add("Mahabubabad");
                    option2.add("Mahbubnagar");
                    option2.add("Mancherial");
                    option2.add("Medak");
                    option2.add("Medchal-Malkajgiri");
                    option2.add("Mulugu");
                    option2.add("Nalgonda");
                    option2.add("Narayanpet");
                    option2.add("Nagarkurnool");
                    option2.add("Nirmal");
                    option2.add("Nizamabad");
                    option2.add("Peddapalli");
                    option2.add("Rajanna Sircilla");
                    option2.add("Ranga Reddy");
                    option2.add("Sangareddy");
                    option2.add("Siddipet");
                    option2.add("Suryapet");
                    option2.add("Vikarabad");
                    option2.add("Wanaparthy");
                    option2.add("Warangal (urban)");
                    option2.add("Warangal (rural)");
                    option2.add("Yadadri Bhuvanagiri");

                }
                else if(stateName.equals("Tripura ")) {
                    option2.add("Dhalai");
                    option2.add("Gomati");
                    option2.add("Khowai[");
                    option2.add("North Tripura");
                    option2.add("Sepahijala");
                    option2.add("South Tripura");
                    option2.add("Unokoti[");
                    option2.add("West Tripura");
                }
                else if(stateName.equals("Uttar Pradesh")) {
                    option2.add("Agra");
                    option2.add("Aligarh");
                    option2.add("Allahabad");
                    option2.add("Ambedkar Nagar");
                    option2.add("Amethi");
                    option2.add("Amroha");
                    option2.add("Auraiya");
                    option2.add("Azamgarh");
                    option2.add("Bagpat");
                    option2.add("Bahraich");
                    option2.add("Ballia");
                    option2.add("Balrampur");
                    option2.add("Banda");
                    option2.add("Barabanki");
                    option2.add("Bareilly");
                    option2.add("Basti");
                    option2.add("Bhadohi");
                    option2.add("Bijnor");
                    option2.add("Budaun");
                    option2.add("Bulandshahr");
                    option2.add("Chandauli");
                    option2.add("Chitrakoot");
                    option2.add("Deoria");
                    option2.add("Etah");
                    option2.add("Etawah");
                    option2.add("Faizabad");
                    option2.add("Farrukhabad");
                    option2.add("Fatehpur");
                    option2.add("Firozabad");
                    option2.add("Gautam Buddh Nagar");
                    option2.add("Ghaziabad");
                    option2.add("Ghazipur");
                    option2.add("Gonda");
                    option2.add("Gorakhpur");
                    option2.add("Hamirpur");
                    option2.add("Hapur");
                    option2.add("Hardoi");
                    option2.add("Hathras");
                    option2.add("Jalaun");
                    option2.add("Jaunpur");
                    option2.add("Jhansi");
                    option2.add("Kannauj");
                    option2.add("Kanpur Dehat");
                    option2.add("Kanpur Nagar");
                    option2.add("Kasganj");
                    option2.add("Kaushambi");
                    option2.add("Kushinagar");
                    option2.add("Lakhimpur Kheri");
                    option2.add("Lalitpur");
                    option2.add("Lucknow");
                    option2.add("Maharajganj");
                    option2.add("Mahoba");
                    option2.add("Mainpuri");
                    option2.add("Mathura");
                    option2.add("Mau");
                    option2.add("Meerut");
                    option2.add("Mirzapur");
                    option2.add("Moradabad");
                    option2.add("Muzaffarnagar");
                    option2.add("Pilibhit");
                    option2.add("Pratapgarh");
                    option2.add("Raebareli");
                    option2.add("Rampur");
                    option2.add("Saharanpur");
                    option2.add("Sambhal");
                    option2.add("Sant Kabir Nagar");
                    option2.add("Shahjahanpur");
                    option2.add("Shamli");
                    option2.add("Shravasti");
                    option2.add("Siddharthnagar");
                    option2.add("Sitapur");
                    option2.add("Sonbhadra");
                    option2.add("Sultanpur");
                    option2.add("Unnao");
                    option2.add("Varanasi");
                }
                else if(stateName.equals("Uttrakhand")) {
                    option2.add("Almora");
                    option2.add("Bageshwar");
                    option2.add("Chamoli");
                    option2.add("Champawat");
                    option2.add("Dehradun");
                    option2.add("Haridwar");
                    option2.add("Nainital");
                    option2.add("Pauri Garhwal");
                    option2.add("Pithoragarh");
                    option2.add("Rudraprayag");
                    option2.add("Tehri Garhwal");
                    option2.add("Udham Singh Nagar\t");
                    option2.add("Uttarkashi");
                }
                else if(stateName.equals("West Bengal")) {
                    option2.add("Alipurduar");
                    option2.add("Bankura");
                    option2.add("Paschim Bardhaman");
                    option2.add("Purba Bardhaman");
                    option2.add("Birbhum");
                    option2.add("Cooch Behar");
                    option2.add("Dakshin Dinajpur");
                    option2.add("Darjeeling");
                    option2.add("Hooghly");
                    option2.add("Howrah");
                    option2.add("Jalpaiguri");
                    option2.add("Jhargram");
                    option2.add("Kalimpong");
                    option2.add("Kolkata");
                    option2.add("Maldah");
                    option2.add("Murshidabad\t");
                    option2.add("Nadia");
                    option2.add("North 24 Parganas");
                    option2.add("Paschim Medinipur");
                    option2.add("Purba Medinipur");
                    option2.add("Purulia");
                    option2.add("South 24 Parganas");
                    option2.add("Uttar Dinajpur");
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, option2);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(dataAdapter3);



        //Location
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    db = openHelper.getWritableDatabase();
                    String category = msg.getText().toString();
                    String date = get_date.getText().toString();
                    String locations = get_location.getText().toString();
                    String description = get_description.getText().toString();
                    String state = states.getSelectedItem().toString();
                    String districts = district.getSelectedItem().toString();
                  //  String district = district.getSelectedItem().toString();
                    CheckEditTextIsEmptyOrNot(date, description);
                    if (CheckEditTextEmpty == true) {
                        InsertData(category, date, description,state,districts, imageViewToByte(get_upload1), imageViewToByte(get_upload2), imageViewToByte(get_upload3), imageViewToByte(get_upload4), imageViewToByte(get_upload5));
                        Intent intent = new Intent(Form.this, rating.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Form.this, "Please Fill All the information", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.out.println("Exception occured : " + ex);
                }


            }
        });

    } // onCreate;
//district

    public void resetCity (String stateName)
    {

    }


//

//Location

void getLocation() {
    try {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
    }
    catch(SecurityException e) {
        e.printStackTrace();
    }
}

 //   @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            get_location.setText(get_location.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+ addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }

    }

  //  @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Form.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

 //   @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

   // @Override
    public void onProviderEnabled(String provider) {

    }


    //Inserting Data into database - Like INSERT INTO QUERY.
    public void InsertData(String category, String date, String description,String state,String district, byte[] image, byte[] image2, byte[] image3, byte[] image4, byte[] image5) {  //

        ContentValues values = new ContentValues();
    //    if (get_upload2.getDrawable() == null & get_upload3.getDrawable() == null & get_upload4.getDrawable() == null & get_upload5.getDrawable() == null) {
            values.put(DatabaseHelper.category, category);
            values.put(DatabaseHelper.dates, date);
        values.put(DatabaseHelper.description, description);
            values.put(DatabaseHelper.state, state);
            values.put(DatabaseHelper.district, district);
           // values.put(DatabaseHelper.location, location);

            //values.put(DatabaseHelper.image, image);
            if (get_upload2.getDrawable() == null & get_upload3.getDrawable() == null & get_upload4.getDrawable() == null & get_upload5.getDrawable() == null)
                values.put(DatabaseHelper.image1, image);
            else if (get_upload2.getDrawable() != null & get_upload3.getDrawable() == null & get_upload4.getDrawable() == null & get_upload5.getDrawable() == null) {
                values.put(DatabaseHelper.image1, image);
                values.put(DatabaseHelper.image2, image2);
            } else if (get_upload2.getDrawable() != null & get_upload3.getDrawable() != null & get_upload4.getDrawable() == null & get_upload5.getDrawable() == null) {
                values.put(DatabaseHelper.image1, image);
                values.put(DatabaseHelper.image2, image2);
                values.put(DatabaseHelper.image3, image3);
            } else if (get_upload2.getDrawable() != null & get_upload3.getDrawable() != null & get_upload4.getDrawable() != null & get_upload5.getDrawable() == null) {
                values.put(DatabaseHelper.image1, image);
                values.put(DatabaseHelper.image2, image2);
                values.put(DatabaseHelper.image3, image3);
                values.put(DatabaseHelper.image4, image4);
            } else if (get_upload2.getDrawable() != null & get_upload3.getDrawable() != null & get_upload4.getDrawable() != null & get_upload5.getDrawable() != null) {
                values.put(DatabaseHelper.image1, image);
                values.put(DatabaseHelper.image2, image2);
                values.put(DatabaseHelper.image3, image3);
                values.put(DatabaseHelper.image4, image4);
                values.put(DatabaseHelper.image5, image5);

            }

        long id = db.insert(DatabaseHelper.infoTable, null, values);
    }

    public void CheckEditTextIsEmptyOrNot(String date, String description) {
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(description)) {
            CheckEditTextEmpty = false;
        } else {
            CheckEditTextEmpty = true;
        }

    }

// change image from bitmap to byte for storing
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

// Display selected image in form
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
        /*       Uri selectedImage = data.getData();
               String[] filePathColumn = {MediaStore.Images.Media.DATA};
               Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
               cursor.moveToNext();
                   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                   String picturePath = cursor.getString(columnIndex);
            int count = data.getClipData().getItemCount();
            for(int i = 0; i < count; i++){
                Uri imageUri = data.getClipData().getItemAt(i).getUri();

        }

                   ImageView imageView2 = (ImageView) findViewById(R.id.img2);
                   ImageView imageView3 = (ImageView) findViewById(R.id.img3);
                   ImageView imageView4 = (ImageView) findViewById(R.id.img4);
                   ImageView imageView5 = (ImageView) findViewById(R.id.img5);

                   imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                   imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                   imageView3.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                   imageView4.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                   imageView5.setImageBitmap(BitmapFactory.decodeFile(picturePath));
*/          ImageView imageView = (ImageView) findViewById(R.id.img1);
            ImageView imageView2 = (ImageView) findViewById(R.id.img2);
            ImageView imageView3 = (ImageView) findViewById(R.id.img3);
            ImageView imageView4 = (ImageView) findViewById(R.id.img4);
            ImageView imageView5 = (ImageView) findViewById(R.id.img5);
         //   imagePathList = new ArrayList<>();
            ClipData clipData = data.getClipData();
            int count = data.getClipData().getItemCount();
            if(count  == 5 ){
                imageView.setImageURI(clipData.getItemAt(0).getUri());
                imageView2.setImageURI(clipData.getItemAt(1).getUri());
                imageView3.setImageURI(clipData.getItemAt(2).getUri());
                imageView4.setImageURI(clipData.getItemAt(3).getUri());
                imageView5.setImageURI(clipData.getItemAt(4).getUri());
            }
            else if(count  == 4 ){
                imageView.setImageURI(clipData.getItemAt(0).getUri());
                imageView2.setImageURI(clipData.getItemAt(1).getUri());
                imageView3.setImageURI(clipData.getItemAt(2).getUri());
                imageView4.setImageURI(clipData.getItemAt(3).getUri());
            }
            else if(count  == 3 ){
                imageView.setImageURI(clipData.getItemAt(0).getUri());
                imageView2.setImageURI(clipData.getItemAt(1).getUri());
                imageView3.setImageURI(clipData.getItemAt(2).getUri());
            }
            else if(count  == 2 ){
                imageView.setImageURI(clipData.getItemAt(0).getUri());
                imageView2.setImageURI(clipData.getItemAt(1).getUri());
            }
            else if(count  == 1 ){
                imageView.setImageURI(clipData.getItemAt(0).getUri());
            }



    }
    }

    private void getImageFilePath(Uri uri) {
        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];

        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            imagePathList.add(imagePath);
            cursor.close();
        }
    }


    // Date_Picker
    @Override
    public void onClick(View v) {

        if (v == ImageButton) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            get_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}



