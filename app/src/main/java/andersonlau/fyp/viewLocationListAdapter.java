package andersonlau.fyp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewLocationListAdapter extends RecyclerView.Adapter<viewLocationListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewLocationList> productList;
    String id;

    public viewLocationListAdapter(Context mCtx, List<viewLocationList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public viewLocationListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_location, parent, false);
        return new viewLocationListAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewLocationListAdapter.HistoryViewHolder holder, int position) {
        viewLocationList product = productList.get(position);

        holder.username.setText(product.getUsername());
        holder.location.setText(product.getLocation());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView username, location;
        CardView card;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            username = itemView.findViewById(R.id.tvUsername);
            location = itemView.findViewById(R.id.tvLocation);
        }

        @Override
        public void onClick(View v) {

        }
    }
}