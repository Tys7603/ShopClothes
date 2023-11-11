package com.example.shopclothes.view.fragment.billFragment.chlidBill;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopclothes.R;
import com.example.shopclothes.adapter.AdapterBill;
import com.example.shopclothes.adapter.MyBottomSheetBill;
import com.example.shopclothes.adapter.MyBottomSheetCart;
import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.databinding.FragmentChildBillBinding;
import com.example.shopclothes.model.Bill;
import com.example.shopclothes.view.activity.billDetail.BillDetailActivity;
import com.example.shopclothes.view.activity.product.detailProduct.DetailProductPresenter;
import com.example.shopclothes.view.fragment.billFragment.BillContract;
import com.example.shopclothes.view.fragment.billFragment.BillPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class WaitPickupFragment extends Fragment implements BillContract.View {
    private FragmentChildBillBinding mBinding;
    private BillContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BillPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentChildBillBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressDialog = ProgressDialog.show(getContext(), "", AppConstants.LOADING);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        mPresenter.readListBill(user.getUid(), AppConstants.DELIVERY_STATUS_WAIT_PICKUP);
    }

    @Override
    public void onListBill(List<Bill> billList) {
        if (billList.size() != 0){
            Collections.reverse(billList);
            mBinding.ivEmptyBill.setVisibility(View.GONE);
            mBinding.tvBill.setVisibility(View.GONE);
        }else {
            mBinding.ivEmptyBill.setVisibility(View.VISIBLE);
            mBinding.tvBill.setVisibility(View.VISIBLE);
        }
        MyBottomSheetBill myBottomSheetBill = new MyBottomSheetBill();
        AdapterBill adapterBill = new AdapterBill(billList, 1,  myBottomSheetBill, null, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mBinding.rcvBill.setLayoutManager(layoutManager);
        mBinding.rcvBill.setAdapter(adapterBill);
        mProgressDialog.dismiss();
    }

    @Override
    public void nextScreenDetailBill(String id) {
        mPresenter.nextActivity(getContext(), BillDetailActivity.class, id);
    }


}