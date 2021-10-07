package com.agntic.waves;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.agntic.waves.widget.PermissionHandler;
import com.bumptech.glide.Glide;

public class Login extends Activity {

    // Get Save
    SharedPreferences one_play_preferences;
    SharedPreferences.Editor one_play_editor;

    int oneplay, custom;

    String Ipserver, User, Pass, Ipweb, name;

    EditText IPserver, username, password, IPweb, Name, Custom;

    CardView btn_send;
    ImageView loginback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set Layout
        setContentView(R.layout.login);

        try {
            checkPermissions();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            //font
            IPserver = (EditText) findViewById(R.id.IPserver);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);
            IPweb = (EditText) findViewById(R.id.IPweb);
            Name = (EditText) findViewById(R.id.Name);
            Custom = (EditText) findViewById(R.id.custom);
            btn_send = (CardView) findViewById(R.id.btn_send);
            loginback = (ImageView) findViewById(R.id.loginback);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            //Get file font
            Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

            IPserver.setTypeface(typeface);
            username.setTypeface(typeface);
            password.setTypeface(typeface);
            IPweb.setTypeface(typeface);
            Name.setTypeface(typeface);
            Custom.setTypeface(typeface);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            // Get Save
            one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
            one_play_editor = one_play_preferences.edit();
            oneplay = one_play_preferences.getInt("one_play_app", 3);
            Ipserver = one_play_preferences.getString("IPserver", "");
            User = one_play_preferences.getString("username", "");
            Pass = one_play_preferences.getString("password", "");
            Ipweb = one_play_preferences.getString("IPweb", "");
            name = one_play_preferences.getString("NameRoom", "");
            custom = one_play_preferences.getInt("Custom", 1);

            one_play_editor.putBoolean("ONE", false);
            one_play_editor.apply();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            //load Image
            Glide.with(this)
                    .load(getImage("main_bg"))
                    .thumbnail(0.5f)
                    .into(loginback);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Set Record
                    one_play_editor.putString("IPserver", IPserver.getText().toString());
                    one_play_editor.putString("username", username.getText().toString());
                    one_play_editor.putString("password", password.getText().toString());
                    one_play_editor.putString("IPweb", IPweb.getText().toString());
                    one_play_editor.putString("NameRoom", Name.getText().toString());
                    one_play_editor.putString("auth", Custom.getText().toString());
                    one_play_editor.putInt("one_play_app", 4);
                    one_play_editor.apply();
                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }

                try {
                    Intent uou = new Intent(Login.this, Main.class);
                    startActivity(uou);
                    Login.this.finish();
                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
            }
        });

    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

    private void checkPermissions() {

        String[] per = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.RECEIVE_BOOT_COMPLETED, android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.RECEIVE_BOOT_COMPLETED,android.Manifest.permission.FOREGROUND_SERVICE,android.Manifest.permission.WAKE_LOCK,android.Manifest.permission.ACCESS_FINE_LOCATION};

        new PermissionHandler().checkPermission(this, per, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                // permission granted
                // your code
            }

            @Override
            public void onPermissionDenied() {
                // User canceled permission
                Toast.makeText(Login.this, "در صورت نپذیرفتن درخواست ها برنامه با مشکل مواجه می شود!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("permissions", permissions);
        intent.putExtra("grantResults", grantResults);
        sendBroadcast(intent);
    }

}
