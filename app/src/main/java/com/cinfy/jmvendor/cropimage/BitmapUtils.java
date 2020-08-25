package com.cinfy.jmvendor.cropimage;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;


import com.cinfy.jmvendor.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by VGCSANROID on 12/10/2015.
 */
public class BitmapUtils {
    public static File pathNew = null;

    public static Bitmap decodeFileNew(String path) {

        if (path == null) {
            return null;
        }
        int orientation;
        File f = new File(path);
        Bitmap bm = null; //Decode image size
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            int IMAGE_MAX_SIZE = 1024;
            int IMAGE_MAX_SIZE_H = 768;
            if (o.outHeight > IMAGE_MAX_SIZE_H || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            } //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            bm = BitmapFactory.decodeStream(fis, null, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

//          exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
//              m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap decodeFileForPorfile(String path) {

        if (path == null) {
            return null;
        }
        int orientation;
        File f = new File(path);
        Bitmap bm = null; //Decode image size
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            int IMAGE_MAX_SIZE = 400;
            int IMAGE_MAX_SIZE_H = 400;
            if (o.outHeight > IMAGE_MAX_SIZE_H || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            } //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            bm = BitmapFactory.decodeStream(fis, null, o2);
            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

//          exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
//              m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bitmap;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap decodeFilefromBitmap(Bitmap bmp, String path) {

        if (path == null) {
            return null;
        }
        int orientation;
        File f = new File(path);
        Bitmap bm = null; //Decode image size

        try {


            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

//          exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
//              m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bmp;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bmp;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bmp = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                return bmp;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return bmp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap checkRotation(Activity activity, Bitmap original_bitmap, Uri target, String picturePath) {
        picturePath = "";
        ContentResolver mContentResolver = activity.getContentResolver();
        IImageList mAllImages;
        IImage mImage;
        mAllImages = ImageManager.makeImageList(mContentResolver, target,
                ImageManager.SORT_ASCENDING);
        mImage = mAllImages.getImageForUri(target);
        Bitmap mBitmap = null;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(target.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        if (rotation != 0) {
            picturePath = target.getPath();
            if (picturePath == null) {
//                            return null;
            }
            int orientation;
            File f = new File(picturePath);
            Bitmap bm = null; //Decode image size
            try {
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                FileInputStream fis = new FileInputStream(f);
                BitmapFactory.decodeStream(fis, null, o);
                fis.close();
                int scale = 1;
                int IMAGE_MAX_SIZE = 1600;
                int IMAGE_MAX_SIZE_H = 1600;
                if (o.outHeight > IMAGE_MAX_SIZE_H || o.outWidth > IMAGE_MAX_SIZE) {
                    scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
                } //Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                fis = new FileInputStream(f);
                bm = BitmapFactory.decodeStream(fis, null, o2);
                Bitmap bitmap = bm;

                exif = new ExifInterface(picturePath);

                orientation = exif
                        .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

                Log.e("ExifInteface .........", "rotation =" + orientation);

//          exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

                Log.e("orientation", "" + orientation);
                Matrix m = new Matrix();

                if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                    m.postRotate(180);
//              m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                    // if(m.preRotate(90)){
                    Log.e("in orientation", "" + orientation);
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                            bm.getHeight(), m, true);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();
//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    mBitmap = bitmap;
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                    m.postRotate(90);
                    Log.e("in orientation", "" + orientation);
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                            bm.getHeight(), m, true);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    mBitmap = bitmap;
                } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                    m.postRotate(270);
                    Log.e("in orientation", "" + orientation);
                    bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                            bm.getHeight(), m, true);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    mBitmap = bitmap;
                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                mBitmap = bitmap;

            } catch (Exception e) {
                e.printStackTrace();
//                            return null;
            }
            return mBitmap;
        } else {
            return mImage.thumbBitmap(IImage.ROTATE_AS_NEEDED);
        }

    }


    public static File storeImage(Activity activity, Bitmap image) {
        File pictureFile = getOutputMediaFile(activity);
        if (pictureFile == null) {
            Log.d("File", "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("File", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("File", "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }


    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(Activity activity) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + activity.getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
//        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
//        File mediaFile;
//        String mImageName = "MI_" + timeStamp + ".jpg";

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        File mediaFile;

        String mImageName = "MI_" + ts + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    public static CropImageIntentBuilder getPickFromGallery(final Activity activity, final Intent data) {
        String value="";
        Bitmap thumbnail = null;
        File croppedImageFile=null;
        if (android.os.Build.VERSION.SDK_INT > 23) {
            Uri selectedImage = data.getData();
            String picturePath = getRealPathFromURI(selectedImage, activity);
            BitmapFactory.Options options1 = new BitmapFactory.Options();
            options1.inJustDecodeBounds = true;
            options1.inSampleSize = 1;
            BitmapFactory.decodeFile(picturePath, options1);
            Uri u = Uri.fromFile(new File(picturePath));
            File f = new File(u.getPath());
            long size = f.length();
            final int scale = 1600;


            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            picturePath = c.getString(columnIndex);
            c.close();
            ContentResolver cr = activity.getContentResolver();
            try {
                int w = 0;
                int h = 0;
                int sampleSize = 10;
                BitmapFactory.Options options = new BitmapFactory.Options();
                do {
                    options.inSampleSize = sampleSize;
                    AssetFileDescriptor fileDescriptor = null;
                    fileDescriptor = cr.openAssetFileDescriptor(selectedImage, "r");
                    thumbnail = BitmapFactory.decodeFileDescriptor(
                            fileDescriptor.getFileDescriptor(), null, options);
                    fileDescriptor.close();
                    w = thumbnail.getWidth();
                    h = thumbnail.getHeight();
                    sampleSize--;
                    if (sampleSize == 0) {
                        break;
                    }
                } while (w < 512 || h < 512);

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(selectedImage.getPath());
                    if (exif != null) {
                        thumbnail = BitmapUtils.checkRotation(activity, thumbnail, selectedImage, picturePath);
                    }
                } catch (IOException e) {
                    pathNew = BitmapUtils.storeImage(activity, thumbnail);
                    thumbnail = BitmapUtils.checkRotation(activity, thumbnail, Uri.fromFile(pathNew), picturePath);
                    e.printStackTrace();
                }

                if (size <= 10485760) {

                    if (w <= 850 || h <= 350) {
//                        Toast.makeText(activity, activity.getString(R.string.imageIsNotGood), Toast.LENGTH_SHORT).show();
                        final File finalPathNew = pathNew;
                        Uri croppedImage = Uri.fromFile(finalPathNew);
                        CropImageIntentBuilder cropImage = new CropImageIntentBuilder(scale, scale, croppedImage);
                        cropImage.setOutlineColor(0xFF03A9F4);
                        cropImage.setSourceImage(data.getData());
                        return cropImage;


                    } else {
                        Uri croppedImage = Uri.fromFile(pathNew);
                        CropImageIntentBuilder cropImage = new CropImageIntentBuilder(scale, scale, croppedImage);
                        cropImage.setOutlineColor(0xFF03A9F4);
                        cropImage.setSourceImage(data.getData());
                        return cropImage;
                    }
                } else {
//                    Dialog errorDialog = ErrorDialog.dialogMessage(activity.getString(R.string.warning_text), activity, activity.getString(R.string.please_select_image_less_than_10MB));
//                    errorDialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (thumbnail != null && !thumbnail.isRecycled())
                    thumbnail.recycle();
            }
        } else {
            Uri selectedImage = data.getData();
            String picturePath = getRealPathFromURI(selectedImage, activity);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 1;
            BitmapFactory.decodeFile(picturePath, options);
            Uri u = Uri.fromFile(new File(picturePath));
            File f = new File(u.getPath());
            long size = f.length();

            ExifInterface exif = null;
            try {
                exif = new ExifInterface(selectedImage.getPath());
                if (exif != null) {
                    thumbnail = BitmapUtils.checkRotation(activity, thumbnail, selectedImage, picturePath);
                }
            } catch (IOException e) {
                pathNew = BitmapUtils.storeImage(activity, thumbnail);
                e.printStackTrace();
            }

            if (size <= 10485760) {
//                if(!NetworkUtil.isOnline()){
//                    SharedPreferences sharedpreferences = activity.getSharedPreferences("ImagePref", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedpreferences.edit();
//                    long dtMili = System.currentTimeMillis();
//                    value="test_"+dtMili;
//                    editor.putString("imageName",value);
//                    editor.commit();
//                    editor.apply();
//                }
                int w = options.outWidth;
                int h = options.outHeight;
                if (w <= 850 || h <= 350) {
//                    Toast.makeText(activity, activity.getString(R.string.imageIsNotGood), Toast.LENGTH_SHORT).show();
//                    if(!NetworkUtil.isOnline()){
//                        croppedImageFile = new File(activity.getFilesDir(), value+".jpg");
//                    }else{
                        croppedImageFile = new File(activity.getFilesDir(), "test.jpg");
//                    }
                    Uri croppedImage = Uri.fromFile(croppedImageFile);
                    CropImageIntentBuilder cropImage = new CropImageIntentBuilder(1600, 1600, croppedImage);
                    cropImage.setOutlineColor(0xFF03A9F4);
                    cropImage.setSourceImage(data.getData());
                    return cropImage;

                } else {
//                    if(!NetworkUtil.isOnline()){
//                        croppedImageFile = new File(activity.getFilesDir(), value+".jpg");
//                    }else{
                        croppedImageFile = new File(activity.getFilesDir(), "test.jpg");
//                    }
                    Uri croppedImage = Uri.fromFile(croppedImageFile);
                    CropImageIntentBuilder cropImage = new CropImageIntentBuilder(1600, 1600, croppedImage);
                    cropImage.setOutlineColor(0xFF03A9F4);
                    cropImage.setSourceImage(data.getData());
                    return cropImage;
                }
            } else {
//                Dialog errordialog = ErrorDialog.dialogMessage(activity.getString(R.string.warning_text), activity, activity.getString(R.string.please_select_image_less_than_10MB));
//                errordialog.show();
            }
        }
        return null;
    }

    public static String getRealPathFromURI(Uri contentURI, Activity activity) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            if (result == null){
                result = contentURI.getPath();
            }
            cursor.close();
        }
        return result;
    }


}
