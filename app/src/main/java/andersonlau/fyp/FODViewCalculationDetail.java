package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FODViewCalculationDetail extends AppCompatActivity implements View.OnClickListener {

    TextView tvCalcDesc, tvUsernameFC, tvCalcID;
    String calcDesc = "", username_fc = "", calcID = "", AID ="" ;
    Button btnApprove, btnDisapprove;
    ProgressDialog progressDialog;

    com.android.volley.RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fodview_calculation_detail);

        Bundle bundle = getIntent().getExtras();
        calcID = bundle.getString("calcID");

        progressDialog = new ProgressDialog(FODViewCalculationDetail.this);
        requestQueue = Volley.newRequestQueue(FODViewCalculationDetail.this);

        tvCalcDesc = findViewById(R.id.tvCalcDesc);
        tvUsernameFC = findViewById(R.id.tvUsernameFC);
        tvCalcID = findViewById(R.id.tvCalcID);


        btnApprove = findViewById(R.id.btnApprove);
        btnApprove.setOnClickListener(this);

        btnDisapprove = findViewById(R.id.btnDisapprove);
        btnDisapprove.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadDetail();

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
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/FODViewCalculationDetail.php?calcID=" + calcID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                calcID = response1.getString("calcID");
                                calcDesc = response1.getString("calcDesc");
                                username_fc = response1.getString("username_fc");
                                AID = response1.getString("AID");

                                loadFromSite();

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

    private void loadFromSite()
    {
        System.out.println("Enter loadFromSite");
        tvCalcDesc.setText(calcDesc);
        tvCalcID.setText(calcID);
        tvUsernameFC.setText(username_fc);
        progressDialog.dismiss();

    }

    @Override
    public void onClick(View v) {

        if (v == btnApprove){

            final String FOD_approved = "Yes";

            progressDialog.setMessage("Please wait for a while");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://integratedcustomerinfomationsystem.000webhostapp.com/approvedFOD.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            System.out.println("server: " + ServerResponse);
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(FODViewCalculationDetail.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(FODViewCalculationDetail.this, FODDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(FODViewCalculationDetail.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("FOD_approved", FOD_approved);
                    params.put("AID", AID);
                    params.put("calcID", calcID);

                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }

        else if (v == btnDisapprove) {
            final String FOD_approved = "Disapprove";

            progressDialog.setMessage("Please wait for a while");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://integratedcustomerinfomationsystem.000webhostapp.com/approvedFOD.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            System.out.println("server: " + ServerResponse);
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(FODViewCalculationDetail.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(FODViewCalculationDetail.this, FODDashboard.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(FODViewCalculationDetail.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("FOD_approved", FOD_approved);
                    params.put("AID", AID);
                    params.put("calcID", calcID);
                    return params;
                }
            };

            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }

    }
}


