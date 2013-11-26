/**
 * Description: Fragment with layout and logic to display sub-options and display appropriate fragments.
 * @author Zach Fleischman, John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.Activity;
import android.app.ListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import edu.mines.zfjk.ReverseShootingGallery.OptionsActivity;
import edu.mines.zfjk.ReverseShootingGallery.OptionsAdapter;
import edu.mines.zfjk.ReverseShootingGallery.R;

public class OptionsListFragment extends ListFragment {

    public interface OptionDetailsFragmentDispatcher {
        /**
         * Do whatever is necessary to display the requested details fragment
         * @param pos Index of the desired details fragment in the list
         */
        void displayDetailsFor(int pos);
    }

    private OptionDetailsFragmentDispatcher dispatcher;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Attempt to cast our hosting activity to a Dispatcher object to delegate to
        try {
            dispatcher = (OptionDetailsFragmentDispatcher) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement OptionDetailFragmentDispatcher");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OptionsAdapter adapter = new OptionsAdapter(inflater.getContext(), R.layout.options_row, OptionsActivity.OPTION_NAMES);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        dispatcher.displayDetailsFor(position);
    }
}
