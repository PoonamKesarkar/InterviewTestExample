package com.example.maponnmachinetest.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maponnmachinetest.R;
import com.example.maponnmachinetest.databinding.ActivityProductDetailBinding;
import com.example.maponnmachinetest.model.Data;
import com.example.maponnmachinetest.model.ProductDetailModel;
import com.example.maponnmachinetest.model.ProductListModel;
import com.example.maponnmachinetest.util.CommonMethods;
import com.example.maponnmachinetest.viewmodel.ProductDetailViewModel;
import com.example.maponnmachinetest.viewmodel.ProductViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ProductDetailActivity extends AppCompatActivity {
    private int product_id;
    private ActivityProductDetailBinding activityMainBinding;
    ProductDetailViewModel modelProduct;
    String TAG = this.getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_product_detail);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        setSupportActionBar(activityMainBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        Intent intent = getIntent();
        product_id  = intent.getIntExtra("id",0);
        activityMainBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loadDetailApi();
        init ();
    }

    private void loadDetailApi() {
        modelProduct = new ViewModelProvider(ProductDetailActivity.this).get(ProductDetailViewModel.class);
        modelProduct.getProductDetail(product_id);
        final ProgressDialog pd = new ProgressDialog(this);
        modelProduct.error().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "error aBoolean " + aBoolean);
                if (aBoolean)
                    Toast.makeText(ProductDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();


            }
        });
        modelProduct.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "isLoading aBoolean " + aBoolean);
                if (aBoolean) {
                    pd.setMessage("Loading.....");
                    pd.show();
                } else {
                    pd.dismiss();
                }
            }
        });
        modelProduct.homeDataReceived().observe(this, new Observer<ProductDetailModel>() {
            @Override
            public void onChanged(ProductDetailModel hometBean) {

                    if (new CommonMethods().isInternetConnection(ProductDetailActivity.this)) {
                        //Used glide for loading image

                        Glide.with(ProductDetailActivity.this)
                                .load(hometBean.getData().getImage())
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(activityMainBinding.expandedImage);

                    } else {
                        activityMainBinding.expandedImage.setBackgroundResource(R.drawable.ic_launcher_background);
                    }
                    activityMainBinding.setProductDetail(hometBean.getData());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    activityMainBinding.txtProductDesc.setText(Html.fromHtml(hometBean.getData().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    activityMainBinding.txtProductDesc.setText(Html.fromHtml(hometBean.getData().getDescription()));
                }

            }
        });
    }

    private void init() {

    }
}
