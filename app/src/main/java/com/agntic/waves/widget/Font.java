package com.agntic.waves.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class Font extends AppCompatTextView {

    Context a_Context, context;

    public Font(Context context) {
        super(context);
        a_Context = context;
        init();
    }

    public Font(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Font(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    SharedPreferences font = PreferenceManager.getDefaultSharedPreferences(getContext());
    int language = font.getInt("font_langu_1", 2);

    private void init() {
        if (language == 1) {
            Typeface Typeface = android.graphics.Typeface.createFromAsset(getContext().getAssets(), "IRANSansMobile.ttf");
            setTypeface(Typeface);
        } else if (language == 2) {
            Typeface Typeface = android.graphics.Typeface.createFromAsset(getContext().getAssets(), "IRANSansMobile.ttf");
            setTypeface(Typeface);
        } else if (language == 3) {
        } else {
            Typeface Typeface = android.graphics.Typeface.createFromAsset(getContext().getAssets(), "IRANSansMobile.ttf");
            setTypeface(Typeface);
        }


    }
}
