package edu.mines.zfjk.ReverseShootingGallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class OptionsAdapter extends ArrayAdapter<String> {
    private Context context;
    private int layoutResId;
    private List<String> options;

    // http://www.ezzylearning.com/tutorial.aspx?tid=1763429
    public OptionsAdapter(Context context, int layoutResId, String[] data) {
        super(context, layoutResId, data);
        this.context = context;
        this.layoutResId = layoutResId;
        this.options = Arrays.asList(data);
    }

    @Override
    public int getCount() {
        return options.size();
    }

    /**
     * Return an inflated and populated view to use as a row in a ListView for the given object at pos
     *
     * @param pos         Index of the item the inflated view is for
     * @param convertView Old view to reuse, if possible
     * @param parent      View the returned view will go into
     * @return An inflated and populated view to use as a row in a ListView for the given object at pos
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResId, parent, false);
        }
        String option = options.get(pos);
        ((TextView) row.findViewById(R.id.option_name)).setText(option);
        row.setTag(option);

        return row;
    }
}
