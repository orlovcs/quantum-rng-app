package com.crimsonlabs.orlovcs.reaction;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Random;

public class GenerationHelper {

    ArrayList<Integer> manualGeneration() {
        ArrayList nums = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < 40; i++) {
            //locally generate new number within the API bound
            int n = rand.nextInt(65535); //ANU Bound
            if (!nums.contains(n)){
                nums.add(n);
            }else{
                //if generated number is present, generate until a unique new one is found
                while(nums.contains(n)) {
                    n = rand.nextInt(65535); //ANU Bound
                }
                nums.add(n);
            }
        }
        return nums;
    }

    ArrayList<Integer> processData(JSONArray data_array) {
        ArrayList nums = new ArrayList<Integer>();
        Random rand = new Random();
        try {
            if (data_array != null) {
                for (int i = 0; i < data_array.length(); i++) {
                    int n = data_array.getInt(i);
                    if (!nums.contains(n)){
                        nums.add(n);
                    }else{
                        //if generated number is present, generate until a unique new one is found
                        while(nums.contains(n)) {
                            n = rand.nextInt(65535); //ANU Bound
                        }
                        nums.add(n);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return nums;
    }

}
