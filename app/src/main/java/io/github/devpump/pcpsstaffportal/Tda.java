package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by devpump on 4/1/17.
 */

public class Tda extends Fragment {


    ExpandableListView list_etda;

    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_tda, parent, false);
        list_etda = (ExpandableListView) rootView.findViewById(R.id.list_etda);
        Bundle bndl = getArguments();
        JSONObject job = new JSONObject();

        try {
            job.put("AuthToken", bndl.getString("AuthToken"));
            //Get Leave time
            new Utility(getActivity()).jsonArrayRequest("staffService", "GetTDAList", job, new VolleyCallbackJsonArray() {

                @Override
                public void onSuccessArray(JSONArray job) throws JSONException {
                    Log.v("SUCCESSArray", job.toString());
                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<String>>();

                    List<String> tdaList;

                    for (int i = 0; i < job.length(); i++) {
                        tdaList = new ArrayList<String>();
                        JSONObject jobOb = job.getJSONObject(i);
                        listDataHeader.add(jobOb.getString("Event"));
                        tdaList.add("TDA ID: " + (jobOb.getString("TDA_ID").replaceFirst("^0+(?!$)", "")));
                        tdaList.add("Start Date: " + (new Utility(getActivity()).formatDate(jobOb.getString("DateStart"))));
                        tdaList.add("End Date: " + (new Utility(getActivity()).formatDate(jobOb.getString("DateEnd"))));
                        tdaList.add("Location: " + jobOb.getString("DestinationCity") + ", " + jobOb.getString("DestinationState"));
                        tdaList.add("Status: " + jobOb.getString("StatusDescription"));

                        listDataChild.put(listDataHeader.get(i), tdaList);


                    }
                    listAdapter = new io.github.devpump.pcpsstaffportal.ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                    list_etda.setAdapter(listAdapter);
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
