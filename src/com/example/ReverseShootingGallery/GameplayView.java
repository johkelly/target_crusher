package com.example.ReverseShootingGallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 10/30/13
 * Time: 3:31 PM
 */
public class GameplayView extends View {
    private DrawableTarget target;

    public GameplayView(Context context) {
        super(context);
        target = new DrawableTarget(new Rect(10, 10, 170, 170), R.drawable.target_blue, getResources());
    }

    @Override
    public void onDraw(Canvas c){
        super.onDraw(c);
        target.draw(c);
    }
}
