package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class generateReport extends AppCompatActivity implements View.OnClickListener{

    TextView tvRequestTitle, tvClientName, tvLocation, tvRequestDescription, tvClerkName, tvRID, tvMessage;
    ImageView ImageView;
    String RID = "", location = "", clientName = "", requestTitle = "", requestDescription = "", jobDone = "", clerkName ="", jobDoneID ="", jobID="", message ="", username_pc ="",
    jobTitle="", jobDescription="", imageURL = ""  ;
    Button btnSend;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);

        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        RID = bundle.getString("RID");
        jobDoneID = bundle.getString("jobDoneID");
        jobID = bundle.getString("jobID");
        message = bundle.getString("message");
        username_pc = bundle.getString("username_pc");
        jobTitle = bundle.getString("jobTitle");
        jobDescription = bundle.getString("jobDescription");
        location = bundle.getString("location");
        imageURL = bundle.getString("imageURL");

        tvRID = findViewById(R.id.tvRID);
        tvClientName = findViewById(R.id.tvClientName);
        tvRequestTitle = findViewById(R.id.tvRequestTitle);
        tvLocation = findViewById(R.id.tvLocation);
        tvRequestDescription = findViewById(R.id.tvRequestDescription);
        tvMessage = findViewById(R.id.tvMessage);
        ImageView = findViewById(R.id.ImageView);
        tvClerkName = findViewById(R.id.tvClerkName);

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
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/takeReport.php?RID=" + RID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);

                                requestTitle = response1.getString("requestTitle");
                                clientName = response1.getString("clientName");
                                requestDescription = response1.getString("requestDescription");
                                RID = response1.getString("RID");
                                clerkName = response1.getString("username");

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

    private void loadFromSite()
    {
        System.out.println("Enter loadFromSite");

        tvRID.setText(RID);
        tvClientName.setText(clientName);
        tvRequestTitle.setText(requestTitle);
        tvLocation.setText(location);
        tvRequestDescription.setText(requestDescription);
        tvMessage.setText(message);
        tvClerkName.setText(clerkName);
        Glide.with(this)
                .load(imageURL)
                .into(ImageView);
    }

    @Override
    public void onClick(View v) {

    }
}