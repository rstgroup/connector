package com.rstit.connector.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Tomasz Trybala
 * @since 2017-08-01
 */

public class ChatUtil {
    public static void expandView(@NonNull View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);

            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(widthSpec, heightSpec);

            ValueAnimator mAnimator = slideAnimator(view, 0, view.getMeasuredHeight());
            mAnimator.start();
        }
    }

    public static void collapseView(@NonNull final View view) {
        if (view.getVisibility() != View.GONE) {
            int finalHeight = view.getHeight();
            ValueAnimator mAnimator = slideAnimator(view, finalHeight, 0);
            mAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            mAnimator.start();
        }
    }

    private static @NonNull ValueAnimator slideAnimator(@NonNull View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(valueAnimator -> {
            int value = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.height = value;
            v.setLayoutParams(layoutParams);
        });
        return animator;
    }
}
