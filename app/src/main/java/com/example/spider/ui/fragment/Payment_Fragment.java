package com.example.spider.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
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
import com.example.spider.databinding.IdsFragmentBinding;
import com.example.spider.databinding.PaymentFragmentBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Pymentmethod;
import com.example.spider.model.Website_item;
import com.example.spider.ui.activity.Login_Activity;
import com.example.spider.ui.activity.Registration_Activity;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.ExtentionUtils;
import com.example.spider.view_model.Payment_viewModel;
import com.example.spider.view_model.Paymetn_Method_viewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Payment_Fragment extends Fragment implements View.OnClickListener {


    PaymentFragmentBinding binding;
    public Pymentmethod pymentmethod;
    Payment_viewModel viewModel;
    MainActivity activity;
    AppSharedPref mAppSharedPref;
    ExtentionUtils utils =new ExtentionUtils();
    Website_item website_item;
    String userName,depositeAmt,imgBase64,website_id,rewards="0";
    final int CAMERA=90,GALLERY=100;
    ActivityResultLauncher<Intent> resultLauncherCamera;
    ActivityResultLauncher<Intent> resultLauncherGallery;
    BottomSheetDialog bottomSheetDialog;
    boolean deposite=false;
    List<String> permission=new ArrayList<String>();
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.payment_fragment,container,false);

        viewModel=new ViewModelProvider(this).get(Payment_viewModel.class);

        activity=(MainActivity) getActivity();
        mAppSharedPref=new AppSharedPref(activity);
        Bundle bundle=getArguments();
        if(bundle!=null){
            pymentmethod= (Pymentmethod) bundle.getSerializable("pymentmethod");
            website_item = (Website_item) bundle.getSerializable("selected_website_item");
            userName=bundle.getString("userName");
            depositeAmt=bundle.getString("depositAmt");
            deposite=bundle.getBoolean("deposit",false);
            website_id=bundle.getString("website_id",null);
            rewards=bundle.getString("reward","0");
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

        if(deposite)
            binding.btnCreateClient.setText(getResources().getString(R.string.deposit));
        else
            binding.btnCreateClient.setText("Create");

        binding.progressBar.setVisibility(View.VISIBLE);
        viewModel.getAccDetail(activity,binding,pymentmethod.getId()).observe(activity,adminaccounts -> {

            if(pymentmethod.getId().equals("4")){
                binding.clBank.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);

                binding.txtAccNumber.setText(adminaccounts.get(0).getAccountdetail().get(0).getAccountNo());
                binding.txtAccHolderName.setText(adminaccounts.get(0).getAccountdetail().get(0).getAccountholder());
                binding.txtBankName.setText(adminaccounts.get(0).getAccountdetail().get(0).getBankname());
                binding.txtIfscCode.setText(adminaccounts.get(0).getAccountdetail().get(0).getIfsc());
                binding.txtBranchAdd.setText(adminaccounts.get(0).getAccountdetail().get(0).getBranch());
                binding.txtAccType.setText(adminaccounts.get(0).getAccountdetail().get(0).getBankType());

                binding.txtBankSubHeader.setText("Send INR "+depositeAmt+" to below mentioned account details and upload transaction screenshot.");


            }else {
                binding.clUpi.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.txtAccName.setText(adminaccounts.get(0).getName());
                binding.txtAcc.setText(adminaccounts.get(0).getAccountdetail().get(0).getName());
                binding.txtAccUpiholderName.setText(adminaccounts.get(0).getAccountdetail().get(0).getDisplayname());

                binding.txtSubHeader.setText("Send INR "+depositeAmt+" to "+adminaccounts.get(0).getAccountdetail().get(0).getNumber()
                        +" on "+adminaccounts.get(0).getAccountdetail().get(0).getPaymentmethod()+" and upload transaction screenshot.");

            }



        });
        binding.txtAmt.setText(depositeAmt);
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



    binding.clTakeScreenshot.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            showBottomDialog();

        }
    });
binding.btnCreateClient.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(imgBase64!=null&&!imgBase64.isEmpty()){

            if(!binding.btnCreateClient.getText().equals(getResources().getString(R.string.deposit))) {
                binding.progressBar.setVisibility(View.VISIBLE);

                viewModel.userCreateId(activity, binding, userName, mAppSharedPref.getUserData().getId(),
                        website_item.getId(), depositeAmt, pymentmethod.getId(), imgBase64).observe(activity, it -> {

                    if (it.equals("1")) {


                        binding.progressBar.setVisibility(View.GONE);
                        Fragment fragment = new Home_Fragment();
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, true, activity.getResources().getString(R.string.home), null);


                    }

                });
            }else {
                binding.progressBar.setVisibility(View.VISIBLE);

                viewModel.userDeposite(activity, binding, mAppSharedPref.getUserData().getId(),
                         depositeAmt, pymentmethod.getId(), imgBase64,website_id,rewards).observe(activity, it -> {

                    if (it.equals("1")) {


                        binding.progressBar.setVisibility(View.GONE);
                        Fragment fragment = new Home_Fragment();
                        utils.loadFragment(activity.getSupportFragmentManager(), fragment, R.id.main_container, true, activity.getResources().getString(R.string.home), null);


                    }

                });

            }
        }else utils.showToast(activity,"Please Upload Screenshot");
    }
});

        binding.txtAccNumber.setOnClickListener(this);
        binding.txtAccHolderName.setOnClickListener(this);
        binding.txtBankName.setOnClickListener(this);
        binding.txtIfscCode.setOnClickListener(this);
        binding.txtBranchAdd.setOnClickListener(this);
        binding.txtAccType.setOnClickListener(this);
        binding.txtAcc.setOnClickListener(this);
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

    @Override
    public void onResume() {
        super.onResume();
        activity.toolbar_title.setText(Payment_Fragment.this.getTag());

    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            switch(requestCode) {
                case CAMERA:

                    Uri selectedImage = data.getData();



                    break;
                case GALLERY:

                    Uri selectedImage1 = data.getData();

                    break;

        }
    }*/

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

    @Override
    public void onClick(View view) {

        switch (view.getId()){

               case R.id.txt_acc :
               textCopy(binding.txtAcc.getText().toString());
               break;

                case R.id.txt_acc_number :
                textCopy(binding.txtAccNumber.getText().toString());
                break;

                case R.id.txt_acc_holder_name :
                textCopy(binding.txtAccHolderName.getText().toString());
                break;

                case R.id.txt_bank_name :
                textCopy(binding.txtBankName.getText().toString());
                break;

                case R.id.txt_ifsc_code :
                textCopy(binding.txtIfscCode.getText().toString());
                break;

                case R.id.txt_branch_add :
                textCopy(binding.txtBranchAdd.getText().toString());
                break;

                case R.id.txt_acc_type :
                textCopy(binding.txtAccType.getText().toString());
                break;
        }

    }

    public void textCopy(String text){

        utils.copyText(activity,text);
        utils.showToast(activity,"Copied");
    }
}
