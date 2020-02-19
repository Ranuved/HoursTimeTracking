package com.works.admin.hours_timetracking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

public class No_internet extends Activity {
    FontTextView start;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private static final int PREFERENCE_PRIVATE_MODE = 0;
    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    boolean run = true;
    Handler handler1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

        setContentView(R.layout.inernet_connection);
        sh_pref = getSharedPreferences("shref", PREFERENCE_PRIVATE_MODE);
        toEdit = sh_pref.edit();
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        start = (FontTextView) findViewById(R.id.start);
        handler1 = new Handler();
        handler1.postDelayed(runnable1, 60);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetPresent = cd.isConnectingToInternet();
                if (!sh_pref.getString("access_token", "").equalsIgnoreCase("")) {
                    if (!isInternetPresent) {
                    } else {
                        Intent intent = new Intent(No_internet.this, Dashbord.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler1.postDelayed(runnable1, 0);
    }

    public Runnable runnable1 = new Runnable() {

        public void run() {
            // Log.e("three","yes");

            if (run == true) {
                isInternetPresent = cd.isConnectingToInternet();
                Log.e("isInternetPresent", isInternetPresent.toString());
                if (!isInternetPresent) {
                       /* Intent intent = new Intent(No_internet.this, No_internet.class);
                        startActivity(intent);
                        finish();*/
                } else {
                    run = false;
                    Intent intent = new Intent(No_internet.this, Dashbord.class);
                    startActivity(intent);
                    finish();
                }

                handler1.postDelayed(this, 60);

            }
        }
    };


}
