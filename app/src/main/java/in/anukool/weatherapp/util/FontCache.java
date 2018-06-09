package in.anukool.weatherapp.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;

import in.anukool.weatherapp.R;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(Context context, String fontName) {
        Typeface typeface;
        if (fontCache.containsKey(fontName)) {
            typeface = fontCache.get(fontName);
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            fontCache.put(fontName, typeface);
        }
        return typeface;
    }

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + name + ".otf");
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

    /**
     * Sets a font on a textview based on the custom com.my.package:font attribute
     * If the custom font attribute isn't found in the attributes nothing happens
     *
     * @param textview
     * @param context
     * @param attrs
     */
    public static void setCustomFont(TextView textview, Context context, AttributeSet attrs) {
        String fontName = context.getString(R.string.font_regular);
        TypedArray a = null;
        if (attrs != null) {
            a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            if (a.hasValue(R.styleable.CustomTextView_fontName)) {
                fontName = a.getString(R.styleable.CustomTextView_fontName);
            }
        }
        if (!textview.isInEditMode()) {
            if (fontName != null) {
                setCustomFont(textview, fontName, context);
            }
        }
        if (a != null) {
            a.recycle();
        }
    }


    /**
     * Sets a font on a textview
     *
     * @param textview
     * @param font
     * @param context
     */
    public static void setCustomFont(TextView textview, String font, Context context) {
        if (font == null) {
            return;
        }
        Typeface tf = FontCache.getTypeface(context, font);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }

}

