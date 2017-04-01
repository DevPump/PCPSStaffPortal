package io.github.devpump.pcpsstaffportal;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by devpump on 3/29/17.
 */


public class LeaveTime extends Fragment {
    ListView list_leaveHead, list_absences;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_leavetime, parent, false);

        list_leaveHead = (ListView) rootView.findViewById(R.id.list_leaveHead);
        list_absences = (ListView) rootView.findViewById(R.id.list_absences);
                Bundle bndl = getArguments();
                JSONObject job = new JSONObject();


                try {
                    job.put("AuthToken",bndl.getString("AuthToken"));
                    //Get Leave time
                    new Utility(getActivity()).jsonArrayRequest("staffService", "getESSLeaveList", job, new VolleyCallbackJsonArray() {

                        @Override
                        public void onSuccessArray(JSONArray job) throws JSONException {
                            Log.v("SUCCESSArray",job.toString());
                            List<String> listist = new ArrayList<String>();
                            for(int i=0; i<job.length(); i++) {
                                listist.add(job.getJSONObject(i).getString("KTEXT") + ": " + job.getJSONObject(i).getString("AVAILABLE"));
                            }

                            ArrayAdapter<String> leaveHeadItems = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_list_item_1, listist);
                            list_leaveHead.setAdapter(leaveHeadItems);

                        }

                        @Override
                        public void onFail(String response) {
                            Log.v("Err", response);
                        }
                    });
                    //Get Absences
                    job.put("NumberOfMonths", "12");
                    new Utility(getActivity()).jsonArrayRequest("staffService", "getESSAbsenceList", job, new VolleyCallbackJsonArray() {

                        @Override
                        public void onSuccessArray(JSONArray job) throws JSONException {
                            Log.v("SUCCESSArray",job.toString());
                            List<String> listAbsences = new ArrayList<String>();
                            for(int i=0; i<job.length(); i++) {
                                listAbsences.add(job.getJSONObject(i).getString("WORKDATE") + ": " + job.getJSONObject(i).getString("ATEXT") + " " + job.getJSONObject(i).getString("CATSHOURS"));
                            }

                            ArrayAdapter<String> leaveAbsences = new ArrayAdapter<String>(
                                    getActivity(),
                                    android.R.layout.simple_list_item_1, listAbsences);
                            list_absences.setAdapter(leaveAbsences);

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

    //https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments

}