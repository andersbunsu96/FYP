package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

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

public class PCCreateJob extends AppCompatActivity  implements View.OnClickListener{

    String RID = "", requestTitle = "";
    TextView tvRID, tvRequestTitle;
    EditText etJobTitle, etJobDesc, etLocation;
    Spinner spinner;
    Button btnCreateJob;
    ProgressDialog progressDialog;

    private ArrayList<String> pc;
    private JSONArray result;

    SharedPreferences prefs;
    String username_pc;

    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pccreate_job);
        Bundle bundle = getIntent().getExtras();
        RID = bundle.getString("RID");

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username_pc = prefs.getString("username","none");

        tvRequestTitle = findViewById(R.id.tvRequestTitle);
        tvRID = findViewById(R.id.tvRID);
        btnCreateJob = findViewById(R.id.btnCreateJob);

        etJobDesc = findViewById(R.id.etJobDesc);
        etJobTitle = findViewById(R.id.etJobTitle);
        etLocation = findViewById(R.id.etLocation);

        btnCreateJob.setOnClickListener(this);

        tvRID.setText(RID);
        tvRequestTitle.setText(requestTitle);

        System.out.println(RID);
        System.out.println(requestTitle);

        requestQueue = Volley.newRequestQueue(PCCreateJob.this);

        progressDialog = new ProgressDialog(PCCreateJob.this);


        pc = new ArrayList<>();

        //Initializing Spinner
        spinner = findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class itself we are passing this to setOnItemSelectedListener

        //This method will fetch the data from the URL
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadDetail();
        getData();
    }

    private void loadDetail() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        System.out.println("Enter loadDetail");
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getJobInfo.php?RID=" + RID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                RID = response1.getString("RID");
                                requestTitle = response1.getString("requestTitle");

                                loadFromSite();
                                progressDialog.dismiss();

                            }
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
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        System.out.println("Exit loadProduct");
    }

    private void loadFromSite() {
        System.out.println("Enter loadFromSite");
        tvRID.setText(RID);
        tvRequestTitle.setText(requestTitle);

        setTitle(requestTitle);

    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config2.DATA_URL,
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
                pc.add(json.getString(Config2.username));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<>(PCCreateJob.this, android.R.layout.simple_spinner_dropdown_item, pc));
    }

    @Override
    public void onClick(View v) {

        final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/createJob.php";

        final String teamLeader = spinner.getSelectedItem().toString().trim();
        final String jobDescription = etJobDesc.getText().toString().trim();
        final String jobTitle = etJobTitle.getText().toString().trim();
        final String location = etLocation.getText().toString().trim();
        final String jobDone = "No";

        if (TextUtils.isEmpty(jobTitle)) {
            etJobTitle.setError("Please enter job title");
            etJobTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(jobDescription)) {
            etJobDesc.setError("Please job description");
            etJobDesc.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(location)) {
            etLocation.setError("Please enter location");
            etLocation.requestFocus();
            return;
        }

        else {

        progressDialog.setMessage("Adding new record");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        System.out.println("server: " + ServerResponse);
                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(PCCreateJob.this, ServerResponse, Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent (PCCreateJob.this, ProjectCoordinatorDashboard.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(PCCreateJob.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("teamLeader", teamLeader);
                params.put("RID", RID);
                params.put("jobDescription",jobDescription);
                params.put("jobTitle", jobTitle);
                params.put("location", location);
                params.put("jobDone", jobDone);
                params.put("username_pc", username_pc);

                return params;
            }
        };

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }}
}