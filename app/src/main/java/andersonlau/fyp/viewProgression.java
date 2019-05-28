package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.View.GONE;

public class viewProgression extends AppCompatActivity {
    RecyclerView recyclerView;
    List<viewProgressionList> viewProgression;

    ProgressDialog progressDialog;
    TextView blank;
    Context mCtx;

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_progression);

        viewProgression = new ArrayList<>();
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
        setTitle("View Progression");
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getProgression.php";
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
                                    viewProgression.add(new viewProgressionList(
                                            product.getString("RID"),
                                            product.getString("requestTitle"),
                                            product.getString("requestSent"),
                                            product.getString("assignmentSent"),
                                            product.getString("FODApprove"),
                                            product.getString("createJob"),
                                            product.getString("technicianAssign"),
                                            product.getString("jobDone")
                                    ));
                                }
                                //creating adapter object and setting it to recyclerview
                                viewProgressionListAdapter adapter = new viewProgressionListAdapter(mCtx, viewProgression);
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
