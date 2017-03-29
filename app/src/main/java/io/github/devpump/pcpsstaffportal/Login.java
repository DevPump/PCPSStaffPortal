package io.github.devpump.pcpsstaffportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        JSONObject job = new JSONObject();
        try {
            //Create JSON Object for submitting.
            job.put("email", et_username.getText());
            job.put("password", et_password.getText());
            
            new Utility(Login.this).jsonRequest("AuthorizationService", "ValidateUser", job, new VolleyCallback() {//
                public void onSuccess(JSONObject job) {
                    Log.v("OnSuccess", job.toString());
                    Intent i = new Intent(Login.this, Home.class);
                    i.putExtra("LoginData", job.toString());
                    startActivity(i);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}