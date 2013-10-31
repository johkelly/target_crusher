package com.example.ReverseShootingGallery;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/30/13
 * Time: 3:27 PM
 */
public class DrawableTarget {
    private int width, height;
    private Point pos;
    private final Drawable drawable;

    public DrawableTarget(int x, int y, int resId, Resources resources) {
        drawable = resources.getDrawable(resId);
        width = drawable.getIntrinsicHeight();
        height = drawable.getIntrinsicHeight();
        pos = new Point(x, y);
    }

    public void draw(Canvas c){
        drawable.setBounds(pos.x - width/2, pos.y - height/2, pos.x + width/2, pos.y + height/2);
        drawable.draw(c);
    }

    public void translate(double dx, double dy){
        pos.x += dx;
        pos.y += dy;
    }

    public void clamp(int maxX, int maxY){
        clamp(0, 0, maxX, maxY);
    }

    public void clamp(int minX, int minY, int maxX, int maxY){
        pos.x = Math.max(minX, Math.min(pos.x, maxX));
        pos.y = Math.max(minY, Math.min(pos.y, maxY));
    }
}
