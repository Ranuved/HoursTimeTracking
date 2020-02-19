package com.works.admin.hours_timetracking;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Locale;

public class Error extends Activity {
    FontTextView enable;
    private static final int PREFERENCE_PRIVATE_MODE = 0;
    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    String senable = "no";
    String time_zone_enable = "yes";

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

        setContentView(R.layout.error);
        sh_pref = getSharedPreferences("shref", PREFERENCE_PRIVATE_MODE);
        toEdit = sh_pref.edit();
        enable = (FontTextView) findViewById(R.id.enable);
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (Settings.Global.getInt(getContentResolver(), Settings.Global.AUTO_TIME) == 1) {
                // Enabled
                senable = "yes";
                Log.e("Enabled", "yes");
            } else {
                // Disabed
                senable = "no";
                Log.e("Enabled", "no");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
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
        if (senable.equalsIgnoreCase("yes") && time_zone_enable.equalsIgnoreCase("yes")) {
            Intent i = new Intent(Error.this, Dashbord.class);
            startActivity(i);
            finish();
        } else {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Error.this, Dashbord.class);
        startActivity(i);
        finish();
    }
}
