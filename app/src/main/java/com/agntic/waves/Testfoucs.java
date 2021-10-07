package com.agntic.waves;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Testfoucs extends Activity{
    LinearLayout esm,ramz;
    ImageView stream,mainbackfilm;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start activ
        setContentView(R.layout.testfoucs);

         esm = findViewById(R.id.esm);
        ramz = findViewById(R.id.ramz);
         stream = findViewById(R.id.stream);
         mainbackfilm = findViewById(R.id.mainbackfilm);

        mainbackfilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        esm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ramz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ramz.setFocusable(false);
        esm.setFocusable(false);

    }

}
