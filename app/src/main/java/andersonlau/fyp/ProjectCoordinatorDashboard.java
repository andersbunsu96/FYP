package andersonlau.fyp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class ProjectCoordinatorDashboard extends AppCompatActivity implements View.OnClickListener{

    ImageButton btnCreateJob, btnAssignLeader, btnViewStatus, btnViewAssignment, btnViewRequest;
    SharedPreferences prefs;
    String username;
    TextView textUsername;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_coordinator_dashboard);

        btnViewAssignment = findViewById(R.id.btnViewAssignment);
        btnViewRequest = findViewById(R.id.btnViewRequest);
        btnViewStatus = findViewById(R.id.btnViewStatus);

        btnViewStatus.setOnClickListener(this);
        btnViewRequest.setOnClickListener(this);
        btnViewAssignment.setOnClickListener(this);

        com.android.volley.RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(ProjectCoordinatorDashboard.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username","none");

        textUsername = findViewById(R.id.textUsername);

        textUsername.setText(username);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnViewRequest)
        {
            Intent intent = new Intent (this, viewRequestPC.class);
            startActivity(intent);
        }


         else if (v == btnViewStatus)
        {
            Intent intent = new Intent(this, PCViewJobStatusActivity.class);
            startActivity(intent);
        }

        else if (v == btnViewAssignment)
        {
            Intent intent = new Intent(this, PCViewAssignment.class);
            startActivity(intent);
        }

        else if (v == logout)
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
