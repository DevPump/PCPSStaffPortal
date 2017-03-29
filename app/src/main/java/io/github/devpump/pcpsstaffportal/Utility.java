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

    public void jsonRequest(JSONObject jsonPostData, final VolleyCallback callback) throws JSONException { //String barCode, String itemName, double itemQuantity){
        //Create Object request with via POST method with JSON Object.
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "https://staff.mypolkschools.net/Services/AuthorizationService.svc/json/ValidateUser",jsonPostData,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON Response", response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });

        // Adding request to request queue
        rq.add(req);
    }
}

interface VolleyCallback{
    void onSuccess(JSONObject job);
}
