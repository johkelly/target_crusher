package com.example.ReverseShootingGallery.fragments;


import android.app.Activity;
import android.app.ListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import com.example.ReverseShootingGallery.OptionsActivity;
import com.example.ReverseShootingGallery.OptionsAdapter;
import com.example.ReverseShootingGallery.R;

public class OptionsListFragment extends ListFragment {

    public interface OptionDetailsFragmentDispatcher {
        void displayDetailsFor(int pos);
    }

    private OptionDetailsFragmentDispatcher dispatcher;

    // http://developer.android.com/training/basics/fragments/communicating.html
    // no longer used?

    /**
     * Callback for when this fragment attaches to an activity. Here we capture the parent as a dispatcher.
     *
     * @param activity parent activity being attached to
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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

    /**
     * Callback for when this fragment creates its views/layouts.
     *
     * @param inflater           utility object
     * @param container          where this fragment is going
     * @param savedInstanceState unused
     * @return the created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OptionsAdapter adapter = new OptionsAdapter(inflater.getContext(), R.layout.options_row, OptionsActivity.OPTION_NAMES);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Callback for when an item in the listing was clicked
     *
     * @param l        Containing ListView
     * @param v        Row view for the item
     * @param position Index of the item
     * @param id       unused
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        dispatcher.displayDetailsFor(position);
    }
}
