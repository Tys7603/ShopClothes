package com.example.shopclothes.view.activity.product.productNew;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.shopclothes.constant.AppConstants;
import com.example.shopclothes.network.ApiService;
import com.example.shopclothes.view.activity.MainActivity;
import com.example.shopclothes.view.activity.account.login.LoginActivity;
import com.example.shopclothes.view.activity.product.ResponseProduct;
import com.example.shopclothes.view.fragment.homeFragment.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductNewPresenter implements ProductNewContract.Presenter {

    private final ProductNewContract.View view;

    public ProductNewPresenter(ProductNewContract.View view) {
        this.view = view;
    }

    @Override
    public void getListProductNew() {
        ApiService.API_SERVICE.readProductNew().enqueue(new Callback<ResponseProduct>() {
            @Override
            public void onResponse(@NonNull Call<ResponseProduct> call,@NonNull Response<ResponseProduct> response) {
                assert response.body() != null;
                if (AppConstants.SUCCESS.equals(response.body().getStatus())) {
                    view.onList(response.body().getProductList());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseProduct> call,@NonNull Throwable t) {

            }
        });
    }

    @Override
    public void nextActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
