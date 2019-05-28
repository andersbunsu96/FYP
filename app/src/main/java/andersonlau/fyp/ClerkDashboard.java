package andersonlau.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class ClerkDashboard extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;
    String username;
    TextView textUsername;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clerk_dashboard);
        com.android.volley.RequestQueue requestQueue;
        System.out.println("jere");
        requestQueue = Volley.newRequestQueue(ClerkDashboard.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username", "none");

        textUsername = findViewById(R.id.textUsername);

        textUsername.setText(username);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

    }

    public void activityCreateJob(View v) {
        Intent myIntent = new Intent(getBaseContext(),
                record_request_chooser.class);
        startActivity(myIntent);
    }

    public void activityViewRecordedRequest(View v) {
        Intent myIntent = new Intent(getBaseContext(),
                viewRecordedRequest.class);
        startActivity(myIntent);
    }

    public void viewReport(View v) {
        Intent myIntent = new Intent(getBaseContext(),
                viewReport.class);
        startActivity(myIntent);
    }

    public void jobDone (View v)
    {
        Intent myIntent = new Intent(getBaseContext(), viewRecordedRequestNotDone.class);
        startActivity(myIntent);
    }

    public void viewProgression (View v)
    {
        Intent myIntent = new Intent(getBaseContext(), viewProgression.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View v) {
        if (v == logout)
        {

            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("isLogin");
            editor.remove("username");
            editor.remove("type");
            editor.remove("isLogin");
            editor.apply();finish();
            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(myIntent);
        }
    }
}
