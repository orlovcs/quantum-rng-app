package com.crimsonlabs.orlovcs.reaction;

import org.json.JSONArray;

public interface RetrieveAPIHelper {
     void onSuccess(JSONArray data_array);
     void onFail(String Response);
}
