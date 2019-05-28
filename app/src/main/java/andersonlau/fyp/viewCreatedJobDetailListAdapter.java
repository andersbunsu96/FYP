package andersonlau.fyp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class viewCreatedJobDetailListAdapter extends RecyclerView.Adapter<viewCreatedJobDetailListAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<viewCreatedJobDetailList> productList;
    String id;

    public viewCreatedJobDetailListAdapter(Context mCtx, List<viewCreatedJobDetailList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public viewCreatedJobDetailListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rv_job, parent, false);
        return new viewCreatedJobDetailListAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewCreatedJobDetailListAdapter.HistoryViewHolder holder, int position) {
        viewCreatedJobDetailList product = productList.get(position);

        holder.jobID.setText(product.getJobID());
        Glide.with(mCtx)
                .load(product.getImageURL())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(holder.imageURL);
        holder.message.setText(product.getMessage());
        holder.userID.setText(product.getUserID());
        holder.username_technician.setText(product.getUsername_technician());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userID, jobID, message, username_technician;
        ImageView imageURL;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            jobID = itemView.findViewById(R.id.tvJobID);
            imageURL = itemView.findViewById(R.id.imageView);
            message = itemView.findViewById(R.id.tvMessage);
            userID = itemView.findViewById(R.id.tvUserID);
            username_technician = itemView.findViewById(R.id.tvUsernameTechnician);
        }

        @Override
        public void onClick(View v) {

        }
    }
}