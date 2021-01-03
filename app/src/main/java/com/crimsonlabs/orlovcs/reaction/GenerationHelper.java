package com.crimsonlabs.orlovcs.reaction;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;

public class GenerationHelper {

    void manualGeneration(ArrayList nums){
        nums = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 40; i++){
            int n = rand.nextInt(65535); //ANU Bound
            nums.add(n);
        }
    }


    void processData(JSONArray data_array, ArrayList nums){
        nums = new ArrayList<>();
        try {
            if (data_array != null){
                for(int i=0;i<data_array.length();i++){
                    int data = data_array.getInt(i);
                    nums.add(data);
                }}


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
