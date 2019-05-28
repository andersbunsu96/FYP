package andersonlau.fyp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;
import android.preference.PreferenceManager;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class financialControllerSendCalculation extends AppCompatActivity implements View.OnClickListener {

    TextView tvRID, tvID, tvPC, tvAssignmentName, tvFile, tvAssignmentDesc;
    EditText etCalDesc;
    Button btnSend, btnChoose, sendPDF;
    Uri filePath;
    String UPLOAD_URL = "https://integratedcustomerinfomationsystem.000webhostapp.com/sendPDF.php";
    String filename;
    File file;
    String PdfNameHolder, PdfPathHolder, PdfID;
    String RID, ID, PC, assignmentName, username_fc, assignmentDesc;
    String encoded;

    SharedPreferences prefs;

    com.android.volley.RequestQueue requestQueue;
    ProgressDialog progressDialog;

    private static final int PICKFILE_RESULT_CODE = 1;
    public int PDF_REQ_CODE = 1;
    public static final String PDF_UPLOAD_HTTP_URL = "https://integratedcustomerinfomationsystem.000webhostapp.com/uploadpdf.php";

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_controller_send_calculation);

        Bundle bundle = getIntent().getExtras();
        ID = bundle.getString("ID");

        progressDialog = new ProgressDialog(financialControllerSendCalculation.this);

        setTitle("Send Calculation");

        tvAssignmentName = findViewById(R.id.tvAssignmentName);
        tvRID = findViewById(R.id.tvRID);
        tvID = findViewById(R.id.tvID);
        tvPC = findViewById(R.id.tvPC);
        tvFile = findViewById(R.id.tvFile);
        tvAssignmentDesc = findViewById(R.id.tvAssignmentDesc);
        sendPDF = findViewById(R.id.sendPDF);

        btnSend = findViewById(R.id.btnSend);
        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(this);
        etCalDesc = findViewById(R.id.etCalDesc);
        btnSend.setOnClickListener(this);
        sendPDF.setOnClickListener(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        username_fc = prefs.getString("username", "none");

        requestQueue = Volley.newRequestQueue(financialControllerSendCalculation.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        loadDetail();
    }

    @Override
    public void onClick(View v) {

        if (v == btnChoose) {

            showFileChooser();

        } else if (v == btnSend) {

            sendFile();
        }

        else if (v == sendPDF)
        {
            PdfUploadFunction();
        }
    }

    private void loadDetail() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        System.out.println("Enter loadDetail");
        String URL_PRODUCTS = "https://integratedcustomerinfomationsystem.000webhostapp.com/getIDFCforSendCalc.php?ID=" + ID;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                RID = response1.getString("RID");
                                ID = response1.getString("ID");
                                PC = response1.getString("username_pc");
                                assignmentName = response1.getString("assignmentName");
                                assignmentDesc = response1.getString("assignmentDesc");

                                loadFromSite();
                                progressDialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        System.out.println("Exit loadProduct");
    }

    private void loadFromSite() {
        System.out.println("Enter loadFromSite");
        tvRID.setText(RID);
        tvID.setText(ID);
        tvAssignmentName.setText(assignmentName);
        tvPC.setText(PC);
        tvAssignmentDesc.setText(assignmentDesc);

    }

    private void showFileChooser() {

        Intent intent = new Intent();
        System.out.println("here");
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            uri = data.getData();

            btnChoose.setText("PDF is Selected");

        }
    }

    // PDF upload function starts from here.
    public void PdfUploadFunction() {

        // Getting pdf name from EditText.

        // Getting file path using Filepath class.

        PdfNameHolder = "this";

        PdfPathHolder = FilePath.getPath(this, uri);

        // If file path object is null then showing toast message to move file into internal storage.
        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        }
        // If file path is not null then PDF uploading file process will starts.
        else {

            try {

                PdfID = UUID.randomUUID().toString();

                new MultipartUploadRequest(this, PdfID, PDF_UPLOAD_HTTP_URL)
                        .addFileToUpload(PdfPathHolder, "pdf")
                        .addParameter("name", "test")
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload();

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void sendFile() {

        final String SERVER_ADDRESS = "https://integratedcustomerinfomationsystem.000webhostapp.com/sendCalculation.php";

        final String calcDesc = etCalDesc.getText().toString().trim();
        final String RID = tvRID.getText().toString().trim();
        final String ID = tvID.getText().toString().trim();
        final String PC = tvPC.getText().toString().trim();
        final String calcURL = "Test jer";
        final String approved = "No ";

        if (TextUtils.isEmpty(calcDesc)) {
            etCalDesc.setError("Please enter calculation description");
            etCalDesc.requestFocus();
        } else {

            progressDialog.setMessage("Adding new record");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            System.out.println("server: " + ServerResponse);
                            // Showing Echo Response Message Coming From Server.
                            Toast.makeText(financialControllerSendCalculation.this, ServerResponse, Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(financialControllerSendCalculation.this, FinancialController.class);
                            startActivity(intent);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            // Hiding the progress dialog after all task complete.
                            progressDialog.dismiss();
                            // Showing error message if something goes wrong.
                            Toast.makeText(financialControllerSendCalculation.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("PC", PC);
                    params.put("username_fc", username_fc);
                    params.put("calcDesc", calcDesc);
                    params.put("ID", ID);
                    params.put("RID", RID);
                    params.put("calcURL", calcURL);
                    params.put("approved", approved);

                    return params;
                }
            };
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }
    }

}
