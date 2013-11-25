package com.example.ReverseShootingGallery;

import android.os.Bundle;
import android.view.Menu;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/11/13
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends MenuDisplayingActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.blue_color_button);
        return true;
    }
}