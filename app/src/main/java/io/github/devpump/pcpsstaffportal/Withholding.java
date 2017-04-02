package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by devpump on 4/1/17.
 */

public class Withholding extends Fragment {

    ListView list_withholding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_withholding, parent, false);

        list_withholding = (ListView) rootView.findViewById(R.id.list_withholding);
        Bundle bndl = getArguments();

        try {
            JSONObject job = new JSONObject();
            job.put("AuthToken", bndl.getString("AuthToken"));
            new Utility(getActivity()).jsonRequest("staffService", "GetFilingStatus", job, new VolleyCallbackJsonObject() {
                @Override
                public void onSuccess(JSONObject job) throws JSONException {
                    List<String> withholdingArray = new ArrayList<String>();
                    ArrayAdapter<String> withholdingArrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1, withholdingArray);
                    Iterator<String> iter = job.keys();
                    while(iter.hasNext()){
                        String key = iter.next();
                            withholdingArray.add(key + ": " + job.get(key).toString());
                    }
                    list_withholding.setAdapter(withholdingArrayAdapter);
                    Log.v("SUCCESS", job.toString());
                }

                @Override
                public void onFail(String response) {
                    Log.v("Err", response);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}