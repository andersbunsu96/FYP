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

public class FCViewCalculationListAdapter extends RecyclerView.Adapter<FCViewCalculationListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<FODViewCalculationList> productList;
    String id;

    public FCViewCalculationListAdapter(Context mCtx, List<FODViewCalculationList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public FCViewCalculationListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_fc_view_calculation, parent, false);
        return new FCViewCalculationListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull FCViewCalculationListAdapter.HistoryViewHolder holder, int position) {
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
            username_pc = itemView.findViewById(R.id.tvUsernamePC);
            calcDesc = itemView.findViewById(R.id.tvCalcDesc);
            approved = itemView.findViewById(R.id.tvApproved);

        }

        @Override
        public void onClick(View v) {
        }

    }
}
