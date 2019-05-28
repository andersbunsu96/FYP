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

import java.util.List;

public class recordedRequestListAdapter extends RecyclerView.Adapter<recordedRequestListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewRecordedRequestList> productList;
    String id;

    public recordedRequestListAdapter(Context mCtx, List<viewRecordedRequestList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public recordedRequestListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_view_recorded_request, parent, false);
        return new recordedRequestListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull recordedRequestListAdapter.HistoryViewHolder holder, int position) {
        viewRecordedRequestList product = productList.get(position);

        holder.RID.setText(product.getRID());
        holder.requestTitle.setText(product.getRequestTitle());
        holder.requestDescription.setText(product.getRequestDescription());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RID, requestTitle, requestDescription;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(this);
            RID = itemView.findViewById(R.id.tvRID);
            requestTitle = itemView.findViewById(R.id.tvRequestTitle);
            requestDescription = itemView.findViewById(R.id.tvRequestDescription);

        }

        @Override
        public void onClick(View v) {
            String pRID = RID.getText().toString();
            Intent intent = new Intent(mCtx, recordedRequestDetail.class);
            intent.putExtra("RID", pRID);
            mCtx.startActivity(intent);
        }
    }
}