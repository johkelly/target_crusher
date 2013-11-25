package edu.mines.zfjk.ReverseShootingGallery.filters;

public class AccelLowPassFilter {
    float filterX, filterY;
    static float alpha = .1f;

    public void put(float x, float y) {
        filterX = filterX + alpha * (x - filterX);
        filterY = filterY + alpha * (y - filterY);
    }

    public float getX() {
        return filterX;
    }

    public float getY() {
        return filterY;
    }
}
