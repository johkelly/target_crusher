/**
 * Description: Accumulates 2D input values and attenuates out high-frequency variations
 * @author John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery.filters;

public class AccelLowPassFilter {
    float filterX, filterY;
    static float alpha = .1f;

    /**
     * Adds the 2D data point (x,y) to the low pass filter
     * @param x x-value of the data
     * @param y y-value of the data
     */
    public void put(float x, float y) {
        filterX = filterX + alpha * (x - filterX);
        filterY = filterY + alpha * (y - filterY);
    }

    /**
     * Filtered X getter.
     * @return X value of accumulated points, filtered of high-frequency variations
     */
    public float getX() {
        return filterX;
    }


    /**
     * Filtered Y getter.
     * @return Y value of accumulated points, filtered of high-frequency variations
     */
    public float getY() {
        return filterY;
    }
}
