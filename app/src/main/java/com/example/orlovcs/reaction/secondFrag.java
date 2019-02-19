package com.example.orlovcs.reaction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class secondFrag extends Fragment {

    View myView;

    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        myView = inflater.inflate(R.layout.second_layout, container, false);
        return myView;
    }


}
