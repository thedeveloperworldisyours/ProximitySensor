package com.thedeveloperworldisyours.proximitysensor.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by javierg on 21/02/2017.
 */

public class ButtonFont extends Button {
    public ButtonFont(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ButtonFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/beach.ttf", context);
        setTypeface(customFont);
    }

}
