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

public class PCViewAssignmentListAdapter extends RecyclerView.Adapter<PCViewAssignmentListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<PCViewAssignmentList> productList;
    String id;

    public PCViewAssignmentListAdapter(Context mCtx, List<PCViewAssignmentList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public PCViewAssignmentListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_pc_view_assignment, parent, false);
        return new PCViewAssignmentListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PCViewAssignmentListAdapter.HistoryViewHolder holder, int position) {
        PCViewAssignmentList product = productList.get(position);

        holder.ID.setText(product.getID());
        holder.RID.setText(product.getRID());
        holder.assignmentName.setText(product.getAssignmentName());
        holder.assignmentDesc.setText(product.getAssignmentDesc());
        holder.FOD_approved.setText(product.getFOD_approved());
        holder.jobCreated.setText(product.getJobCreated());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RID, assignmentName, assignmentDesc, ID, FOD_approved, jobCreated;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            RID = itemView.findViewById(R.id.tvRID);
            ID = itemView.findViewById(R.id.tvID);
            assignmentName = itemView.findViewById(R.id.tvAssignmentName);
            assignmentDesc = itemView.findViewById(R.id.tvAssignmentDesc);
            FOD_approved = itemView.findViewById(R.id.tvFOD_approved);
            jobCreated = itemView.findViewById(R.id.tvJobCreated);

        }

        @Override
        public void onClick(View v) {
            if (FOD_approved.getText().toString().equals("Yes") && jobCreated.getText().toString().equals("No")){
            String pRID = RID.getText().toString();
            Intent intent = new Intent(mCtx, PCCreateJob.class);
            intent.putExtra("RID", pRID);
            mCtx.startActivity(intent);
            }

            else {
                String String_error = "Cannot make a job before FOD approved or the job already created";
                Toast.makeText(mCtx, String_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}