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

public class viewLocation extends AppCompatActivity {

    RecyclerView recyclerView;
    List<viewLocationList> viewLocation;

    TextView blank;
    Context mCtx;
    String username_fc, ID;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);

        setTitle("View Technician Location");
        viewLocation = new ArrayList<>();
        blank = findViewById(R.id.blank);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mCtx = this;
        SharedPreferences prefs;
        com.android.volley.RequestQueue requestQueue;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username_fc = prefs.getString("username", "none");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadProducts();
    }

    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getTechnicianLocation.php";
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
                                    viewLocation.add(new viewLocationList(
                                            product.getString("username"),
                                            product.getString("location")
                                    ));
                                }
                                //creating adapter object and setting it to recyclerview
                                viewLocationListAdapter adapter = new viewLocationListAdapter(mCtx, viewLocation);
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
