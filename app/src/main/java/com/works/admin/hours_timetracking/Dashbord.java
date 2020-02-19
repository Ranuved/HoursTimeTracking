package com.works.admin.hours_timetracking;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Dashbord extends FragmentActivity implements OnMapReadyCallback {
    RelativeLayout logout;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private static final int PREFERENCE_PRIVATE_MODE = 0;
    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    String text = "";
    String jsonResponse = "";
    ProgressDialog progressDialog;
    DynamicWidthSpinner spn_type;
    ArrayList<Country_type> list_type = new ArrayList<Country_type>();
    String final_type_name, final_type_id = "0", jsonResponse_user = "", jsonResponse_hours = "";
    FontTextView name;
    String u_name, u_email, user_id;
    String strrt_timestamp, end_timestamp, minutes = "0";
    int total_minuts = 0;
    int tota_hrs = 0;
    FontTextView total_week, time1, time2, start_btn;
    Handler handler, handler1;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    int Seconds, Minutes, MilliSeconds;
    final int sdk = Build.VERSION.SDK_INT;
    FontTextView one, two, three, four, five, six, seven;
    int year1, month1, day1, year2, month2, day2, year3, month3, day3, year4, day4, month4, year5, month5, day5, year6, month6, day6, year7, month7, day7;
    String d_one, d_two, d_three, d_four, d_five, d_six, d_seven, jsonResponse_start = "";
    ProgressBar progress1, progress2, progress3, progress4, progress5, progress6, progress7;
    String lat = " ", longt = " ", jsonResponse_end = "";
    String timestamp;
    List<Integer> list = new ArrayList<Integer>();
    List<Integer> l = new ArrayList<Integer>();
    int big, new_big;
    String make = "";
    LinearLayout lsp;
    FontTextView f_type;
    String expires_at = "";
    Handler mHandler = new Handler();
    Handler mHandler2 = new Handler();
    String start_date = "", end_date = "";
    long seconds1;
    long minutes1;
    long hours1;
    long diff;
    String jsonResponse_status = "";
    String today_minuts = "";
    String todayString;
    int today_min = 0;
    Runnable myRunnable;
    ScrollView scrl;
    //add this boolean
    boolean run = true;
    ImageView img;
    float x, x1, y;
    String status_startTime, status_workType, status_workTypeId;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String my_timestamp = "0", gps_timestamp = "0";
    RelativeLayout maply;
    FontTextView setting;
    String coordinates = "", server_time = "", jsonResponse_time = "";
    String currentDateandTime, system_utc_time;
    long different;
    String current_utc_time;
    String enable = "no";
    String time_zone_enable = "yes";

    private void Error() {
        final Dialog dialog = new Dialog(Dashbord.this, R.style.NewDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_error);


        dialog.setCanceledOnTouchOutside(true);
        FontTextView trya = (FontTextView) dialog.findViewById(R.id.trya);
        FontTextView yes = (FontTextView) dialog.findViewById(R.id.yes);

        trya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onStart() {
        super.onStart();
        String language = Locale.getDefault().getDisplayLanguage();
        Log.e("language", language);
        if (language.equalsIgnoreCase("eesti")) {
            language = "et";
        } else {
            language = "en";
        }
        getDaysOfWeek();

        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
        lat = sh_pref.getString("lat", "");
        longt = sh_pref.getString("long", "");

        //get autotime
        try {
            if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 1) {
                // Enabled
                enable = "yes";
                Log.e("Enabled", "yes");
            } else {
                // Disabed
                enable = "no";
                Log.e("Enabled", "no");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //get autotimezone
        try {
            if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME_ZONE) == 1) {
                // Enabled
                time_zone_enable = "yes";
                Log.e("Enabledtimezone", "yes");
            } else {
                // Disabed
                time_zone_enable = "no";
                Log.e("Enabledtimezone", "no");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        mHandler2 = new Handler();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("refresh", "yes");
                        if (googleMap != null && currentLocation != null)
                            animateCamera(currentLocation);

                    }
                });
            }
        }, 0, 10000);

        if (!isInternetPresent) {
            Intent intent = new Intent(Dashbord.this, No_internet.class);
            startActivity(intent);
            finish();
        } else {
            logout = (RelativeLayout) findViewById(R.id.logout);
            name = (FontTextView) findViewById(R.id.name);
            spn_type = (DynamicWidthSpinner) findViewById(R.id.spn_type);
            total_week = (FontTextView) findViewById(R.id.total_week);
            time1 = (FontTextView) findViewById(R.id.timeone);
            time2 = (FontTextView) findViewById(R.id.timetwo);
            start_btn = (FontTextView) findViewById(R.id.start);
            one = (FontTextView) findViewById(R.id.one);
            two = (FontTextView) findViewById(R.id.two);
            three = (FontTextView) findViewById(R.id.three);
            four = (FontTextView) findViewById(R.id.four);
            five = (FontTextView) findViewById(R.id.five);
            six = (FontTextView) findViewById(R.id.six);
            seven = (FontTextView) findViewById(R.id.seven);
            scrl = (ScrollView) findViewById(R.id.scrl);
            maply = (RelativeLayout) findViewById(R.id.maply);
            setting = (FontTextView) findViewById(R.id.setting);

            progress1 = (ProgressBar) findViewById(R.id.p1);
            progress2 = (ProgressBar) findViewById(R.id.p2);
            progress3 = (ProgressBar) findViewById(R.id.p3);
            progress4 = (ProgressBar) findViewById(R.id.p4);
            progress5 = (ProgressBar) findViewById(R.id.p5);
            progress6 = (ProgressBar) findViewById(R.id.p6);
            progress7 = (ProgressBar) findViewById(R.id.p7);

            lsp = (LinearLayout) findViewById(R.id.lsp);
            lsp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spn_type.performClick();
                }
            });

            f_type = (FontTextView) findViewById(R.id.type);
            img = (ImageView) findViewById(R.id.img);

            handler = new Handler();
            handler1 = new Handler();
            mHandler2 = new Handler();


            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatterr = new SimpleDateFormat("yyyy-MM-dd");
            todayString = formatterr.format(todayDate);
            Log.e("todayString", todayString);

            Date date2 = new Date();
            String strDateFormat = "HH:mm:ss";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            end_date = dateFormat.format(date2);
            Log.e("end_date", end_date);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spn_type.performClick();
                    if (spn_type.getSelectedItem() == null) { // user selected nothing...
                        spn_type.performClick();
                    }
                }
            });

            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    finish();
                }
            });


            Load_status1 k = new Load_status1();
            k.execute();

            start_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lat = sh_pref.getString("lat", "");
                    longt = sh_pref.getString("long", "");
                    run = true;
                    if (enable.equalsIgnoreCase("yes") && time_zone_enable.equalsIgnoreCase("yes")) {
                        // timestamp
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        currentDateandTime = sdf.format(new Date());
                        Log.e("currentDateandTime", currentDateandTime);
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        f.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Log.e("currentDateandTime1", f.format(new Date()));
                        system_utc_time = f.format(new Date());
                        String dd = f.format(new Date());
                        Log.e("dd", dd);


                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        df.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date daten = null;
                        try {
                            daten = df.parse(dd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        df.setTimeZone(TimeZone.getDefault());
                        String formattedDate = df.format(daten);

                        Log.e("after_formattedDate", formattedDate);

//timestamp
                        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Timestamp ts = new Timestamp(((Date) df1.parse(df.format(new Date()))).getTime());
                            Log.e("newtime", String.valueOf(ts.getTime()));
                            timestamp = String.valueOf(ts.getTime() / 1000);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (start_btn.getText().toString().equalsIgnoreCase(Dashbord.this.getString(R.string.START))) {

                            if (final_type_id.equalsIgnoreCase("0")) {
                                Toast.makeText(Dashbord.this, Dashbord.this.getString(R.string.Please), Toast.LENGTH_SHORT).show();
                            } else {
                                isInternetPresent = cd.isConnectingToInternet();
                                if (isInternetPresent) {
                                    Load_status s = new Load_status();
                                    s.execute();
                                } else {
                                    Error();
                                }


                            }

                        } else {

                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                if (lat.equalsIgnoreCase(" ")) {
                                    coordinates = "";
                                } else {
                                    coordinates = lat + "," + longt;
                                }
                                Load_work_end e = new Load_work_end();
                                e.execute();
                            } else {
                                Error();
                            }


                        }

                    } else {
                        Intent i = new Intent(Dashbord.this, Error.class);
                        startActivity(i);
                        finish();

                    }


                }
            });

            Calendar c1 = GregorianCalendar.getInstance();
            //first day of week
            c1.set(Calendar.DAY_OF_WEEK, 1);
            c1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            year1 = c1.get(Calendar.YEAR);
            month1 = c1.get(Calendar.MONTH) + 1;
            day1 = c1.get(Calendar.DAY_OF_MONTH);
            Log.e("day1", String.valueOf(day1));


            DecimalFormat formatter1 = new DecimalFormat("00");
            String aFormatted1 = formatter1.format(month1);
            String aFormatted11 = formatter1.format(day1);
            if (aFormatted11.equalsIgnoreCase("32")) {
                aFormatted11 = "01";
                aFormatted1 = formatter1.format(month1 + 1);
            }

            //2nd day of week
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.DAY_OF_WEEK, 2);
            year2 = c2.get(Calendar.YEAR);
            month2 = c2.get(Calendar.MONTH) + 1;
            day2 = c2.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter2 = new DecimalFormat("00");
            String aFormatted2 = formatter2.format(month2);
            String aFormatted21 = formatter2.format(day2);
            if (aFormatted21.equalsIgnoreCase("32")) {
                aFormatted21 = "01";
                aFormatted2 = formatter2.format(month2 + 1);
            }


            //3rd day of week
            Calendar c3 = Calendar.getInstance();
            c3.set(Calendar.DAY_OF_WEEK, 3);
            year3 = c3.get(Calendar.YEAR);
            month3 = c3.get(Calendar.MONTH) + 1;
            day3 = c3.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter3 = new DecimalFormat("00");
            String aFormatted3 = formatter3.format(month3);
            String aFormatted31 = formatter3.format(day3);


            //4th day of week
            Calendar c4 = Calendar.getInstance();
            c4.set(Calendar.DAY_OF_WEEK, 4);
            year4 = c4.get(Calendar.YEAR);
            month4 = c4.get(Calendar.MONTH) + 1;
            day4 = c4.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter4 = new DecimalFormat("00");
            String aFormatted4 = formatter4.format(month4);
            String aFormatted41 = formatter4.format(day4);


            //5th day of week
            Calendar c5 = Calendar.getInstance();
            c5.set(Calendar.DAY_OF_WEEK, 5);
            year5 = c5.get(Calendar.YEAR);
            month5 = c5.get(Calendar.MONTH) + 1;
            day5 = c5.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter5 = new DecimalFormat("00");
            String aFormatted5 = formatter5.format(month5);
            String aFormatted51 = formatter5.format(day5);


            //6th day of week
            Calendar c6 = Calendar.getInstance();
            c6.set(Calendar.DAY_OF_WEEK, 6);
            year6 = c6.get(Calendar.YEAR);
            month6 = c6.get(Calendar.MONTH) + 1;
            day6 = c6.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter6 = new DecimalFormat("00");
            String aFormatted6 = formatter6.format(month6);
            String aFormatted61 = formatter6.format(day6);


            //last day of week
            Calendar c7 = Calendar.getInstance();
            c7.set(Calendar.DAY_OF_WEEK, 7);
            year7 = c7.get(Calendar.YEAR);
            month7 = c7.get(Calendar.MONTH) + 1;
            day7 = c7.get(Calendar.DAY_OF_MONTH) + 1;

            DecimalFormat formatter7 = new DecimalFormat("00");
            String aFormatted7 = formatter7.format(month7);
            String aFormatted71 = formatter7.format(day7);

            list_type.clear();
            list_type.add(new Country_type("0", Dashbord.this.getString(R.string.Chooseworktype)));

            Load_worktypes l = new Load_worktypes();
            l.execute();

            CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                    R.layout.spinner_layout_night, list_type);
            adapter.setDropDownViewResource(R.layout.spinner_layout_night);
            spn_type.setAdapter(adapter);
            spn_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final_type_name = list_type.get(position).getName();
                    final_type_id = list_type.get(position).getId();
                    Log.e("final_type_id", final_type_id);
                    toEdit.putString("final_type_name", final_type_name);
                    toEdit.commit();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            Load_user_detail u = new Load_user_detail();
            u.execute();

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exit();

                }
            });
        }
    }

    public void printDifference(Date startDate, Date endDate) {
        //milliseconds
        different = startDate.getTime() - endDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);
        Log.e("different", String.valueOf(different));
        /*long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);*/
    }

    private void exit() {
        final Dialog dialog = new Dialog(Dashbord.this, R.style.NewDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_logout);
        dialog.setCanceledOnTouchOutside(true);
        FontTextView no = (FontTextView) dialog.findViewById(R.id.no);
        FontTextView yes = (FontTextView) dialog.findViewById(R.id.yes);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                toEdit.putString("access_token", "");
                toEdit.commit();
                Intent i = new Intent(Dashbord.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        String language = Locale.getDefault().getDisplayLanguage();
        Log.e("language", language);
        if (language.equalsIgnoreCase("eesti")) {
            language = "et";
        } else {
            language = "en";
        }
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.dashbord_test);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        sh_pref = getSharedPreferences("shref", PREFERENCE_PRIVATE_MODE);
        toEdit = sh_pref.edit();


    }

    private void differ() {
        Log.e("start_end_date", start_date + "//" + end_date);
//HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(start_date);
            d2 = format.parse(end_date);
            Log.e("d1,d2", d1 + "/" + d2);
            //in milliseconds
            diff = d2.getTime() - d1.getTime();
            Log.e("diff", String.valueOf(diff));
            long diffHours = diff / (60 * 60 * 1000) % 24;
            Log.e("test", diffHours + " hours, ");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void get_current_date() {
        Date date2 = new Date();
        String strDateFormat = "HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date2);
        Log.e("start_date", formattedDate);
        toEdit.putString("start_date", formattedDate);
        toEdit.commit();
        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
    }


    public Runnable runnable = new Runnable() {
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            int h = (int) (UpdateTime / 3600000);
            int m = (int) (UpdateTime - h * 3600000) / 60000;
            int s = (int) (UpdateTime - h * 3600000 - m * 60000) / 1000;
            String t1 = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m);
            String t2 = String.valueOf(s < 10 ? "0" + s : s);
            time1.setText(t1);
            time2.setText(t2);
            handler.postDelayed(this, 0);
        }

    };

    public Runnable runnable1 = new Runnable() {

        public void run() {
            // Log.e("three","yes");

            if (run == true) {


                //  Log.e("runnable", "call");

                if (today_minuts.equalsIgnoreCase("")) {
                    today_min = 0;

                } else {
                    today_min = Integer.parseInt(today_minuts);
                }

                String t1 = time1.getText().toString();
                String t2 = time2.getText().toString();
                // Log.e("t1111", t1 + "/" + t2);

                String[] separated = t1.split(":");
                String hh = separated[0]; // this will contain "Fruit"
                String mm = separated[1];
                int mns = Integer.parseInt(hh) * 60;
                int r = mns + Integer.parseInt(mm);
                mns = mns + Integer.parseInt(mm);

                // Log.e("rrrr", String.valueOf(r));
                Calendar calendar = Calendar.getInstance();
                int day22 = calendar.get(Calendar.DAY_OF_WEEK);

           /* Log.e("t111", t1);
            Log.e("mns", String.valueOf(mns));
            Log.e("day22", String.valueOf(day22));
            Log.e("t22", t2);*/

                new_big();

                if (day22 == 2) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    one.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    //  Log.e("go11", String.valueOf(mns));
                    //  float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));
                    // int p = Math.round(y);
                    /*if (y > 0.0 && y < 1.0) {
                        //Log.e("if", "yes");
                        progress1.setProgress(7);
                    } else {
                        // Log.e("else", "yes");

                        progress1.setProgress(p);
                    }*/
                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    //  Log.e("y6", String.valueOf(y));
                    int p = Math.round(y);
                    progress1.setProgress(p);

                    //  progress2.setProgress(p);
                    one.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    //  progress1.startAnimation(slide_up);

                } else if (day22 == 3) {
                    mns = mns + today_min;
                    //Log.e("go22", String.valueOf(mns));
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    two.setText(a + ":" + b);

                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    //float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));
                    // int p = Math.round(y);
                   /* if (y > 0.0 && y < 1.0) {
                        progress2.setProgress(7);
                    } else {
                        progress2.setProgress(p);
                    }*/
                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    // Log.e("y6", String.valueOf(y));
                    int p = Math.round(y);
                    progress2.setProgress(p);

                    //  progress2.setProgress(p);
                    two.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    //  progress2.startAnimation(slide_up);


                } else if (day22 == 4) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    three.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    // float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));

                   /* if (y > 0.0 && y < 1.0) {
                        Log.e("if", "yes");
                        progress3.setProgress(7);
                    } else {
                        Log.e("else", "yes");

                        progress3.setProgress(p);
                    }*/

                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    int p = Math.round(y);
                    //  Log.e("y6", String.valueOf(y));

                    progress3.setProgress(p);

                    three.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    // progress3.startAnimation(slide_up);

                } else if (day22 == 5) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    four.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    // float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));

                    //Log.e("ppppp", String.valueOf(p));
                   /* if (y > 0.0 && y < 1.0) {
                        // Log.e("if", "yes");
                        progress4.setProgress(7);
                    } else {
                        // Log.e("else", "yes");

                        progress4.setProgress(p);
                    }*/
                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    // Log.e("y6", String.valueOf(y));
                    int p = Math.round(y);
                    progress4.setProgress(p);

                    four.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    // progress4.startAnimation(slide_up);

                } else if (day22 == 6) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    five.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));
                    //int p = Math.round(y);
                    /*if (y > 0.0 && y < 1.0) {
                        // Log.e("if", "yes");
                        progress5.setProgress(7);
                    } else {
                        // Log.e("else", "yes");

                        progress5.setProgress(p);
                    }*/
                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    // Log.e("y6", String.valueOf(y));
                    int p = Math.round(y);
                    progress5.setProgress(p);


                    five.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    //  progress5.startAnimation(slide_up);

                } else if (day22 == 7) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));
                    six.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    // float y = (float) (h * (2.5));
                    // Log.e("yyyy2", String.valueOf(y));

