package andersonlau.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class record_request_chooser extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnNormal, btnEmergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_request_chooser);
        setTitle("Record Customer Request");
        btnNormal = findViewById(R.id.btnNormal);
        btnEmergency = findViewById(R.id.btnEmergency);

        btnNormal.setOnClickListener(this);
        btnEmergency.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnNormal)
        {
            Intent myIntent = new Intent(this, RecordCustomerRequest.class);
            startActivity(myIntent);
        }

        else if (v == btnEmergency)
        {
            Intent myIntent = new Intent(this, EmergencyJob.class);
            startActivity(myIntent);
        }

    }
}
