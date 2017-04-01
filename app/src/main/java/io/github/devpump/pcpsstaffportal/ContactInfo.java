package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devpump on 4/1/17.
 */

public class ContactInfo extends Fragment {

    EditText et_contactInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_contactinfo, parent, false);

        et_contactInfo = (EditText) rootView.findViewById(R.id.et_contactInfoTest);

        Bundle bndl = getArguments();

        try {
            JSONObject job = new JSONObject();
            job.put("AuthToken", bndl.getString("AuthToken"));
            job.put("LockForEdit", "false");
            new Utility(getActivity()).jsonRequest("staffService", "GetAddress", job, new VolleyCallbackJsonObject() {
                @Override
                public void onSuccess(JSONObject job) throws JSONException {
                    et_contactInfo.setText(job.toString());
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