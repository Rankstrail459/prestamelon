package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OffersCatalogueAdapter extends RecyclerView.Adapter<OffersCatalogueAdapter.ViewHolder> {

    private CatalogueOffer[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewItemName, textViewItemPrice, textViewNextAvailability;
        public final ImageView imageViewItemMainPhoto;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewItemName = view.findViewById(R.id.text_view_item_name);
            textViewItemPrice = view.findViewById(R.id.text_view_item_price);
            textViewNextAvailability = view.findViewById(R.id.text_view_next_availability);
            imageViewItemMainPhoto = view.findViewById(R.id.image_view_item_photo);

        }

        public TextView getTextViewItemName() {
            return textViewItemName;
        }
        public TextView getTextViewItemPrice() {
            return textViewItemPrice;
        }
        public TextView getTextViewNextAvailability() {
            return textViewNextAvailability;
        }
        public ImageView getImageViewItemMainPhoto() {
            return imageViewItemMainPhoto;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public OffersCatalogueAdapter(CatalogueOffer[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_catalogue_offer, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextViewItemName().setText(localDataSet[position].ItemName);
        viewHolder.getTextViewItemPrice().setText(localDataSet[position].ItemPrice);
        viewHolder.getTextViewNextAvailability().setText(localDataSet[position].NextAvailability);
        viewHolder.getImageViewItemMainPhoto().setImageBitmap(localDataSet[position].ItemMainPhoto);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class CatalogueOffer {
        public String ItemName, ItemPrice, NextAvailability;
        public Bitmap ItemMainPhoto;

        public CatalogueOffer(String itemName, String itemPrice, String nextAvailability, Bitmap itemMainPhoto) {
            ItemName = itemName;
            ItemPrice = itemPrice;
            NextAvailability = nextAvailability;

            ItemMainPhoto = itemMainPhoto;
        }
    }
}
