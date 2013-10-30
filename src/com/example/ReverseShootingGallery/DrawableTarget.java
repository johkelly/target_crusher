package com.example.ReverseShootingGallery;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/30/13
 * Time: 3:27 PM
 */
public class DrawableTarget {
    protected final Rect drawRect;
    private final Drawable drawable;

    public DrawableTarget(Rect dRect, int resId, Resources resources) {
        drawRect = dRect;
        drawable = resources.getDrawable(resId);
    }

    public void draw(Canvas c){
        drawable.setBounds(drawRect);
        drawable.draw(c);
    }
}
