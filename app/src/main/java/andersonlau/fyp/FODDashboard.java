package andersonlau.fyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FODDashboard extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences prefs;
    String username;
    ImageButton btnViewCal, btnViewApprovedCal;
    TextView tvUsername;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foddashboard);

        tvUsername = findViewById(R.id.tvUsername);

        btnViewCal = findViewById(R.id.btnViewCal);
        btnViewApprovedCal = findViewById(R.id.btnViewApprovedCal);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username = prefs.getString("username", "none");

        tvUsername.setText(username);

        btnViewCal.setOnClickListener(this);
        btnViewApprovedCal.setOnClickListener(this);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.logout, menu);

        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == btnViewCal)
        {
            Intent intent = new Intent(this, FODViewCalculation.class);
            startActivity(intent);
        }

        else if (v == btnViewApprovedCal){
            Intent intent = new Intent(this, FODViewApprovedCalculation.class);
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
