package com.cinfy.jmvendor.dashboard;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cinfy.jmvendor.R;
import com.cinfy.jmvendor.base.MyApplication;
import com.cinfy.jmvendor.base.NetworkEventListener;
import com.cinfy.jmvendor.base.NetworkUrl;
import com.cinfy.jmvendor.base.ProgressBarDialog;
import com.cinfy.jmvendor.base.Utils;
import com.cinfy.jmvendor.cropimage.BitmapUtils;
import com.cinfy.jmvendor.cropimage.CropImageIntentBuilder;
import com.cinfy.jmvendor.dashboard.bean.BrandData;
import com.cinfy.jmvendor.dashboard.bean.BrandDataList;
import com.cinfy.jmvendor.dashboard.bean.FetchRequestTypeData;
import com.cinfy.jmvendor.dashboard.bean.FetchRequestTypeList;
import com.cinfy.jmvendor.dashboard.bean.FetchSubTypeData;
import com.cinfy.jmvendor.dashboard.bean.FetchSubTypeList;
import com.cinfy.jmvendor.dashboard.bean.MeasuringData;
import com.cinfy.jmvendor.dashboard.bean.MeasuringDataList;
import com.cinfy.jmvendor.dashboard.bean.SuccessBean;
import com.cinfy.jmvendor.dashboard.manager.DashBoardManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddItemFragment extends Fragment {

    private ProgressBar progressBar;
    private ImageView addImageView;
    private int REQUEST_PICTURE = 0x10;
    byte[] BYTE;
    private int REQUEST_CROP_PICTURE = 0x20;
    private int REQUEST_PICTURE_GALLERY = 0x30;
    private File photo = null, predPhoto;
    private File croppedImageFile;
    private LocationManager locationManager;
    private Uri imageURI;
    private RelativeLayout headLayout;
    private Bitmap thumbnail = null;
    private File pathNew = null;
    private String picturePath = "";
    private String ImageKeyValue = "";
    private FetchRequestTypeData fetchRequestTypeData;
    private FetchSubTypeData fetchSubTypeData;
    private BrandData brandData;
    private MeasuringData measuringData;
    private Spinner typeSpinner, subtypeSpinner, brandSpinner, measuringSpinner, citySpinner;
    private int typeItemPosition = -1;
    private int subTypeItemPosition = -1;
    private int brandItemPosition = -1;
    private int measuringPosition = -1, cityPosition = -1;
    private List<String> typeList = new ArrayList<String>();
    private List<String> subTypeList = new ArrayList<String>();
    private List<String> brandList  = new ArrayList<String>();
    private List<String> measuringList = new ArrayList<String>();
    private List<String> cityList = new ArrayList<String>();
    private RelativeLayout addVariantLayout, confirmationLayout;
    private String manufacturingDate, expiryDate;
    private int size, price, quantity;
    private EditText shortEditText;
    private EditText longEditText;

    ByteArrayOutputStream bytearrayoutputstream;
    private RequestQueue mQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.add_item, container, false);

        init(rootView);
        getTypeData();
        getCity();

        return rootView;

    }

    private void init(View rootView) {
        citySpinner = rootView.findViewById(R.id.citySpinner);
        addVariantLayout = rootView.findViewById(R.id.addVariantLayout);
        measuringSpinner = rootView.findViewById(R.id.measuringSpinner);
        brandSpinner = rootView.findViewById(R.id.brandSpinner);
        subtypeSpinner = rootView.findViewById(R.id.subtypeSpinner);
        typeSpinner = rootView.findViewById(R.id.typeSpinner);
        progressBar = rootView.findViewById(R.id.progressBar);
        bytearrayoutputstream = new ByteArrayOutputStream();

        addImageView = rootView.findViewById(R.id.addImageView);
        confirmationLayout = rootView.findViewById(R.id.confirmationLayout);
        shortEditText = rootView.findViewById(R.id.shortEditText);
        longEditText = rootView.findViewById(R.id.longEditText);


        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProfilePicture();
            }
        });

        addVariantLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVariant();
            }
        });

        confirmationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // mQueue=Volley.newRequestQueue(getContext());
                // direct();

                if (!Utils.isOnline()) {
                    Utils.getDialog("Message", getActivity(), "Internet not available", 0).show();
                } else if (TextUtils.isEmpty(shortEditText.getText().toString().trim())) {
                    Utils.getDialog("Message", getActivity(), "Short Name can not be empty", 0).show();
                } else if (TextUtils.isEmpty(longEditText.getText().toString().trim())) {
                    Utils.getDialog("Message", getActivity(), "Long Name can not be empty", 0).show();
                } else if (citySpinner.getSelectedItem().toString().equalsIgnoreCase("Select") ) {
                    Utils.getDialog("Message", getActivity(), "Select City", 0).show();
                } else if (typeSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    Utils.getDialog("Message", getActivity(), "Select Type", 0).show();
                } else if (subtypeSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    Utils.getDialog("Message", getActivity(), "Select Sub Type", 0).show();
                } else if (brandSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    Utils.getDialog("Message", getActivity(), "Select Brand", 0).show();
                } else if (measuringSpinner.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    Utils.getDialog("Message", getActivity(), "Select Measuring Unit", 0).show();
                }
