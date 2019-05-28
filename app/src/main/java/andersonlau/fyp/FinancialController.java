package andersonlau.fyp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

public class FinancialController extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences prefs;
    String username;
    TextView textUsername;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_controller);

        com.android.volley.RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(FinancialController.this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username", "none");

        textUsername = findViewById(R.id.textUsername);

        textUsername.setText(username);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    public void activityViewAssignment(View v) {
        Intent myIntent = new Intent(getBaseContext(),
                FCViewAssignment.class);
        startActivity(myIntent);
    }

    public void activityViewCalculation(View v) {
        Intent myIntent = new Intent(getBaseContext(),
                financialControllerViewCalculation.class);
        startActivity(myIntent);
    }

    public void activityViewAssignmentDone(View v)
    {
        Intent myIntent = new Intent(getBaseContext(),FCViewAssignmentDone.class);
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
