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
                layout = R.layout.fragment_screen_slide_page_one;
                break;
            case 1:
                layout = R.layout.fragment_screen_slide_page_two;
                break;
            case 2:
                layout = R.layout.fragment_screen_slide_page_three;
                break;
            case 3:
                layout = R.layout.fragment_screen_slide_page_four;
                break;
            case 4:
                layout = R.layout.fragment_screen_slide_page_five;
                break;
            case 5:
                layout = R.layout.fragment_screen_slide_page_six;
                break;
            case 6:
                layout = R.layout.fragment_screen_slide_page_seven;
                break;
            case 7:
                layout = R.layout.fragment_screen_slide_page_eight;
                break;
            case 8:
                layout = R.layout.fragment_screen_slide_page_nine;
                break;
            default:
                layout = R.layout.fragment_screen_slide_page_one;
        }

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                layout, container, false);


        return rootView;
    }
}
