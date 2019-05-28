package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "https://integratedcustomerinfomationsystem.000webhostapp.com/login.php";
    public static final String UPLOAD_USR = "username";
    public static final String UPLOAD_PWD = "password";
    private EditText username;
    private EditText password;
    private Button login;

    String getUsr, getPwd;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernamePref = "username";
    public static final String passwordPref = "password";
    public static final String userType = "type";
    public String usrType;
    public boolean isLogin = false;
    SharedPreferences sharedpreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = sharedpreferences.getBoolean("isLogin", false);
        if (isLogin){
            usrType = sharedpreferences.getString("type", "none");
            if (usrType.equals("Clerk")){
                System.out.println(usrType);
                Intent intent = new Intent(MainActivity.this, ClerkDashboard.class);
                startActivity(intent);
                finish();
            }

            else if (usrType.equals("Project Coordinator")){
                Intent intent = new Intent(MainActivity.this, ProjectCoordinatorDashboard.class);
                startActivity(intent);
                finish();
            }

            else if (usrType.equals("Financial Controller")){
                Intent intent = new Intent(MainActivity.this, FinancialController.class);
                startActivity(intent);
                finish();
            }

            else if (usrType.equals("FOD")){
                Intent intent = new Intent(MainActivity.this, FODDashboard.class);
                startActivity(intent);
                finish();
            }

            else if (usrType.equals("Team Leader")){
                Intent intent = new Intent(MainActivity.this, TeamLeaderDashboard.class);
                startActivity(intent);
                finish();
            }

            else if (usrType.equals("Technician")){
                Intent intent = new Intent(MainActivity.this, TechnicianDashboard.class);
                startActivity(intent);
                finish();
            }
        }
         else {
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            username = findViewById(R.id.etUsername);
            password = findViewById(R.id.etPassword);
            login = findViewById(R.id.buttonSignIn);

            login.setOnClickListener(this);
        }
    }

    private void goLogin() {
        class UploadImage extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Signing In", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {//This part involves the php codes
                super.onPostExecute(s);
                loading.dismiss();
                System.out.println("s is " +s);
                String usrType="";
                if (!s.equals("fail")) {// IF in php, the data was found AND IF the echo produced is "Correct", then...
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    if (s.equals("Clerk")){
                        usrType ="Clerk";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type:" + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, ClerkDashboard.class);
                        startActivity(intent);
                    }

                    else if (s.equals("Project Coordinator")){

                        usrType = "Project Coordinator";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, ProjectCoordinatorDashboard.class);
                        startActivity(intent);
                    }

                    else if (s.equals("Financial Controller")){

                        usrType = "Financial Controller";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, FinancialController.class);
                        startActivity(intent);

                    }

                    else if (s.equals("FOD")){
                        usrType = "FOD";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, FODDashboard.class);
                        startActivity(intent);
                    }
                    else if (s.equals("Team Leader"))
                    {
                        usrType = "Team Leader";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, TeamLeaderDashboard.class);
                        startActivity(intent);
                    }
                    else if (s.equals("Technician"))
                    {
                        usrType = "Technician";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.putBoolean("isLogin", true);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, TechnicianDashboard.class);
                        startActivity(intent);
                    }

                    else if (s.equals("Register"))
                    {
                        usrType = "Register";
                        editor.putString(usernamePref, getUsr);
                        editor.putString(passwordPref, getPwd);
                        editor.putString(userType, usrType);
                        editor.commit();
                        System.out.println("user type: " + usrType);
                        finish();//The operation from php will be ended (finish()) and..
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(intent);
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();

                data.put(UPLOAD_USR, getUsr);
                data.put(UPLOAD_PWD, getPwd);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }
    @Override
    public void onClick(View view) {
        if (view == login) {
            int gotUsername = 0;
            int gotPassword = 0;
            if (view == login) {
                getPwd = password.getText().toString();
                getUsr = username.getText().toString();
                if (getUsr.length() > 0) {
                    gotUsername = 1;

                }
                if (getPwd.length() > 0) {
                    gotPassword = 1;
                }

                /* Proceed to login */
                if (gotUsername == 1 && gotPassword == 1) {
                    goLogin();
                }

                /* print error message */
                else if (gotUsername == 0 && gotPassword == 1) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 1 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 0 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}
