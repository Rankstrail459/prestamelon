package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewOfferItemsPhotosAdapter extends RecyclerView.Adapter<NewOfferItemsPhotosAdapter.ViewHolder> {

    private ItemPhoto[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageViewItemPhoto, imageViewMainPhotoCheck;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageViewItemPhoto = view.findViewById(R.id.image_view_item_photo);
            imageViewMainPhotoCheck = view.findViewById(R.id.image_view_main_photo_check);

        }

        public ImageView getImageViewItemPhoto() {
            return imageViewItemPhoto;
        }
        public ImageView getImageViewMainPhotoCheck() {
            return imageViewMainPhotoCheck;
        }
    }

    public NewOfferItemsPhotosAdapter(List<ItemPhoto> list) {
        ItemPhoto[] dataSet = new ItemPhoto[list.size()];

        for(int i = 0; i < dataSet.length; i++) {
            dataSet[i] = list.get(i);
        }

        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_new_offer_photo, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getImageViewItemPhoto().setImageBitmap(localDataSet[position].ItemPhoto);

        if (localDataSet[position].MainPhoto) {
            viewHolder.getImageViewMainPhotoCheck().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ItemPhoto {
        public Bitmap ItemPhoto;
        public boolean MainPhoto;

        public ItemPhoto(Bitmap itemPhoto, boolean mainPhoto) {
            ItemPhoto = itemPhoto;
            MainPhoto = mainPhoto;
        }
    }
}
