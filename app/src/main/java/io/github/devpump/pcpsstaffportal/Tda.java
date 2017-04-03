package io.github.devpump.pcpsstaffportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by devpump on 4/1/17.
 */

public class Tda extends Fragment{


    ListView list_tda;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup parent, final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View rootView = inflater.inflate(R.layout.fragment_tda, parent, false);

        list_tda = (ListView) rootView.findViewById(R.id.list_tda);
        Bundle bndl = getArguments();
        JSONObject job = new JSONObject();


        try {
            job.put("AuthToken",bndl.getString("AuthToken"));
            //Get Leave time
            new Utility(getActivity()).jsonArrayRequest("staffService", "GetTDAList", job, new VolleyCallbackJsonArray() {

                @Override
                public void onSuccessArray(JSONArray job) throws JSONException {
                    Log.v("SUCCESSArray",job.toString());
                    List<String> tdasArray = new ArrayList<String>();
                    for(int i=0; i<job.length(); i++) {
                        JSONObject jobOb = job.getJSONObject(i);
                        Iterator<String> iter = jobOb.keys();
                        while(iter.hasNext()){
                            String key = iter.next().trim();
                            if(key.equals("DateStart") || key.equals("DateEnd")){
                                Date result = null;
                                String str = jobOb.get(key).toString();
                                str = str.replace("/Date(", "");
                                str = str.replace(")/","");
                                str = str.substring(0, str.length() -5);
                                //[^0-9]", "");
                                if (!TextUtils.isEmpty(str)) {
                                    try {
                                        result = new Date(Long.parseLong(str));
                                        Log.v("Date",result.toString());

                                        tdasArray.add(key + ": " + (new SimpleDateFormat("MM-dd-yyyy").format(result)));
                                    } catch (NumberFormatException e) {
                                    }
                                }
                            }else{
                                tdasArray.add(key + ": " + jobOb.get(key).toString());
                            }

                        }
                    }


                    ArrayAdapter<String> tdaItems= new ArrayAdapter<String>(
                            getActivity(),
                            android.R.layout.simple_list_item_1, tdasArray);
                    list_tda.setAdapter(tdaItems);

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
