package io.github.devpump.pcpsstaffportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void doLogin(View v) {
        EditText et_username = (EditText) findViewById(R.id.et_username);
        EditText et_password = (EditText) findViewById(R.id.et_password);

        JSONObject jobAuthData = new JSONObject();
        try {
            //Create JSON Object for submitting.
            jobAuthData.put("email", et_username.getText());
            jobAuthData.put("password", et_password.getText());
            
            new Utility(Login.this).jsonRequest("AuthorizationService", "ValidateUser", jobAuthData, new VolleyCallbackJsonObject() {//
                public void onSuccess(JSONObject job) {
                    Log.v("OnSuccess", job.toString());
                    Intent i = new Intent(Login.this, Home.class);
                    i.putExtra("LoginData", job.toString());
                    startActivity(i);
                }

                public void onFail(String response) {
                 Toast toasty = Toast.makeText(Login.this, "Failed to login",Toast.LENGTH_LONG);
                    toasty.show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}