package com.example.ReverseShootingGallery;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;


/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/30/13
 * Time: 3:27 PM
 */
public class DrawableTarget {
    private static String logstr = DrawableTarget.class.getSimpleName()+".log";

    private int width, height;
    private double xVelocity;
    private double yVelocity;
    private Point pos;
    private final Drawable drawable;

    public DrawableTarget(int x, int y, int resId, Resources resources) {
        drawable = resources.getDrawable(resId);
        width = drawable.getIntrinsicHeight();
        height = drawable.getIntrinsicHeight();
        pos = new Point(x, y);
        xVelocity = 0;
        yVelocity = 0;
    }

    public void draw(Canvas c){
        drawable.setBounds(pos.x - width/2, pos.y - height/2, pos.x + width/2, pos.y + height/2);
        drawable.draw(c);
    }

    public void setVelocity(double vx, double vy){
        Log.d(logstr, "SetVelocity: " + vx + " "  + vy);
        xVelocity = vx;
        yVelocity = vy;
    }

    public void update(){
        Log.d(logstr, "Update; " + xVelocity + " " + yVelocity);
        pos.x += xVelocity;
        pos.y += yVelocity;
    }

    public void clamp(int maxX, int maxY){
        clampPosition(0, 0, maxX, maxY);
    }

    public void clampPosition(int minX, int minY, int maxX, int maxY){
        pos.x = Math.max(minX, Math.min(pos.x, maxX));
        pos.y = Math.max(minY, Math.min(pos.y, maxY));
    }
}
