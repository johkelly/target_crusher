package com.example.ReverseShootingGallery.filters;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/23/13
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccelLowPassFilter {
    float filterX, filterY;
    static float alpha = .1f;

    public void put(float x, float y){
        filterX = filterX + alpha * (x - filterX);
        filterY = filterY + alpha * (y - filterY);
    }

    public float getX(){
        return  filterX;
    }
    public float getY(){
        return filterY;
    }
}