//                else if (thumbnail == null) {
//                    Utils.getDialog("Message", getActivity(), "Select Image", 0).show();
//                }
                else if (price == 0) {
                    Utils.getDialog("Message", getActivity(), "Add other mandatory fields ", 0).show();
                } else {
                    sendItemDetails();
                }
            }
        });
    }

    private void sendItemDetails() {

        Map measuringUnitId = new HashMap();

        Map subItemTypeId = new HashMap();

        Map brandId = new HashMap();

        Map supplierId = new HashMap();

        if (measuringData != null) {
            measuringUnitId.put("measuringUnitId", measuringData.data.get(measuringPosition).id);
        }
        if (fetchSubTypeData != null) {
            subItemTypeId.put("subItemTypeId", fetchSubTypeData.data.get(subTypeItemPosition).id);
        }
        if (brandData != null) {
            brandId.put("brandMasterId", brandData.data.get(brandItemPosition).id);
        }
            supplierId.put("id", 1);

        ArrayList<Object> itemDetailOptions = new ArrayList<>();

        Map item = new HashMap();

        item.put("measuringQuantity", size);
        item.put("itemPrice", price);
//_
        item.put("itemDetailId", fetchSubTypeData.data.get(subTypeItemPosition).id);
        item.put("mfDate", manufacturingDate);
        item.put("exDate", expiryDate);
        item.put("mrpAmount", price);
        item.put("availableQuantity", quantity);

        itemDetailOptions.add(item);
Log.v("aa",itemDetailOptions.toString());



        Map map = new HashMap();
        map.put("itemDetailOptions", itemDetailOptions);
        map.put("brandId", brandId);
        map.put("measuringUnitId", measuringUnitId);
        map.put("subItemTypeId", subItemTypeId);
        map.put("supplierId", supplierId);
        map.put("cityId", cityPosition);

        map.put("itemShortName", shortEditText.getText().toString().trim());
        map.put("itemLongName", longEditText.getText().toString().trim());
        map.put("itemImagePath", getEncoded64Image(thumbnail) );

        Map data = new HashMap<>();

        data.put("data", map);

        Log.d("ba", data.toString());
        new JSONObject(data);




        ProgressBarDialog.getInstance().addProgressDialog(getActivity(), progressBar);

        DashBoardManager.createItem(data, new NetworkEventListener() {
            @Override
            public void OnSuccess(Object object) {
                ProgressBarDialog.getInstance().dismissProgressDialog(getActivity(), progressBar);
                ProgressBarDialog.getInstance().dismissProgressDialog(getActivity(), progressBar);

                if (object instanceof SuccessBean) {
                    SuccessBean successBean = (SuccessBean) object;
                    if (successBean.status > 0) {
                        Utils.getDialog("मैसेज", getActivity(), "अनुरोध सफलतापूर्वक भेजा गया", 1).show();
                    } else {
                        Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
                    }
                } else {
                    Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
                }

            }

            @Override
            public void OnError(VolleyError exception) {
                ProgressBarDialog.getInstance().dismissProgressDialog(getActivity(), progressBar);
                Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
            }
        }
        );

    }


    public static String getEncoded64Image(Bitmap bitmap) {
        String  Image="Not added";
        if(bitmap==null){
            return  Image;
        } else{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream);
            byte[] byteFormat = stream.toByteArray();
            String base64Image = Base64.encodeToString(byteFormat, Base64.DEFAULT);
            return base64Image;
        }
    }



    private void addVariant() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.add_variant, null);

        final ProgressBar progressBar = customView.findViewById(R.id.progressBar);
        final EditText manufacturingEditText = customView.findViewById(R.id.manufacturingEditText);
        final EditText expiryEditText = customView.findViewById(R.id.expiryEditText);
        final EditText sizeEditText = customView.findViewById(R.id.sizeEditText);
        final EditText priceEditText = customView.findViewById(R.id.priceEditText);
        final EditText quantityEditText = customView.findViewById(R.id.quantityEditText);
        final RelativeLayout saveLayout = customView.findViewById(R.id.saveLayout);
        final TextView qtTextView = customView.findViewById(R.id.qtTextView);

        builder.setView(customView);
        builder.create();
        final AlertDialog dialog = builder.show();


        if (measuringData != null ) {
            qtTextView.setText(measuringData.data.get(measuringPosition).name);
//            measuringUnitId.put("measuringUnitId", measuringData.data.get(measuringPosition).name);
        }
        customView.findViewById(R.id.cancelImgView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isOnline()) {
                    Utils.getDialog("Message", getActivity(), "Internet not available", 0).show();
                } else if (TextUtils.isEmpty(sizeEditText.getText().toString().trim())) {
                    Utils.getDialog("Message", getActivity(), "Size can not be empty", 0).show();
                } else if (TextUtils.isEmpty(priceEditText.getText().toString().trim())) {
                    Utils.getDialog("Message", getActivity(), "Price can not be empty", 0).show();
                } else if (TextUtils.isEmpty(quantityEditText.getText().toString().trim())) {
                    Utils.getDialog("Message", getActivity(), "Quantity can not be empty", 0).show();
                } else {
                    manufacturingDate = manufacturingEditText.getText().toString().trim();
                    expiryDate = expiryEditText.getText().toString().trim();
                    size = Integer.parseInt(sizeEditText.getText().toString());
                    price = Integer.parseInt(priceEditText.getText().toString());
                    quantity = Integer.parseInt(quantityEditText.getText().toString());dialog.dismiss();
                }
            }
        });

    }

    private void getCity() {

        cityList.add("Select");
        cityList.add("जबलपुर");
        cityList.add("छिंदवाड़ा");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cityList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(dataAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityPosition = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getMeasuringUnit() {
        try {
            InputStream is = getActivity().getAssets().open("measuring_unit_master.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();


            String text = new String(buffer);
            Gson gson = new Gson();
            measuringData = gson.fromJson(text, MeasuringData.class);

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
        measuringList.add("Select");
        for (MeasuringDataList ward : measuringData.data) {
            measuringList.add(ward.name);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, measuringList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measuringSpinner.setAdapter(dataAdapter);
        measuringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    measuringPosition = i - 1;
                } else {
                    measuringPosition = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getTypeData() {
        getMeasuringUnit();
        try {
            InputStream is = getActivity().getAssets().open("request_type_master.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            Gson gson = new Gson();
            fetchRequestTypeData = gson.fromJson(text, FetchRequestTypeData.class);

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
        typeList.add("Select");
        for (FetchRequestTypeList ward : fetchRequestTypeData.data) {
            typeList.add(ward.requestTypeMasterName);
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    typeItemPosition = i - 1;
                    getSubTypeData(fetchRequestTypeData.data.get(typeItemPosition).requestTypeMasterId);
                } else {
                    typeItemPosition = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getSubTypeData(int requestTypeMasterId) {
        if (fetchSubTypeData == null) {
            try {
                InputStream is = getActivity().getAssets().open("sub_item_typ.txt");

                int size = is.available();

                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                // Convert the buffer into a string.
                String text = new String(buffer);
                Gson gson = new Gson();

                fetchSubTypeData = gson.fromJson(text, FetchSubTypeData.class);

            } catch (IOException e) {
                // Should never happen!
                throw new RuntimeException(e);
            }

        }
        subTypeList.clear();
        subTypeList.add("Select");
        getBrandData(0);

        for (FetchSubTypeList ward : fetchSubTypeData.data) {
            if (ward.request_type_id == requestTypeMasterId) {
                subTypeList.add(ward.name);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subTypeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subtypeSpinner.setAdapter(dataAdapter);
        subtypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    subTypeItemPosition = i - 1;
                    getBrandData(fetchSubTypeData.data.get(i - 1).id);
                } else {
                    subTypeItemPosition = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getBrandData(int subID) {
        if (brandData == null) {
            try {
                InputStream is = getActivity().getAssets().open("brand_master.txt");

                int size = is.available();

                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                // Convert the buffer into a string.
                String text = new String(buffer);
                Gson gson = new Gson();
                brandData = gson.fromJson(text, BrandData.class);

            } catch (IOException e) {
                // Should never happen!
                throw new RuntimeException(e);
            }
        }

        brandList.clear();
        brandList.add("Select");
        for (BrandDataList ward : brandData.data) {
            if (ward.sub_item_type_id == subID) {
                brandList.add(ward.brand_name);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, brandList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(dataAdapter);
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    brandItemPosition = i;
                } else {
                    brandItemPosition = i - 1;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getTypeDataAPI() {
        ProgressBarDialog.getInstance().addProgressDialog(getActivity(), progressBar);

        DashBoardManager.getTypeData(new NetworkEventListener() {
            @Override
            public void OnSuccess(Object object) {
                ProgressBarDialog.getInstance().dismissProgressDialog(getActivity(), progressBar);
                if (object instanceof FetchRequestTypeData) {
                    fetchRequestTypeData = (FetchRequestTypeData) object;
                    if (fetchRequestTypeData.status > 0) {
                        typeList.add("Select");
                        for (FetchRequestTypeList ward : fetchRequestTypeData.data) {
                            typeList.add(ward.requestTypeMasterName);
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, typeList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        typeSpinner.setAdapter(dataAdapter);
                        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                typeItemPosition = i;

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
                    }
                } else {
                    Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
                }

            }

            @Override
            public void OnError(VolleyError exception) {
                ProgressBarDialog.getInstance().dismissProgressDialog(getActivity(), progressBar);
                Utils.getDialog("मैसेज", getActivity(), "डाटा नहीं मिला, फिर प्रयास करें", 0).show();
            }
        });
    }

    public void getProfilePicture() {
        try {
            if (!((DashBoardActivity) getActivity()).checkPermission(Manifest.permission.CAMERA)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                        .setTitle(getString(R.string.info))
                        .setMessage(getString(R.string.camera_permission))
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (getActivity() == null) {
                                    return;
                                }
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getActivity().getPackageName(), null));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false);

                builder.create();
                builder.show();
                return;
            }
            List<String> optionsList = new ArrayList<String>();
            optionsList.add("Camera");
            optionsList.add("Gallery");
            optionsList.add("Cancel");

            final CharSequence[] options = optionsList.toArray(new CharSequence[optionsList.size()]);
             for (int i = 0; i > options.length; i++) {
                options[i] = optionsList.get(i);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle(getString(R.string.app_name));
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Camera")) {




                        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";

                        predPhoto = new File(imageFilePath);
                        Uri imageFileUri = Uri.fromFile(predPhoto);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
                        startActivityForResult(intent, REQUEST_PICTURE);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



                    } else if (options[item].equals("Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, REQUEST_PICTURE_GALLERY);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            Log.e("getProfilePicture", e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == getActivity().RESULT_OK) {
                 if ((requestCode == REQUEST_PICTURE) && (resultCode == Activity.RESULT_OK)) {


                    photo = predPhoto;
                    imageURI = Uri.fromFile(photo);
                    CropImageIntentBuilder cropImage = new CropImageIntentBuilder(150, 150, imageURI);
                    cropImage.setOutlineColor(0xFF03A9F4);
                    cropImage.setSourceImage(imageURI);
                    startActivityForResult(cropImage.getIntent(getActivity()), REQUEST_CROP_PICTURE);


                } else if ((requestCode == REQUEST_PICTURE_GALLERY) && (resultCode == Activity.RESULT_OK)) {

                    CropImageIntentBuilder cropImage = BitmapUtils.getPickFromGallery(getActivity(), data);
                    if (cropImage != null) {
                        startActivityForResult(cropImage.getIntent(getActivity()), REQUEST_CROP_PICTURE);
                    }
                } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == Activity.RESULT_OK)) {
                    if (photo != null) {
                        picturePath = photo.getAbsolutePath();
                        Bitmap bitmap = BitmapUtils.decodeFileForPorfile(picturePath);
                        photo = null;
                        ImageKeyValue = "image";
                        addImageView.setBackgroundResource(0);
                        addImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                        thumbnail = bitmap;
                    } else {
                        if (android.os.Build.VERSION.SDK_INT > 23) {
                            if (BitmapUtils.pathNew != null) {
                                picturePath = BitmapUtils.pathNew.getAbsolutePath();
                                Bitmap bitmap = BitmapUtils.decodeFileForPorfile(picturePath);
                                addImageView.setBackgroundResource(0);
                                addImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 150, 150, true));
                                BitmapUtils.pathNew = null;
                                thumbnail = bitmap;
                            }
                        } else {
                            croppedImageFile = new File(getActivity().getFilesDir(), "test.jpg");
                            picturePath = croppedImageFile.getAbsolutePath();
                            Bitmap bitmap1 = BitmapUtils.decodeFileForPorfile(picturePath);
                            ImageKeyValue = "image";
                            addImageView.setBackgroundResource(0);
                            addImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap1, 150, 150, true));
                            thumbnail = bitmap1;
                        }
                        ImageKeyValue = "image";
                    }
                }
            }
        } catch (Exception e) {
            Log.e("onActivityResu", e.getMessage());
        }
    }

    public void direct() {   /*


        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, NetworkUrl.fetchRequestType,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {


                    JSONObject jsonObject = new JSONObject(response);



                      String  user = jsonObject.getString("data");
                      Log.d("user",user);
                    for(int i=0;i<jsonObject.length();i++){




                        Toast.makeText(getContext(), user, Toast.LENGTH_SHORT).show();
                    }
                    //shortEditTextz.setText(user);



                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);
*/



/*



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, NetworkUrl.fetchRequestType, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                Gson gson = new Gson();
                                FetchRequestTypeData bean = gson.fromJson(employee.toString(), FetchRequestTypeData.class);
                                Log.v("aaa",employee.toString());
                               int firstName = employee.getInt("requestTypeMasterId");
                                String age = employee.getString("requestTypeMasterName");
                                int mail = employee.getInt("createdDate");
                                shortEditTextz.append(age + ", " + String.valueOf(firstName) + ", " + String.valueOf(mail) + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);




*/









    }

    }















