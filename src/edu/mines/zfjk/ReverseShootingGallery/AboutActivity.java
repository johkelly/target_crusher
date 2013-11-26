/**
 * Description: Static activity for displaying About information
 * @author John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.os.Bundle;
import android.view.Menu;

public class AboutActivity extends MenuDisplayingActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_about);
        return true;
    }
}