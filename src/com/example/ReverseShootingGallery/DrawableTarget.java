package com.example.ReverseShootingGallery;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;


/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/30/13
 * Time: 3:27 PM
 */
public class DrawableTarget {
    private static String logstr = DrawableTarget.class.getSimpleName() + ".log";

    private int width, height;
    private double xVelocity;
    private double yVelocity;
    private double posX;
    private double posY;
    private final Drawable drawable;
    private double collRadius;

    public double velScale = 3.0;
    public double drawScale = 1.0;

    public DrawableTarget(int x, int y, int resId, Resources resources, double drawScale, double collRadius) {
        drawable = resources.getDrawable(resId);
        width = (int) (drawable.getIntrinsicHeight() * drawScale);
        height = (int) (drawable.getIntrinsicHeight() * drawScale);
        posX = x;
        posY = y;
        xVelocity = 0;
        yVelocity = 0;
        this.collRadius = collRadius;
    }

    public void draw(Canvas c) {
        int l = (int) (posX - width / 2);
        int t = (int) (posY - height / 2);
        int r = (int) (posX + width / 2);
        int b = (int) (posY + height / 2);
        drawable.setBounds(l, t, r, b);
        drawable.draw(c);
    }

    public void setVelocity(double vx, double vy) {
        Log.d(logstr, "SetVelocity: " + vx + " " + vy);
        xVelocity = velScale * vx;
        yVelocity = velScale * vy;
    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        Log.d(logstr, "Update; " + xVelocity + " " + yVelocity);
        posX += xVelocity;
        posY += yVelocity;
    }

    public void clamp(int maxX, int maxY) {
        clampPosition(0, 0, maxX, maxY);
    }

    public void clampPosition(int minX, int minY, int maxX, int maxY) {
        posX = Math.max(minX + width / 2, Math.min(posX, maxX - width / 2));
        posY = Math.max(minY + height / 2, Math.min(posY, maxY - height / 2));
    }

    public double distanceToCenterOf(DrawableTarget other) {
        return Math.sqrt(Math.pow(posX - other.posX, 2) + Math.pow(posY - other.posY, 2));
    }

    public double getCollRadius() {
        return collRadius * drawScale;
    }
}
