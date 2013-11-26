/**
 * Description: Parent activity to ensure a consistent ActionBar behavior across the app
 * @author John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.mines.zfjk.ReverseShootingGallery.fragments.HighScoreDialogFragment;

public class MenuDisplayingActivity extends Activity implements HighScoreDialogFragment.HSDListener {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Launch an activity or popup a dialog depending on the action item
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
                HighScoreDialogFragment f = new HighScoreDialogFragment();
                f.setListener(this);
                fm.beginTransaction().add(f, "high").addToBackStack("high").commit();
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