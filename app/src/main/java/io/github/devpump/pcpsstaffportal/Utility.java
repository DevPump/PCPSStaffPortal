package io.github.devpump.pcpsstaffportal;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by devpump on 3/28/17.
 */

public class Utility {
    public RequestQueue rq;

    Utility(Context context){
        rq = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void jsonRequest(String serviceName, String endPoint, JSONObject jsonPostData, final VolleyCallback callback) throws JSONException {
        //Create Object request with via POST method with JSON Object.
        String url = "https://staff.mypolkschools.net/Services/" + serviceName + ".svc/json/" + endPoint;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url,jsonPostData,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON Response", response.toString());
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                callback.onFail("Unsuccessful response");
            }
        });

        // Adding request to request queue
        rq.add(req);
    }
}

interface VolleyCallback{
    void onSuccess(JSONObject job) throws JSONException;
    void onFail(String response);
}