//                    if (y > 0.0 && y < 1.0) {
//                        //Log.e("if", "yes");
//                        progress6.setProgress(7);
//                    } else {
//                        // Log.e("else", "yes");
//
//                        progress6.setProgress(p);
//                    }

                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    int p = Math.round(y);
                    // Log.e("y6", String.valueOf(y));
                    progress6.setProgress(p);
                    six.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    //  progress6.startAnimation(slide_up);

                } else if (day22 == 8) {
                    mns = mns + today_min;
                    int hours = mns / 60; //since both are ints, you get an int
                    int minutes = mns % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));
                    seven.setText(a + ":" + b);
                    float h = Float.parseFloat(String.valueOf(mns)) / 60;
                    // float y = (float) (h * (2.5));
                    // int p = Math.round(y);
                    // Log.e("yyyy2", String.valueOf(y));

                    /*if (y > 0.0 && y < 1.0) {
                        // Log.e("if", "yes");
                        progress7.setProgress(7);
                    } else {
                        // Log.e("else", "yes");

                        progress7.setProgress(p);
                    }
*/
                    y = (float) (mns / 100.0);
                    y = (float) ((y / x1) * 100.0);
                    // Log.e("y6", String.valueOf(y));
                    int p = Math.round(y);
                    progress7.setProgress(p);

                    seven.setTextColor(Color.parseColor("#ffffff"));
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out_up);

                    // progress7.startAnimation(slide_up);

                } else {

                }
                if (big < mns) {
                    make = todayString;
                }


                if (make.equalsIgnoreCase(d_one)) {
                    progress1.setProgress(100);
                } else if (make.equalsIgnoreCase(d_two)) {
                    progress2.setProgress(100);
                } else if (make.equalsIgnoreCase(d_three)) {
                    progress3.setProgress(100);
                } else if (make.equalsIgnoreCase(d_four)) {
                    progress4.setProgress(100);
                } else if (make.equalsIgnoreCase(d_five)) {
                    progress5.setProgress(100);
                } else if (make.equalsIgnoreCase(d_six)) {
                    progress6.setProgress(100);
                } else if (make.equalsIgnoreCase(d_seven)) {
                    progress7.setProgress(100);
                }


                // Log.e("aaanu", String.valueOf(total_minuts));
                int new_total_minuts = total_minuts + r;
                int hours = new_total_minuts / 60; //since both are ints, you get an int
                int minutes = new_total_minuts % 60;
                System.out.printf("%d:%02d", hours, minutes);
                String a = (String.format("%d", hours));
                String b = (String.format("%02d", minutes));

                total_week.setText(Dashbord.this.getString(R.string.WeekTotal) + ": " + a + ":" + b);
                handler1.postDelayed(this, 60);

            }
        }
    };

    private void new_big() {
        String one_ = one.getText().toString();
        String[] separated1 = one_.split(":");
        String hh1 = separated1[0]; // this will contain "Fruit"
        String mm1 = separated1[1];
        int mns1 = Integer.parseInt(hh1) * 60;
        mns1 = mns1 + Integer.parseInt(mm1);
        // Log.e("mns", String.valueOf(mns1));
        l.add(mns1);

        String two_ = two.getText().toString();
        String[] separated2 = two_.split(":");
        String hh2 = separated2[0]; // this will contain "Fruit"
        String mm2 = separated2[1];
        int mns2 = Integer.parseInt(hh2) * 60;
        mns2 = mns2 + Integer.parseInt(mm2);
        //  Log.e("mns2", String.valueOf(mns2));
        l.add(mns2);

        String three_ = three.getText().toString();
        String[] separated3 = three_.split(":");
        String hh3 = separated3[0]; // this will contain "Fruit"
        String mm3 = separated3[1];
        int mns3 = Integer.parseInt(hh3) * 60;
        mns3 = mns3 + Integer.parseInt(mm3);
        // Log.e("mns2", String.valueOf(mns3));
        l.add(mns3);

        String four_ = four.getText().toString();
        String[] separated4 = four_.split(":");
        String hh4 = separated4[0]; // this will contain "Fruit"
        String mm4 = separated4[1];
        int mns4 = Integer.parseInt(hh4) * 60;
        mns4 = mns4 + Integer.parseInt(mm4);
        // Log.e("mns4", String.valueOf(mns4));
        l.add(mns4);

        String five_ = five.getText().toString();
        String[] separated5 = five_.split(":");
        String hh5 = separated5[0]; // this will contain "Fruit"
        String mm5 = separated5[1];
        int mns5 = Integer.parseInt(hh5) * 60;
        mns5 = mns5 + Integer.parseInt(mm5);
        //  Log.e("mns5", String.valueOf(mns5));
        l.add(mns5);

        String six_ = six.getText().toString();
        String[] separated6 = six_.split(":");
        String hh6 = separated6[0]; // this will contain "Fruit"
        String mm6 = separated6[1];
        int mns6 = Integer.parseInt(hh6) * 60;
        mns6 = mns6 + Integer.parseInt(mm6);
        //  Log.e("mns6", String.valueOf(mns6));
        l.add(mns6);

        String seven_ = seven.getText().toString();
        String[] separated7 = seven_.split(":");
        String hh7 = separated7[0]; // this will contain "Fruit"
        String mm7 = separated7[1];
        int mns7 = Integer.parseInt(hh7) * 60;
        mns7 = mns6 + Integer.parseInt(mm7);
        // Log.e("mns7", String.valueOf(mns7));
        l.add(mns7);

        if (l.size() <= 0) {
        } else {
            new_big = Collections.max(l);


        }
        x1 = (float) (new_big / 100.0);
        // Log.e("new_big1", String.valueOf(new_big));
        // Log.e("x1_neew", String.valueOf(x1));

        float y1 = (float) (mns1 / 100.0);
        y1 = (float) ((y1 / x1) * 100.0);
        int p = Math.round(y1);
        progress1.setProgress(p);
        // one.setTextColor(Color.parseColor("#ffffff"));

        float y2 = (float) (mns2 / 100.0);
        y2 = (float) ((y2 / x1) * 100.0);
        int p2 = Math.round(y2);
        progress2.setProgress(p2);

        float y3 = (float) (mns3 / 100.0);
        y3 = (float) ((y3 / x1) * 100.0);
        int p3 = Math.round(y3);
        progress3.setProgress(p3);

        float y4 = (float) (mns4 / 100.0);
        y4 = (float) ((y4 / x1) * 100.0);
        int p4 = Math.round(y4);
        progress4.setProgress(p4);

        float y5 = (float) (mns5 / 100.0);
        y5 = (float) ((y5 / x1) * 100.0);
        int p5 = Math.round(y5);
        progress5.setProgress(p5);

        float y6 = (float) (mns6 / 100.0);
        y6 = (float) ((y6 / x1) * 100.0);
        int p6 = Math.round(y6);
        progress6.setProgress(p6);

        float y7 = (float) (mns7 / 100.0);
        y7 = (float) ((y7 / x1) * 100.0);
        int p7 = Math.round(y7);
        progress7.setProgress(p7);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.postDelayed(runnable, 0);
        handler1.postDelayed(runnable1, 0);
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(Dashbord.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Dashbord.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(Dashbord.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(Dashbord.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void animateCamera(@NonNull Location location) {

        toEdit.putString("lat", String.valueOf(location.getLatitude()));
        toEdit.putString("long", String.valueOf(location.getLongitude()));
        toEdit.commit();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
        showMarker(location);
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        toEdit.putString("lat", String.valueOf(latLng.latitude));
        toEdit.putString("long", String.valueOf(latLng.longitude));
        toEdit.commit();
        if (currentLocationMarker == null)
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
        currentLocationMarker.setTitle("My Location");

    }


    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.e("comemap", "1");
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            toEdit.putString("lat", String.valueOf(currentLocation.getLatitude()));
            toEdit.putString("long", String.valueOf(currentLocation.getLongitude()));
            toEdit.commit();
            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            showMarker(currentLocation);
        }
    };

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Dashbord.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Dashbord.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        /*if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }*/
                        // mMap.setMyLocationEnabled(true);
                        maply.setVisibility(View.GONE);
                    }
                } else {
                    maply.setVisibility(View.VISIBLE);
                    Toast.makeText(Dashbord.this, Dashbord.this.getString(R.string.permissiondenied),
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public class Load_worktypes extends AsyncTask<String, Void, String> {
        public Load_worktypes() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected String doInBackground(String... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/worktypes");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("GET");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                int responseCode = myURLConnection.getResponseCode();
                // Log.e("responseCode1111", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse = IOUtils.toString(inputStr, encoding);
                    //  Log.e("jsonResponse", jsonResponse);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {
            // progressDialog.dismiss();
            try {
                if (jsonResponse.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), Dashbord.this.getString(R.string.failedgetwork), Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    String work_types = jsonObject.getString("work_types");

                    JSONArray jsonArray = new JSONArray(work_types);
                    //    Log.e("jsonArray", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String id = j.getString("id");
                        String name = j.getString("name");
                        //   Log.e("name", name);
                        list_type.add(new Country_type(id, name));


                    }

                }


            } catch (Exception e) {
                // TODO: handle exception
            }

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* progressDialog = new ProgressDialog(Dashbord.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/


        }
    }

    public class Load_status extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = null;

        public Load_status() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected String doInBackground(String... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/status");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("GET");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                int responseCode = myURLConnection.getResponseCode();
                // Log.e("responseCode1111", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_status = IOUtils.toString(inputStr, encoding);
                    Log.e("jsonResponse_status", jsonResponse_status);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {
            // progressDialog.dismiss();
            try {
                if (jsonResponse_status.equalsIgnoreCase("")) {
                    // Toast.makeText(getApplicationContext(), "User already working", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject = new JSONObject(jsonResponse_status);
                    String working_status = jsonObject.getString("working");
                    Log.e("working_status", working_status);
                    if (working_status.equalsIgnoreCase("false")) {
                        lsp.setVisibility(View.GONE);
                        f_type.setVisibility(View.VISIBLE);
                        f_type.setText(sh_pref.getString("final_type_name", ""));
                        time1.setText("00:00");
                        time2.setText("00");
                        Log.e("latlong", lat + "/123" + longt);
                        if (lat.equalsIgnoreCase(" ")) {
                            coordinates = "";
                        } else {
                            coordinates = lat + "," + longt;
                        }
                        Load_work_start l = new Load_work_start();
                        l.execute();


                    } else {
                        Toast.makeText(getApplicationContext(), Dashbord.this.getString(R.string.Useralreadyworking), Toast.LENGTH_LONG).show();
                    }
                }


            } catch (Exception e) {
                // TODO: handle exception
            }
            if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Dashbord.this);
            progressDialog.setMessage(Dashbord.this.getString(R.string.Loading));
            progressDialog.setCancelable(false);
            progressDialog.show();


        }
    }

    public class Load_status1 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = null;

        public Load_status1() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected String doInBackground(String... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/status");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("GET");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                int responseCode = myURLConnection.getResponseCode();
                // Log.e("responseCode1111", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_status = IOUtils.toString(inputStr, encoding);
                    Log.e("jsonResponse_status", jsonResponse_status);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {
            // progressDialog.dismiss();
            try {
                if (jsonResponse_status.equalsIgnoreCase("")) {
                    // Toast.makeText(getApplicationContext(), "User already working", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject jsonObject = new JSONObject(jsonResponse_status);
                    String working_status = jsonObject.getString("working");
                    status_startTime = jsonObject.getString("startTime");
                    status_workType = jsonObject.getString("workType");
                    status_workTypeId = jsonObject.getString("workTypeId");
                    Log.e("working_status", working_status);
                    Log.e("status_startTime", status_startTime);
                    if (working_status.equalsIgnoreCase("true")) {
                        start_btn.setText(Dashbord.this.getString(R.string.STOP));
                        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                            start_btn.setBackgroundDrawable(ContextCompat.getDrawable(Dashbord.this, R.drawable.red_btn));

                        } else {
                            start_btn.setBackground(ContextCompat.getDrawable(Dashbord.this, R.drawable.red_btn));

                        }
                        final_type_id = status_workTypeId;
                        lsp.setVisibility(View.GONE);
                        f_type.setVisibility(View.VISIBLE);
                        f_type.setText(status_workType);

                        Log.e("status_startTime", status_startTime);
                        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.ENGLISH);
                        df.setTimeZone(TimeZone.getTimeZone("Israel"));
                        Date date = df.parse(status_startTime);
                        df.setTimeZone(TimeZone.getDefault());
                        String formattedDate = df.format(date);
                        Log.e("new_time", formattedDate);
                        String a[] = formattedDate.split(" ");
                        String time = a[1];
                        Log.e("Split_time", time);
                        start_date = time;
                        differ();
                        StartTime = SystemClock.uptimeMillis() - diff;

                        handler.postDelayed(runnable, 0);
                        tota_hrs = 0;
                        total_minuts = 0;
                        Load_hours1 l = new Load_hours1();
                        l.execute();
                        Log.e("  today_minuts", today_minuts);

                        running_status();
                    } else {
                        total_minuts = 0;
                        tota_hrs = 0;
                        Load_hours b = new Load_hours();
                        b.execute();
                        time1.setText("00:00");
                        time2.setText("00");
                        start_btn.setText(Dashbord.this.getString(R.string.START));
                        lsp.setVisibility(View.VISIBLE);
                        f_type.setVisibility(View.GONE);
                        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                            start_btn.setBackgroundDrawable(ContextCompat.getDrawable(Dashbord.this, R.drawable.green_btn));

                        } else {
                            start_btn.setBackground(ContextCompat.getDrawable(Dashbord.this, R.drawable.green_btn));

                        }
                        // Toast.makeText(getApplicationContext(), "User already working", Toast.LENGTH_LONG).show();
                    }
                }


            } catch (Exception e) {
                // TODO: handle exception
            }
            if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Dashbord.this);
            progressDialog.setMessage(Dashbord.this.getString(R.string.Loading));
            progressDialog.setCancelable(false);
            progressDialog.show();


        }
    }

    private void running_status() {
        handler1.postDelayed(runnable1, 60);
    }


    public class Load_work_start extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load_work_start() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/start?workType=" + final_type_id + "&timestamp=" + timestamp + "&coordinates=" + coordinates);
                Log.e("myURL", myURL.toString());
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("POST");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("workType", final_type_id));
                params1.add(new BasicNameValuePair("timestamp", timestamp));
                params1.add(new BasicNameValuePair("coordinates", lat + "," + longt));
                //  Log.e("param", params1.toString());

                OutputStream os = myURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params1));
                writer.flush();
                writer.close();
                os.close();

                myURLConnection.connect();

                int responseCode = myURLConnection.getResponseCode();
                //  Log.e("responseCodehours", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_start = IOUtils.toString(inputStr, encoding);
                    Log.e("jsonResponse_start", jsonResponse_start);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            try {
                JSONObject jsonObject1 = new JSONObject(jsonResponse_start);
                String errors = jsonObject1.getString("errors");
                errors = errors.replace("[", "");
                errors = errors.replace("]", "");
                List<String> items = Arrays.asList(errors.split("\\s*,\\s*"));
                Log.e("items", items.toString());
                Log.e("items_size", String.valueOf(items.size()));
                if (items.size() == 1 && "".equals(items.get(0))) {
                    // perform work
                    Log.e("if", "yes");
                    toEdit.putString("start", "yes");
                    toEdit.putString("t1", "00:00");
                    toEdit.putString("t2", "00");
                    toEdit.commit();
                    start_btn.setText(Dashbord.this.getString(R.string.STOP));
                    if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                        start_btn.setBackgroundDrawable(ContextCompat.getDrawable(Dashbord.this, R.drawable.red_btn));

                    } else {
                        start_btn.setBackground(ContextCompat.getDrawable(Dashbord.this, R.drawable.red_btn));

                    }

                    get_current_date();
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    tota_hrs = 0;
                    total_minuts = 0;
                    Load_hours1 n = new Load_hours1();
                    n.execute();
                    running_status();
                } else {
                    Log.e("else", "yes");
                    Toast.makeText(getApplicationContext(), Dashbord.this.getString(R.string.UnabletotartworkPleasetryagainlater), Toast.LENGTH_LONG).show();

                }
            } catch (
                    JSONException e)

            {
                e.printStackTrace();
            }
            /*if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();*/


        }

        protected void onPreExecute() {
            super.onPreExecute();
           /* this.progressDialog = new ProgressDialog(Dashbord.this);
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();*/
        }

    }

    public class Load_work_end extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load_work_end() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/end?timestamp=" + timestamp + "&coordinates=" + coordinates);
                //  Log.e("myURL", myURL.toString());
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("POST");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                int responseCode = myURLConnection.getResponseCode();
                // Log.e("jsonResponse_end123", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_end = IOUtils.toString(inputStr, encoding);
                    Log.e("jsonResponse_end", jsonResponse_end);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!jsonResponse_end.equalsIgnoreCase("")) {
                JSONObject jsonObject1 = null;
                try {
                    jsonObject1 = new JSONObject(jsonResponse_end);
                    String errors = jsonObject1.getString("errors");
                    errors = errors.replace("[", "");
                    errors = errors.replace("]", "");
                    List<String> items = Arrays.asList(errors.split("\\s*,\\s*"));
                    Log.e("items", items.toString());
                    Log.e("items_size", String.valueOf(items.size()));
                    if (items.size() == 1 && "".equals(items.get(0))) {
                        run = false;
                        toEdit.putString("t1", "00:00");
                        toEdit.putString("t2", "00");
                        toEdit.putString("start", "no");
                        toEdit.commit();
                        time1.setText("00:00");
                        time2.setText("00");
                        start_btn.setText(Dashbord.this.getString(R.string.START));
                        lsp.setVisibility(View.VISIBLE);
                        f_type.setVisibility(View.GONE);
                        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                            start_btn.setBackgroundDrawable(ContextCompat.getDrawable(Dashbord.this, R.drawable.green_btn));

                        } else {
                            start_btn.setBackground(ContextCompat.getDrawable(Dashbord.this, R.drawable.green_btn));

                        }
                        // stopService(new Intent(Dashbord.this, HelloService.class));
                        handler.removeCallbacksAndMessages(null);
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.removeCallbacks(runnable1);
                        tota_hrs = 0;
                        total_minuts = 0;
                        Load_hours h = new Load_hours();
                        h.execute();
                    } else {
                        Toast.makeText(getApplicationContext(), Dashbord.this.getString(R.string.UnabletoendworkPleasetryagainlater), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }

            if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();


        }

        protected void onPreExecute() {
            super.onPreExecute();

            this.progressDialog = new ProgressDialog(Dashbord.this);
            this.progressDialog.setMessage(Dashbord.this.getString(R.string.Loading));
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

    }

    public class Load_user_detail extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load_user_detail() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("GET");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                int responseCode = myURLConnection.getResponseCode();
                //   Log.e("responseCode1111", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_user = IOUtils.toString(inputStr, encoding);
                    // Log.e("jsonResponse_user", jsonResponse_user);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!jsonResponse_user.equalsIgnoreCase("")) {
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonResponse_user);
                    String user_detail = jsonObject1.getString("user");
                    JSONObject j = new JSONObject(user_detail);
                    user_id = j.getString("id");
                    u_name = j.getString("name");
                    u_email = j.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                toEdit.putString("u_name", u_name);
                toEdit.commit();
                name.setText(u_name);

            } else {
                //Toast.makeText(MainActivity.this, "Failed to login!! Please try again", Toast.LENGTH_LONG).show();
            }
          /*  if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();*/


        }

        protected void onPreExecute() {
            super.onPreExecute();
            /*this.progressDialog = new ProgressDialog(Dashbord.this);
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();*/
        }

    }

    public class Load_hours extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load_hours() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/hours");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("POST");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("Start", strrt_timestamp));
                params1.add(new BasicNameValuePair("End", end_timestamp));


                OutputStream os = myURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params1));
                writer.flush();
                writer.close();
                os.close();

                myURLConnection.connect();

                int responseCode = myURLConnection.getResponseCode();
                Log.e("responseCodetotalhours", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_hours = IOUtils.toString(inputStr, encoding);
                    Log.e("responseCodetotalhours", jsonResponse_hours);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!jsonResponse_hours.equalsIgnoreCase("")) {
                my_checks();


                try {
                    JSONObject jsonObject1 = new JSONObject(jsonResponse_hours);
                    String workingHours = jsonObject1.getString("workingHours");
                    JSONArray jsonArray = new JSONArray(workingHours);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        minutes = j.getString("minutes");
                        String hours = j.getString("hours");
                        list.add(Integer.parseInt(minutes));
                        String[] separated = hours.split(":");
                        String r1 = separated[0]; // this will contain "Fruit"
                        String r2 = separated[1];
                        tota_hrs = Integer.parseInt(r1) + tota_hrs;
                        total_minuts = Integer.parseInt(minutes) + total_minuts;
                        String date = j.getString("date");
                        Log.e("ddd", date);
                        Log.e("ddd1", d_one + d_two + d_three + d_four + d_five + d_six + d_seven);
                        if (date.equalsIgnoreCase(d_one)) {
                            one.setText(hours);
                           /* float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            //  Log.e("yyyy1", String.valueOf(y));
                            int p = Math.round(y);
                            // progress1.setProgress(p);

                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress1.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress1.setProgress(p);
                            }

                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y1", String.valueOf(y));
*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress1.setProgress(p);
                            one.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            if (date.equalsIgnoreCase(todayString)) {
                                progress1.startAnimation(slide_up);
                            }

                        } else if (date.equalsIgnoreCase(d_two)) {

                            two.setText(hours);
                          /*  float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy2", String.valueOf(y));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress2.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress2.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            //  Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress2.setProgress(p);

                            //  progress2.setProgress(p);
                            two.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress2.startAnimation(slide_up);
                            }
                        } else if (date.equalsIgnoreCase(d_three)) {

                            three.setText(hours);
                          /*  float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            Log.e("yyyy3", String.valueOf(h));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress3.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress3.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            //   Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress3.setProgress(p);
                            three.setTextColor(Color.parseColor("#ffffff"));

                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress3.startAnimation(slide_up);
                            }
                        } else if (date.equalsIgnoreCase(d_four)) {

                            four.setText(hours);
                            /*float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy4", String.valueOf(y));
                            int p = Math.round(y);
                            // progress4.setProgress(p);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress4.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress4.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y4", String.valueOf(y));
                            int p = Math.round(y);
                            progress4.setProgress(p);

                            four.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress4.startAnimation(slide_up);
                            }
                        } else if (date.equalsIgnoreCase(d_five)) {
                            // Log.e("comming", "ys");
                            five.setText(hours);
                           /* float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy5", String.valueOf(y));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress5.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress5.setProgress(p);
                            }*/

                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            //  Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress5.setProgress(p);
                            //progress5.setProgress(p);
                            five.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress5.startAnimation(slide_up);
                            }
                        } else if (date.equalsIgnoreCase(d_six)) {
                            //  Log.e("comming", "ys");
                            six.setText(hours);
                          /*  float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            //  Log.e("yyyy6", String.valueOf(y));
                            int p = Math.round(y);
                            //  progress6.setProgress(p);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress6.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress6.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            //  Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress6.setProgress(p);
                            six.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress6.startAnimation(slide_up);
                            }
                        } else if (date.equalsIgnoreCase(d_seven)) {

                            seven.setText(hours);
                         /*   float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy7", String.valueOf(y));
                            int p = Math.round(y);

                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress7.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress7.setProgress(p);
                            }*/

                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            //  Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress7.setProgress(p);

                            seven.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);
                            if (date.equalsIgnoreCase(todayString)) {
                                progress7.startAnimation(slide_up);
                            }
                        } else {

                        }


                    }
                    if (list.size() <= 0) {
                    } else {
                        big = Collections.max(list);


                    }

                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject j = jsonArray.getJSONObject(k);
                        String m = j.getString("minutes");
                        String date = j.getString("date");
                        int y = Integer.parseInt(m);
                        if (big == y) {
                            if (m.equalsIgnoreCase("0")) {

                            } else {
                                make = date;
                            }


                        }

                    }
                    if (make.equalsIgnoreCase(d_one)) {
                        progress1.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_two)) {
                        progress2.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_three)) {
                        progress3.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_four)) {
                        progress4.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_five)) {
                        progress5.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_six)) {
                        progress6.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_seven)) {
                        progress7.setProgress(100);
                    }

                   /* Log.e("rvvvvvvvvvv", String.valueOf(big));
                    Log.e("tota_hrs", String.valueOf(tota_hrs));
                    Log.e("total_minuts", String.valueOf(total_minuts));*/
                    int hours = total_minuts / 60; //since both are ints, you get an int
                    int minutes = total_minuts % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    total_week.setText(Dashbord.this.getString(R.string.WeekTotal) + ": " + a + ":" + b);
                  /*  if (total_minuts >= 60) {
                        tota_hrs = tota_hrs + 1;
                        total_minuts = total_minuts - 60;
                        total_week.setText("Week Total: " + tota_hrs + ":" + total_minuts);
                    } else {
                        total_week.setText("Week Total: " + tota_hrs + ":" + total_minuts);
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //Toast.makeText(MainActivity.this, "Failed to login!! Please try again", Toast.LENGTH_LONG).show();
            }
           /* if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();
*/

        }

        protected void onPreExecute() {
            super.onPreExecute();
           /* this.progressDialog = new ProgressDialog(Dashbord.this);
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();*/
        }

    }

    private void my_checks() {
        try {
            JSONObject jsonObject1 = new JSONObject(jsonResponse_hours);
            String workingHours = jsonObject1.getString("workingHours");
            JSONArray jsonArray = new JSONArray(workingHours);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                String minutes = j.getString("minutes");
                list.add(Integer.parseInt(minutes));
            }
            if (list.size() <= 0) {
            } else {
                new_big = Collections.max(list);


            }
            x1 = (float) (new_big / 100.0);
            // Log.e("new_big", String.valueOf(new_big));
            // Log.e("x1", String.valueOf(x1));

        } catch (Exception e) {

        }
    }

    public class Load_hours1 extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load_hours1() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            URL myURL = null;
            try {
                myURL = new URL("https://app.hours.fi/api/user/hours");
                HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
                String basicAuth = "Bearer" + " " + sh_pref.getString("access_token", "");
                myURLConnection.setRequestProperty("Authorization", basicAuth);
                myURLConnection.setRequestMethod("POST");//  change as your requested method
                myURLConnection.setRequestProperty("Content-Type", "application/json");
                myURLConnection.setRequestProperty("Content-Language", "en-US");
                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("Start", strrt_timestamp));
                params1.add(new BasicNameValuePair("End", end_timestamp));


                OutputStream os = myURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params1));
                writer.flush();
                writer.close();
                os.close();

                myURLConnection.connect();

                int responseCode = myURLConnection.getResponseCode();
                Log.e("responseCodetotalhours", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream inputStr = myURLConnection.getInputStream();
                    String encoding = myURLConnection.getContentEncoding() == null ? "UTF-8"
                            : myURLConnection.getContentEncoding();
                    jsonResponse_hours = IOUtils.toString(inputStr, encoding);
                    Log.e("responseCodetotalhours", jsonResponse_hours);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!jsonResponse_hours.equalsIgnoreCase("")) {
                my_checks();
                try {
                    JSONObject jsonObject1 = new JSONObject(jsonResponse_hours);
                    String workingHours = jsonObject1.getString("workingHours");
                    JSONArray jsonArray = new JSONArray(workingHours);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        minutes = j.getString("minutes");
                        String hours = j.getString("hours");
                        list.add(Integer.parseInt(minutes));
                        String[] separated = hours.split(":");
                        String r1 = separated[0]; // this will contain "Fruit"
                        String r2 = separated[1];
                        tota_hrs = Integer.parseInt(r1) + tota_hrs;
                        total_minuts = Integer.parseInt(minutes) + total_minuts;
                        String date = j.getString("date");
                        Log.e("ddd", date);
                        // Log.e("ddd1", d_one + d_two + d_three + d_four + d_five + d_six + d_seven);

                        if (todayString.equalsIgnoreCase(date)) {
                            Log.e("yessss", "gooo");
                            today_minuts = minutes;
                            Log.e("minutes", minutes);
                            Log.e("today_minuts", today_minuts);
                        } else {
                            Log.e("noooo", "gooo");
                        }
                        if (date.equalsIgnoreCase(d_one)) {

                            one.setText(hours);
                            /*float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            //  Log.e("yyyy1", String.valueOf(y));
                            int p = Math.round(y);
                            // progress1.setProgress(p);

                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress1.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress1.setProgress(p);
                            }
*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress1.setProgress(p);

                            one.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            //  progress1.startAnimation(slide_up);
                        } else if (date.equalsIgnoreCase(d_two)) {

                            two.setText(hours);
                           /* float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy2", String.valueOf(y));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress2.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress2.setProgress(p);
                            }
*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress2.setProgress(p);
                            //  progress2.setProgress(p);
                            two.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            // progress2.startAnimation(slide_up);
                        } else if (date.equalsIgnoreCase(d_three)) {

                            three.setText(hours);
                            /*float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            Log.e("yyyy3", String.valueOf(h));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress3.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress3.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress3.setProgress(p);
                            three.setTextColor(Color.parseColor("#ffffff"));

                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            // progress3.startAnimation(slide_up);

                        } else if (date.equalsIgnoreCase(d_four)) {
                            four.setText(hours);
                            /*float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy4", String.valueOf(y));
                            int p = Math.round(y);
                            // progress4.setProgress(p);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress4.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress4.setProgress(p);
                            }
*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress4.setProgress(p);

                            four.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            // progress4.startAnimation(slide_up);
                        } else if (date.equalsIgnoreCase(d_five)) {
                            five.setText(hours);
                            /*float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy5", String.valueOf(y));
                            int p = Math.round(y);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress5.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress5.setProgress(p);
                            }
*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6222", String.valueOf(y));
                            int p = Math.round(y);
                            progress5.setProgress(p);

                            //progress5.setProgress(p);
                            five.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            // progress5.startAnimation(slide_up);
                        } else if (date.equalsIgnoreCase(d_six)) {
                            six.setText(hours);
                          /*  float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            //  Log.e("yyyy6", String.valueOf(y));
                            int p = Math.round(y);
                            //  progress6.setProgress(p);
                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress6.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress6.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress6.setProgress(p);

                            six.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            //   progress6.startAnimation(slide_up);
                        } else if (date.equalsIgnoreCase(d_seven)) {
                            seven.setText(hours);
                          /*  float h = Float.parseFloat(minutes) / 60;
                            float y = (float) (h * (2.5));
                            // Log.e("yyyy7", String.valueOf(y));
                            int p = Math.round(y);

                            if (y > 0.0 && y < 1.0) {
                                Log.e("if", "yes");
                                progress7.setProgress(7);
                            } else {
                                Log.e("else", "yes");

                                progress7.setProgress(p);
                            }*/
                            y = (float) (Float.parseFloat(minutes) / 100.0);
                            y = (float) ((y / x1) * 100.0);
                            Log.e("y6", String.valueOf(y));
                            int p = Math.round(y);
                            progress7.setProgress(p);

                            //progress7.setProgress(p);
                            seven.setTextColor(Color.parseColor("#ffffff"));
                            Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.slide_out_up);

                            //   progress7.startAnimation(slide_up);
                        } else {

                        }


                    }
                    if (list.size() <= 0) {
                    } else {
                        big = Collections.max(list);


                    }

                    for (int k = 0; k < jsonArray.length(); k++) {
                        JSONObject j = jsonArray.getJSONObject(k);
                        String m = j.getString("minutes");
                        String date = j.getString("date");
                        int y = Integer.parseInt(m);
                        if (big == y) {
                            if (m.equalsIgnoreCase("0")) {

                            } else {
                                make = date;
                            }


                        }

                    }
                    if (make.equalsIgnoreCase(d_one)) {
                        progress1.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_two)) {
                        progress2.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_three)) {
                        progress3.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_four)) {
                        progress4.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_five)) {
                        progress5.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_six)) {
                        progress6.setProgress(100);
                    } else if (make.equalsIgnoreCase(d_seven)) {
                        progress7.setProgress(100);
                    }

                   /* Log.e("rvvvvvvvvvv", String.valueOf(big));
                    Log.e("tota_hrs", String.valueOf(tota_hrs));
                    Log.e("total_minuts", String.valueOf(total_minuts));*/
                    int hours = total_minuts / 60; //since both are ints, you get an int
                    int minutes = total_minuts % 60;
                    System.out.printf("%d:%02d", hours, minutes);
                    String a = (String.format("%d", hours));
                    String b = (String.format("%02d", minutes));

                    total_week.setText(Dashbord.this.getString(R.string.WeekTotal) + ": " + a + ":" + b);
                  /*  if (total_minuts >= 60) {
                        tota_hrs = tota_hrs + 1;
                        total_minuts = total_minuts - 60;
                        total_week.setText("Week Total: " + tota_hrs + ":" + total_minuts);
                    } else {
                        total_week.setText("Week Total: " + tota_hrs + ":" + total_minuts);
                    }*/


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                //Toast.makeText(MainActivity.this, "Failed to login!! Please try again", Toast.LENGTH_LONG).show();
            }
           /* if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();
*/

        }

        protected void onPreExecute() {
            super.onPreExecute();
           /* this.progressDialog = new ProgressDialog(Dashbord.this);
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();*/
        }

    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public void getDaysOfWeek() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 7; i++) {
            Log.e("system", df.format(c.getTime()));
            System.out.println(df.format(c.getTime()));
            if (i == 0) {
                d_one = df.format(c.getTime());

            }
            if (i == 1) {
                d_two = df.format(c.getTime());

            }
            if (i == 2) {
                d_three = df.format(c.getTime());

            }
            if (i == 3) {
                d_four = df.format(c.getTime());

            }
            if (i == 4) {
                d_five = df.format(c.getTime());

            }
            if (i == 5) {
                d_six = df.format(c.getTime());

            }
            if (i == 6) {
                d_seven = df.format(c.getTime());

            }
            c.add(Calendar.DATE, 1);
        }


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(d_one);
            strrt_timestamp = String.valueOf(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        DateFormat formatter11 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = (Date) formatter11.parse(d_seven);
            end_timestamp = String.valueOf(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
