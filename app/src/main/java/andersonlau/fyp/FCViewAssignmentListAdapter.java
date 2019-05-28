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

public class FCViewAssignmentListAdapter extends RecyclerView.Adapter<FCViewAssignmentListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<FCViewAssignmentList> productList;
    String id;

    public FCViewAssignmentListAdapter(Context mCtx, List<FCViewAssignmentList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public FCViewAssignmentListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_pc_view_assignment, parent, false);
        return new FCViewAssignmentListAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FCViewAssignmentListAdapter.HistoryViewHolder holder, int position) {
        FCViewAssignmentList product = productList.get(position);

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
            if (FOD_approved.getText().toString().equals("No") || FOD_approved.getText().toString().equals("Disapprove")  && jobCreated.getText().toString().equals("No")) {
                String pRID = RID.getText().toString();
                String pID = ID.getText().toString();
                Intent intent = new Intent(mCtx, financialControllerSendCalculation.class);
                intent.putExtra("RID", pRID);
                intent.putExtra("ID", pID);
                mCtx.startActivity(intent);
            }

            else if (FOD_approved.getText().toString().equals("No ")){
                String String_error = "Wait for FOD to reply.";
                Toast.makeText(mCtx, String_error, Toast.LENGTH_SHORT).show();
            }
            else
            {
                String String_error = "FOD already approved the assignment, cannot send calculation.";
                Toast.makeText(mCtx, String_error, Toast.LENGTH_SHORT).show();
            }
        }

    }
}