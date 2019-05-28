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

public class PCViewJobStatusListAdapter extends RecyclerView.Adapter<PCViewJobStatusListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<PCViewJobStatusList> productList;
    String id;

    public PCViewJobStatusListAdapter(Context mCtx, List<PCViewJobStatusList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public PCViewJobStatusListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_pc_view_job_status, parent, false);
        return new PCViewJobStatusListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PCViewJobStatusListAdapter.HistoryViewHolder holder, int position) {
        PCViewJobStatusList product = productList.get(position);

        holder.jobID.setText(product.getJobID());
        holder.RID.setText(product.getRID());
        holder.jobTitle.setText(product.getJobTitle());
        holder.jobDescription.setText(product.getJobDescription());
        holder.teamLeader.setText(product.getTeamLeader());
        holder.jobDone.setText(product.getJobDone());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RID, jobID, jobTitle, jobDescription, teamLeader, jobDone;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            jobID = itemView.findViewById(R.id.tvJID);
            RID = itemView.findViewById(R.id.tvRID);
            jobTitle = itemView.findViewById(R.id.tvJobTitle);
            jobDescription = itemView.findViewById(R.id.tvJobDesc);
            teamLeader = itemView.findViewById(R.id.tvTeamLeader);
            jobDone = itemView.findViewById(R.id.tvJobDone);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
