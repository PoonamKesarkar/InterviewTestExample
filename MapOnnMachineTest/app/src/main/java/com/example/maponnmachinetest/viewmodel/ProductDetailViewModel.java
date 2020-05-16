package com.example.maponnmachinetest.viewmodel;

import android.util.Log;

import com.example.maponnmachinetest.model.ProductDetailModel;
import com.example.maponnmachinetest.retrofit.ProductDetailService;
import com.example.maponnmachinetest.retrofit.ProductListService;
import com.example.maponnmachinetest.retrofit.RetrofitUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailViewModel  extends ViewModel {
    String TAG = this.getClass().getName();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<ProductDetailModel> productMutableLiveData = new MutableLiveData<>();
    Call<ProductDetailModel> apiCalUser;
    private ProductDetailService productDetailService;

    public LiveData<Boolean> error() {
        Log.d(TAG, "return isError  : " + isError);
        return isError;
    }

    public LiveData<ProductDetailModel> homeDataReceived() {
        Log.d(TAG, "return onDataReceive  : " + productMutableLiveData);
        return productMutableLiveData;
    }

    public LiveData<Boolean> isLoading() {
        Log.d(TAG, "return isLoading  : " + isLoading);
        return isLoading;
    }

    public void getProductDetail(int productId){
        productDetailService = RetrofitUtil.getInstance().getRetrofit().create(ProductDetailService.class);
        isLoading.setValue(true);
        apiCalUser = productDetailService.getProductDetails(productId);
        Log.d(TAG, "Start getHome call 2 : ");

        apiCalUser.enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                Log.d(TAG, " getHome call onResponse 0 : ");

                if (response.body() != null)
                    Log.d(TAG, " getHomeByID call onResponse 1 : " + response.body().toString());

                if (response.code() == 200 && response.body() != null) {
                    Log.d(TAG, " getHome call onResponse 2 : " + response.body().toString());

                    isError.setValue(false);
                    productMutableLiveData.setValue(response.body());

                } else {
                    isError.setValue(true);
                    productMutableLiveData.setValue(null);
                }
                isLoading.setValue(false);
                apiCalUser = null;

            }

            @Override
            public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }

        });

    }
}
