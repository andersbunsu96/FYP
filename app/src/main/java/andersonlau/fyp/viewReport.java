package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

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

import static android.view.View.GONE;

public class viewReport extends AppCompatActivity {
    RecyclerView recyclerView;
    List<viewReportList> viewReport;

    ProgressDialog progressDialog;
    TextView blank;
    Context mCtx;

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        viewReport = new ArrayList<>();
        blank = findViewById(R.id.blank);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mCtx = this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadProducts();
        setTitle("View Finished Job");
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getReport.php";
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
                                progressDialog.dismiss();
                            }else {

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);

                                    //adding the product to product list
                                    viewReport.add(new viewReportList(
                                            product.getString("jobDoneID"),
                                            product.getString("jobID"),
                                            product.getString("RID"),
                                            product.getString("message"),
                                            product.getString("username_pc"),
                                            product.getString("jobTitle"),
                                            product.getString("jobDescription"),
                                            product.getString("location"),
                                            product.getString("imageURL")

                                    ));
                                }
                                //creating adapter object and setting it to recyclerview
                                viewReportListAdapter adapter = new viewReportListAdapter(mCtx, viewReport);
                                recyclerView.setAdapter(adapter);
                                progressDialog.dismiss();
                            }} catch (JSONException e) {
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
