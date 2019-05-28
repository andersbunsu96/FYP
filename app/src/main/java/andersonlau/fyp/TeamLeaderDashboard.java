package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class TeamLeaderDashboard extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences prefs;
    String username;
    TextView textUsername;
    ImageButton tvLocation, tvCreatedJob, tvFinishedJob;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_leader_dashboard);
        com.android.volley.RequestQueue requestQueue;

        tvLocation = findViewById(R.id.tvLocation);
        tvCreatedJob = findViewById(R.id.tvCreatedJob);
        tvFinishedJob = findViewById(R.id.tvFinishedJob);

        tvLocation.setOnClickListener(this);
        tvCreatedJob.setOnClickListener(this);
        tvFinishedJob.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(TeamLeaderDashboard.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username","none");

        textUsername = findViewById(R.id.textUsername);

        textUsername.setText(username);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvCreatedJob){
            Intent intent = new Intent (this, viewCreatedJobActivity.class);
            startActivity(intent);
        }

        else if (v == tvFinishedJob){
            Intent intent = new Intent (this, viewCreatedJobActivityDone.class);
            startActivity(intent);
        }

        else if (v == tvLocation){
            Intent intent = new Intent (this, viewLocation.class);
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
