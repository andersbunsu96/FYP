package andersonlau.fyp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class technicianViewJobAdapter extends RecyclerView.Adapter<technicianViewJobAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<technicianViewJobList> productList;
    String id;

    public technicianViewJobAdapter(Context mCtx, List<technicianViewJobList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public technicianViewJobAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_technician_job, parent, false);
        return new technicianViewJobAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull technicianViewJobAdapter.HistoryViewHolder holder, int position) {
        technicianViewJobList product = productList.get(position);

        holder.jobID.setText(product.getJobID());
        holder.jobTitle.setText(product.getJobTitle());
        holder.jobDescription.setText(product.getJobDescription());
        holder.jobDone.setText(product.getJobDone());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jobID, jobTitle, jobDescription, jobDone;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            jobID = itemView.findViewById(R.id.tvJobID);
            jobTitle = itemView.findViewById(R.id.tvJobTitle);
            jobDescription = itemView.findViewById(R.id.tvJobDescription);
            jobDone = itemView.findViewById(R.id.tvJobDone);

        }

        @Override
        public void onClick(View v) {

                String pJobID = jobID.getText().toString();
                String pJobTitle = jobTitle.getText().toString();
                String pJobDescription = jobDescription.getText().toString();
                String pJobDone = jobDone.getText().toString();
                Intent intent = new Intent(mCtx, viewTechnicianJobDetail.class);
                intent.putExtra("jobID", pJobID);
                intent.putExtra("jobTitle", pJobTitle);
                intent.putExtra("jobDescription", pJobDescription);
                intent.putExtra("jobDone", pJobDone);

                mCtx.startActivity(intent);
        }
    }
}