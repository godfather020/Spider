package com.example.spider.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.databinding.ConcernFragmentBinding;
import com.example.spider.databinding.PaymentFragmentBinding;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Transactionhistory;
import com.example.spider.model.Website_item;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Concern_viewModel;
import com.example.spider.view_model.Payment_viewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Concern_Fragment extends Fragment  {


    ConcernFragmentBinding binding;
    public Pymentmethod pymentmethod;
    Concern_viewModel viewModel;
    MainActivity activity;
    AppSharedPref mAppSharedPref;
    ExtentionUtils utils =new ExtentionUtils();
    Website_item website_item;
    String withdrawId,imgBase64="";
    final int CAMERA=90,GALLERY=100;
    ActivityResultLauncher<Intent> resultLauncherCamera;
    ActivityResultLauncher<Intent> resultLauncherGallery;
    BottomSheetDialog bottomSheetDialog;
    boolean deposite=false;
    List<String> permission=new ArrayList<String>();
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 3;
    String title;
    Transactionhistory transactionhistory;
    boolean comeFromMenu=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.concern_fragment,container,false);

        viewModel=new ViewModelProvider(this).get(Concern_viewModel.class);

        activity=(MainActivity) getActivity();
        mAppSharedPref=new AppSharedPref(activity);


        title=Concern_Fragment.this.getTag();

        if(title.equals(activity.getResources().getString(R.string.cancelation))){

            binding.txtHeader.setVisibility(View.GONE);

            binding.btnRaiseConcern.setText(activity.getResources().getString(R.string.cancelation));


        }
        Bundle bundle=getArguments();
        if(bundle!=null){

            withdrawId=bundle.getString("withdrawId");
            transactionhistory=(Transactionhistory) bundle.getSerializable("item_trans_detail");
            comeFromMenu=bundle.getBoolean("menu",false);
        }

        View view =binding.getRoot();

        if (check_Permission()) {
            init();
        } else {
            requestPermission();
        }


        return view;
    }
    public void  init(){

        if(comeFromMenu){

            binding.spinnerConcern.setVisibility(View.GONE);
        }

        final List<String> plantsList = new ArrayList<String>(Arrays.asList(activity.getResources().getStringArray(R.array.drop_down_array)));

        utils.setSpinnerAdapter(activity,binding.spinnerConcern,plantsList, new AdapterView.OnItemSelectedListener(){


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (activity, "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Initializing an ArrayAdapter
        /*final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                activity,R.layout.spinner_item,R.id.txt_spinner,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv =  view.findViewById(R.id.txt_spinner);
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.spinnerConcern.setAdapter(spinnerArrayAdapter);

        binding.spinnerConcern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (activity, "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        binding.clTakeScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBottomDialog();

            }
        });

        resultLauncherCamera=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if(result.getResultCode()== Activity.RESULT_OK){


                    bottomSheetDialog.dismiss();

                    binding.imgScreenshot.setVisibility(View.VISIBLE);
                    binding.imgBlank.setVisibility(View.GONE);
                    binding.txtUploadTag.setVisibility(View.GONE);
                    Bitmap bitmap = null;
                    Uri uri=null;

                    if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.LOLLIPOP_MR1) {
                        utils.setImage(result.getData().getData().toString(), binding.imgScreenshot, getResources().getDrawable(R.drawable.app_logo));

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), result.getData().getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Bundle bundle=result.getData().getExtras();
                        bitmap= bundle.getParcelable("data");
                        File file = createImageFile();
                        if (file != null) {
                            FileOutputStream fout;
                            try {
                                fout = new FileOutputStream(file);

                                bitmap.compress(Bitmap.CompressFormat.PNG, 70, fout);
                                fout.flush();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            uri=Uri.fromFile(file);
                        }
                        utils.setImage(uri.toString(), binding.imgScreenshot, getResources().getDrawable(R.drawable.app_logo));

                        bitmap = (Bitmap) result.getData().getExtras().get("data");
//                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(result.getData().getExtras().get("data").toString()));

                    }
                    imgBase64=convert(bitmap);

                    Log.d("bitmap",imgBase64);

                }

            }
        });
        resultLauncherGallery=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if(result.getResultCode()== Activity.RESULT_OK){


                    bottomSheetDialog.dismiss();

                    binding.imgScreenshot.setVisibility(View.VISIBLE);
                    binding.imgBlank.setVisibility(View.GONE);
                    binding.txtUploadTag.setVisibility(View.GONE);
                    Bitmap bitmap = null;
                    Uri uri=null;


                    utils.setImage(result.getData().getData().toString(), binding.imgScreenshot, getResources().getDrawable(R.drawable.app_logo));

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), result.getData().getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgBase64=convert(bitmap);

                    Log.d("bitmap",imgBase64);

                }

            }
        });

        binding.btnRaiseConcern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.edtReason.getText().toString()!=null && !binding.edtReason.getText().toString().isEmpty()){


                    if(binding.btnRaiseConcern.getText().toString().equals(activity.getResources().getString(R.string.cancelation))){

                        android.app.AlertDialog.Builder  builder=new android.app.AlertDialog.Builder(activity);
                        builder.setCancelable(false);
                        builder.setTitle("Are you want to cancel withdraw request.");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                cancleWithdraw();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        android.app.AlertDialog alertDialog=builder.create();
                        alertDialog.show();

                    }else {


                        raiseConcern();

                    }

                }else {

                    utils.showToast(activity,"Please enter your reason.");
                    binding.tilReason.setFocusable(true);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(title);
    }

    private void showBottomDialog() {
        Bundle bundle=new Bundle();

        bottomSheetDialog=new BottomSheetDialog(activity);

        bottomSheetDialog.setContentView(R.layout.dialog_bottom);
        LinearLayout ll_camera=bottomSheetDialog.findViewById(R.id.camera);
        LinearLayout ll_gallery=bottomSheetDialog.findViewById(R.id.gallery);

        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                resultLauncherCamera.launch(takePicture);
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                /* startActivityForResult(pickPhoto , GALLERY);*/

//                Intent takePicture = new Intent(String.valueOf(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

                resultLauncherGallery.launch(pickPhoto);
            }
        });

        bottomSheetDialog.show();
    }

    public String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public boolean check_Permission(){

        int camera= ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int gallery=ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE);
        int gallery1=ContextCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if(camera!= PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.CAMERA);
        }

        if(gallery!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        } if(gallery1!=PackageManager.PERMISSION_GRANTED){

            permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permission.isEmpty()){


            return false;
        }


        return true;
    }
    public void  requestPermission(){
        ActivityCompat.requestPermissions(activity,permission.toArray(new String[permission.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){

            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                Map<String,Integer> perm=new HashMap<>();

//                perm.put(Manifest.permission.CAMERA,PackageManager.PERMISSION_GRANTED);
//                perm.put(Manifest.permission.READ_EXTERNAL_STORAGE,PackageManager.PERMISSION_GRANTED);
//                perm.put(Manifest.permission.READ_CONTACTS,PackageManager.PERMISSION_GRANTED);

                if(grantResults.length > 0){

                    for(int i=0;i <permissions.length;i++)
                        perm.put(permissions[i],grantResults[i]);



                    if(perm.get(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
                            &&perm.get(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                            &&perm.get(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                    ){

                        init();

                    }else {

                        if( ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.CAMERA)||
                                ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)||
                                ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                            showDialogOK("Camera ,Gallery and Contact Permission required for this app", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            requestPermission();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            // proceed with logic by disabling the related features or quit the app.
                                            init();
                                            break;
                                    }
                                }
                            });

                        }

                    }



                }
                break;
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        String root=activity.getDir("my_sub_dir",Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if(!myDir.exists()){
            myDir.mkdirs();
        }
        try {
            mFileTemp=File.createTempFile(imageFileName,".jpg",myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
    }

    public void cancleWithdraw(){

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.cancelWithdraw(activity,binding,withdrawId,binding.edtReason.getText().toString(),imgBase64).observe(activity,result -> {

            if(result.equals("1")){
                activity.getSupportFragmentManager().popBackStackImmediate();
            }

        });
    }
    public void raiseConcern(){

        binding.progressBar.setVisibility(View.VISIBLE);
        if(!comeFromMenu){
        viewModel.raiseConcern(activity,binding,withdrawId,binding.edtReason.getText().toString(),imgBase64,transactionhistory,comeFromMenu).observe(activity,result -> {

            if(result.equals("1")){



                utils.loadFragment(activity.getSupportFragmentManager(), new Concern_List_Fragment(), R.id.main_container, false, getResources().getString(R.string.raise_a_concern), null);


            }

        });
        }else {

            viewModel.raiseConcern(activity,binding,withdrawId,binding.edtReason.getText().toString(),imgBase64,transactionhistory,comeFromMenu).observe(activity,result -> {

                if(result.equals("1")){



                    utils.loadFragment(activity.getSupportFragmentManager(), new Concern_List_Fragment(), R.id.main_container, false, getResources().getString(R.string.raise_a_concern), null);


                }

            });

        }

    }
}
