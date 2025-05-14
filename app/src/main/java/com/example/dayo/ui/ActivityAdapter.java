package com.example.dayo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dayo.R;
import com.example.dayo.data.database.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.util.Log;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<Activity> activityList;
    private Context context;

    public ActivityAdapter(Context context, List<Activity> activityList) {
        this.context = context;
        this.activityList = activityList != null ? activityList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);
        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        Activity currentActivity = activityList.get(position);

        holder.activityTitle.setText(currentActivity.getName());
        holder.activityLocation.setText(currentActivity.getLocation());
        // Format price to show two decimal places and a euro or dollar sign
        holder.activityPrice.setText(String.format(Locale.getDefault(), "%.2f€", currentActivity.getPrice())); // Ή $
        holder.activityDuration.setText(String.format(Locale.getDefault(), "%d minutes", currentActivity.getDuration()));

        String imageName = currentActivity.getImageName();
        if (imageName != null && !imageName.isEmpty()) {
            // Το getIdentifier ψάχνει έναν πόρο με βάση το όνομα, τον τύπο ("drawable"), και το package.
            int imageResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

            if (imageResourceId != 0) { // Αν βρέθηκε το resource ID
                holder.activityImage.setImageResource(imageResourceId);
            } else {
                // Η εικόνα δεν βρέθηκε, βάλε ένα placeholder ή logαρε το σφάλμα
                Log.w("ActivityAdapter", "Drawable resource not found for: " + imageName);
                holder.activityImage.setImageResource(R.drawable.exampleimage);
            }
        } else {
            // Δεν υπάρχει όνομα εικόνας, βάλε ένα placeholder
            Log.w("ActivityAdapter", "No image name");
            holder.activityImage.setImageResource(R.drawable.exampleimage); // Βάλε μια default εικόνα
        }

        // TODO: Πρόσθεσε OnClickListener για το holder.bookNowButton αν χρειάζεται
        // holder.bookNowButton.setOnClickListener(v -> {
        //     // Λογική για κλικ στο κουμπί
        // });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public void setActivities(List<Activity> activities) {
        this.activityList = activities != null ? activities : new ArrayList<>();
        notifyDataSetChanged(); // Ενημέρωσε το RecyclerView για τις αλλαγές
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        ImageView activityImage;
        TextView activityTitle;
        TextView activityLocation;
        TextView activityPrice;
        TextView activityDuration;
        Button bookNowButton;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            activityImage = itemView.findViewById(R.id.activityImage);
            activityTitle = itemView.findViewById(R.id.activityTitle);
            activityLocation = itemView.findViewById(R.id.activitylocation); // Έλεγξε το ID, στο XML είναι activitylocation
            activityPrice = itemView.findViewById(R.id.activitprice);     // Έλεγξε το ID, στο XML είναι activitprice
            activityDuration = itemView.findViewById(R.id.activityduration); // Έλεγξε το ID, στο XML είναι activityduration
            bookNowButton = itemView.findViewById(R.id.bookNowButton);
        }
    }
}