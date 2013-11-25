package edu.mines.zfjk.ReverseShootingGallery;


import android.os.Bundle;
import android.view.Menu;

public class HelpActivity extends MenuDisplayingActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_help);
        return true;
    }
}