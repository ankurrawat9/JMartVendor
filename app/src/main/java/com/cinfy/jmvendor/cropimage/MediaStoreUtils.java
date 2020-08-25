package com.cinfy.jmvendor.cropimage;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

public final class MediaStoreUtils {
    private MediaStoreUtils() {
    }

    public static Intent getPickImageIntent(final Context context) {
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        return Intent.createChooser(intent, "Select picture");
    }


    public static Intent getPickImageCamera(final Context context) {

//        final Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_IMAGE_CAPTURE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        return Intent.createChooser(intent, "Select pictures");
    }



}
