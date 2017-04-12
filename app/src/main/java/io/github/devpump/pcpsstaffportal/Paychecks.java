package io.github.devpump.pcpsstaffportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by devpump on 4/2/17.
 */

public class Paychecks extends Fragment {


    ListView list_paychecks;
    JSONObject jobIntent;
    ArrayList payCheckURI;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_paychecks, parent, false);

        list_paychecks = (ListView) rootView.findViewById(R.id.list_paychecks);
        Bundle bndl = getArguments();
        jobIntent = new JSONObject();
         payCheckURI = new ArrayList();

        try {
            jobIntent.put("AuthToken",bndl.getString("AuthToken"));
            //Get Leave time
            new Utility(getActivity()).jsonArrayRequest("staffService", "GetRemunerationStatementsList", jobIntent, new VolleyCallbackJsonArray() {

                @Override
                public void onSuccessArray(JSONArray job) throws JSONException {
                    Log.v("SUCCESSArray",job.toString());
                    List<String> paychecksArray = new ArrayList<String>();
                    for(int i=0; i<job.length(); i++) {
                        JSONObject jobOb = job.getJSONObject(i);
                        paychecksArray.add(jobOb.getString("DocumentPeriod"));


                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, );
                        //startActivity(browserIntent);
                        payCheckURI.add(Uri.parse("https://staff.mypolkschools.net/Services/StaffService.svc/json/GetDocument?authToken=" + jobIntent.getString("AuthToken").toString() + "&id="+ jobOb.getString("DocumentImageId") + "&filename=./" + jobOb.getString("DocumentFilename")).toString());

                    }

                    Collections.sort(paychecksArray);

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
        list_paychecks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(payCheckURI.get(position).toString()));
                startActivity(browserIntent);

            }
        });
        return rootView;
    }
}
