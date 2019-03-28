package com.example.orlovcs.reaction;

import android.content.Intent;

public class sharefunc {

    Intent i;

    public sharefunc(String s) {
        i = create(s);
    }


    Intent create(String s){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, s);
        return sharingIntent;
    }
}
