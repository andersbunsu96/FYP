package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class TechnicianDashboard extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;
    String username;
    TextView textUsername;
    ImageButton updateLocation, viewJob, viewJobDone;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_dashboard);

        com.android.volley.RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(TechnicianDashboard.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username","none");

        updateLocation = findViewById(R.id.updateLocation);
        viewJob = findViewById(R.id.viewJob);
        viewJobDone = findViewById(R.id.viewJobDone);

        updateLocation.setOnClickListener(this);
        viewJob.setOnClickListener(this);
        viewJobDone.setOnClickListener(this);

        textUsername = findViewById(R.id.textUsername);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

        textUsername.setText(username);
        setTitle("Technician Dashboard");
    }

    @Override
    public void onClick(View v) {
        if (v == viewJob){
            Intent intent = new Intent (this, technicianViewJob.class);
            startActivity(intent);
        }

        else if (v == viewJobDone){
            Intent intent = new Intent (this, technicianViewJobFinishJob.class);
            startActivity(intent);
        }

        else if (v == updateLocation){
            Intent intent = new Intent (this, technicianUpdateLocation.class);
            startActivity(intent);
        }

        else  if (v == logout)
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
