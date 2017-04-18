package io.github.devpump.pcpsstaffportal;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by devpump on 4/2/17.
 */

public class W2 extends Fragment {


    ListView list_w2s;
    ArrayList w2URI;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_w2, parent, false);

        list_w2s = (ListView) rootView.findViewById(R.id.list_w2);
        final Bundle bndl = getArguments();
        JSONObject job = new JSONObject();

        w2URI = new ArrayList();


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

                        https://staff.mypolkschools.net/Services/StaffService.svc/json/GetDocument?authToken=626c9c85-a2af-4231-bf84-9e13cccb2775&id=1046196&filename=./PCSB_SAP00043370_W2_20160127.PDF
                        w2Array.add(jobOb.getString("DocumentPeriod").toString());

                        w2URI.add("https://staff.mypolkschools.net/Services/StaffService.svc/json/GetDocument?authToken=" + bndl.getString("AuthToken").toString() + "&id=" + jobOb.getString("DocumentImageId").toString() + "&filename=./" +  jobOb.getString("DocumentFilename").toString());
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

        list_w2s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent webViewIntent = new Intent(getActivity(), MasterWebView.class);
                webViewIntent.putExtra("url", w2URI.get(position).toString());
                startActivity(webViewIntent);

            }
        });

        return rootView;
    }
}
