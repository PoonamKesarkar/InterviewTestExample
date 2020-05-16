package com.example.maponnmachinetest.viewmodel;

import android.util.Log;

import com.example.maponnmachinetest.model.ProductListModel;
import com.example.maponnmachinetest.retrofit.ProductListService;
import com.example.maponnmachinetest.retrofit.RetrofitUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel  extends ViewModel {
    String TAG = this.getClass().getName();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<ProductListModel> productMutableLiveData = new MutableLiveData<>();
    Call<ProductListModel> apiCalUser;
    private ProductListService productListService;

    public LiveData<Boolean> error() {
        Log.d(TAG, "return isError  : " + isError);
        return isError;
    }

    public LiveData<ProductListModel> homeDataReceived() {
        Log.d(TAG, "return onDataReceive  : " + productMutableLiveData);
        return productMutableLiveData;
    }

    public LiveData<Boolean> isLoading() {
        Log.d(TAG, "return isLoading  : " + isLoading);
        return isLoading;
    }

    public void getHomeData(){
        productListService = RetrofitUtil.getInstance().getRetrofit().create(ProductListService.class);
        isLoading.setValue(true);
        apiCalUser = productListService.getAllProductList(0,6);
        Log.d(TAG, "Start getHome call 2 : ");

        apiCalUser.enqueue(new Callback<ProductListModel>() {
            @Override
            public void onResponse(Call<ProductListModel> call, Response<ProductListModel> response) {
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
            public void onFailure(Call<ProductListModel> call, Throwable t) {
                Log.d(TAG, " getHome call onFailure : " + t.toString());
                isError.setValue(true);
                isLoading.setValue(false);
                apiCalUser = null;
            }

        });

    }
}
