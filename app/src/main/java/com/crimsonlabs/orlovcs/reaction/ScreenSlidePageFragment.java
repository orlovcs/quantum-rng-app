package com.crimsonlabs.orlovcs.reaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {

    private int theme_index;

    ScreenSlidePageFragment(int page){
        theme_index = page;
    }

    @Override
    public ViewGroup onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layout;
        switch(theme_index) {
            case 0:
                layout = R.layout.fragment_screen_slide_page_original;
                break;
            case 1:
                layout = R.layout.fragment_screen_slide_page_cyan;
                break;
            default:
                layout = R.layout.fragment_screen_slide_page_original;
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                layout, container, false);


        return rootView;
    }
}
