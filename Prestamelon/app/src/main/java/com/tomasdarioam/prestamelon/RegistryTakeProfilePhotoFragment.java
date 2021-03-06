package com.tomasdarioam.prestamelon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.transition.platform.MaterialSharedAxis;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.tomasdarioam.prestamelon.BitmapUtils.getBitmapFromPhotoPath;

public class RegistryTakeProfilePhotoFragment extends Fragment {

    private boolean mPair = false;
    public RegistryTakeProfilePhotoFragment(int index) {
        if((index % 2) == 0) mPair = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, mPair));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.Y, !mPair));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registry_take_profile_photo, container, false);
    }


    ImageView imvPrevisualizacion;
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View viewFragment = getView();

        viewFragment.findViewById(R.id.btn_take_profile_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
        
        imvPrevisualizacion = viewFragment.findViewById(R.id.image_profile_photo);
        
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
        // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_TAKE_PHOTO) {
            setPic();
        }
    }

    private void setPic() {
        int targetWidth = imvPrevisualizacion.getWidth();
        int targetHeight = imvPrevisualizacion.getHeight();

        Bitmap previewBitmap = BitmapUtils.getBitmapFromPhotoPath(currentPhotoPath, targetWidth, targetHeight);

        if(previewBitmap == null) {
            return;
        }

        imvPrevisualizacion.setImageBitmap(previewBitmap);

        Bitmap fullBitmap = BitmapUtils.getBitmapFromPhotoPath(currentPhotoPath, 0, 0);

        nextFragment(fullBitmap);
    }

    private void nextFragment(Bitmap profilePhoto) {
        callback.profilePhotoTaken(profilePhoto);
    }

    ProfilePhotoTakenListener callback;

    public void setProfilePhotoTakenListener(ProfilePhotoTakenListener callback) {
        this.callback = callback;
    }

    public interface ProfilePhotoTakenListener {
        void profilePhotoTaken(Bitmap profilePhoto);
    }

}