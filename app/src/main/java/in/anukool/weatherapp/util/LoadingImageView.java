package in.anukool.weatherapp.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import in.anukool.weatherapp.R;

/**
 * Created by Anukool Srivastav on 16/04/18.
 */
public class LoadingImageView extends AppCompatImageView {

    private static final int ROTATE_ANIMATION_DURATION = 800;
    private static final int IMAGE_RESOURCE_ID = R.drawable.ic_loading;

    public LoadingImageView(Context context) {
        super(context, null);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Set and scale the image
        setImageResource(IMAGE_RESOURCE_ID);

        // Start the animation
        startAnimation();
    }

    /**
     * Starts the rotate animation.
     */
    private void startAnimation() {
        clearAnimation();

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(ROTATE_ANIMATION_DURATION);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        startAnimation(rotate);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
            startAnimation();
        } else {
            clearAnimation();
        }
    }
}
