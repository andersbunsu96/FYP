package andersonlau.fyp;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class viewReportListAdapter extends RecyclerView.Adapter<viewReportListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewReportList> productList;
    String id;

    public viewReportListAdapter(Context mCtx, List<viewReportList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public viewReportListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_report, parent, false);
        return new viewReportListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewReportListAdapter.HistoryViewHolder holder, int position) {
        viewReportList product = productList.get(position);

        holder.jobDoneID.setText(product.getJobDoneID());
        holder.jobID.setText(product.getJobID());
        holder.RID.setText(product.getRID());
        holder.message.setText(product.getMessage());
        holder.username_pc.setText(product.getUsername_pc());
        holder.jobTitle.setText(product.getJobTitle());
        holder.jobDescription.setText(product.getJobDescription());
        holder.location.setText(product.getLocation());
        holder.imageURL.setText(product.getImageURL());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobDoneID, jobID, RID, message, username_pc, jobTitle, jobDescription, location, imageURL;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            jobDoneID = itemView.findViewById(R.id.tvJobDoneID);
            jobID = itemView.findViewById(R.id.tvJobID);
            RID = itemView.findViewById(R.id.tvRID);
            message = itemView.findViewById(R.id.tvMessage);
            username_pc = itemView.findViewById(R.id.tvUsernamePC);
            jobTitle = itemView.findViewById(R.id.tvJobTitle);
            jobDescription = itemView.findViewById(R.id.tvJobDescription);
            location = itemView.findViewById(R.id.tvLocation);
            imageURL = itemView.findViewById(R.id.tvImageURL);
        }

        @Override
        public void onClick(View v) {

            String pRID = RID.getText().toString();
            String pJobDone = jobDoneID.getText().toString();
            String pJobID = jobID.getText().toString();
            String pMessage = message.getText().toString();
            String pUsername_pc = username_pc.getText().toString();
            String pJobTitle = jobTitle.getText().toString();
            String pJobDescription = jobDescription.getText().toString();
            String pLocation = location.getText().toString();
            String pImageURL = imageURL.getText().toString();

            Intent intent = new Intent(mCtx, generateReport.class);
            intent.putExtra("RID", pRID);
            intent.putExtra("jobDoneID", pJobDone);
            intent.putExtra("jobID", pJobID);
            intent.putExtra("message", pMessage);
            intent.putExtra("username_pc", pUsername_pc);
            intent.putExtra("jobTitle", pJobTitle);
            intent.putExtra("jobDescription", pJobDescription);
            intent.putExtra("location", pLocation);
            intent.putExtra("imageURL", pImageURL);

            mCtx.startActivity(intent);
        }
    }
}