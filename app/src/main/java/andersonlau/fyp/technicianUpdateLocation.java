package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class technicianUpdateLocation extends AppCompatActivity implements View.OnClickListener {

    EditText etLocation;
    Button btnSend;
    ProgressDialog progressDialog;
    SharedPreferences prefs;
    com.android.volley.RequestQueue requestQueue;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_update_location);

        setTitle("Update Location");
        btnSend = findViewById(R.id.btnSend);
        etLocation = findViewById(R.id.etLocation);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username","none");

        requestQueue = Volley.newRequestQueue(technicianUpdateLocation.this);
        progressDialog = new ProgressDialog(technicianUpdateLocation.this);

        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/technicianUpdateLocation.php";

        final String location = etLocation.getText().toString().trim();

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
                            Toast.makeText(technicianUpdateLocation.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent (technicianUpdateLocation.this, TechnicianDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(technicianUpdateLocation.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("location", location);

                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }
}
