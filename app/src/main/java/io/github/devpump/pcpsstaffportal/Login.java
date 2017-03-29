package io.github.devpump.pcpsstaffportal;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin (View v){
        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);

        JSONObject job = new JSONObject();
        try {
            //Create JSON Object for submitting.
            job.put("email",et_username.getText());
            job.put("password",et_password.getText());
            jsonRequest(job, new VolleyCallback() {
                public void onSuccess(JSONObject job) {
                    Log.v("OnSuccess",job.toString());

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void jsonRequest(JSONObject jsonPostData, final VolleyCallback callback) throws JSONException { //String barCode, String itemName, double itemQuantity){
        //Create Object request with via POST method with JSON Object.
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, "",jsonPostData,
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
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(req);
    }
}

interface VolleyCallback{
    void onSuccess(JSONObject job);
}