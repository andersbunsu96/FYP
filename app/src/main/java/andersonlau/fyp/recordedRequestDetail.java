package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class recordedRequestDetail extends AppCompatActivity implements View.OnClickListener {

    TextView tvRequestTitle, tvClientName, tvLocation, tvRequestDescription, tvUsername, tvRID;
    String RID = "", location = "", clientName = "", requestTitle = "", requestDescription = "", username ="", jobDone="" ;
    Button btnSend;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_request_detail);

        Bundle bundle = getIntent().getExtras();
        RID = bundle.getString("RID");

        tvRequestTitle = findViewById(R.id.tvRequestTitle);
        tvClientName = findViewById(R.id.tvClientName);
        tvLocation = findViewById(R.id.tvLocation);
        tvRequestDescription = findViewById(R.id.tvRequestDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvRID = findViewById(R.id.tvRID);

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

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
         *
         * */


        System.out.println("Enter loadDetail");
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/recordedRequestDetail.php?RID=" + RID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                username = response1.getString("username");
                                requestTitle = response1.getString("requestTitle");
                                clientName = response1.getString("clientName");
                                location = response1.getString("location");
                                requestDescription = response1.getString("requestDescription");
                                RID = response1.getString("RID");
                                jobDone = response1.getString("jobDone");

                                loadFromSite();
                                progressDialog.dismiss();
                                if (jobDone.equals("Yes")){ btnSend.setVisibility(View.GONE); }

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
        tvLocation.setText(location);
        tvRequestTitle.setText(requestTitle);
        tvClientName.setText(clientName);
        tvUsername.setText(username);
        tvRequestDescription.setText(requestDescription);
        tvRID.setText(RID);
        setTitle(requestTitle);

    }

    @Override
    public void onClick(View v) {

        if (jobDone.equals("Yes"))
        {
            Toast.makeText(this, "Cannot send customer request anymore as job already done", Toast.LENGTH_SHORT).show();
        }

        else{

            Intent intent = new Intent(this, sendRequestDetail.class);
            intent.putExtra("RID", RID);
            intent.putExtra("requestTitle", requestTitle);
            finish();
            startActivity(intent);}
    }
}


