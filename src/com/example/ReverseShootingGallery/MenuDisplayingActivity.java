package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/11/13
 * Time: 6:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class MenuDisplayingActivity extends Activity {

    // TODO: Allow to remove on menu item (no menu to "self")

    @Override
    // http://developer.android.com/guide/topics/ui/actionbar.html
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_about:
                intent = new Intent(this, AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.action_help:
                intent = new Intent(this, HelpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.action_options:
                intent = new Intent(this, OptionsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                break;
            case R.id.action_scores:
                Toast.makeText(this, "High Scores Yo", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }
}