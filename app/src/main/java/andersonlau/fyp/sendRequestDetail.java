package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class sendRequestDetail extends AppCompatActivity  implements View.OnClickListener{

    String RID = "", requestTitle = "", username = "";
    TextView tvRID, tvRequestTitle;
    Spinner spinner;
    Button btnSend;
    ProgressDialog progressDialog;

    private ArrayList<String> pc;
    private JSONArray result;

    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request_detail);
        Bundle bundle = getIntent().getExtras();
        RID = bundle.getString("RID");
        requestTitle = bundle.getString("requestTitle");

        tvRequestTitle = findViewById(R.id.tvRequestTitle);
        tvRID = findViewById(R.id.tvRID);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(this);

        tvRID.setText(RID);
        tvRequestTitle.setText(requestTitle);

        setTitle(requestTitle);
        System.out.println(RID);
        System.out.println(requestTitle);

        requestQueue = Volley.newRequestQueue(sendRequestDetail.this);

        progressDialog = new ProgressDialog(sendRequestDetail.this);


        pc = new ArrayList<>();

        //Initializing Spinner
        spinner = findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener

        //This method will fetch the data from the URL
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getData();
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
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
                pc.add(json.getString(Config.username));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<>(sendRequestDetail.this, android.R.layout.simple_spinner_dropdown_item, pc));
        progressDialog.dismiss();
    }


    @Override
    public void onClick(View v) {

         final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/sendCustomerRequest.php";

         final String username = spinner.getSelectedItem().toString().trim();
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
                        Toast.makeText(sendRequestDetail.this, ServerResponse, Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent (sendRequestDetail.this, ClerkDashboard.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(sendRequestDetail.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("RID", RID);

                return params;
            }
        };

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}