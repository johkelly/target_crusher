package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.ReverseShootingGallery.fragments.HighScoreDialogFragment;

public class MenuDisplayingActivity extends Activity implements HighScoreDialogFragment.HSDListener {

    @Override
    // http://developer.android.com/guide/topics/ui/actionbar.html
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
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
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().add(new HighScoreDialogFragment(this), "high").addToBackStack("high").commit();
                return true;
            default:
                return false;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void onVisible() {
        // Subclass handles this
    }

    @Override
    public void onDismiss() {
        // Subclass handles this
    }
}