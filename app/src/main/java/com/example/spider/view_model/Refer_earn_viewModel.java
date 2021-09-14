package com.example.spider.view_model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spider.MainActivity;
import com.example.spider.api.RetrofitInstance;
import com.example.spider.api.RetrofitInterface;
import com.example.spider.databinding.ReferEarnFragmentBinding;
import com.example.spider.databinding.RegistrationActivityBinding;
import com.example.spider.model.DATA;
import com.example.spider.model.Organisation_Pojo;
import com.example.spider.model.Referalcode;
import com.example.spider.model.Response;
import com.example.spider.model.UserDetail;
import com.example.spider.ui.activity.Registration_Activity;
import com.example.spider.utils.AppSharedPref;
import com.example.spider.utils.Constant;
import com.example.spider.utils.ExtentionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Refer_earn_viewModel extends ViewModel {

    private static final String TAG = "Refer_earn_viewModel";
    private MutableLiveData<List<UserDetail>> userDetail;
    private MutableLiveData<List<Referalcode>> checkReferalCode=new MutableLiveData<>();
   private  List <UserDetail> detail=new ArrayList<>();
   MainActivity activity;
   ReferEarnFragmentBinding binding;
   ExtentionUtils extentionUtils=new ExtentionUtils();
    RetrofitInterface retrofitInterface= RetrofitInstance.getmRetrofitInstance().create(RetrofitInterface.class);


    public LiveData<List<UserDetail>> getContactList(MainActivity activity,ReferEarnFragmentBinding binding){

        this.activity=activity;
        userDetail = new MutableLiveData<List<UserDetail>>();
        this.binding=binding;



//            if (userDetail == null) {

                loadUsers();
//            }
//            detail.clear();
//            detail.add(new UserDetail("Savan","8839891738"));
//            userDetail.setValue(detail);



       return userDetail;
    }

    public LiveData<List<Referalcode>> checkReferalCode(MainActivity activity, ReferEarnFragmentBinding binding) {

        this.activity = activity;
        this.binding = binding;
        binding.progressBar.setVisibility(View.VISIBLE);




        DATA data = new DATA();
        data.setUid(new AppSharedPref(activity).getUserData().getId());



        Organisation_Pojo organisation_pojo = new Organisation_Pojo(Constant.FUNC_REFERAL_CODE, data);
        String jsonBody = new Gson().toJson(organisation_pojo, Organisation_Pojo.class);
        Log.d("ReferalCode", jsonBody);
        RequestBody param = extentionUtils.toRequestBody(jsonBody);
        runapi_checkReferCode(param);



        return checkReferalCode;
    }


    public void  loadUsers(){
detail.clear();
              ContentResolver cr = activity.getContentResolver();

//RowContacts for filter Account Types
                Cursor contactCursor = cr.query(
                        ContactsContract.RawContacts.CONTENT_URI,
                        new String[]{ContactsContract.RawContacts._ID,
                                ContactsContract.RawContacts.CONTACT_ID},
                        ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                        new String[]{"com.whatsapp"},
                        null);

//ArrayList for Store Whatsapp Contact
                ArrayList<String> myWhatsappContacts = new ArrayList<>();

                if (contactCursor != null) {
                    if (contactCursor.getCount() > 0) {
                        if (contactCursor.moveToFirst()) {
                            do {
                                //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                                String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                                if (whatsappContactId != null) {
                                    //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID
                                    Cursor whatsAppContactCursor = cr.query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                                                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                            new String[]{whatsappContactId}, null);

                                    if (whatsAppContactCursor != null) {
                                        whatsAppContactCursor.moveToFirst();
                                        String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                                        String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                        String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                        whatsAppContactCursor.close();

                                        //Add Number to ArrayList
                                        myWhatsappContacts.add(number);
//
//                                        Log.d(TAG, " WhatsApp contact id  :  " + id);
//                                       Log.d(TAG, " WhatsApp contact name :  " + name);
//                                        Log.d(TAG, " WhatsApp contact number :  " + number);

                                        detail.add(new UserDetail(name,number));

                                    }
                                }
                            } while (contactCursor.moveToNext());
                            contactCursor.close();
                        }
                    }
                }

        userDetail.setValue(detail);
                Log.d(TAG, " WhatsApp contact size :  " + detail.size());
    }

    public void  runapi_checkReferCode(RequestBody param){

        Call<Response> call =retrofitInterface.signUp(param);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<com.example.spider.model.Response> call, retrofit2.Response<Response> response) {

                if(response.body().getMsgCode()==1){

                    checkReferalCode.setValue(response.body().getReferalcode());
                }else {
                    binding.progressBar.setVisibility(View.GONE);

                    extentionUtils.showToast(activity,response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<com.example.spider.model.Response> call, Throwable t) {
//                Log.e("loginResponse","error=== "+t.getMessage().toString());
                binding.progressBar.setVisibility(View.GONE);
                extentionUtils.showToast(activity,t.getMessage());

            }
        });
    }

}
