package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;

public class viewTechnicianJobDetail extends AppCompatActivity implements View.OnClickListener {

    TextView tvJobTitle, tvLocation, tvJobDone, tvJobDescription, tvUsername, tvJobID, blank;
    RecyclerView recyclerView;
    List<viewTechnicianJobDetailList> viewTechnicianJobDetail;
    Context mCtx;
    ProgressDialog progressDialog;
    String location = "", jobTitle = "", jobDone = "", username_pc = "", jobDescription = "", jobID ="", RID="";
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technician_job_detail);

        Bundle bundle = getIntent().getExtras();
        jobID = bundle.getString("jobID");
        jobDescription = bundle.getString("jobDescription");
        jobDone = bundle.getString("jobDone");
        jobTitle = bundle.getString("jobTitle");

        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvJobDone = findViewById(R.id.tvJobDone);
        tvLocation = findViewById(R.id.tvLocation);
        tvJobDescription = findViewById(R.id.tvJobDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvJobID = findViewById(R.id.tvJobID);


        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        viewTechnicianJobDetail = new ArrayList<>();
        blank = findViewById(R.id.blank);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mCtx = this;

        setTitle(jobTitle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadDetail();
        loadProducts();

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
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/viewCreatedJobDetail.php?jobID=" + jobID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                location = response1.getString("location");
                                username_pc = response1.getString("username_pc");
                                RID = response1.getString("RID");
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
        tvLocation.setText(location);
        tvJobTitle.setText(jobTitle);
        tvJobDone.setText(jobDone);
        tvUsername.setText(username_pc);
        tvJobDescription.setText(jobDescription);
        tvJobID.setText(jobID);
        setTitle(jobTitle);

        if (jobDone.equals("Yes")){
            btnSend.setVisibility(GONE);
        }

    }

    @Override
    public void onClick(View v) {

        if (jobDone.equals("Yes")){
            Toast.makeText(this, "Cannot send updates as job already done", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, technicianSendUpdate.class);
            intent.putExtra("jobID", jobID);
            intent.putExtra("jobTitle", jobTitle);
            intent.putExtra("jobDescription", jobDescription);
            finish();
            startActivity(intent);}
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getJobIDofTask.php?jobID="+ jobID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            if (array.length() == 0){
                                blank.setText("No data for now");
                                blank.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(GONE);
                            }
                            else{
                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);

                                    //adding the product to product list
                                    viewTechnicianJobDetail.add(new viewTechnicianJobDetailList(
                                            product.getString("jobID"),
                                            product.getString("imageURL"),
                                            product.getString("message"),
                                            product.getString("userID"),
                                            product.getString("username_technician")
                                    ));
                                }
                                //creating adapter object and setting it to recyclerview
                                viewTechnicianJobDetailListAdapter adapter = new viewTechnicianJobDetailListAdapter(mCtx, viewTechnicianJobDetail);
                                recyclerView.setAdapter(adapter);
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

        //adding our stringrequest to queue
        Volley.newRequestQueue(mCtx).add(stringRequest);
    }
}
