package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/11/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelpActivity extends MenuDisplayingActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_help);
        return true;
    }
}