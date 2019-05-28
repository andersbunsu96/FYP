package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmergencyJob extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    String RID, username;
    Button btnSend;
    EditText etRequestTitle, etRequestDescription, etJobTitle, etJobDesc, etLocation, etClientName;
    SharedPreferences prefs;
    com.android.volley.RequestQueue requestQueue;
    Spinner spinner;
    private ArrayList<String> pc;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_job);
        setTitle("Emergency Job");
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        etRequestDescription = findViewById(R.id.etRequestDescription);
        etRequestTitle = findViewById(R.id.etRequestTitle);
        etJobTitle = findViewById(R.id.etJobTitle);
        etJobDesc = findViewById(R.id.etJobDesc);
        etLocation = findViewById(R.id.etLocation);
        etClientName = findViewById(R.id.etClientName);
        spinner = findViewById(R.id.spinner);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username","none");
        pc = new ArrayList<>();

      //  progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading team leader name...");
        progressDialog.show();
        getData();
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config3.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            result = new JSONArray(response);

                            //Storing the Array of JSON String to our JSON Array
                            //result = j.getJSONObject(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getPC(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getPC(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                pc.add(json.getString(Config3.username));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<>(EmergencyJob.this, android.R.layout.simple_spinner_dropdown_item, pc));
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        final String requestTitle = etRequestTitle.getText().toString().trim();
        final String requestDescription = etRequestDescription.getText().toString().trim();
        final String jobDescription = etJobDesc.getText().toString().trim();
        final String jobTitle = etJobTitle.getText().toString().trim();
        final String location = etLocation.getText().toString().trim();
        final String clientName = etClientName.getText().toString().trim();
        final String teamLeader = spinner.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(requestTitle)) {
            etRequestTitle.setError("Please enter title");
            etRequestTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(requestDescription)) {
            etRequestDescription.setError("Please enter request description");
            etRequestDescription.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(jobTitle)) {
            etJobTitle.setError("Please enter request description");
            etJobTitle.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(jobDescription)) {
            etJobDesc.setError("Please enter client name");
            etJobDesc.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(location)) {
            etLocation.setError("Please enter location");
            etLocation.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(clientName)) {
            etClientName.setError("Please enter location");
            etClientName.requestFocus();
            return;
        }

        else {

            progressDialog.setMessage("Adding new record");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://integratedcustomerinfomationsystem.000webhostapp.com/createEmergencyJob.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            System.out.println("server: " + ServerResponse);
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(EmergencyJob.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(EmergencyJob.this, ClerkDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(EmergencyJob.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("requestTitle", requestTitle);
                    params.put("requestDescription", requestDescription);
                    params.put("clientName", clientName);
                    params.put("jobDescription", jobDescription);
                    params.put("jobTitle", jobTitle);
                    params.put("location", location);
                    params.put("username", username);
                    params.put("teamLeader", teamLeader);
                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }
}
