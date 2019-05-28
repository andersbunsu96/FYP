package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class assignTechnician extends AppCompatActivity  implements View.OnClickListener, Spinner.OnItemSelectedListener {

    String jobID = "", jobTitle = "", jobDescription = "", userID, RID="";
    TextView tvJobID, tvJobTitle, tvJobDescription, tvUserID;
    Spinner spinner;
    Button btnSend;
    ProgressDialog progressDialog;

    private ArrayList<String> pc, id;
    private JSONArray result;

    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_technician);
        Bundle bundle = getIntent().getExtras();

        jobID = bundle.getString("jobID");
        jobTitle = bundle.getString("jobTitle");
        jobDescription = bundle.getString("jobDescription");
        RID = bundle.getString("RID");

        tvJobID = findViewById(R.id.tvJobID);
        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvJobDescription = findViewById(R.id.tvJobDescription);
        tvUserID = findViewById(R.id.tvUserID);

        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

        tvJobID.setText(jobID);
        tvJobTitle.setText(jobTitle);
        tvJobDescription.setText(jobDescription);

        setTitle(jobTitle);
        System.out.println(jobID);
        System.out.println(jobTitle);

        requestQueue = Volley.newRequestQueue(assignTechnician.this);

        progressDialog = new ProgressDialog(assignTechnician.this);


        pc = new ArrayList<>();

        //Initializing Spinner
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);


        //Adding an Item Selected Listener to our Spinner

        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener

        //This method will fetch the data from the URL
        getData();
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("https://integratedcustomerinfomationsystem.000webhostapp.com/getTechnicianUsername.php",
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
                pc.add(json.getString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<>(assignTechnician.this, android.R.layout.simple_spinner_dropdown_item, pc));

    }

    private String getUserID(int position){
        String userID="";
        try {
            JSONObject json = result.getJSONObject(position);
            userID = json.getString("userID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return userID;
    }


    @Override
    public void onClick(View v) {

        final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/assignTechnician.php";

        final String username_technician = spinner.getSelectedItem().toString().trim();
        final String userID = tvUserID.getText().toString().trim();

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
                            Toast.makeText(assignTechnician.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(assignTechnician.this, TeamLeaderDashboard.class);
                            startActivity(intent);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(assignTechnician.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username_technician", username_technician);
                    params.put("userID", userID);
                    params.put("jobID", jobID);
                    params.put("jobDescription", jobDescription);
                    params.put("jobTitle", jobTitle);
                    params.put("RID", RID);

                    return params;
                }

            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tvUserID.setText(getUserID(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        tvUserID.setText("");
    }
}

