package andersonlau.fyp;

import android.app.Activity;
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

public class FODViewCalculationListAdapter extends RecyclerView.Adapter<FODViewCalculationListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<FODViewCalculationList> productList;
    String id;

    public FODViewCalculationListAdapter(Context mCtx, List<FODViewCalculationList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public FODViewCalculationListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_fc_view_calculation, parent, false);
        return new FODViewCalculationListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FODViewCalculationListAdapter.HistoryViewHolder holder, int position) {
        FODViewCalculationList product = productList.get(position);

        holder.calcID.setText(product.getCalcID());
        holder.AID.setText(product.getAID());
        holder.username_pc.setText(product.getUsername_pc());
        holder.username_fc.setText(product.getUsername_fc());
        holder.calcDesc.setText(product.getCalcDesc());
        holder.approved.setText(product.getApproved());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView calcID, AID, username_pc, username_fc, calcDesc, approved;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            calcID = itemView.findViewById(R.id.tvCalcID);
            AID = itemView.findViewById(R.id.tvAID);
            username_fc = itemView.findViewById(R.id.tvUsernameFC);
            username_pc =itemView.findViewById(R.id.tvUsernamePC);
            calcDesc = itemView.findViewById(R.id.tvCalcDesc);
            approved = itemView.findViewById(R.id.tvApproved);

        }

        @Override
        public void onClick(View v) {

            if (approved.getText().toString().equals("No") || approved.getText().toString().equals("No ") || approved.getText().toString().equals("Noo")){
            String pcalcID = calcID.getText().toString();
            Intent intent = new Intent(mCtx, FODViewCalculationDetail.class);
            intent.putExtra("calcID", pcalcID);
            ((Activity)mCtx).finish();
            mCtx.startActivity(intent);}

             else {
                    String String_error = "It's already been approved";
                    Toast.makeText(mCtx, String_error, Toast.LENGTH_SHORT).show();
                }
        }
    }
}