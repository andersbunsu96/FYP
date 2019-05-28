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

public class viewProgressionListAdapter extends RecyclerView.Adapter<viewProgressionListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewProgressionList> productList;
    String id;

    public viewProgressionListAdapter(Context mCtx, List<viewProgressionList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public viewProgressionListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_progression, parent, false);
        return new viewProgressionListAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewProgressionListAdapter.HistoryViewHolder holder, int position) {
        viewProgressionList product = productList.get(position);
        String requestSent, assignmentSent, FODApprove, createJob, technicianAssign, jobDone;

        requestSent = product.getRequestSent();
        assignmentSent = product.getAssignmentSent();
        FODApprove = product.getFODApprove();
        createJob = product.getCreateJob();
        technicianAssign = product.getTechnicianAssign();
        jobDone = product.getJobDone();

        holder.RID.setText(product.getRID());
        holder.requestTitle.setText(product.getRequestTitle());
        holder.requestSent.setText(product.getRequestSent());
        holder.assignmentSent.setText(product.getAssignmentSent());
        holder.FODApprove.setText(product.getFODApprove());
        holder.createJob.setText(product.getCreateJob());
        holder.technicianAssign.setText(product.getTechnicianAssign());
        holder.jobDone.setText(product.getJobDone());

        if (requestSent.equals("Yes")){
            holder.requestSent.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

        if (assignmentSent.equals("Yes")){
            holder.assignmentSent.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

        if (FODApprove.equals("Yes")){
            holder.FODApprove.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

        if (createJob.equals("Yes")){
            holder.createJob.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

        if (technicianAssign.equals("Yes")){
            holder.technicianAssign.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

        if (jobDone.equals("Yes")){
            holder.jobDone.setBackgroundColor(mCtx.getResources().getColor(R.color.green));
        }

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RID, requestTitle, requestSent, assignmentSent, FODApprove, createJob, technicianAssign, jobDone;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            RID = itemView.findViewById(R.id.tvRID);
            requestTitle = itemView.findViewById(R.id.tvRequestTitle);
            requestSent = itemView.findViewById(R.id.tvRequestSent);
            assignmentSent = itemView.findViewById(R.id.tvAssignmentSent);
            FODApprove = itemView.findViewById(R.id.tvFOD);
            createJob = itemView.findViewById(R.id.tvCreateJob);
            technicianAssign = itemView.findViewById(R.id.tvTechnicianAssign);
            jobDone = itemView.findViewById(R.id.tvJobDone);

        }

        @Override
        public void onClick(View v) {

        }
    }
}