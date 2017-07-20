package com.rstit.connector.util;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Tomasz Trybala
 * @since 2017-07-20
 */
@Keep
public class MoveUpBehavior extends CoordinatorLayout.Behavior<View> {
    public MoveUpBehavior() {
        super();
    }

    public MoveUpBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@Nullable CoordinatorLayout parent, @Nullable View child, @Nullable View dependency) {
        if (dependency != null && child != null) {
            float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(translationY);
        }

        return true;
    }

    public void onDependentViewRemoved(@Nullable CoordinatorLayout parent, @Nullable View child, @Nullable View dependency) {
        if (child != null) {
            ViewCompat.animate(child).translationY(0).start();
        }
    }
}
