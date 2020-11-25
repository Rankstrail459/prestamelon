package com.tomasdarioam.prestamelon;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NewOfferItemsPhotosAdapter extends RecyclerView.Adapter<NewOfferItemsPhotosAdapter.ViewHolder> {

    private ItemPhoto[] mLocalDataSet;

    private ClickInterface mClickInterface;

    public interface ClickInterface {
        void itemPhotoClickEvent(int position);
        void buttonDeletePhotoClickEvent(int position);
    }

    public void setClickInterface(ClickInterface clickInterface) {
        mClickInterface = clickInterface;
    }

    private class ItemPhotoOnClickListener implements View.OnClickListener {
        private int mPosition;

        void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mClickInterface.itemPhotoClickEvent(mPosition);
        }
    }

    private class ButtonDeletePhotoOnClickListener implements View.OnClickListener {
        private int mPosition;

        void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mClickInterface.buttonDeletePhotoClickEvent(mPosition);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageViewItemPhoto, imageViewMainPhotoCheck;
        ItemPhotoOnClickListener itemPhotoOnClickListener;
        ButtonDeletePhotoOnClickListener buttonDeletePhotoOnClickListener;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageViewItemPhoto = view.findViewById(R.id.image_view_item_photo);

            itemPhotoOnClickListener = new ItemPhotoOnClickListener();
            imageViewItemPhoto.setOnClickListener(itemPhotoOnClickListener);

            imageViewMainPhotoCheck = view.findViewById(R.id.image_view_main_photo_check);

            FloatingActionButton buttonDeletePhoto = view.findViewById(R.id.button_delete_photo);

            buttonDeletePhotoOnClickListener = new ButtonDeletePhotoOnClickListener();
            buttonDeletePhoto.setOnClickListener(buttonDeletePhotoOnClickListener);
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

        mLocalDataSet = dataSet;
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

        viewHolder.getImageViewItemPhoto().setImageBitmap(mLocalDataSet[position].ItemPhoto);

        viewHolder.itemPhotoOnClickListener.setPosition(position);
        viewHolder.buttonDeletePhotoOnClickListener.setPosition(position);

        if (mLocalDataSet[position].MainPhoto) {
            viewHolder.getImageViewMainPhotoCheck().setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mLocalDataSet.length;
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
