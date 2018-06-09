package in.anukool.weatherapp.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class CustomTextView extends AppCompatTextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontCache.setCustomFont(this, context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FontCache.setCustomFont(this, context, attrs);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }

    public void setFontStyle(String fontName){
        FontCache.setCustomFont(this, fontName, getContext());
    }

}

