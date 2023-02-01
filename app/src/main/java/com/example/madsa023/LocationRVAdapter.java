package com.example.madsa023;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class LocationRVAdapter extends ListAdapter<LocationModal, LocationRVAdapter.ViewHolder> {

    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // creating a constructor class for our adapter class.
    LocationRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<LocationModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<LocationModal>() {
        @Override
        public boolean areItemsTheSame(LocationModal oldItem, LocationModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(LocationModal oldItem, LocationModal newItem) {
            // below line is to check the Location name, description and Location duration.
            return oldItem.getLocationName().equals(newItem.getLocationName()) &&
                    oldItem.getLocationDescription().equals(newItem.getLocationDescription()) &&
                    oldItem.getLocationDuration().equals(newItem.getLocationDuration());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        LocationModal model = getLocationAt(position);
        holder.LocationNameTV.setText(model.getLocationName());
        holder.LocationDescTV.setText(model.getLocationDescription());
        holder.LocationDurationTV.setText(model.getLocationDuration());
    }

    // creating a method to get Location modal for a specific position.
    public LocationModal getLocationAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView LocationNameTV, LocationDescTV, LocationDurationTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            LocationNameTV = itemView.findViewById(R.id.idTVLocationName);
            LocationDescTV = itemView.findViewById(R.id.idTVLocationDescription);
            LocationDurationTV = itemView.findViewById(R.id.idTVLocationDuration);

            // adding on click listener for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // inside on click listener we are passing
                    // position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LocationModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

