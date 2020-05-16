package com.example.maponnmachinetest.retrofit;

import com.example.maponnmachinetest.model.ProductListModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductDetailService {
    @GET("product")
    Call<ProductListModel> getProductDetails();
}
