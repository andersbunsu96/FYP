package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class PCSendAssignment extends AppCompatActivity  implements View.OnClickListener{

    String RID = "", requestTitle = "", username = "", requestDescription="";
    TextView tvRID, tvRequestTitle, usernamePC, tvRequestDescription;
    EditText etAssignmentName, etAssignmentDesc;
    Spinner spinner;
    Button btnSend;
    ProgressDialog progressDialog;

    SharedPreferences prefs;
    String username_pc, username_fc;

    com.android.volley.RequestQueue requestQueue;


    private ArrayList<String> fc;
    private JSONArray result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcsend_assignment);
        Bundle bundle = getIntent().getExtras();
        RID = bundle.getString("RID");
        requestTitle = bundle.getString("requestTitle");
        requestDescription = bundle.getString("requestDescription");

        tvRequestTitle = findViewById(R.id.tvRequestTitle);
        tvRequestDescription = findViewById(R.id.tvRequestDescription);
        tvRID = findViewById(R.id.tvRID);

        usernamePC = findViewById(R.id.username_pc);

        etAssignmentDesc = findViewById(R.id.etAssignmentDesc);
        etAssignmentName = findViewById(R.id.etAssignmentName);

        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

        tvRID.setText(RID);
        tvRequestTitle.setText(requestTitle);
        tvRequestDescription.setText(requestDescription);

        setTitle(requestTitle);
        System.out.println(RID);
        System.out.println(requestTitle);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username_pc = prefs.getString("username","none");

        usernamePC.setText(username_pc);

        requestQueue = Volley.newRequestQueue(PCSendAssignment.this);

        progressDialog = new ProgressDialog(PCSendAssignment.this);


        fc = new ArrayList<>();

        //Initializing Spinner
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        spinner = findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener

        //This method will fetch the data from the URL
        getData();
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("https://integratedcustomerinfomationsystem.000webhostapp.com/getFinancialControllerUsername.php",
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
                            getFC(result);
                            progressDialog.dismiss();

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

    private void getFC(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                fc.add(json.getString("username_fc"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<>(PCSendAssignment.this, android.R.layout.simple_spinner_dropdown_item, fc));
    }


    @Override
    public void onClick(View v) {

        final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/sendAssignment.php";

        final String username_fc = spinner.getSelectedItem().toString().trim();
        final String assignmentName = etAssignmentName.getText().toString().trim();
        final String assignmentDesc = etAssignmentDesc.getText().toString().trim();
        final String FOD_approved = "No";
        final String jobCreated = "No";

        if (TextUtils.isEmpty(assignmentName)) {
            etAssignmentName.setError("Please enter assignment name");
            etAssignmentName.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(assignmentDesc)) {
            etAssignmentDesc.setError("Please enter assignment description");
            etAssignmentDesc.requestFocus();
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
                            Toast.makeText(PCSendAssignment.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent (PCSendAssignment.this, ProjectCoordinatorDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(PCSendAssignment.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username_pc", username_pc);
                    params.put("username_fc", username_fc);
                    params.put("assignmentName", assignmentName);
                    params.put("assignmentDesc", assignmentDesc);
                    params.put("FOD_approved", FOD_approved);
                    params.put("RID", RID);
                    params.put("jobCreated", jobCreated);

                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }
}