package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by devpump on 4/2/17.
 */

public class Paychecks extends Fragment {


    ListView list_paychecks;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_paychecks, parent, false);

        list_paychecks = (ListView) rootView.findViewById(R.id.list_paychecks);
        Bundle bndl = getArguments();
        JSONObject job = new JSONObject();


        try {
            job.put("AuthToken",bndl.getString("AuthToken"));
            //Get Leave time
            new Utility(getActivity()).jsonArrayRequest("staffService", "GetRemunerationStatementsList", job, new VolleyCallbackJsonArray() {

                @Override
                public void onSuccessArray(JSONArray job) throws JSONException {
                    Log.v("SUCCESSArray",job.toString());
                    List<String> paychecksArray = new ArrayList<String>();
                    for(int i=0; i<job.length(); i++) {
                        JSONObject jobOb = job.getJSONObject(i);
                        Iterator<String> iter = jobOb.keys();
                        while(iter.hasNext()){
                            String key = iter.next();
                            paychecksArray.add(key + ": " + jobOb.get(key).toString());
                        }
                    }
                    ArrayAdapter<String> paycheckitems= new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1, paychecksArray);
                    list_paychecks.setAdapter(paycheckitems);
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
