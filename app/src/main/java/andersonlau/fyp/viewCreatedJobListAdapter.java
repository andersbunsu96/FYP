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

public class viewCreatedJobListAdapter extends RecyclerView.Adapter<viewCreatedJobListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewCreatedJobList> productList;
    String id;

    public viewCreatedJobListAdapter(Context mCtx, List<viewCreatedJobList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public viewCreatedJobListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_tl_view_job, parent, false);
        return new viewCreatedJobListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewCreatedJobListAdapter.HistoryViewHolder holder, int position) {
        viewCreatedJobList product = productList.get(position);

        holder.jobID.setText(product.getJobID());
        holder.jobTitle.setText(product.getJobTitle());
        holder.jobDescription.setText(product.getJobDescription());
        holder.teamLeader.setText(product.getTeamLeader());
        holder.location.setText(product.getLocation());
        holder.jobDone.setText(product.getJobDone());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobID, jobTitle, jobDescription, teamLeader, location, jobDone;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            jobID = itemView.findViewById(R.id.tvJobID);
            jobTitle = itemView.findViewById(R.id.tvJobTitle);
            jobDescription = itemView.findViewById(R.id.tvJobDescription);
            teamLeader = itemView.findViewById(R.id.tvTeamLeader);
            location = itemView.findViewById(R.id.tvLocation);
            jobDone = itemView.findViewById(R.id.tvJobDone);

        }

        @Override
        public void onClick(View v) {
                String pJobID = jobID.getText().toString();
                String pJobTitle = jobTitle.getText().toString();
                String pJobDescription = jobDescription.getText().toString();
                String pLocation = location.getText().toString();
                String pJobDone = jobDone.getText().toString();
                Intent intent = new Intent(mCtx, viewCreatedJobDetail.class);
                intent.putExtra("jobID", pJobID);
                intent.putExtra("jobDescription", pJobDescription);
                intent.putExtra("jobTitle", pJobTitle);
                intent.putExtra("jobDone", pJobDone);
                intent.putExtra("location", pLocation);
                mCtx.startActivity(intent);
        }
    }
}