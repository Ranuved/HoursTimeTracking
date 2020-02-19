package com.works.admin.hours_timetracking;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends Activity {
    FontTextView login, foregt;
    String access_token = "", token_type, expires_at;
    private static final int PREFERENCE_PRIVATE_MODE = 0;
    SharedPreferences sh_pref;
    SharedPreferences.Editor toEdit;
    String str_email, str_password;
    FontEdittext ed_email, ed_password;
    public static String sDefSystemLanguage;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FontTextView go;

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
        Resources res = MainActivity.this.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        setContentView(R.layout.login_activity);
        sh_pref = getSharedPreferences("shref", PREFERENCE_PRIVATE_MODE);
        toEdit = sh_pref.edit();
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        login = (FontTextView) findViewById(R.id.login);
        foregt = (FontTextView) findViewById(R.id.forget);
        ed_email = (FontEdittext) findViewById(R.id.ed_email);
        ed_password = (FontEdittext) findViewById(R.id.ed_password);
        go = (FontTextView) findViewById(R.id.go);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        foregt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Webview_fassai.class);
                i.putExtra("type", "1");
                startActivity(i);
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Webview_fassai.class);
                i.putExtra("type", "2");
                startActivity(i);
            }
        });
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = df.format(c);
        Log.e("formattedDate", formattedDate);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        ed_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (ed_email.getText().toString().matches(emailPattern) && s.length() > 0) {
                    ed_email.setTextColor(Color.parseColor("#FFFFFF"));

                } else {
                    ed_email.setTextColor(Color.parseColor("#FE0000"));

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        Time time = new Time();
        time.setToNow();
        Log.e("isInternetPresent11", isInternetPresent.toString());
        Log.e("TIME TEST", Long.toString(time.toMillis(false)));
        if (!sh_pref.getString("access_token", "").equalsIgnoreCase("")) {
            if (!isInternetPresent) {
                Intent intent = new Intent(MainActivity.this, No_internet.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, Dashbord.class);
                startActivity(intent);
                finish();
            }

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInternetPresent = cd.isConnectingToInternet();
                Log.e("isInternetPresent22", isInternetPresent.toString());
                str_email = ed_email.getText().toString();
                str_password = ed_password.getText().toString();
                if (str_email.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), MainActivity.this.getString(R.string.Pleaseenteremailid), Toast.LENGTH_SHORT).show();
                } else if (str_password.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), MainActivity.this.getString(R.string.Pleaseenterpassword), Toast.LENGTH_SHORT).show();
                } else if (!ed_email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(), MainActivity.this.getString(R.string.Invalidemail), Toast.LENGTH_SHORT).show();
                } else {
                    if (isInternetPresent) {
                        Load l = new Load();
                        l.execute();
                    } else {
                        Toast.makeText(getApplicationContext(), MainActivity.this.getString(R.string.check), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

    }

    private void exit() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.NewDialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_login_failed);


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
                dialog.dismiss();

                toEdit.putString("access_token", "");
                toEdit.commit();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();


    }

    public class Load extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        HashMap<String, String> localArrayList;

        public Load() {
        }

        @TargetApi(Build.VERSION_CODES.FROYO)
        @SuppressWarnings({})
        protected Void doInBackground(Void... params) {
            localArrayList = new HashMap<String, String>();
            localArrayList.put("email", str_email);
            localArrayList.put("password", str_password);
            /*localArrayList.put("email", "hourslogintest@gmail.com");
            localArrayList.put("password", "HoursApp");*/
            String str = new Jsonparsor()
                    .getJSONFromUrl("https://app.hours.fi/api/authenticate",
                            localArrayList);
            Log.e("authenticate", str + localArrayList.toString());

            try {
                JSONObject jsonObject = new JSONObject(str);
                access_token = jsonObject.getString("access_token");
                token_type = jsonObject.getString("token_type");
                expires_at = jsonObject.getString("expires_at");

            } catch (Exception e) {

            }


            return null;
        }


        protected void onPostExecute(Void paramVoid) {
            super.onPostExecute(paramVoid);
            if (!access_token.equalsIgnoreCase("")) {
                toEdit.putString("access_token", access_token);
                toEdit.putString("expires_at", expires_at);
                toEdit.commit();
                Intent intent = new Intent(MainActivity.this, Dashbord.class);
                startActivity(intent);
                finish();
            } else {
                exit();
                // Toast.makeText(MainActivity.this, "Failed to login!! Please try again", Toast.LENGTH_LONG).show();
            }
            if (this.progressDialog.isShowing())
                this.progressDialog.dismiss();


        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(MainActivity.this);
            this.progressDialog.setMessage(MainActivity.this.getString(R.string.Loading));
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

    }
}
