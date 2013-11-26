/**
 * Description: Drawable and mobile game element for the GameplayView
 * @author John Kelly, Zach Fleischman
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class DrawableTarget {

    private final Drawable drawable;
    private final Drawable explosion;

    private final static double VEL_SCALE = 3.0;

    private int width, height;

    private double xVelocity;
    private double yVelocity;
    private double posX;
    private double posY;

    private boolean exploding = false;

    private double collRadius;

    private boolean noisey;
    private double xNoise;
    private double yNoise;

    private double DRAW_SCALE = 1.0;

    public DrawableTarget(int x, int y, int resId, Resources resources, double drawScale, double collRadius) {
        drawable = resources.getDrawable(resId);
        explosion = resources.getDrawable(R.drawable.splosion);
        width = (int) (drawable.getIntrinsicHeight() * drawScale);
        height = (int) (drawable.getIntrinsicHeight() * drawScale);
        posX = x;
        posY = y;
        xVelocity = 0;
        yVelocity = 0;
        noisey = false;
        xNoise = 0;
        yNoise = 0;
        this.collRadius = collRadius;
    }

    public void setExploding(boolean e) {
        exploding = e;
    }

    public void setNoisy(boolean n) {
        noisey = n;
    }

    public void draw(Canvas c) {
        int l = (int) (posX - width / 2);
        int t = (int) (posY - height / 2);
        int r = (int) (posX + width / 2);
        int b = (int) (posY + height / 2);
        if (exploding) {
            explosion.setBounds(l, t, r, b);
            explosion.draw(c);
        } else {
            drawable.setBounds(l, t, r, b);
            drawable.draw(c);
        }
    }

    /**
     * Set the velocity of this object, adjusting by some noisiness
     *
     * @param vx Expected x velocity
     * @param vy Expected y velocity
     */
    public void setVelocity(double vx, double vy) {
        Random r = new Random();
        double noiseWidth = 2.5;
        xNoise = Math.max(-1.0 * noiseWidth, Math.min(noiseWidth, xNoise + -1 + r.nextDouble() * 2));
        yNoise = Math.max(-5.0, Math.min(5.0, yNoise + -1 + r.nextDouble() * 2));
        if (noisey) {
            xVelocity = VEL_SCALE * vx + xNoise;
            yVelocity = VEL_SCALE * vy + yNoise;
        } else {
            xVelocity = VEL_SCALE * (vx);
            yVelocity = VEL_SCALE * (vy);
        }
    }

    public void setPosition(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        if (!exploding) {
            posX += xVelocity;
            posY += yVelocity;
        }
    }

    public void clamp(int maxX, int maxY) {
        clampPosition(0, 0, maxX, maxY);
    }

    /**
     * Clamp this object to be entirely inside the specified bounding box.
     *
     * @param minX Left side of bounding box
     * @param minY Top side of bounding box
     * @param maxX Right side of bounding box
     * @param maxY Bottom side of bounding box
     */
    public void clampPosition(int minX, int minY, int maxX, int maxY) {
        posX = Math.max(minX + width / 2, Math.min(posX, maxX - width / 2));
        posY = Math.max(minY + height / 2, Math.min(posY, maxY - height / 2));
    }

    /**
     * Calculate how far this object's center is from another's
     *
     * @param other Other object to compare centers to
     * @return The distance between the centers of the this object and {@code other}
     */
    public double distanceToCenterOf(DrawableTarget other) {
        return Math.sqrt(Math.pow(posX - other.posX, 2) + Math.pow(posY - other.posY, 2));
    }

    /**
     * Get a scaled radius around this object's center within which it is considered to be colliding with any point.
     *
     * @return The collision radius, scaled by this object's current scaling factor.
     */
    public double getCollRadius() {
        return collRadius * DRAW_SCALE;
    }

    /**
     * Set this object's draw scale and intermediary measurements
     *
     * @param scale Scale to use; 1.0 = natural size
     */
    public void setScale(double scale) {
        DRAW_SCALE = scale;
        width = (int) (drawable.getIntrinsicHeight() * DRAW_SCALE);
        height = (int) (drawable.getIntrinsicHeight() * DRAW_SCALE);
    }
}
