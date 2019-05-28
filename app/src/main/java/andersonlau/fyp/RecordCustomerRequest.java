package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RecordCustomerRequest extends AppCompatActivity implements View.OnClickListener {

    EditText etRequestTitle, etClientName, etLocation, etRequestDescription;
    Button submitButton;
    ProgressDialog progressDialog;
    SharedPreferences prefs;
    String username;

    com.android.volley.RequestQueue requestQueue;
    private static final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/recordcustomerrequest.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_customer_request);

        progressDialog = new ProgressDialog(RecordCustomerRequest.this);
        requestQueue = Volley.newRequestQueue(RecordCustomerRequest.this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        etRequestTitle = findViewById(R.id.etRequestTitle);
        etClientName = findViewById(R.id.etClientName);
        etLocation = findViewById(R.id.etLocation);
        etRequestDescription = findViewById(R.id.etRequestDescription);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        username = prefs.getString("username","none");
        System.out.println(username);

    }

    @Override
    public void onClick(View v) {
        final String requestTitle = etRequestTitle.getText().toString().trim();
        final String clientName = etClientName.getText().toString().trim();
        final String location = etLocation.getText().toString().trim();
        final String requestDescription = etRequestDescription.getText().toString().trim();


        if (TextUtils.isEmpty(requestTitle)) {
            etRequestTitle.setError("Please enter title");
            etRequestTitle.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(clientName)) {
            etClientName.setError("Please enter client name");
            etClientName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(location)) {
            etLocation.setError("Please enter location");
            etLocation.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(requestDescription)) {
            etRequestDescription.setError("Please enter request description");
            etRequestDescription.requestFocus();
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
                            Toast.makeText(RecordCustomerRequest.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(RecordCustomerRequest.this, ClerkDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(RecordCustomerRequest.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("clientName", clientName);
                    params.put("requestTitle", requestTitle);
                    params.put("location", location);
                    params.put("requestDescription", requestDescription);
                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }
}
