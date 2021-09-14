package com.example.spider.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.adapter.Acc_Detail_Adapter;
import com.example.spider.adapter.Payment_Detail_Adapter;
import com.example.spider.databinding.AddAccDialogFragmentBinding;
import com.example.spider.databinding.PaymentDetailFragmentBinding;
import com.example.spider.model.Accountdetail;
import com.example.spider.model.Parcable_Property;
import com.example.spider.model.Pymentmethod;
import com.example.spider.view_model.Add_Acc_Dialog_viewModel;

public class Add_Acc_Dialog_Fragment extends AppCompatDialogFragment {

    public AddAccDialogFragmentBinding binding;
    Add_Acc_Dialog_viewModel viewModel;
    MainActivity activity;
    String TAG = "Add_Acc_Dialog_Fragment";
    Pymentmethod pymentmethod;
    Accountdetail accountdetail;
    int payment_item_position,acc_item_position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        activity = (MainActivity) getActivity();


        Bundle bundle=getArguments();
        if(bundle!=null){
            pymentmethod=(Pymentmethod) bundle.getSerializable("payment_method");
            accountdetail=(Accountdetail) bundle.getSerializable("edit_acc");
            payment_item_position=bundle.getInt("payment_item_postion");
            acc_item_position= bundle.getInt("acc_item_postion");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.add_acc_dialog_fragment, container, false);
        viewModel = new ViewModelProvider(activity).get(Add_Acc_Dialog_viewModel.class);
        View view = binding.getRoot();
        init();
        return view;

    }

    public void init() {


        if(pymentmethod!=null) {
            if (pymentmethod.getId().equals("4")) {
                binding.clBank.setVisibility(View.VISIBLE);
//            binding.txtBankSubHeader.setText("Add Your "+pymentmethod.getName());


            } else {
                binding.txtSubHeader.setText("Add Your " + pymentmethod.getName() + " Number");

                binding.textInputLayout7.setHint(pymentmethod.getName() + " Number");
//            binding.edtUpiNo.setHint(pymentmethod.getName()+" Number");
                binding.clUpi.setVisibility(View.VISIBLE);
                binding.btnAddBank.setVisibility(View.VISIBLE);
            }
        }else if(accountdetail!=null){
            if(accountdetail.getPaymentmethodid().equals("4")) {
                binding.clBank.setVisibility(View.VISIBLE);
                binding.edtAccNO.setText(accountdetail.getAccountNo());
                binding.edtAccHolderName.setText(accountdetail.getAccountholder());
                binding.edtIfsc.setText(accountdetail.getIfsc());
                binding.btnAddBank.setText(activity.getResources().getString(R.string.edit));
                if (accountdetail.getBankType().equals(activity.getResources().getString(R.string.sav_acc))) {
                    binding.rbtnSavingAcc.setChecked(true);
                } else
                    binding.rbtnCurrentAcc.setChecked(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBar.setVisibility(View.VISIBLE);

                        viewModel.getBankDetail(activity, binding, binding.edtIfsc.getText().toString());
                    }
                }, 500);

            } else {
                binding.txtSubHeader.setText("Add Your " + accountdetail.getPaymentmethod() + " Number");

                binding.textInputLayout7.setHint(accountdetail.getPaymentmethod() + " Number");
//            binding.edtUpiNo.setHint(pymentmethod.getName()+" Number");
                binding.clUpi.setVisibility(View.VISIBLE);
                binding.btnAddBank.setVisibility(View.VISIBLE);
                binding.edtName.setText(accountdetail.getDisplayname());
                binding.edtUpiNo.setText(accountdetail.getNumber());
                binding.btnAddBank.setText(activity.getResources().getString(R.string.edit));
            }
        }
        binding.edtIfsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Log.d(TAG,"before");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() >= 11) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setVisibility(View.VISIBLE);

                            viewModel.getBankDetail(activity, binding, binding.edtIfsc.getText().toString());
                        }
                    }, 500);


                }

                Log.d(TAG,"onTExt");
            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d(TAG,"after");


            }
        });

        binding.btnAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pymentmethod != null) {
                    if (pymentmethod.getId().equals("4")) {
                        viewModel.addtBankDetail(activity, binding).observe(activity, userDetails -> {

                            if (userDetails != null) {

                                if (userDetails.equals("1")) {

                                    Bundle bundle=new Bundle();
                                    bundle.putString("add","done");
                                    bundle.putInt("payment_item_position",payment_item_position);
                                    bundle.putInt("acc_item_position",acc_item_position);
                                    activity.getSupportFragmentManager().setFragmentResult(new Paymet_Detail_Fragment().REQUEST_KEY,bundle);
                                    dismiss();
                                }
                            }

                        });
                    } else {

                        viewModel.addtUpiDetail(activity, binding, true, pymentmethod.getId()).observe(activity, userDetails -> {

                            if (userDetails != null) {

                                if (userDetails.equals("1")) {
                                    Bundle bundle=new Bundle();
                                    bundle.putString("add","done");
                                    bundle.putInt("payment_item_position",payment_item_position);
                                    bundle.putInt("acc_item_position",acc_item_position);
                                    activity.getSupportFragmentManager().setFragmentResult(new Paymet_Detail_Fragment().REQUEST_KEY,bundle);
                                    dismiss();
                                }
                            }

                        });
                    }
                }


                    if (accountdetail != null) {
                        if (accountdetail.getPaymentmethodid().equals("4")) {
                            viewModel.updateBankDetail(activity, binding,accountdetail.getId()).observe(activity, userDetails -> {

                                if (userDetails != null) {

                                    if (userDetails.equals("1")) {

//                                        new Payment_Detail_Adapter().notifyDataSetChanged();
//                                        new Acc_Detail_Adapter().notifyDataSetChanged();

                                        Bundle bundle=new Bundle();
                                        bundle.putString("update","done");
                                        bundle.putInt("payment_item_position",payment_item_position);
                                        bundle.putInt("acc_item_position",acc_item_position);

                                      getParentFragmentManager().setFragmentResult(new Paymet_Detail_Fragment().REQUEST_KEY,bundle);

                                        Add_Acc_Dialog_Fragment.this.dismiss();
                                    }
                                }

                            });
                        } else {

                            viewModel.updateUpiDetail(activity, binding, true,accountdetail.getPaymentmethodid(),accountdetail.getId()).observe(activity, userDetails -> {

                                if (userDetails != null) {

                                    if (userDetails.equals("1")) {


                                       /* Parcable_Property parcable_property=new Parcable_Property();

                                        parcable_property.setAcc_item_positon(acc_item_position);
                                        parcable_property.setPayment_item_positon(payment_item_position);
                                        parcable_property.setType("update");
                                        Bundle bundle=new Bundle();
                                       bundle.putParcelable("update",parcable_property);*/
                                        Bundle bundle=new Bundle();
                                        bundle.putString("update","done");
                                        bundle.putInt("acc_item_position",acc_item_position);
                                        bundle.putInt("payment_item_position",payment_item_position);

                                        Log.d("result","set result dialog");
                                        getParentFragmentManager().setFragmentResult(new Paymet_Detail_Fragment().REQUEST_KEY,bundle);

                                        Add_Acc_Dialog_Fragment.this.dismiss();
                                    }
                                }

                            });
                        }
                    }
                }

        });


   }

}
