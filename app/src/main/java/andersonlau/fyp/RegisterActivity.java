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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Spinner SpinnerUserType;
    ProgressDialog progressDialog;
    com.android.volley.RequestQueue requestQueue;
    String username;
    private static final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/registrationapi.php";
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username", "none");

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        SpinnerUserType = findViewById(R.id.SpinnerUserType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userType, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinnerUserType.setAdapter(adapter);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String username = etUsername.getText().toString().trim();
        final String userType = SpinnerUserType.getSelectedItem().toString().trim();
        final String password = etPassword.getText().toString().trim();

        progressDialog.setMessage("Registering...");
        progressDialog.show();

        //first we will do the validations
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("Please enter username");
            etUsername.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Enter a password");
            etPassword.requestFocus();
            return;
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            System.out.println("server: " + ServerResponse);
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(RegisterActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            prefs = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.remove("isLogin");
                            editor.remove("username");
                            editor.remove("type");
                            editor.remove("isLogin");
                            editor.apply();finish();
                            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("userType", userType);
                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(RegisterActivity.this);

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }
}

