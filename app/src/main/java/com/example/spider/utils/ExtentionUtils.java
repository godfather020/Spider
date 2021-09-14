package com.example.spider.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.inter_face.DatePickerListener;
import com.example.spider.inter_face.SearchviewListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ExtentionUtils {

    public void showToast(Context context,String Msg){
        Toast.makeText(context,Msg,Toast.LENGTH_LONG).show();

    }

    public  void loadFragment( FragmentManager fragmentManager, Fragment fragment
            , int containerId, boolean shouldRemovePreviousFragments, CharSequence currentTitle, Bundle arg){


        FragmentTransaction transaction=fragmentManager.beginTransaction();

        if(shouldRemovePreviousFragments){
            if(fragmentManager.getBackStackEntryCount()>0){

                for ( int i=0;i<=fragmentManager.getBackStackEntryCount();i++) {

                    fragmentManager.popBackStackImmediate();
                }
            }
        }else transaction.addToBackStack(currentTitle.toString());

        if(arg!=null) {

            Log.d("bundle==",arg.toString());
            fragment.setArguments(arg);
        }
        transaction.replace(containerId,fragment,currentTitle.toString()).commit();
    }

public RequestBody toRequestBody(String jsonBody){

        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonBody);


    }

    public void setCircleImage(String url, CircleImageView circleImageView, Drawable placeHolde){

        Picasso.get().load(url).placeholder(placeHolde).into(circleImageView);
    }

    public void setImage(String url, ImageView imageView, Drawable placeHolde){

        Picasso.get().load(url).placeholder(placeHolde).into(imageView);
    }

    public void copyText(Context context ,String text){

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }

    public void setSearchView(Activity activity, SearchView searchView, SearchviewListener searchviewListener){

        SearchManager searchManager=(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);

         searchView.setSearchableInfo(searchManager
                .getSearchableInfo(activity.getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchviewListener.getSeachView(searchView);


    }

    public void setSpinnerAdapter(Activity activity, Spinner spinner, List<String> plantsList, OnItemSelectedListener onItemSelectedListener){

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                activity,R.layout.spinner_item,R.id.txt_spinner,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
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
                    tv.setTextColor(activity.getResources().getColor(R.color.app_theme_color));
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
       spinner.setAdapter(spinnerArrayAdapter);


        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                onItemSelectedListener.onItemSelected(parent,view,position,id);
                if(position > 0){
                    // Notify the selected item text

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                onItemSelectedListener.onNothingSelected(parent);
            }
        });
    }

    public void showDatePicker(Activity activity, DatePickerListener datePickerListener){
        final Date[] date = {null};
        Calendar mcurrentDate = Calendar.getInstance();
        final int[] mYear = {mcurrentDate.get(Calendar.YEAR)};
        final int[] mMonth = {mcurrentDate.get(Calendar.MONTH)};
        final int[] mDay = {mcurrentDate.get(Calendar.DAY_OF_MONTH)};

        DatePickerDialog mDatePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
//                String myFormat = "dd MMM yy"; //Change as you need
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
//                binding.edtFromDate.setText(sdf.format(myCalendar.getTime()));
//                date[0] =myCalendar.getTime();

                SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yy");
                String d=sdf1.format(myCalendar.getTime());
                Date date= null;
                try {
                    date = sdf1.parse(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                datePickerListener.getDate(date);

                mDay[0] = selectedday;
                mMonth[0] = selectedmonth;
                mYear[0] = selectedyear;
            }
        }, mYear[0], mMonth[0], mDay[0]);
        //mDatePicker.setTitle("Select date");
       mDatePicker.show();

    }
//    public void setSpinnerAdapter(MainActivity activity, AppCompatSpinner spinnerConcern, List<String> plantsList, OnItemSelectedListener onItemSelectedListener) {
//    }
}
