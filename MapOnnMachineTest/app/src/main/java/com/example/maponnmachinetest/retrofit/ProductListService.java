package com.example.maponnmachinetest.retrofit;

import com.example.maponnmachinetest.model.ProductListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ProductListService {
    @Headers({
            "X-OC-MERCHANT-ID: dlMDJT8UNnV8OFi8hk5OqCEUgYiAU6wsddud6M3iCn2RB1MmeaeDQt1F2axybRicFTXfrK5NO8ARBXCINPaE HuaPvn8P5ZiyBgAtsxxHJNpr28URCrTanE564lxUyeCT5r0hg1ITr0GOKDuvIAbwWGc8c5b7K0vcKUcirWi02qUY SkEfVhOv05EwCST8DOZ8r5ZMcxOsvcZtnXrZ42YYBy78xGwzlltGLLodTkqViEz2Sz3RAJaPOAC4iRjNCMk7"
    })
    @GET("src?route=api/v2_0_5/products/bestsellers")
    Call<ProductListModel> getAllProductList(@Query("offset") int offset,@Query("limit") int limit);
}
