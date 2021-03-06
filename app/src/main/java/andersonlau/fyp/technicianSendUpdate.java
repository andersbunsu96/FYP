package andersonlau.fyp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class technicianSendUpdate extends AppCompatActivity implements View.OnClickListener {

    String jobID = "", jobTitle = "", username_technician = "", message = "";
    TextView tvJobID, tvJobTitle;
    ImageView ImageView;
    EditText etMessage;
    Button btnSend;
    Bitmap bm1;
    SharedPreferences prefs;
    com.android.volley.RequestQueue requestQueue;

    ProgressDialog progressDialog;

    boolean imagepresent = false;

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int RESULT_LOAD_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_send_update);

        tvJobID = findViewById(R.id.tvJobID);
        tvJobTitle = findViewById(R.id.tvJobTitle);

        ImageView = findViewById(R.id.ImageView);

        etMessage = findViewById(R.id.etMessage);

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        ImageView.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        jobID = bundle.getString("jobID");
        jobTitle = bundle.getString("jobTitle");
        setTitle(jobTitle);

        requestQueue = Volley.newRequestQueue(technicianSendUpdate.this);

        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username_technician = prefs.getString("username", "none");

        loadFromSite();
    }

    private void loadFromSite() {
        System.out.println("Enter loadFromSite");
        tvJobID.setText(jobID);
        tvJobTitle.setText(jobTitle);
    }

    public void dispatchTakePictureIntent() {

        final CharSequence[] items={"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(technicianSendUpdate.this);
        builder.setTitle("Choose Picture");
        builder.setIcon(R.drawable.ic_icis);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(items[which].equals("Camera")){

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    System.out.println("image_one_is_capture");
                }
                else if(items[which].equals("Gallery")){

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select file"), RESULT_LOAD_IMAGE);
                    System.out.println("image_one_is_capture");
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView.setImageBitmap(imageBitmap);
                bm1 = imageBitmap;
                return;
            }
            else if(requestCode == RESULT_LOAD_IMAGE){

                Uri selectedImage = data.getData();
                ImageView.setImageURI(selectedImage);
                bm1 = ((BitmapDrawable) ImageView.getDrawable()).getBitmap();
                imagepresent = true;
                return;
            }
        }
    }

    private void uploadImage(){

        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            final String message = etMessage.getText().toString().trim();
            final String jobID = tvJobID.getText().toString().trim();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(technicianSendUpdate.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                System.out.println("Error: \n" + s);
                Intent intent = new Intent (technicianSendUpdate.this, TechnicianDashboard.class);
                finish();
                startActivity(intent);
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bm1 = params[0];
                String uploadImage = getStringImage(bm1);
                HashMap<String,String> data = new HashMap<>();

                data.put("img", uploadImage);
                data.put("message", message);
                data.put("jobID", jobID);
                data.put("username_technician", username_technician);
                String result = rh.sendPostRequest("https://integratedcustomerinfomationsystem.000webhostapp.com/technicianUploadImage.php",data);
                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bm1);
    }

    @Override
    public void onClick(View v) {

        if (v == ImageView) { dispatchTakePictureIntent();
        } else if (v == btnSend) {

            final String message = etMessage.getText().toString().trim();
            final String jobID = tvJobID.getText().toString().trim();

            if (TextUtils.isEmpty(message)) {
                etMessage.setError("Please enter calculation description");
                etMessage.requestFocus();
            }

            else if (!imagepresent) {
                Toast.makeText(technicianSendUpdate.this, "Please upload image first", Toast.LENGTH_LONG).show(); }

                else { uploadImage();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
