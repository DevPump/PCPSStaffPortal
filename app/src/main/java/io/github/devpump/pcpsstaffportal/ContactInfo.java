package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by devpump on 4/1/17.
 */

public class ContactInfo extends Fragment {

    ListView list_contactInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_contactinfo, parent, false);

        list_contactInfo = (ListView) rootView.findViewById(R.id.list_contactInfo);
        Bundle bndl = getArguments();

        try {
            JSONObject job = new JSONObject();
            job.put("AuthToken", bndl.getString("AuthToken"));
            job.put("LockForEdit", "false");
            new Utility(getActivity()).jsonRequest("staffService", "GetAddress", job, new VolleyCallbackJsonObject() {
                @Override
                public void onSuccess(JSONObject job) throws JSONException {
                    job = job.getJSONObject("CurrentAddress");
                    List<String> contactInfoArray = new ArrayList<String>();
                    ArrayAdapter<String> contactInfoArrayAdapter = new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1, contactInfoArray);
                    Iterator<String> iter = job.keys();
                    while(iter.hasNext()){
                        String key = iter.next();
                            contactInfoArray.add(key + ": " + job.get(key).toString());
                    }
                    list_contactInfo.setAdapter(contactInfoArrayAdapter);
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