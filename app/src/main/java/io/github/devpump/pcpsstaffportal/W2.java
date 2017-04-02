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

public class W2 extends Fragment {


    ListView list_w2s;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_w2, parent, false);

        list_w2s = (ListView) rootView.findViewById(R.id.list_w2);
        Bundle bndl = getArguments();
        JSONObject job = new JSONObject();


        try {
            job.put("AuthToken",bndl.getString("AuthToken"));
            //Get Leave time
            new Utility(getActivity()).jsonArrayRequest("staffService", "GetW2StatementList", job, new VolleyCallbackJsonArray() {

                @Override
                public void onSuccessArray(JSONArray job) throws JSONException {
                    Log.v("SUCCESSArray",job.toString());
                    List<String> w2Array = new ArrayList<String>();
                    for(int i=0; i<job.length(); i++) {
                        JSONObject jobOb = job.getJSONObject(i);
                        Iterator<String> iter = jobOb.keys();
                        while(iter.hasNext()){
                            String key = iter.next();
                            w2Array.add(key + ": " + jobOb.get(key).toString());
                        }
                    }
                    ArrayAdapter<String> w2items= new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1, w2Array);
                    list_w2s.setAdapter(w2items);
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
